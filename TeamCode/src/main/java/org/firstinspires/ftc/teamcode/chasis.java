package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class chasis {

    private LinearOpMode myOpMode = null;

    private DcMotor LeftFrontDrive = null;
    private DcMotor RightFrontDrive = null;
    private DcMotor LeftBackDrive = null;
    private DcMotor RightBackDrive = null;
    private DcMotor ArmMotor = null;
    private Servo   claw = null;
    private Servo   Wrist = null;


    public static final double MID_SERVO       = 0.5 ;
    public static final double HAND_SPEED      = 0.02 ;
    public static final double ARM_UP_POWER    = 0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;



     public chasis (LinearOpMode opmode) {
         myOpMode = opmode;
     }





    public void init() {

        LeftFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "Frontleft");
        LeftBackDrive = myOpMode.hardwareMap.get(DcMotor.class, "Backleft");
        RightFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "Frontright");
        RightBackDrive = myOpMode.hardwareMap.get(DcMotor.class, "Backright");
        ArmMotor = myOpMode.hardwareMap.get(DcMotor.class, "ArmMotor");
        Wrist = myOpMode.hardwareMap.get(Servo.class, "Wrist_Servo");
        claw = myOpMode.hardwareMap.get(Servo.class, "Claw_Servo");







        LeftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        LeftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RightBackDrive.setDirection(DcMotor.Direction.FORWARD);
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

