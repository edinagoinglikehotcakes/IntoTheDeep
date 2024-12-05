package org.firstinspires.ftc.teamcode.Opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Arm;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Wrist;
import org.firstinspires.ftc.teamcode.robot.chasis;

@TeleOp(name="ServoTest", group="Linear OpMode")
@Config
public class ServoTest extends LinearOpMode {
     // Declare OpMode members for each of the 4 motors.
        private ElapsedTime runtime = new ElapsedTime();
        private chasis robotchasis;
        private Arm RobotArm;
        private Wrist RobotWrist;
        private Claw RobotClaw;

        public static double WRISTPOS = 0.1;
        public static double CLAWPOS = 0.1;

        @Override
        public void runOpMode() {

            robotchasis = new chasis(this);
            RobotArm = new Arm(this);
            RobotClaw = new Claw(this);
            RobotWrist = new Wrist(this);

            // Initialize the hardware variables. Note that the strings used here must correspond
            // to the names assigned during the robot configuration step on the DS or RC devices.
            robotchasis.init();
            RobotArm.init();
            RobotWrist.init();
            RobotClaw.init();
            // ########################################################################################
            // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
            // ########################################################################################
            // Most robots need the motors on one side to be reversed to drive forward.
            // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
            // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
            // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
            // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
            // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
            // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.


            // Wait for the game to start (driver presses START)

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            waitForStart();
            runtime.reset();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                if (gamepad1.a) {
                    RobotWrist.setWristPosition(WRISTPOS);
                } else if (gamepad1.b) {
                    RobotClaw.setClawPosition(CLAWPOS);

                }
                RobotClaw.update();
                RobotWrist.update();
                telemetry.update();

            }

        }

    }

