package org.firstinspires.ftc.teamcode;

public class PIDController extends PIDFController {
    public PIDController(double kp, double ki, double kd){
        super(kp,ki,kd,0);
    }
    public void setPID(double kp, double ki, double kd){
        setPIDF(kp, ki, kd,0);
    }
}
