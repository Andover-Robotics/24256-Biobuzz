package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;


public class Outtake {
    public final OpMode opMode;
    public final DcMotorEx ballMotor;
    public final DcMotorEx supportMotor;
    public final DcMotorEx rotationMotor;
    public boolean isRunning;
    public boolean isRotating;


    public Outtake(OpMode opMode) {
        this.opMode = opMode;

        ballMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballMotor");
        ballMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ballMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        supportMotor = opMode.hardwareMap.get(DcMotorEx.class, "supportMotor");
        supportMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        supportMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        supportMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rotationMotor = opMode.hardwareMap.get(DcMotorEx.class, "rotationMotor");
        rotationMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        isRunning = false;
        isRotating = false;
    }


    public void rotate() {
        rotate(1.0);
    }


    public void rotate(double power) {
        isRotating = power != 0;
        rotationMotor.setPower(Range.clip(power, -1.0, 1.0));
    }


    public void stopRotate() {
        isRotating = false;
        rotationMotor.setPower(0);
    }


    public void outtake() {
        outtake(1.0);
    }


    public void outtake(double power) {
        isRunning = power != 0;
        setBallPower(Range.clip(power, -1.0, 1.0));
    }


    public void intake() {
        intake(1.0);
    }


    public void intake(double power) {
        isRunning = power != 0;
        setBallPower(-Math.abs(Range.clip(power, -1.0, 1.0)));
    }


    public void stop() {
        isRunning = false;
        setBallPower(0);
    }


    public void toggle() {
        if (isRunning) {
            stop();
        } else {
            outtake();
        }
    }


    private void setBallPower(double power) {
        ballMotor.setPower(power);
        supportMotor.setPower(power);
    }
}