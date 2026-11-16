package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.PID;

public class Outtake {
    public final OpMode opMode;
    public Coordinate robot;
    public double robotHeading;
    // Note: These constants may be moved to a different file later
    public Coordinate[] cellCoordinates = new Coordinate[4];
    cellCoordinates[0] = new Coordinate(13,-13);
    cellCoordinates[1] = new Coordinate(-13,-13);
    cellCoordinates[2] = new Coordinate(13,13);
    cellCoordinates[3] = new Coordinate(-13,13);

    // this will change to a regular servo
    public final CRServo rotationServo;
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;
    public PID rotationController = new PID();
    public PID ballMotorController = new PID();
    public PID supportMotorController = new PID();
    public double angle = 0;
    // constants; subject to change
    public final double ROTATIONS_PER_SECOND = 516;
    public final double MAX_ANGLE = 320;
    public final double MIN_ANGLE = -320;
    public final double FORWARD_MOV = 50;
    public final double STRAFE_MOV = 50;
    public final double ROTATE_MOV = 50;

    public boolean isRunning;
    public boolean isRotating;

    GoBildaPinpointDriver pinpoint;

    public Outtake(OpMode opMode){
       this.opMode = opMode;
       this.rotationServo = opMode.hardwareMap.get(CRServo.class, "rotationServo");
       this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
       this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");
       pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
       pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, robot.x, robot.y, AngleUnit.DEGREES, robotHeading));

       isRunning = false;
       isRotating = false;
    }

    public void updateAngle(double power, double deltaTime) throws InterruptedException {
      // this will need to run in the Bot.java loop or whatever to continously update the angle while running
      // will change this to work with regular servo in next update, or will be deleted
      angle += power * ROTATIONS_PER_SECOND * deltaTime;
      if (angle > MAX_ANGLE){
          stop();
          rotateTo(0,power);
      } else if (angle < MIN_ANGLE){
          stop();
          rotateTo(0, -power);
      }
    }

    public void lock(Coordinate target, double power) throws InterruptedException {
        pinpoint.update();
        Pose2D pose2D = pinpoint.getPosition();
        robot.x = pose2D.getX(DistanceUnit.INCH);
        robot.y = pose2D.getY(DistanceUnit.INCH);
        double targetAngle = (Math.atan2(-robot.y - target.y, -robot.x - target.x) * 180)/Math.PI;
        double outtakeAngle = targetAngle;
        rotateTo(outtakeAngle, power);
    }


    public void rotateTo(double target, double power) throws InterruptedException {
        rotate(power);
        double tolerance = 0.1;
        while (Math.abs(target - angle) > tolerance) {
            Thread.sleep(10);
        }
        stopRotation();
    }

    public void rotate(double target){
        isRotating = true;
        double current = rotationServo.getPower();
        double power = rotationController.pid(target, current, false);
        rotationServo.setPower(power);
    }

    public void stopRotation(){
        isRotating = false;
        rotationServo.setPower(0);
        rotationController.reset();
    }

    public void start(double target){
        isRunning = true;
        double currentMainMotor = ballMotor.getVelocity();
        double currentSupportMotor = ballMotorSupport.getVelocity();
        double powerMain = ballMotorController.pid(target, currentMainMotor, true);
        double powerSupport = supportMotorController.pid(target, currentSupportMotor, true);
        ballMotor.setPower(powerMain);
        ballMotorSupport.setPower(powerSupport);
    }

    public void stop(){
        isRunning = false;
        ballMotor.setPower(0);
        ballMotorSupport.setPower(0);
        supportMotorController.reset();
        ballMotorController.reset();
    }

    public void toggle(double target){
        if (isRunning){
            stop();
        } else{
            start(target);
        }
    }

    public void toggleRotation(double target){
        if (isRotating){
            stopRotation();
        } else{
            rotate(target);
        }
    }
}
