package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveMechumWheel {

    public static void moveRobot(double left_stick_x, double  left_stick_y, double right_stick_x,
                                 DcMotor leftFront,
                                 DcMotor rightFront,
                                 DcMotor leftRear,
                                 DcMotor rightRear){
        double r = Math.hypot(left_stick_x, left_stick_y);
        double robotAngle = Math.atan2(left_stick_y, left_stick_x) - Math.PI / 4;
        double rightX = right_stick_x;
        double leftFrontPower = r * Math.cos(robotAngle) + rightX;
        double rightFrontPower = r * Math.sin(robotAngle) - rightX;
        double leftBackPower = r * Math.sin(robotAngle) + rightX;
        double rightBackPower = r * Math.cos(robotAngle) - rightX;

        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftBackPower);
        rightRear.setPower(rightBackPower);
    }
}
