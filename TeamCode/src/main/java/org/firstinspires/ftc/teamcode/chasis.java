package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class chasis {

    private DcMotor LeftFrontDrive = null;
    private DcMotor RightFrontDrive = null;
    private DcMotor LeftBackDrive = null;
    private DcMotor RightBackDrive = null;


     public chasis (HardwareMap hardwareMap) {
         LeftFrontDrive = hardwareMap.get(DcMotor.class, "Frontleft");
         LeftBackDrive = hardwareMap.get(DcMotor.class, "Backleft");
         RightFrontDrive = hardwareMap.get(DcMotor.class, "Frontright");
         RightBackDrive = hardwareMap.get(DcMotor.class, "Backright");


         LeftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
         LeftBackDrive.setDirection(DcMotor.Direction.REVERSE);
         RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
         RightBackDrive.setDirection(DcMotor.Direction.FORWARD);

     }





    public void init() {

    }

    public void resetstart(){

    }

    public void drive(double forward, double sideways, double twist)
    {
        double max;    // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = forward;  // Note: pushing stick forward gives negative value
        double lateral = sideways;
        double yaw     = twist;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Send calculated power to wheels
        LeftFrontDrive.setPower(leftFrontPower);
        RightFrontDrive.setPower(rightFrontPower);
        LeftBackDrive.setPower(leftBackPower);
        RightBackDrive.setPower(rightBackPower);

    }
}
