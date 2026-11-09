package org.firstinspires.ftc.teamcode.teleop;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Teleop.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Teleop.Subsystems.Turret;


import java.util.ArrayList;
import java.util.List;


public class TeleOp {
    public static void TeleOp(String[] args) {
        while (isRunning = true) {
            String robot;
        }
        if(gp1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.outtake.Outtake();
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robot.intake.Outake(-1.0);

        }if(gp2.wasJustPressed(GamepadKeys.Button.Right_Bumper)){
            robot.intake.Intake();
        }
        if(gp2.wasJustPressed(GamepadKeys.Button.Left_Bumper)){
            robot.intake.Intake(-1.0);
        }
        if(gp2.wasJustPressed(GamepadKeys.Button.Left_Pad)){
            robot.intake.Intake(0);
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Square)){
            robot.outtake.rotationmotor(2.0);

        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Circle)){
            robot.outtake.rotationmotor(0.5);
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Left_Pad)){
            robot.outtake.rotate();

        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Right_Pad)){
            robot.outtake.rotate(-1.0);
        }
        if (gp1.wasJustPressed(GamepadeKeys.Button.X)){
            isRunning = false;
            break;
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Dpad_Down)) {
            bot.switchAlliance();
        }
        if (!Bot.isRed) {
            telemetry.addData("alliance", "Blue");
        } else {
            telemetry.addData("alliance", "Red");
        }
        telemetry.update();
    }



}
