package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Note: this is the intake subsystem utilizing a PID
// Check out the original version without a PID, if you are reading this delete whichever one you think is redundant and keep the other.

public class IntakePID {
   public final OpMode opMode;
   public final DcMotorEx ballMotorOne;
   public final DcMotorEx ballMotorTwo;
   public PID ballMotorOneController = new PID();
   public PID ballMotorTwoController = new PID();
   public boolean isRunning;

   public Intake(OpMode opMode) {
       this.opMode = opMode;
       this.ballMotorOne = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorOne");
       this.ballMotorTwo = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorTwo");
       this.isRunning = false;
   }

   public void intake(double target){
       isRunning = true;
       double currentOne = ballMotorOne.getVelocity();
       double powerOne = ballMotorOneController.pid(target, currentOne);
       double currentTwo = ballMotorTwo.getVelocity();
       double powerTwo = ballMotorOneController.pid(target, currentTwo);
       ballMotorOne.setPower(powerOne);
       ballMotorTwo.setPower(powerTwo);
   }

   public void stop(){
       isRunning = false;
       ballMotorOne.setPower(0);
       ballMotorTwo.setPower(0);
   }

   public void toggle(){
       isRunning = !isRunning;

       if(isRunning){
           intake(1.0);
       }   else{
           stop();
       }
   }
}
