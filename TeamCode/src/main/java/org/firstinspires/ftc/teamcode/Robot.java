package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class Robot {
    public final OpMode opMode;
    public static Robot instance;
    public final Drivetrain drivetrain;
    public final Intake intake;
    public final Outtake outtake;

    public Robot(OpMode opMode) {
        this.opMode = opMode;
        instance = this;
        drivetrain = new Drivetrain(opMode);
        intake = new Intake(opMode);
        outtake = new Outtake(opMode);
    }
}