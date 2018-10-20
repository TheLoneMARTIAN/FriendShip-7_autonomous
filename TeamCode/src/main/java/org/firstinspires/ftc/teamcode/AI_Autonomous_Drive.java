package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.InterruptedException;

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
    public void runOpMode()  throws  InterruptedException{
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
        //drive forward
        Foward(1);
        Thread.sleep(4000);
        Strafeleft(1);
        Thread.sleep(500);
        Straferight(1);
        Thread.sleep(500);
        Backward(1);
        Thread.sleep(4000);
    }

    public void Foward (double power){
        leftFront.setPower(power);
        leftRear.setPower(power);
        rightFront.setPower(power);
        rightRear.setPower(power);
    }
    public void Strafeleft(double power){
        leftFront.setPower(-power);
        leftRear.setPower(power);
        rightFront.setPower(power);
        rightRear.setPower(-power);
    }
    public void Straferight(double power){
        leftFront.setPower(power);
        leftRear.setPower(-power);
        rightFront.setPower(-power);
        rightRear.setPower(power);
    }
    public void Backward (double power){
        leftFront.setPower(-power);
        leftRear.setPower(-power);
        rightFront.setPower(-power);
        rightRear.setPower(-power);
    }

}