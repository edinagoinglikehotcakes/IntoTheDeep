package org.firstinspires.ftc.teamcode.Opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;

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
    public static int WALL_ARM = 3970;
    public static double WALL_WRIST = 0.8;
    public static int LIFT_ARM = 3900;
    public static double BACKOFF = 3.0;

    public static Pose2d STARTING_POSE = new Pose2d(-16.5, 63.0, Math.toRadians(90.0));

    private Action pickupFromWall(){
        return new SequentialAction(
                new ParallelAction(
                        RobotArm.armAction(WALL_ARM),
                        RobotWrist.wristAction(WALL_WRIST),
                        RobotClaw.clawAction(Claw.OPENPOSITION)
                ),
                new SleepAction(3.0),
                RobotClaw.clawAction(Claw.CLOSEPOSITION),

                new ParallelAction(
                        RobotArm.armAction(LIFT_ARM),
                        drive.actionBuilder(drive.pose).strafeTo(drive.pose.position.minus(new Vector2d(0,-BACKOFF))).build()
                )
        );
    }

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


        move = pickupFromWall();

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


