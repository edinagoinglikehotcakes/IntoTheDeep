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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Wrist;

@Config
@Autonomous(name = "Auto_Observation", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Observation extends LinearOpMode {
    private Arm RobotArm;
    private Wrist RobotWrist;
    private Claw RobotClaw;

    MecanumDrive drive;
    Action move;

    //starting locations
    public static double CHAMBER_HEADING = 270.0;
    public static Pose2d STARTING_POSE_SPECIMEN = new Pose2d(-16.5, 63.0, Math.toRadians(CHAMBER_HEADING));

    //samples
    public static double SAMPLE_Y = 25.0+24.0;
    public static double SAMPLE_MOVE = 9;

    public static Vector2d TEAM_FLOOR_SAMPLE = new Vector2d(-48.0+24.0, SAMPLE_Y);
    public static double TEAM_SAMPLE_HEADING = 225.0;


    //Observation locations
    public static Vector2d OBSERVATION = new Vector2d(-55,45);

    public static double OBSERVATION_WAIT = 1.5;
    public static double OBSERVATION_HEADING = 90.0;

    //Chamber locations
    public static double CHAMBER_Y = 24+9+16.0;
    public static double CHAMBER_MOVE = 3;
    public static Vector2d CHAMBER = new Vector2d(-11,CHAMBER_Y);

    public static double CLAW_CLOSE_WAIT = 0.5;
    public static double CLAW_OPEN_WAIT = 0.5;
    public static double HOOK_DISTANCE = 3.0;

    private static Vector2d getChamberPosition(int whichvisit) {
        return CHAMBER.plus(new Vector2d(CHAMBER_MOVE*(whichvisit-1), 0));
    }
    private static Vector2d getHookedSample(int whichvisit){
        Vector2d chamber = CHAMBER.plus(new Vector2d(CHAMBER_MOVE*(whichvisit-1), 0));
        return chamber.minus(new Vector2d(0,HOOK_DISTANCE));
    }
    private static Vector2d getTeamSamplePosition(int whichvisit){
        return TEAM_FLOOR_SAMPLE.minus(new Vector2d(SAMPLE_MOVE*(whichvisit-1),0));
    }
    private Action grabSampleFromFloor(){
        return new SequentialAction(
                new ParallelAction(
                        RobotWrist.wristAction(Wrist.COLLECTIONWRIST),
                        RobotArm.armAction(Arm.COLLECTION_POSITION)
                ),
                RobotClaw.clawAction(Claw.CLOSEPOSITION),
                new SleepAction(CLAW_CLOSE_WAIT)
        );
    }

    private Action buildSpecimens(MecanumDrive drive){
        return new SequentialAction(
                //go to Chamber with hand specimen and move in
                drive.actionBuilder(STARTING_POSE_SPECIMEN)
                        .strafeTo(getChamberPosition(1))
                        .strafeTo(getHookedSample(1))
                        .build(),
                //TODO open claw
                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(1),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(1))
                        .build(),

                // pickup sample 1
                drive.actionBuilder(new Pose2d(getChamberPosition(1),Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(1),Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                grabSampleFromFloor(),

                // hand to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(1), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 1

                // pickup sample 2
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(2), Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                grabSampleFromFloor(),

                // hand to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(2), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 2

                // pick up specimen 1 from human
                // TODO grab specimen 1 from wall

                //go to Chamber with hand specimen and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(2),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(2))
                        .build(),
                //TODO open claw
                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(2),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(2))
                        .build(),

                // pickup sample 3
                drive.actionBuilder(new Pose2d(getChamberPosition(2),Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(3), Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                grabSampleFromFloor(),

                // hand sample 3 to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(3), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 3

                // pick up specimen 2 from human
                // TODO grab specimen 2 from wall

                //go to Chamber with specimen 2 and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(3),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(3))
                        .build(),
                //TODO open claw

                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(3),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(3))
                        .build(),

                // pick up specimen 3 from human
                drive.actionBuilder(new Pose2d(getChamberPosition(3), Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                // TODO grab specimen 3 from wall

                //go to Chamber with hand specimen and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(4),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(4))
                        .build(),
                //TODO open claw

                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(4),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(4))
                        .build()
        );
    }


    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, STARTING_POSE_SPECIMEN);

        RobotArm = new Arm(this);
        RobotClaw = new Claw(this);
        RobotWrist = new Wrist(this);

        RobotArm.init();
        RobotClaw.init();
        RobotWrist.init();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        move = drive.actionBuilder(STARTING_POSE_SPECIMEN)
                .strafeToConstantHeading(new Vector2d(-63.0,63.0))
                .build();
                //buildSpecimens(drive);

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

