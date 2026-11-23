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
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;
    public boolean isRunning;

    public Outtake(OpMode opMode, Follower follower){
        this.opMode = opMode;
        this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");
        ballMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ballMotorSupport.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isRunning = false;
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
