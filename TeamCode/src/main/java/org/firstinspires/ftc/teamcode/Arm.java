package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.ServoCommands;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Arm {
    int remeber_Position;
    private LinearOpMode myOpMode = null;
    private Telemetry tel = null;

    private DcMotor ArmMotor = null;
    private Servo claw = null;
    private Servo   Wrist = null;

    private boolean initialized = false;
    public static double OPENPOSITION    = 0.35 ;
    public static double CLOSEPOSITION   = 0.55 ;

    public static int START_POSITION          = 20;
    public static int COLLECTION_POSITION     = 3650;
    public static int OVER_BARRIER_POSITION   = 3600;
    public static int MAX_COLLECTION_WRIST    = 3000;
    public static int PUT_IN_BASKET_POSITION  = 2200;
    public static int PUT_ON_CHAMBER_POSITION = 2500;
    public static int ATTACH_TO_RUNG_POSITION = 4000;
    public static int HANGING_POSITION        = 4500;

    public static double STARTWRIST              = 0;
    public static double COLLECTIONWRIST         = 0.575;
    public static double BASKETANDCHAMBERWRIST   = 0.42;
    public static double MOVESPEED = 0.4;

    public Arm (LinearOpMode opmode, Telemetry telemetry) {
        myOpMode = opmode;
        tel = telemetry;
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
       remeber_Position=Position;
       if (!initialized || true) {
            ArmMotor.setTargetPosition(Position);
            ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor.setPower(Speed);
            initialized = true;
        }

       tel.addData("target current", "%d %d", Position , ArmMotor.getCurrentPosition());
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
        startWrist();
        close_clawthingy();
        MoveArm(START_POSITION,MOVESPEED);
    }
    public void moveToCollection(){
        collectionwrist();
        MoveArm(COLLECTION_POSITION,MOVESPEED);
    }
    public void moveToOverBarrier(){
        collectionwrist();
        MoveArm(OVER_BARRIER_POSITION,MOVESPEED);
    }
    public void moveToBasket(){
        basketandchamberwrist();
        MoveArm(PUT_IN_BASKET_POSITION,MOVESPEED);
    }
    public void moveToChamber(){
        basketandchamberwrist();
        MoveArm(PUT_ON_CHAMBER_POSITION,MOVESPEED);
    }
    public void moveToClimb(){
        MoveArm(ATTACH_TO_RUNG_POSITION,MOVESPEED);
    }

    public void moveToHang(){
        MoveArm(HANGING_POSITION,MOVESPEED);
    }
    public void startWrist () {
        Wrist.setPosition((STARTWRIST));
    }
    public void collectionwrist () {
        Wrist.setPosition((COLLECTIONWRIST));
    }
    public void basketandchamberwrist (){
        Wrist.setPosition((BASKETANDCHAMBERWRIST));
    }

    public void setWristPosition(double pos) {
        Wrist.setPosition(pos);
    }
    public void setClawPosition(double pos) {
        claw.setPosition(pos);
    }
    public double getWristPosition() {return Wrist.getPosition();}
    public double getClawPosition() {return claw.getPosition();}
}
/*yay.java EXISTS ONCE MORE!!!!
AND BOB ISNT LONLEY*/
