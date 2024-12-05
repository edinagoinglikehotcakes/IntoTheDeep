package org.firstinspires.ftc.teamcode.Opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Wrist;

public class ArmReset extends LinearOpMode {
    private Arm RobotArm;
    private Wrist RobotWrist;
    private Claw RobotClaw;    private int armPosition;


    @Override
    public void runOpMode() {
        RobotArm = new Arm(this);
        RobotClaw = new Claw(this);
        RobotWrist = new Wrist(this);
        RobotArm.init();
        RobotClaw.init();
        RobotWrist.init();

        waitForStart();

        Action action = new SequentialAction(
                new InstantAction(() -> RobotClaw.close_clawthingy()),
                new InstantAction(() -> RobotWrist.startWrist()),
                new InstantAction(() -> RobotArm.movePos(-2000)),
                new SleepAction(2.0),
                new InstantAction(()-> RobotArm.resetEncoders()),
                new InstantAction(() -> RobotArm.moveToStart())
        );
        Actions.runBlocking(action);
    }
}
