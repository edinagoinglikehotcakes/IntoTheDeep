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

    private boolean initialized = false;

    public static final double MID_SERVO       = 0.5 ;
    public static final double HAND_SPEED      = 0.02 ;
    public static final double ARM_UP_POWER    = 0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public static final double OPENPOSITION    = 0.0 ;
    public static final double CLOSEPOSITION   = 0.2 ;

    public static final int START_POSITION          = 20;
    public static final int COLLECTION_POSITION     = 3800;
    public static final int OVER_BARRIER_POSITION   = 3600;
    public static final int PUT_IN_BASKET_POSITION  = 2200;
    public static final int PUT_ON_CHAMBER_POSITION = 2500;
    public static final int ATTACH_TO_RUNG_POSITION = 4000;
    public static final int HANGING_POSITION        = 4500;

    public static final double MOVESPEED = 0.4;

    public Arm (LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public void init() {

        ArmMotor = myOpMode.hardwareMap.get(DcMotor.class, "ArmMotor");
        Wrist = myOpMode.hardwareMap.get(Servo.class, "Wrist_Servo");
        claw = myOpMode.hardwareMap.get(Servo.class, "Claw_Servo");

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
        if (!initialized || true) {
            ArmMotor.setTargetPosition(Position);
            ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor.setPower(Speed);
            initialized = true;
        }
        if (ArmMotor.getCurrentPosition()== Position) {
            ArmMotor.setPower(0);
            initialized = false;
            return;
        }
    }
    public int getpos(){
        return ArmMotor.getCurrentPosition();
    }

    public void moveToStart(){
        MoveArm(START_POSITION,MOVESPEED);
        open_clawthingy();
    }
    public void moveToCollection(){
        MoveArm(COLLECTION_POSITION,MOVESPEED);
    }
    public void moveToOverBarrier(){
        MoveArm(OVER_BARRIER_POSITION,MOVESPEED);
    }
    public void moveToBasket(){
        MoveArm(PUT_IN_BASKET_POSITION,MOVESPEED);
    }
    public void moveToChamber(){
        MoveArm(PUT_ON_CHAMBER_POSITION,MOVESPEED);
    }
    public void moveToClimb(){
        MoveArm(ATTACH_TO_RUNG_POSITION,MOVESPEED);
    }

    public void moveToHang(){
        MoveArm(HANGING_POSITION,MOVESPEED);
    }

}

