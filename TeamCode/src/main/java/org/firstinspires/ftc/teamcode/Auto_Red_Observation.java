package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

@Config
@Autonomous (name = "Auto_Red_Observation", group = "Autonomous")

public class Auto_Red_Observation extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;


    @Override
    public void init() {
        RobotArm.init();


        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(135,89.5,180));
/*
        path = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(0, 0, Point.CARTESIAN),
                                new Point(24.0, 0, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
                */


        path = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(134.355, 84.336, Point.CARTESIAN),
                                new Point(128.972, 84.336, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(128.972, 84.336, Point.CARTESIAN),
                                new Point(134.355, 126.953, Point.CARTESIAN)
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


