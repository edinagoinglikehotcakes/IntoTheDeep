package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Wrist;

@Autonomous(name="Arm Reset", group = "Autonomous Pathing Tuning")

public class ArmTest extends OpMode {
    private Arm RobotArm;
    private Wrist RobotWrist;
    private Claw RobotClaw;    private int armPosition;
    private double clawPosition;
    private double wristPosition;

    public static int MOTOR_BIG_INC = 100;
    public static int MOTOR_SMALL_INC = 5;
    public static double SERVO_INC = 0.05;

    @Override
    public void init() {
        RobotArm = new Arm(this);
        RobotClaw = new Claw(this);
        RobotWrist = new Wrist(this);
        RobotArm.init();
        RobotWrist.init();
        RobotClaw.init();
        RobotClaw.close_clawthingy();
        armPosition = 0;
        clawPosition = 0.0;
        wristPosition = 0.0;
    }

    private int clampARM(int val) {
        if (val<Arm.MINARM) return Arm.MINARM;
        if (val>Arm.MAXARM) return Arm.MAXARM;
        return val;
    }
    private double clampServo(double val) {
        if (val<0.0) return 0.0;
        if (val>1.0) return 1.0;
        return val;
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            clawPosition = clampServo(clawPosition+SERVO_INC);
        }
        if (gamepad1.left_bumper) {
            clawPosition = clampServo(clawPosition-SERVO_INC);
        }
        if (gamepad1.start){
            RobotArm.resetEncoders();
        }
        if (gamepad1.a) {
            armPosition = clampARM(armPosition+MOTOR_BIG_INC);
        }
        if (gamepad1.b) {
            armPosition = clampARM(armPosition-MOTOR_BIG_INC);
        }
        if (gamepad1.x) {
            armPosition = clampARM(armPosition+MOTOR_SMALL_INC);
        }
        if (gamepad1.y) {
            armPosition = clampARM(armPosition-MOTOR_SMALL_INC);
        }
        if (gamepad1.dpad_up) {
            wristPosition = clampServo(wristPosition+SERVO_INC);
        }
        if (gamepad1.dpad_down) {
            wristPosition = clampServo(wristPosition-SERVO_INC);
        }
        RobotArm.movePos(armPosition);
        RobotClaw.setClawPosition(clawPosition);
        RobotWrist.setWristPosition(wristPosition);
        telemetry.addData("Gamepad", gamepad1);
        telemetry.addData("Arm position",RobotArm.getPos());
        telemetry.addData("Wrist Position", RobotWrist.getWristPosition());
        telemetry.addData("Claw position", RobotClaw.getClawPosition());
    }
}
