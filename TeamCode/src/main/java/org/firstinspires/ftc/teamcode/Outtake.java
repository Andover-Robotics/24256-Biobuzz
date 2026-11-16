package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.PID;

public class Outtake {
    public final OpMode opMode;
    public double xCoordinateRobot;
    public double yCoordinateRobot;
    public double robotHeading;

    // ALL OF THESE BELOW ARE PLACEHOLDERS
    public double xCoordinateRedCellOne = 13;
    public double yCoordinateRedCellOne = -13;
    public double xCoordinateRedCellTwo = -13;
    public double yCoordinateRedCellTwo = -13;
    public double xCoordinateBlueCellOne = 13;
    public double yCoordinateBlueCellOne = 13;
    public double xCoordinateBlueCellTwo = -13;
    public double yCoordinateBlueCellTwo = 13;
    // PLACEHOLDERS DONE

    public final CRServo rotationServo;
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;

    public PID rotationController = new PID();
    public PID ballMotorController = new PID();
    public PID supportMotorController = new PID();

    public double angle = 0;
    public double targetAngle = 0;

    // constants
    public final double ROTATIONS_PER_SECOND = 516;
    public final double MAX_ANGLE = 175;
    public final double MIN_ANGLE = -175;
    public final double FORWARD_MOV = 50;
    public final double STRAFE_MOV = 50;
    public final double ROTATE_MOV = 50;
    // these values are temporary for now, subject to change

    public boolean isRunning;
    public boolean isRotating;

    GoBildaPinpointDriver pinpoint;

    public Outtake(OpMode opMode) {
        this.opMode = opMode;
        this.rotationServo = opMode.hardwareMap.get(CRServo.class, "rotationServo");
        this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");

        pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, xCoordinateRobot, yCoordinateRobot, AngleUnit.DEGREES, robotHeading));

        isRunning = false;
        isRotating = false;
    }

    public void updateAngle(double power, double deltaTime) {
        angle += power * ROTATIONS_PER_SECOND * deltaTime;
        if (angle > MAX_ANGLE) {
            stop();
            rotateTo(0, power);
        } else if (angle < MIN_ANGLE) {
            stop();
            rotateTo(0, -power);
        }
    }

    public void lock(double xCoordinateTarget, double yCoordinateTarget, double power) {
        pinpoint.update();
        Pose2D pose2D = pinpoint.getPosition();
        xCoordinateRobot = pose2D.getX(DistanceUnit.INCH);
        yCoordinateRobot = pose2D.getY(DistanceUnit.INCH);
        robotHeading = pose2D.getHeading(AngleUnit.DEGREES);
        double fieldAngle = Math.toDegrees(Math.atan2(yCoordinateTarget - yCoordinateRobot, xCoordinateTarget - xCoordinateRobot));
        double outtakeAngle = fieldAngle - robotHeading;
        while (outtakeAngle > 180) outtakeAngle -= 360;
        while (outtakeAngle < -180) outtakeAngle += 360;
        rotateTo(outtakeAngle, power);
    }

    public void rotateTo(double target, double power) {
        this.targetAngle = target;
        double tolerance = 0.1;
        if (Math.abs(target - angle) > tolerance) {
            rotate(target);
        } else {
            stopRotation();
        }
    }

    public void rotate(double target) {
        isRotating = true;
        double power = rotationController.pid(target, angle);
        rotationServo.setPower(power);
    }

    public void stopRotation() {
        isRotating = false;
        rotationServo.setPower(0);
        rotationController.reset();
    }

    public void start(double target) {
        isRunning = true;
        double currentMainMotor = ballMotor.getVelocity();
        double currentSupportMotor = ballMotorSupport.getVelocity();
        double powerMain = ballMotorController.pid(target, currentMainMotor);
        double powerSupport = supportMotorController.pid(target, currentSupportMotor);
        ballMotor.setPower(powerMain);
        ballMotorSupport.setPower(powerSupport);
    }

    public void stop() {
        isRunning = false;
        ballMotor.setPower(0);
        ballMotorSupport.setPower(0);
        supportMotorController.reset();
        ballMotorController.reset();
    }

    public void toggle(double target) {
        if (isRunning) {
            stop();
        } else {
            start(target);
        }
    }

    public void toggleRotation(double target) {
        if (isRotating) {
            stopRotation();
        } else {
            rotate(target);
        }
    }
}
