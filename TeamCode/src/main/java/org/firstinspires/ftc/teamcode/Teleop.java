package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends OpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Outtake outtake;

    private final double intakePowerIn = 1.0;
    private final double intakePowerReverse = -1.0;
    private final double turretSpeed = 2800;

    @Override
    public void init() {
        drivetrain = new Drivetrain(this);
        intake = new Intake(this);
        outtake = new Outtake(this);
    }

    @Override
    public void loop() {
        drivetrain.moveRobot(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if (gamepad2.right_bumper) {
            intake.setPower(intakePowerIn);
        } else if (gamepad2.left_bumper || gamepad1.left_bumper) {
            intake.setPower(intakePowerReverse);
        } else {
            intake.setPower(0);
        }

        if (gamepad1.x) {
            outtake.start();
        } else if (gamepad1.b) {
            outtake.stop();
        }

        if (gamepad1.dpad_left) {
            outtake.rotate(-turretSpeed);
        } else if (gamepad1.dpad_right) {
            outtake.rotate(turretSpeed);
        } else {
            outtake.stopRotation();
        }
    }
}