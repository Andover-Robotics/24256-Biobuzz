package org.firstinspires.ftc.teamcode.teleop;

// Note: I see a majority of this code was most likely copied from other repositories or from AI
// Try to write this yourselves because this code won't work
// Make sure to also check documentation for TeleOp to learn more about how this will work

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
    // this is not how a class works
    // you need a constructor and methods
    public static void TeleOp(String[] args) {
        // isRunning is not defined
        while (isRunning = true) {
            String robot; // what is this supposed to be
        }
        // the input detection only runs once
        if(gp1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.outtake.Outtake(); // a robot object hasn't been defined as a field
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robot.intake.Outake(-1.0); // misspelling

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
            isRunning = false; // isRunning never defined
            break; 
        }
        if (gp1.wasJustPressed(GamepadKeys.Button.Dpad_Down)) {
            bot.switchAlliance(); // a bot object was never defined as a field
        }
        if (!Bot.isRed) {
            telemetry.addData("alliance", "Blue");
        } else {
            telemetry.addData("alliance", "Red");
        }
        telemetry.update();
    }



}
