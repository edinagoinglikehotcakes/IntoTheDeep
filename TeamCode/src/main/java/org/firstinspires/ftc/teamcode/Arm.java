package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;

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

    public static double MID_SERVO       = 0.5 ;
    public static double HAND_SPEED      = 0.02 ;
    public static double ARM_UP_POWER    = 0.45 ;
    public static double ARM_DOWN_POWER  = -0.45 ;
    public static double OPENPOSITION    = 0.0 ;
    public static double CLOSEPOSITION   = 0.2 ;

    public static int START_POSITION          = 20;
    public static int COLLECTION_POSITION     = 4000;
    public static int OVER_BARRIER_POSITION   = 100;
    public static int PUT_IN_BASKET_POSITION  = 40;
    public static int PUT_ON_CHAMBER_POSITION = 45;
    public static int ATTACH_TO_RUNG_POSITION = 15;
    public static int HANGING_POSITION        = 2000;
    public static double STARTWRIST              = 0;
    public static double COLLECTIONWRIST         = 10.;
    public static double BASKETANDCHAMBERWRIST   = 50;
    public static final double MOVESPEED = 0.4;

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
    public void startWrist () {
        Wrist.setPosition((STARTWRIST));
    }
    public void collectionwrist () {
        Wrist.setPosition((COLLECTIONWRIST));
    }
    public void basketandchamberwrist (){
        Wrist.setPosition((BASKETANDCHAMBERWRIST));
    }
}
/*yay.java EXISTS ONCE MORE!!!!
AND BOB ISNT LONLEY*/
