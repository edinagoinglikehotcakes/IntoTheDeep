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

public class Claw {
    private OpMode opmode;

    private Servo clawServo = null;
    public static double OPENPOSITION    = 0.35 ;
    public static double CLOSEPOSITION   = 0.55 ;
    private static double MINCLAW               = 0.2;
    private static double MAXCLAW               = 0.6;

    private double remember_Position;
    private double ACCURACY = 0.05;

    public Claw(OpMode op){
        opmode = op;
    }

    public void init(){
        clawServo = opmode.hardwareMap.get(Servo.class, "Claw_Servo");
    }
    public void update(){
        Telemetry telemetry = opmode.telemetry;
        telemetry.addData("Claw position", getClawPosition());
        telemetry.addData("Claw target", remember_Position);
        telemetry.addData("Claw busy", isBusy());
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
