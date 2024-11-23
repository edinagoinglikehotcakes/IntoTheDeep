package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

@Config
@Autonomous(name = "Auto_Blue_Basket", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Blue_Basket extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;
    private int slow;

    public static double STARTING_X = 9.0;
    public static double STARTING_Y = 54.50;
    public static double HEADING = 0.0;
    public static double DISTANCE = 35.0;
    public static double ENDING_Y = STARTING_Y+DISTANCE;
    public static double TIME = 0.2;

    public static int ACCURACY = 20;

    public static int WAITTIME = 200;
    public static int WAITTIME2 = 220;

    @Override
    public void init() {
        RobotArm.init();
        slow = 0;
        RobotArm.resetEncoders();
        RobotArm.close_clawthingy();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(STARTING_X,STARTING_Y,Math.toRadians(HEADING)));

        path = follower.pathBuilder()
 /*               .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(9.000, 89.500, Point.CARTESIAN),
                                new Point(33.202, 89.371, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(33.202, 89.371, Point.CARTESIAN),
                                new Point(42.661, 109.061, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(42.661, 109.061, Point.CARTESIAN),
                                new Point(16.987, 126.241, Point.CARTESIAN)
                        )
                )

  */
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(STARTING_X, STARTING_Y, Point.CARTESIAN),
                                new Point(STARTING_X+10, ENDING_Y-10, Point.CARTESIAN)
                        )
                )
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(STARTING_X+10, STARTING_Y-10, Point.CARTESIAN),
                                new Point(STARTING_X, ENDING_Y, Point.CARTESIAN)
                        )
                )

                .addParametricCallback(TIME, () -> RobotArm.moveToAutonomousBasket())
                .setTangentHeadingInterpolation()
                .build();
        follower.followPath(path,true);
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("busy", follower.isBusy());
        telemetry.addData("armposition", RobotArm.getpos());
        telemetry.update();
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
            //do something after path
        }
    }
}