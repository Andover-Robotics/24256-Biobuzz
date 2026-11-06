package org.firstinspires.ftc.teamcode;

public class Outtake{

    public class Outtake {
        public final OpMode opMode;
        public final DcMotorEx rotationMotor;
        public final DcMotorEx[] ballMotor = new DcMotorEx[2];
        public boolean isRunning;
        public boolean isRotating;

        public Outtake(OpMode opMode){
            this.opMode = opMode;
            this.rotationMotor = opMode.hardwareMap.get(DcMotorEx.class, "rotationMotor");
            for (int i = 0; i < 2; i++){
                this.ballMotor[i] = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor" + i);
            }
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
            for (int i = 0; i < 2; i++){
                ballMotor[i].setPower(1.0);
            }
        }

        public void outtake(double power){
            isRunning = true;
            for (int i = 0; i < 2; i++){
                ballMotor[i].setPower(power);
            }
        }

        public void intake(){
            isRunning = false;
            for (int i = 0; i < 2; i++){
                ballMotor[i].setPower(0);
            }
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


}

