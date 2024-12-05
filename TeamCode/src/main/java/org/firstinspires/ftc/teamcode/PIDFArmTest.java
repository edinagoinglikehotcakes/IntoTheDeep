package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Config
@TeleOp(name = "PIDF Arm Test", group = "Autonomous")
public class PIDFArmTest  extends OpMode {
    private Arm RobotArm;

    public static int pos = 600;

    @Override
    public void init(){
        RobotArm = new Arm(hardwareMap);
        RobotArm.init();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }
    @Override
    public void loop(){
        RobotArm.movePos(pos);
        RobotArm.update();
        telemetry.addData("pos", RobotArm.getpos());
        telemetry.addData("target", RobotArm.remember_Position);
        telemetry.update();
    }
}
