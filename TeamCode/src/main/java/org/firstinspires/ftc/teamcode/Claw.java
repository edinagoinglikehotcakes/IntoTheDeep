package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config

public class Claw {
    private Servo clawServo = null;
    public static double OPENPOSITION    = 0.35 ;
    public static double CLOSEPOSITION   = 0.55 ;
    private static double MINCLAW               = 0.2;
    private static double MAXCLAW               = 0.6;

    private double remember_Position;
    private double ACCURACY = 0.05;

    public Claw(HardwareMap hardwareMap){
        clawServo = hardwareMap.get(Servo.class, "Claw_Servo");
    }

    public void init(){

    }
    public void update(){

    }
    public void open_clawthingy(){
        setClawPosition(OPENPOSITION);
    }
    public void close_clawthingy(){
        setClawPosition(CLOSEPOSITION);
    }

    public void setClawPosition(double pos) {
        remember_Position = MathUtils.clamp(pos, MINCLAW, MAXCLAW);
        clawServo.setPosition(remember_Position);
    }

    public double getClawPosition() {return clawServo.getPosition();}

    public boolean isBusy(){
        if (Math.abs(getClawPosition()-remember_Position) <= ACCURACY) return false;
        return true;
    }

    public class ClawAction implements Action {
        private boolean initialized = false;
        private double auto_remember_position;

        public ClawAction(double position)
        {
            auto_remember_position = position;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                setClawPosition(auto_remember_position);
                initialized = true;
            }
            return isBusy();
        }
    }
    public Action clawAction(double pos){
        return new Claw.ClawAction(pos);
    }
}
