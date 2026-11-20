package org.firstinspires.ftc.teamcode.subsystems;
import org.firstinspires.ftc.teamcode.Coordinate;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.PID;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;


public class Outtake {
    public final OpMode opMode;
    public Coordinate robot;
    public double robotHeading;

    private final Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    public Pose[] cellCoordinates = new Pose[]{
            p.of(13, -13, 0),
            p.of(-13, -13, 0),
            p.of(13, 13, 0),
            p.of(-13, 13, 0)
    };
    public final double MAX_ANGLE = 180;
    public final double MIN_ANGLE = -180;

    public final Servo rotationServo;
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;
    public PID rotationController = new PID();
    public PID ballMotorController = new PID();
    public PID supportMotorController = new PID();
    public double angle;
    public boolean isRunning;
    public boolean isRotating;

    GoBildaPinpointDriver pinpoint;

    public Outtake(OpMode opMode, Follower follower){
        this.opMode = opMode;
        this.follower = follower;
        this.rotationServo = opMode.hardwareMap.get(Servo.class, "rotationServo");
        this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");
        this.angle = rotationServo.getPosition();


        isRunning = false;
        isRotating = false;
    }

    public void updateAngle(double angle, double deltaTime) throws InterruptedException {
      angle = rotationServo.getPosition();
      if (angle > MAX_ANGLE){
          stop();
          rotateTo(0,angle);
      } else if (angle < MIN_ANGLE){
          stop();
          rotateTo(0, -angle);
      }
    }

    public void lock(Pose target, double angle) throws InterruptedException {
        Pose current = follower.getPose();
        double targetAngle = Math.toDegrees(Math.atan2(
                -current.getY() - target.getY(),
                -current.getX() - target.getX()
        ));
        double outtakeAngle = targetAngle;
        rotateTo(outtakeAngle, angle);
    }


    public void rotateTo(double target, double angle) throws InterruptedException {
        rotate(angle);
        double tolerance = 0.1;
        while (Math.abs(target - angle) > tolerance) {
            Thread.sleep(10);
        }
        stopRotation();
    }

    public void rotate(double target){
        isRotating = true;
        double current = rotationServo.getPosition();
        double angle = rotationController.pid(target, current, false);
        rotationServo.setPosition(angle);
    }

    public void stopRotation(){
        isRotating = false;
        rotationServo.setPosition(0);
        rotationController.reset();
    }

    public void start(double target){
        isRunning = true;
        double currentMainMotor = ballMotor.getVelocity();
        double currentSupportMotor = ballMotorSupport.getVelocity();
        double positionMain = ballMotorController.pid(target, currentMainMotor, true);
        double positionSupport = supportMotorController.pid(target, currentSupportMotor, true);
        ballMotor.setPower(positionMain);
        ballMotorSupport.setPower(positionSupport);
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
