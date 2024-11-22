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
@Autonomous(name = "Auto_Blue_Observation", group = "Autonomous")

public class Auto_Blue_Observation extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;

    @Override
    public void init() {
        RobotArm.init();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(9,50,0));

        path = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(9.000, 50.000, Point.CARTESIAN),
                                new Point(8.494, 7.718, Point.CARTESIAN)
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