package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Wrist {
    private OpMode opmode;

    private Servo wristServo = null;
    private double realCollectionPosition;

    public static double STARTWRIST              = 0;
    public static double COLLECTIONWRIST         = 0.62;
    public static double BASKETANDCHAMBERWRIST   = 0.45;
    public static double SCOOTCH                 = .0125;
    private static double MINWRIST               = 0;
    private static double MAXWRIST               = 0.8;
    public static double MANUALCOLLECTIONWRIST         = 0.175;


    private double remember_Position;
    private double ACCURACY = 0.05;

    public Wrist(OpMode op){
        opmode = op;
    }
    public void init() {
        init(true);
    }
    public void init(boolean auto) {
        wristServo = opmode.hardwareMap.get(Servo.class, "Wrist_Servo");
        if (auto)
            realCollectionPosition = COLLECTIONWRIST;
        else
            realCollectionPosition = MANUALCOLLECTIONWRIST;


    }

    public void update(){
        Telemetry telemetry = opmode.telemetry;
        telemetry.addData("Wrist position", getWristPosition());
        telemetry.addData("Wrist target", remember_Position);
        telemetry.addData("Wrist busy", isBusy());
    }

    public void setRealCollectionPosition()
    {
        realCollectionPosition = getWristPosition();
    }

    public void startWrist () {
        remember_Position = STARTWRIST;
        wristServo.setPosition(remember_Position);
    }
    public void collectionwrist () {
        remember_Position =realCollectionPosition;
        wristServo.setPosition(remember_Position);
    }
    public void basketandchamberwrist (){
        remember_Position = BASKETANDCHAMBERWRIST;
        wristServo.setPosition(remember_Position);
    }

    public void setWristPosition(double pos) {
        remember_Position = MathUtils.clamp(pos, MINWRIST, MAXWRIST);
        wristServo.setPosition(remember_Position);
    }

    public void scootchUp(){
        setWristPosition(remember_Position + SCOOTCH);
    }
    public void scootchDown(){
        setWristPosition(remember_Position - SCOOTCH);
    }

    public double getWristPosition() {return wristServo.getPosition();}

    public boolean isBusy(){
        if (Math.abs(getWristPosition()-remember_Position) <= ACCURACY) return false;
        return true;
    }

    public class WristAction implements Action {
        private boolean initialized = false;
        private double auto_remember_position;

        public WristAction(double position)
        {
            auto_remember_position = position;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                setWristPosition(auto_remember_position);
                initialized = true;
            }
            return isBusy();
        }
    }
    public Action wristAction(double pos){
        return new Wrist.WristAction(pos);
    }
}
