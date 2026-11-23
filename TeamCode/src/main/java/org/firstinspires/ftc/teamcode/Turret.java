package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
public class Turret {
    public final OpMode opMode;

    private final Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    public final double maxAngle = 180;
    public final double minAngle = -180;
    public final double servoRange = 360;
    public final double centerPosition = 0.5;
    public final Servo rotationServo;
    public double angle;
    public boolean isRotating;

    public Turret(OpMode opMode, Follower follower){
        this.opMode = opMode;
        this.follower = follower;
        this.rotationServo = opMode.hardwareMap.get(Servo.class, "rotationServo");
        this.angle = 0;
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
}
