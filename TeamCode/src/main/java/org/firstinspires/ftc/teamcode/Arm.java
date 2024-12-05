package org.firstinspires.ftc.teamcode;

import android.icu.math.MathContext;
import android.icu.number.Precision;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import kotlin.math.MathKt;

@Config
public class Arm {
    int remember_Position;

    private DcMotor ArmMotor = null;

    private PIDController controller;
    public static double p = 0.005, i = 0.000001, d = 0.0001, f = 0.05;

    private final double TICKS_PER_DEGREE = 640 / 45.0;
    private final double INITIAL_ANGLE = 45.0;

    private int realCollectionPosition;

    private boolean initialized = false;

    public static int START_POSITION          = 20;
    public static int COLLECTION_POSITION     = 3960;
    public static int OVER_BARRIER_POSITION   = 3450;
    public static int MAX_COLLECTION_WRIST    = 3000;
    public static int PUT_IN_BASKET_POSITION  = 2200;
    public static int AUTO_PUT_IN_BASKET_POSITION  = 2400;

    public static int PUT_ON_CHAMBER_POSITION = 2500;
    public static int READY_TO_RUNG_POSITION = 1775;
    public static int ATTACH_TO_RUNG_POSITION = 2000;
    public static int HANGING_POSITION        = 20;

    public static int SCOOTCH                 = 2;
    public static int MAXARM                  = 4000;
    public static int MINARM                  = 0;

    public static int ACCURACY                = 10;

    public static double MOVESPEED = 0.6;

    public Arm (HardwareMap hardwareMap) {
        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
    }

    public void init() {
        realCollectionPosition = COLLECTION_POSITION;

        ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ArmMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        controller = new PIDController(p, i, d);

    }

    public void update() {
        controller.setPID(p, i, d);
        int currentPos = ArmMotor.getCurrentPosition();
        double pidf = controller.calculate(currentPos, remember_Position);
        double ff = Math.cos(Math.toRadians(getAngle(remember_Position)))*f;
        double power = pidf+ff;
        if (power > 1.0) power = 1.0;
        if (power < -1.0) power = -1.0;

        ArmMotor.setPower(power);
    }


    public void resetEncoders() {
        ArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setRealCollectionPosition()
    {
        realCollectionPosition = ArmMotor.getCurrentPosition();
    }



    public int getpos(){
        return ArmMotor.getCurrentPosition();
    }

    private double getAngle(int pos){
        return pos/TICKS_PER_DEGREE - INITIAL_ANGLE;
    }
    private int getPos(double angle){
        return (int) ((angle+INITIAL_ANGLE)*TICKS_PER_DEGREE);
    }

    public void movePos(int position){
        remember_Position = MathUtils.clamp(position, MINARM, MAXARM);
    }
    public void moveDegress(double deg){
        movePos(getPos(deg));
    }

    public void moveToStart(){
        movePos( START_POSITION);
    }
    public void moveToCollection(){

        movePos(realCollectionPosition);
    }
    public void moveToOverBarrier(){
        movePos(OVER_BARRIER_POSITION);
    }
    public void moveToAutonomousBasket(){
        movePos(AUTO_PUT_IN_BASKET_POSITION);
    }
    public void moveToBasket(){
        movePos(PUT_IN_BASKET_POSITION);
    }
    public void moveToChamber(){
        movePos(PUT_ON_CHAMBER_POSITION);
    }
    public void moveToClimb(){
        movePos(ATTACH_TO_RUNG_POSITION);
    }

    public void moveToHang(){
        movePos(HANGING_POSITION);
    }
    public void scootchUp(){
        movePos(remember_Position + SCOOTCH);
    }
    public void scootchDown(){
        movePos(remember_Position - SCOOTCH);
    }

    public boolean isBusy(){
        if (Math.abs(getpos()-remember_Position) <= ACCURACY) return false;
        return true;
    }

    public class ArmAction implements Action {
        private boolean initialized = false;
        private int auto_remember_position;

        public ArmAction(int position)
        {
            auto_remember_position = position;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                movePos(auto_remember_position);
                initialized = true;
            }
            telemetryPacket.put("Arm Pos", getpos());
            telemetryPacket.put("Target", auto_remember_position);
            return isBusy();
        }
    }
    public Action armAction(int pos){
        return new ArmAction(pos);
    }
}
/*yay.java EXISTS ONCE MORE!!!!
AND BOB ISNT LONLEY*/
