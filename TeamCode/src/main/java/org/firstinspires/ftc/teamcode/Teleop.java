package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends OpMode {
    private Robot robot;

    private final double intakePowerIn = 1.0;
    private final double intakePowerReverse = -1.0;
    private final double turretSpeed = 2800;
    private final double shooterSpeed = 2800;

    @Override
    public void init() {
        robot = new Robot(this);
    }

    @Override
    public void loop() {
        robot.drivetrain.moveRobot(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if (gamepad2.right_bumper) {
            robot.intake.intake(intakePowerIn);
        } else if (gamepad2.left_bumper || gamepad1.left_bumper) {
            robot.intake.intake(intakePowerReverse);
        } else {
            robot.intake.stop();
        }

        if (gamepad1.b) {
            robot.outtake.stop();
        } else if (gamepad1.x || robot.outtake.isRunning) {
            robot.outtake.start(shooterSpeed);
        }

        if(gamepad1.dpad_up) {
            robot.outtake.lock(robot.outtake.cellCoordinates[0]);
        }
        if(gamepad1.dpad_down) {
            robot.outtake.lock(robot.outtake.cellCoordinates[1]);
        }
        if(gamepad1.dpad_right) {
            robot.outtake.lock(robot.outtake.cellCoordinates[2]);
        }
        if(gamepad1.dpad_left) {
            robot.outtake.lock(robot.outtake.cellCoordinates[3]);
        }

        if (gamepad1.left_bumper) {
            robot.outtake.rotate(-turretSpeed);
        } else if (gamepad1.right_bumper) {
            robot.outtake.rotate(turretSpeed);
        } else if (robot.outtake.isRotating) {
            robot.outtake.stopRotation();
        }

    }
}
