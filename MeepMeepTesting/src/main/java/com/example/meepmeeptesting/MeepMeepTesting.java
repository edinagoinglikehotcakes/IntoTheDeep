package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MeepMeepTesting {
    //starting locations
    public static double CHAMBER_HEADING = 270.0;
    public static Pose2d STARTING_POSE_SPECIMEN = new Pose2d(-16.5, 63.0, Math.toRadians(CHAMBER_HEADING));
    public static double BASKET_START_HEADING = 270.0;
    public static Pose2d STARTING_POSE_BASKET = new Pose2d(16.5, 63.0, Math.toRadians(BASKET_START_HEADING));

    //samples
    public static double SAMPLE_Y = 25.0+24.0;
    public static double SAMPLE_MOVE = 9;

    public static Vector2d TEAM_FLOOR_SAMPLE = new Vector2d(-48.0+24.0, SAMPLE_Y);
    public static double TEAM_SAMPLE_HEADING = 225.0;

    public static Vector2d YELLOW_FLOOR_SAMPLE = new Vector2d(48.0-24.0, SAMPLE_Y);
    public static double YELLOW_SAMPLE_HEADING = 315.0;

    //Basket locations
    public static Vector2d BASKET = new Vector2d(69-20.0,69-20.0);
    public static double BASKET_HEADING = 45.0;

    //Observation locations
    public static Vector2d OBSERVATION = new Vector2d(-55,45);
    public static Vector2d OBSERVATION_BACK = new Vector2d(-55,37);
    public static Vector2d OBSERVATION_WALL = new Vector2d(-55,60);

    public static double OBSERVATION_WAIT = 1.5;
    public static double OBSERVATION_HEADING = 90.0;

    //Chamber locations
    public static double CHAMBER_Y = 24+9+16.0;
    public static double CHAMBER_MOVE = 3;
    public static Vector2d CHAMBER = new Vector2d(-11,CHAMBER_Y);


    public static double HOOK_DISTANCE = 3.0;

    private static Vector2d getChamberPosition(int whichvisit) {
        return CHAMBER.plus(new Vector2d(CHAMBER_MOVE*(whichvisit-1), 0));
    }
    private static Vector2d getHookedSample(int whichvisit){
        Vector2d chamber = CHAMBER.plus(new Vector2d(CHAMBER_MOVE*(whichvisit-1), 0));
        return chamber.minus(new Vector2d(0,HOOK_DISTANCE));
    }
    private static Vector2d getTeamSamplePosition(int whichvisit){
        return TEAM_FLOOR_SAMPLE.minus(new Vector2d(SAMPLE_MOVE*(whichvisit-1),0));
    }

    private static Action buildSpecimens(DriveShim drive){
        return new SequentialAction(
                //go to Chamber with hand specimen and move in
                drive.actionBuilder(STARTING_POSE_SPECIMEN)
                        .strafeTo(getChamberPosition(1))
                        .strafeTo(getHookedSample(1))
                        .build(),
                //TODO open claw
                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(1),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(1))
                        .build(),

                // pickup sample 1
                drive.actionBuilder(new Pose2d(getChamberPosition(1),Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(1),Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                //TODO grab sample 1

                // hand to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(1), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 1

                // pickup sample 2
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(2), Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                //TODO grab sample 2

                // hand to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(2), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 2

                // pick up specimen 1 from human
                // TODO grab specimen 1 from wall

                //go to Chamber with hand specimen and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(2),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(2))
                        .build(),
                //TODO open claw
                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(2),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(2))
                        .build(),

                // pickup sample 3
                drive.actionBuilder(new Pose2d(getChamberPosition(2),Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(getTeamSamplePosition(3), Math.toRadians(TEAM_SAMPLE_HEADING))
                        .build(),
                //TODO grab sample 3

                // hand sample 3 to human
                drive.actionBuilder(new Pose2d(getTeamSamplePosition(3), Math.toRadians(TEAM_SAMPLE_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                //TODO Drop sample 3

                // pick up specimen 2 from human
                // TODO grab specimen 2 from wall

                //go to Chamber with specimen 2 and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(3),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(3))
                        .build(),
                //TODO open claw

                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(3),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(3))
                        .build(),

                // pick up specimen 3 from human
                drive.actionBuilder(new Pose2d(getChamberPosition(3), Math.toRadians(CHAMBER_HEADING)))
                        .strafeToLinearHeading(OBSERVATION,Math.toRadians(OBSERVATION_HEADING))
                        .build(),
                // TODO grab specimen 3 from wall

                //go to Chamber with hand specimen and move in
                drive.actionBuilder(new Pose2d(OBSERVATION,Math.toRadians(OBSERVATION_HEADING)))
                        .strafeToLinearHeading(getChamberPosition(4),Math.toRadians(CHAMBER_HEADING))
                        .strafeTo(getHookedSample(4))
                        .build(),
                //TODO open claw

                //backoff from specimen
                drive.actionBuilder(new Pose2d(getHookedSample(4),Math.toRadians(CHAMBER_HEADING)))
                        .strafeTo(getChamberPosition(4))
                        .build()
        );
    }





    private static Vector2d getYellowSamplePosition(int whichvisit){
        return YELLOW_FLOOR_SAMPLE.plus(new Vector2d(SAMPLE_MOVE*(whichvisit-1),0));
    }

    private static Action buildBasket(DriveShim drive){
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

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(15,18)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .build();
        DriveShim drive = myBot.getDrive();

        Action action = buildBasket(drive);

        myBot.runAction(action);


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}