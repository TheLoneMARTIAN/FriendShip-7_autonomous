package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="AI Auto Drive")
public class AI_Autonomous_Drive extends LinearOpMode {
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


        int landerPosition = -1;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            if(landerPosition < 0){
                landerPosition = landingGear.getCurrentPosition();
            }

            /*if (gamepad1.dpad_up){
                landerPosition = landerPosition ++;
            }
            else if(gamepad1.dpad_down){
                    landerPosition = landerPosition --;
            }

            if(landerPosition > MaxLanderPosition){
                landerPosition = MaxLanderPosition;
            }
            if(landerPosition < MinLanderPosition){
                landerPosition = MinLanderPosition;
            }*/

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
            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            //double drive = gamepad1.left_stick_y * Speed;
            //double turn = gamepad1.right_stick_x * Speed;
            //double rotate = gamepad2.left_stick_y * Speed;
            //double power = gamepad2.right_stick_y * Speed;
            //leftFrontPower = Range.clip(drive + turn, -1.0, 2.0);
            //rightFrontPower = Range.clip(drive - turn, -1.0, 2.0);
            //leftBackPower = Range.clip(rotate , -1.0, 1.0);
            //rightBackPower = Range.clip(power , -1.0, 1.0);

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            //leftFront.setPower(leftFrontPower);
            //rightFront.setPower(rightFrontPower);
            //leftBack.setPower(leftBackPower);
            //rightBack.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left front (%.2f), right front(%.2f), left back (%.2f), right back(%.2f)", leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
            telemetry.addData("Speed", "Rotor speed (%.2f)", Speed);
            telemetry.addData("Lander", "Position (%d)", landerPosition);
            telemetry.update();
        }
    }
}