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

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Wrist;


@Config
@Autonomous(name = "Auto_Basket", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Basket extends LinearOpMode {
    private Arm RobotArm;
    private Wrist RobotWrist;
    private Claw RobotClaw;

    MecanumDrive drive;
    Action move;

    //starting locations
    public static double CHAMBER_HEADING = 270.0;
    public static Pose2d STARTING_POSE_SPECIMEN = new Pose2d(-16.5, 63.0, Math.toRadians(CHAMBER_HEADING));
    public static double BASKET_START_HEADING = 270.0;
    public static Pose2d STARTING_POSE_BASKET = new Pose2d(16.5, 63.0, Math.toRadians(BASKET_START_HEADING));

    //samples
    public static double SAMPLE_Y = 25.0+24.0-6.5;
    public static double SAMPLE_MOVE = 16.5;


    public static Vector2d YELLOW_FLOOR_SAMPLE = new Vector2d(48.0-10.5, SAMPLE_Y);
    public static double BACKOFF = 10.0;
    public static double YELLOW_SAMPLE_HEADING = 315.0;

    //Basket locations
    public static Vector2d BASKET = new Vector2d(69-10-2.5,69-28-3);
    public static double BASKET_HEADING = 45.0;

    public static double SAFEY = 45.0;
    public static double SAFEY2 = 25.0;
    public static Vector2d SAFE = new Vector2d(SAFEY,SAMPLE_Y);
    public static Vector2d SAFE2 = new Vector2d(SAFEY2,SAMPLE_Y);

    public static double FUDGE = 5.0;

    public static double TEMP_HEADING = 180.0;

    public static double CLAW_CLOSE_WAIT = 0.5;
    public static double CLAW_OPEN_WAIT = 0.5;

    private Action grabSampleFromFloor(MecanumDrive drive, Pose2d start){
        return new SequentialAction(
                new ParallelAction(
                        RobotWrist.wristAction(Wrist.COLLECTIONWRIST),
                        RobotArm.armAction(Arm.COLLECTION_POSITION)
                ),
                new SleepAction(CLAW_CLOSE_WAIT),
                RobotClaw.clawAction(Claw.CLOSEPOSITION),
                new SleepAction(CLAW_CLOSE_WAIT),
                backofAction(drive, start)
        );
    }

    private Action dropInBasket(Action strafe){
        return new SequentialAction(
                new ParallelAction(
                        strafe,
                        new SequentialAction(
                                new SleepAction(0.15),
                                new ParallelAction(
                                        RobotArm.armAction(Arm.PUT_IN_BASKET_POSITION),
                                        RobotWrist.wristAction(Wrist.BASKETANDCHAMBERWRIST)
                                )
                        )
                 ),
                RobotClaw.clawAction(Claw.OPENPOSITION),
                new SleepAction(CLAW_OPEN_WAIT)
        );
    }

    private Action backofAction(MecanumDrive drive, Pose2d start){
        return drive.actionBuilder(start)
                .strafeTo(start.position.plus(new Vector2d(0,BACKOFF)))
                .build();
    }

    private static Vector2d getYellowSamplePosition(int whichvisit){
        if (whichvisit<3)
            return YELLOW_FLOOR_SAMPLE.plus(new Vector2d(SAMPLE_MOVE*(whichvisit-1),3*(whichvisit-1)));
        return YELLOW_FLOOR_SAMPLE.plus(new Vector2d(SAMPLE_MOVE*(whichvisit-1)-FUDGE,3*(whichvisit-1)));
    }
    private Action buildBasket(MecanumDrive drive){
        return new SequentialAction(
                RobotClaw.clawAction(Claw.CLOSEPOSITION),
                //go to Chamber with hand specimen and move in
                dropInBasket(
                        drive.actionBuilder(STARTING_POSE_BASKET)
                            .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                            .build()
                ),
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeToLinearHeading(getYellowSamplePosition(1), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                grabSampleFromFloor(drive, new Pose2d(getYellowSamplePosition(1),Math.toRadians(YELLOW_SAMPLE_HEADING))),
                dropInBasket(
                        drive.actionBuilder(new Pose2d(getYellowSamplePosition(1).minus(new Vector2d(0,BACKOFF)), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                                .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                                .build()
                ),
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeToLinearHeading(getYellowSamplePosition(2), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                grabSampleFromFloor(drive, new Pose2d(getYellowSamplePosition(2),Math.toRadians(YELLOW_SAMPLE_HEADING))),
                dropInBasket(
                        drive.actionBuilder(new Pose2d(getYellowSamplePosition(2).minus(new Vector2d(0,BACKOFF)), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                                .strafeTo(SAFE2)
                                .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                                .build()
                ),
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeTo(SAFE2)
                        .build(),
                new ParallelAction(
                        RobotWrist.wristAction(Wrist.COLLECTIONWRIST),
                        RobotArm.armAction(Arm.COLLECTION_POSITION)
                ),
                drive.actionBuilder(new Pose2d(SAFE2,Math.toRadians(BASKET_HEADING)))
                        .turnTo(Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .strafeToLinearHeading(getYellowSamplePosition(3), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                RobotClaw.clawAction(Claw.CLOSEPOSITION),
                new SleepAction(CLAW_CLOSE_WAIT),
                dropInBasket(
                        drive.actionBuilder(new Pose2d(getYellowSamplePosition(3), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                                .strafeTo(SAFE2)
                                .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                                .build()
                )
        );
    }

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, STARTING_POSE_BASKET);

        RobotArm = new Arm(this);
        RobotClaw = new Claw(this);
        RobotWrist = new Wrist(this);

        RobotArm.init();
        RobotClaw.init();
        RobotWrist.init();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        move = buildBasket(drive);

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

