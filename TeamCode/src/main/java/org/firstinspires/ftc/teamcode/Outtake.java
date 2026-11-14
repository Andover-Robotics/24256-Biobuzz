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
   // constants
   public final double ROTATIONS_PER_SECOND = 516;
   public final double MAX_ANGLE = 320;
   public final double MIN_ANGLE = -320;
   public final double FORWARD_MOV = 50;
   public final double STRAFE_MOV = 50;
   public final double ROTATE_MOV = 50;
   // these values are temporary for now, subject to change
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

   public void updateAngle(double power, double deltaTime){
      // this will need to run in the Bot.java loop or whatever to continously update the angle while running
      angle += power * ROTATIONS_PER_SECOND * deltaTime;
   }    

  public void lock(double lsy, double lsx, double rsx, double power){
    double time = System.nanoTime();
    double prevTime = 0;
    double drivetrainX = Math.cos((Math.PI*angle)/180);
    double drivetrainY = Math.sin((Math.PI*angle)/180);
    double drivetrainAngle = 0;

    while (true){
      prevTime = time;
      time = System.nanoTime();
      double deltaTime = (time - prevTime) / 1000000000.0;
      drivetrainX += lsy * FORWARD_MOV * deltaTime;
      drivetrainY += lsx * STRAFE_MOV * deltaTime;
      drivetrainAngle += rsx * ROTATE_MOV * deltaTime;
      double targetAngle = (Math.atan2(-drivetrainY, -drivetrainX) * 180)/Math.PI;
      double outtakeAngle = targetAngle - drivetrainAngle;
      rotateTo(outtakeAngle, power);
      Thread.sleep(250);
    }
  }

  public void rotateTo(double target, double power){
    rotate(power);
    while (angle != target){
      continue;
    }
    stopRotation();
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
