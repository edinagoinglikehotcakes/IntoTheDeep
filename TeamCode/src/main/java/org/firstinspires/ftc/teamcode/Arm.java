package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private LinearOpMode myOpMode = null;

    private DcMotor ArmMotor = null;
    private Servo claw = null;
    private Servo   Wrist = null;

    public static final double MID_SERVO       = 0.5 ;
    public static final double HAND_SPEED      = 0.02 ;
    public static final double ARM_UP_POWER    = 0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public static final double OPENPOSITION    = 0.0 ;
    public static final double CLOSEPOSITION   = 0.2 ;

    public Arm (LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public void init() {

        ArmMotor = myOpMode.hardwareMap.get(DcMotor.class, "ArmMotor");
        Wrist = myOpMode.hardwareMap.get(Servo.class, "Wrist_Servo");
        claw = myOpMode.hardwareMap.get(Servo.class, "Claw_Servo");

        ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ArmMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    // do we need a speciman/sample pick up position?
    public void open_clawthingy(){
        claw.setPosition(OPENPOSITION);
    }
    public void close_clawthingy(){
        claw.setPosition((CLOSEPOSITION));
    }
public void MoveArm(int Position, double Speed) {
        ArmMotor.setTargetPosition(Position);
        ArmMotor.setPower(Speed);
        if (ArmMotor.getCurrentPosition()== Position) {
            ArmMotor.setPower(0);
            return;
        }

    }
}

}