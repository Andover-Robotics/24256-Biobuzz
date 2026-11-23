package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
public class Outtake {
    public final OpMode opMode;

    private final Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    public Pose[] cellCoordinates = new Pose[]{
            p.of(85, 59, 0),
            p.of(59, 59, 0),
            p.of(85, 85, 0),
            p.of(59, 85, 0)
    };
    public final double maxAngle = 180;
    public final double minAngle = -180;
    public final double servoRange = 360;
    public final double centerPosition = 0.5;
    public final Servo rotationServo;
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;
    public double angle;
    public boolean isRunning;
    public boolean isRotating;

    public Outtake(OpMode opMode, Follower follower){
        this.opMode = opMode;
        this.follower = follower;
        this.rotationServo = opMode.hardwareMap.get(Servo.class, "rotationServo");
        this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");
        ballMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ballMotorSupport.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.angle = 0;
        isRunning = false;
        isRotating = false;
    }
    public void lock(Pose target){
        Pose current = follower.pose();
        double targetAngle = Math.toDegrees(Math.atan2(
                target.y() - current.y(),
                target.x() - current.x()
        ));
        rotateTo(targetAngle - Math.toDegrees(current.heading()));
    }
    public void rotateTo(double target) {
        angle = target;
        isRotating = false;
        while (this.angle > 180) this.angle -= 360;
        while (this.angle < -180) this.angle += 360;
        this.angle = Math.max(minAngle, Math.min(maxAngle, this.angle));
        rotationServo.setPosition(centerPosition + this.angle / servoRange);
    }
    public void start(double target){
        isRunning = true;
        ballMotor.setVelocity(target);
        ballMotorSupport.setVelocity(target);
    }

    public void stop(){
        isRunning = false;
        ballMotor.setPower(0);
        ballMotorSupport.setPower(0);
    }

    public void toggle(double target){
        if (isRunning){
            stop();
        } else{
            start(target);
        }
    }
}