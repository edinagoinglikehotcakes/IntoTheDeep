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
@Autonomous(name = "Auto_Red_Basket", group = "Autonomous")

public class Auto_Red_Basket extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;

    @Override
    public void init() {
        RobotArm.init();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(135,54.5,180));
        path = follower.pathBuilder()
                .addPath(
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
                .setTangentHeadingInterpolation()
                .build();
        follower.followPath(path,true);
    }

    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            RobotArm.moveToBasket();
            RobotArm.open_clawthingy();
        }
    }
}