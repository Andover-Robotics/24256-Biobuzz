package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
public class Drivetrain {
    private final DcMotor frontLeftMotor;
    private final DcMotor backLeftMotor;
    private final DcMotor frontRightMotor;
    private final DcMotor backRightMotor;
    private final OpMode opMode;
    public Drivetrain(OpMode opMode){

        this.opMode = opMode;
        frontLeftMotor = opMode.hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = opMode.hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = opMode.hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = opMode.hardwareMap.get(DcMotor.class, "backRightMotor");

        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveRobot(double lsy, double lsx, double rsx) {
        double y = lsy;
        double x = lsx;
        double rx = rsx;

        double denominator = Math.max(Math.abs(y) + Math.abs(x)  + Math.abs(rx), 1);
        frontLeftMotor.setPower((y + x + rx) / denominator);
        backLeftMotor.setPower((y - x + rx) / denominator);
        frontRightMotor.setPower((y - x - rx) / denominator);
        backRightMotor.setPower((y + x - rx) / denominator);
    }
}
