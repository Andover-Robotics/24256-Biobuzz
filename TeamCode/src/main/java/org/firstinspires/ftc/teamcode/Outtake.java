package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Outtake {
   public final OpMode opMode;
   public final DcMotorEx rotationMotor;
   public final DcMotorEx ballMotor;
   public final DcMotorEx ballMotorSupport;
   public PID rotationController;
   public PID ballMotorController;
   public PID supportMotorController;
   public boolean isRunning;
   public boolean isRotating;


   public Outtake(OpMode opMode){
       this.opMode = opMode;
       this.rotationMotor = opMode.hardwareMap.get(DcMotorEx.class, "rotationMotor");
       this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
       this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");


       isRunning = false;
       isRotating = false;
   }

  public void rotate(double target){
    double current = rotationMotor.getVelocity();
    double power = rotationController.pid(target, current);
    rotationMotor.setPower(power);
  }

  public void stopRotation(){
    rotationMotor.setPower(0);
    rotationController.reset();
  }

  public void start(double target){
    double currentMainMotor = ballMotor.getVelocity();
    double currentSupportMotor = ballMotorSupport.getVelocity();
    double powerMain = ballMotorController.pid(target, current);
    double powerSupport = supportMotorController.pid(target, current);
    ballMotor.setPower(powerMain);
    ballMotorSupport.setPower(powerSupport);
  }

  public void stop(){
    ballMotor.setPower(0);
    ballMotorSupport.setPower(0);
    supportMotorController.reset();
    ballMotorController.reset();
  }
}
