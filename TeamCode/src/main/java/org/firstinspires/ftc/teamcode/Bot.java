package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.*;

public class Bot {
    public final OpMode opMode;
    public static Robot instance;
    public final Drivetrain drivetrain;
    public final Outtake outtake;
    public final Intake intake;

    public Robot(OpMode opMode){
        this.opMode = opMode;
        instance = this;
        intake = new Intake(opMode);
        outtake = new Outtake(opMode);
        drivetrain = new Drivetrain(opMode);
    }
    public void teleOpIntake(){

    }

    public void teleOpOuttake(){

    }

    public void teleOpDrivetrain() {

    }

    public void autoDrivetrain() {

    }

    public void autoIntake() {

    }


    public void autoOuttake() {

    }
}

