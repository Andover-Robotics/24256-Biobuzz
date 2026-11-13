package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Outtake {
   public final OpMode opMode;
   public final CRServo rotationServo;
   public final DcMotorEx ballMotor;
   public final DcMotorEx ballMotorSupport;
   public PID rotationController = new PID();
   public PID ballMotorController = new PID();
   public PID supportMotorController = new PID();
   public double angle = 0;
   // temporary constants for lazy susan rotation
   public final double ROTATIONS_PER_SECOND = 516;
   public final double MAX_ANGLE = 320;
   public final double MIN_ANGLE = -320;
   // may be subject to change
   public boolean isRunning;
   public boolean isRotating;


   public Outtake(OpMode opMode){
       this.opMode = opMode;
       this.rotationServo = opMode.hardwareMap.get(CRServo.class, "rotationServo");
       this.ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
       this.ballMotorSupport = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorSupport");


       isRunning = false;
       isRotating = false;
   }

   public void updateAngle(double power){
    double rotateTime = MAX_ANGLE/ROTATIONS_PER_SECOND;
    while(isRunning){
      Thread.sleep(250);
      angle += power * (ROTATIONS_PER_SECOND / 4);
      if (angle == MAX_ANGLE){
        rotate(1.0);
        Thread.sleep(rotateTime);
      } else if (angle == MIN_ANGLE){
        rotate(-1.0);
        Thread.sleep(rotateTime);
      }
    }
   }    

  public void rotate(double target){
    isRotating = true;
    double current = rotationServo.getPower();
    double power = rotationController.pid(target, current);
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
    double powerMain = ballMotorController.pid(target, currentMainMotor);
    double powerSupport = supportMotorController.pid(target, currentSupportMotor);
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
    isRunning = !isRunning;
    if (isRunning){
      start(target);
    } else{
      stop();
    }
  }

  public void toggleRotation(double target){
    isRotating = !isRotating;
    if (isRotating){
      rotate(target);
    } else{
      stopRotation();
    }
  }
}
