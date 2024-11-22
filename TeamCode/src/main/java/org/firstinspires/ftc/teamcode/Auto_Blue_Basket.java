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
@Autonomous(name = "Auto_Blue_Basket", group = "Autonomous")

public class Auto_Blue_Basket extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;

    @Override
    public void init() {
        RobotArm.init();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(9,54.5,0));

        path = follower.pathBuilder()
                .addPath(
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
                .setTangentHeadingInterpolation()
                .build();
        follower.followPath(path,true);
    }

    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            //do something after path
        }
    }
}