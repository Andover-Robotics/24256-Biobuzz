package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Note: This is the original version of the intake subsystem
// I made a second version utilizing a PID loop, if you are reading this delete whichever one you think is redundant and keep the other.

public class Intake {
   public final OpMode opMode;
   public final DcMotorEx ballMotorOne;
   public final DcMotorEx ballMotorTwo;
   public boolean isRunning;
  
   public Intake(OpMode opMode) {
       this.opMode = opMode;
       this.ballMotorOne = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorOne");
       this.ballMotorTwo = opMode.hardwareMap.get(DcMotorEx.class, "ballMotorTwo");
       this.isRunning = false;
   }

   public void intake(){
       isRunning = true;
       ballMotorOne.setPower(1.0);
       ballMotorTwo.setPower(1.0);
   }

   public void intake(double power){
       isRunning = true;
       ballMotorOne.setPower(power);
       ballMotorTwo.setPower(power);
   }

   public void stop(){
       isRunning = true;
       ballMotorOne.setPower(0);
       ballMotorTwo.setPower(0);
   }

   public void toggle(){
       isRunning = !isRunning;

       if(isRunning){
           intake();
       }   else{
           stop();
       }
   }
}
