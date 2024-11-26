package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Arm Testing", group = "Autonomous Pathing Tuning")

public class ArmTest extends OpMode {
    private Arm RobotArm = new Arm( this, telemetry);
    private int armPosition;
    private double clawPosition;
    private double wristPosition;

    public static int MOTOR_BIG_INC = 100;
    public static int MOTOR_SMALL_INC = 5;
    public static double SERVO_INC = 0.05;

    @Override
    public void init() {
        RobotArm.init();
        RobotArm.close_clawthingy();
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
        RobotArm.MoveArm(armPosition,Arm.MOVESPEED);
        RobotArm.setClawPosition(clawPosition);
        RobotArm.setWristPosition(wristPosition);
        telemetry.addData("Gamepad", gamepad1);
        telemetry.addData("Arm position",RobotArm.getpos());
        telemetry.addData("Wrist Position", RobotArm.getWristPosition());
        telemetry.addData("Claw position", RobotArm.getClawPosition());
    }
}
