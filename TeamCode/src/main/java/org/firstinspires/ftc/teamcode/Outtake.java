package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Outtake {
    public final OpMode opMode;
    public final DcMotorEx rotationMotor;
    public final DcMotorEx ballMotor;
    public final DcMotorEx ballMotorSupport;


    public boolean isRunning;
    public boolean isRotating;


    public Outtake(OpMode opMode){
        this.opMode = opMode;
        this.rotationMotor = opMode.hardwareMap.get(DcMotorEx.class, "rotationMotor");
        this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");


        isRunning = false;
        isRotating = false;
    }


    public void rotate(){
        isRotating = true;
        rotationMotor.setPower(1.0);
    }


    public void rotate(double power){
        isRotating = true;
        rotationMotor.setPower(power);
    }


    public void stopRotate(){
        isRotating = false;
        rotationMotor.setPower(0);
    }


    public void outtake(){
        isRunning = true;
        ballMotor.setPower(1.0);
        ballMotorSupport.setPower(1.0);
    }


    public void outtake(double power){
        isRunning = true;
        ballMotor.setPower(power);
        ballMotorSupport.setPower(power);
    }


    public void intake(){
        isRunning = false;
        ballMotor.setPower(0);
        ballMotorSupport.setPower(1.0);
    }


    public void toggle(){
        isRunning = !isRunning;


        if(isRunning){
            outtake();
        } else{
            intake();
        }
    }
}
