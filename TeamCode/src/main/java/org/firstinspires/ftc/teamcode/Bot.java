package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.*;


public class Robot {
    public final OpMode opMode;
    public static Robot instance;
    public final Drivetrain drivetrain;
    public final Outtake outtake;
    public final Intake intake;
    public final MoveMotor moveMotor;


    public Robot(OpMode opMode){


        this.opMode = opMode;
        instance = this;
        intake = new Intake(opMode);
        outtake = new Outtake(opMode);
        drivetrain = new Drivetrain(opMode);


    }
}

