package org.firstinspires.ftc.teamcode.Opmodes;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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
    public static double SAMPLE_Y = 25.0+24.0;
    public static double SAMPLE_MOVE = 9;


    public static Vector2d YELLOW_FLOOR_SAMPLE = new Vector2d(48.0-24.0, SAMPLE_Y);
    public static double YELLOW_SAMPLE_HEADING = 315.0;

    //Basket locations
    public static Vector2d BASKET = new Vector2d(69-20.0,69-20.0);
    public static double BASKET_HEADING = 45.0;

    private static Vector2d getYellowSamplePosition(int whichvisit){
        return YELLOW_FLOOR_SAMPLE.plus(new Vector2d(SAMPLE_MOVE*(whichvisit-1),0));
    }
    private static Action buildBasket(MecanumDrive drive){
        return new SequentialAction(
                //go to Chamber with hand specimen and move in
                drive.actionBuilder(STARTING_POSE_BASKET)
                        .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                        .build(),
                //move arm to basket
                //open claw
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeToLinearHeading(getYellowSamplePosition(1), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                //grab sample
                drive.actionBuilder(new Pose2d(getYellowSamplePosition(1), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                        .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                        .build(),
                //move arm to basket
                //open claw
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeToLinearHeading(getYellowSamplePosition(2), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                //grab sample
                drive.actionBuilder(new Pose2d(getYellowSamplePosition(2), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                        .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                        .build(),
                //move arm to basket
                //open claw
                drive.actionBuilder(new Pose2d(BASKET,Math.toRadians(BASKET_HEADING)))
                        .strafeToLinearHeading(getYellowSamplePosition(3), Math.toRadians(YELLOW_SAMPLE_HEADING))
                        .build(),
                //grab sample
                drive.actionBuilder(new Pose2d(getYellowSamplePosition(3), Math.toRadians(YELLOW_SAMPLE_HEADING)))
                        .strafeToLinearHeading(BASKET, Math.toRadians(BASKET_HEADING))
                        .build()
                //move arm to basket
                //open claw

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

