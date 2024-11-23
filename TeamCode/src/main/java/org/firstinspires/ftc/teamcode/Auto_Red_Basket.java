package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
@Config
@Autonomous(name = "Auto_Red_Basket", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Red_Basket extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;
    private int slow;

    public static double STARTING_X = 135.0;
    public static double STARTING_Y = 54.50;
    public static double HEADING = 180.0;
    public static double HEADING_ADJUST = 30.0;
    public static double DISTANCE = 35.0;
    public static double ENDING_Y = STARTING_Y-DISTANCE;
    public static double TIME = 0.2;

    public static int ACCURACY = 20;
    public static int WAITTIME = 200;
    public static int WAITTIME2 = 220;

    @Override
    public void init() {
        RobotArm.init();
        RobotArm.resetEncoders();
        RobotArm.close_clawthingy();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(STARTING_X,STARTING_Y,Math.toRadians(HEADING)));
        path = follower.pathBuilder()
/*                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(135.000, 51.000, Point.CARTESIAN),
                                new Point(117.172, 32.040, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(117.172, 32.040, Point.CARTESIAN),
                                new Point(130.684, 14.281, Point.CARTESIAN)
                        )
                )
  */
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(STARTING_X, STARTING_Y, Point.CARTESIAN),
                                new Point(STARTING_X-10, ENDING_Y+10, Point.CARTESIAN)
                        )
                )
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(STARTING_X-10, STARTING_Y+10, Point.CARTESIAN),
                                new Point(STARTING_X, ENDING_Y, Point.CARTESIAN)
                        )
                )
                .addParametricCallback(TIME, () -> RobotArm.moveToAutonomousBasket())
                .setPathEndHeadingConstraint(Math.toRadians(HEADING+HEADING_ADJUST))
                .setTangentHeadingInterpolation()
                .build();
        follower.followPath(path,true);
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("busy", follower.isBusy());
        telemetry.addData("armposition", RobotArm.getpos());
        if (!follower.isBusy() && RobotArm.getpos() > Arm.AUTO_PUT_IN_BASKET_POSITION - ACCURACY &&
                RobotArm.getpos() < Arm.AUTO_PUT_IN_BASKET_POSITION + ACCURACY ){
            slow = slow +1;
            telemetry.addData("slow", slow);
            telemetry.update();
            if (slow > WAITTIME) {
                RobotArm.open_clawthingy();
            }
            if (slow > WAITTIME2) {
                RobotArm.moveToStart();
            }
        }
    }
}