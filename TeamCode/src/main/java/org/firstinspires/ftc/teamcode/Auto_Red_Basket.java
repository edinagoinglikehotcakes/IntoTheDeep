package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
@Config
@Autonomous(name = "Auto_Red_Basket", group = "Autonomous")

public class Auto_Red_Basket extends OpMode {
    private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;

    @Override
    public void init() {
        RobotArm.init();
        robotchasis.init();
        follower = new Follower(hardwareMap);
        path = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(134.355, 59.664, Point.CARTESIAN),
                                new Point(122.916, 59.664, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(122.916, 59.664, Point.CARTESIAN),
                                new Point(108.561, 36.112, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(108.561, 36.112, Point.CARTESIAN),
                                new Point(125.832, 16.374, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        follower.followPath(path);
    }

    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            follower.followPath(path);

        }
    }
}