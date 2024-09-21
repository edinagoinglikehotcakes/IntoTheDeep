import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Omni Linear OpMode", group="Linear O[Mode" )
public class YAY  extends {

private ElapsedTime runtime = new ElapsedTime();
private DcMotor leftFrontDrive = null;
private DcMotor leftBackDrive = null;
private DcMotor rightFrontDrive = null;
private DcMotor rightBackdrive = null;

    @Override
    public void runOpMode() {



