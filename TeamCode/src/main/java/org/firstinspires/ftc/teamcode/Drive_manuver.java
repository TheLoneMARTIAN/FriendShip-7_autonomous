package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Mecanum Drive", group="Linear Opmode")
public class Drive_manuver extends LinearOpMode {
    private final static int MaxLanderPosition = 50;
    private final static int MinLanderPosition = 10;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    private DcMotor landingGear = null;
    private double Speed = 0.5;
    private double extensionPower = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftRear = hardwareMap.get(DcMotor.class, "left_rear");
        rightRear = hardwareMap.get(DcMotor.class, "right_rear");
        landingGear = hardwareMap.get(DcMotor.class, "lander");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        landingGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int landerPosition = -1;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry

            if (gamepad2.dpad_up){
                landingGear.setDirection(DcMotorSimple.Direction.FORWARD);
                landingGear.setPower(extensionPower);
            }
            else if(gamepad2.dpad_down){
                landingGear.setDirection(DcMotorSimple.Direction.REVERSE);
                landingGear.setPower(extensionPower);
            }else {
                landingGear.setPower(0);
            }

            landerPosition = landingGear.getCurrentPosition();

            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double leftFrontPower = r * Math.cos(robotAngle) + rightX;
            double rightFrontPower = r * Math.sin(robotAngle) - rightX;
            double leftBackPower = r * Math.sin(robotAngle) + rightX;
            double rightBackPower = r * Math.cos(robotAngle) - rightX;

            leftFront.setPower(leftFrontPower);
            rightFront.setPower(rightFrontPower);
            leftRear.setPower(leftBackPower);
            rightRear.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left front (%.2f), right front(%.2f), left back (%.2f), right back(%.2f)", leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
            telemetry.addData("Speed", "Rotor speed (%.2f)", Speed);
            telemetry.addData("Lander", "Position (%d)", landerPosition);
            telemetry.update();
        }
    }
}