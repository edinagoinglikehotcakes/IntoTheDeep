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
@Autonomous(name = "Auto_Blue_Observation", group = "Autonomous", preselectTeleOp = "Manual Driving")

public class Auto_Blue_Observation extends OpMode {
    //private chasis robotchasis = new chasis(this);
    private Arm RobotArm = new Arm(this, telemetry);
    private Follower follower;
    private PathChain path;
//    private Telemetry telemetryA;

    public static double STARTING_X = 9.0;
    public static double STARTING_Y = 57.0;
    public static double DISTANCE = 40.0;
    public static double ENDING_Y = STARTING_Y-DISTANCE;
    public static double HEADING = 0.0;

    @Override
    public void init() {
        RobotArm.init();


        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(STARTING_X,STARTING_Y,Math.toRadians(HEADING)));
//        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());



        path = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(STARTING_X, STARTING_Y, Point.CARTESIAN),
                                new Point(STARTING_X, ENDING_Y, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(HEADING))
                .build();


        follower.followPath(path,true);
    }

    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            //do something after path
        }

/*        follower.telemetryDebug(telemetryA);
        telemetryA.update();
*/
    }
}