package org.firstinspires.ftc.teamcode.Opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Wrist;

@Config
@Autonomous(name = "Auto_Blue_Basket", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Specimen extends LinearOpMode {
    private Arm RobotArm;
    private Wrist RobotWrist;
    private Claw RobotClaw;

    MecanumDrive drive;
    Action move;

    public static Pose2d STARTING_POSE = new Pose2d(-16.5, 63.0, Math.toRadians(90.0));


    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, STARTING_POSE);

        RobotArm = new Arm(this);
        RobotClaw = new Claw(this);
        RobotWrist = new Wrist(this);

        RobotArm.init();
        RobotClaw.init();
        RobotWrist.init();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        move = new SequentialAction(
                new ParallelAction(
                    RobotArm.armAction(Arm.PUT_ON_CHAMBER_POSITION),
                    RobotWrist.wristAction(Wrist.BASKETANDCHAMBERWRIST)
                ),
                RobotClaw.clawAction(Claw.OPENPOSITION)
        );

        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            // Update the dashboard.
            move.preview(packet.fieldOverlay());

            // If the action is done running...
            if (!move.run(packet)) {
                break;
            }

            RobotArm.update();
            RobotWrist.update();
            RobotClaw.update();

            telemetry.update();


            // Update the dashboard.
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }

    }
}


