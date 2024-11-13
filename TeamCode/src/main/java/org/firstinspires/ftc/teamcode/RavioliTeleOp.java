package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
<<<<<<< HEAD
import com.qualcomm.robotcore.util.ElapsedTime;
=======
>>>>>>> 1a006d6696d306b466415a8bafe556a0343cace6

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    RavioliHardware hardware;
<<<<<<< HEAD
    static final double SLOW_SPEED = 0.3;
    static final double FAST_SPEED = 1.0;
    static final double LOW_LAUNCH_POSITION = 0.0;
    static final double MIDDLE_LAUNCH_POSITION = 0.5;
    static final double HIGH_LAUNCH_POSITION = 1.0;
    static final double ARM_SCORE_POSITION = 1.0;
    static final double ARM_REST_POSITION = 0.0;
    double speedConstant;
    boolean fastMode;
    boolean fineControl;
    boolean armScoring;
    ElapsedTime speedSwapButtonTime = null;
    ElapsedTime fineControlButtonTime = null;
    ElapsedTime servoPos1ButtonTime = null;
    ElapsedTime servoPos2ButtonTime = null;
    ElapsedTime servoPos3ButtonTime = null;
    ElapsedTime armPosButtonTime = null;
=======
    Drivetrain drivetrain;
    Intake intake;
    Arm arm;
    Launcher launcher;
>>>>>>> 1a006d6696d306b466415a8bafe556a0343cace6

    @Override
    public void init() {
        hardware = new RavioliHardware();
        hardware.init(hardwareMap);
<<<<<<< HEAD
        fastMode = true;
        fineControl = false;
        armScoring = false;
        speedSwapButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos1ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos2ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos3ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        armPosButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
=======

        drivetrain = new Drivetrain(hardware);
        intake = new Intake(hardware);
        arm = new Arm(hardware);
        launcher = new Launcher(hardware);

>>>>>>> 1a006d6696d306b466415a8bafe556a0343cace6
        telemetry.addData("Status:: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        telemetry.addData("Status:: ", "Started");
        telemetry.update();
    }

    @Override
    public void loop() {
        drive();
        intake();
        moveArm();
        launch();
    }

    private void drive() {
        //check for speed swap for normal mode
        if(gamepad1.square)
            drivetrain.swapSpeed();

        //check for fine control/normal mode swap
        //fine control raises power to the power of 5to make the increase towards 1.0 be more gradual, increasing precision
        //normal mode multiplies power by a speed constant, which the driver can change
        if(gamepad1.triangle)
            drivetrain.swapFineControl();

        //get controller input from joysticks for normal and fine control modes
        double forward, strafe, turn;
        forward = -gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x;
        turn = -gamepad1.right_stick_x;

        //calculate initial powers
        double rightFrontPower = forward - turn - strafe;
        double rightBackPower = forward - turn + strafe;
        double leftFrontPower = forward + turn + strafe;
        double leftBackPower = forward + turn - strafe;

        if(Math.abs(rightFrontPower) > 1 || Math.abs(rightBackPower) > 1 || Math.abs(leftFrontPower) > 1 || Math.abs(leftBackPower) > 1) {
            double max;
            max = Math.max(Math.abs(rightFrontPower), Math.abs(rightBackPower));
            max = Math.max(max, Math.abs(leftFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));

            rightFrontPower /= max;
            rightBackPower /= max;
            leftFrontPower /= max;
            leftBackPower /= max;
        }

        //check input from dpad, which sets maximum power in one direction, overrides fine control and normal modes
        if (gamepad1.dpad_up ) {
            rightFrontPower = 1.0;
            rightBackPower = 1.0;
            leftFrontPower = 1.0;
            leftBackPower = 1.0;
        }
        else if (gamepad1.dpad_down) {
            rightFrontPower = -1.0;
            rightBackPower = -1.0;
            leftFrontPower = -1.0;
            leftBackPower = -1.0;
        }
        else if (gamepad1.dpad_right) {
            rightFrontPower = -1.0;
            rightBackPower = 1.0;
            leftFrontPower = 1.0;
            leftBackPower = -1.0;
        }
        else if (gamepad1.dpad_left) {
            leftFrontPower = -1.0;
            rightBackPower = -1.0;
            rightFrontPower = 1.0;
            leftBackPower = 1.0;
        }
<<<<<<< HEAD

        //set drive motor power
        //test drift fix? pls work
        hardware.rightFront.setPower(rightFrontPower * 0.8);
        hardware.rightBack.setPower(rightBackPower * 0.8);
        hardware.leftFront.setPower(leftFrontPower);
        hardware.leftBack.setPower(leftBackPower);
    }

    private void intake() {
        //check for intake on/off
        if (gamepad2.square) {
            hardware.intakeMotor.setPower(1.0);
            hardware.intakeRampMotor.setPower(1.0);
            telemetry.addData("Intake Status:: ", "On");
        } else {
            hardware.intakeMotor.setPower(0.0);
            hardware.intakeRampMotor.setPower(0.0);
            telemetry.addData("Intake Status:: ", "Off");
        }

        //check for servo position changes
        if(gamepad2.triangle && servoPos1ButtonTime.time() >= 500) {
            hardware.flywheelServoOne.setPosition(LOW_LAUNCH_POSITION);
            hardware.flywheelServoTwo.setPosition(LOW_LAUNCH_POSITION);
            hardware.launcherServo.setPosition(LOW_LAUNCH_POSITION);
            servoPos1ButtonTime.reset();
            telemetry.addData("Launch Position:: ", "LOW");
        }
        else if(gamepad2.cross && servoPos2ButtonTime.time() >= 500) {
            hardware.flywheelServoOne.setPosition(MIDDLE_LAUNCH_POSITION);
            hardware.flywheelServoTwo.setPosition(MIDDLE_LAUNCH_POSITION);
            hardware.launcherServo.setPosition(MIDDLE_LAUNCH_POSITION);
            servoPos2ButtonTime.reset();
            telemetry.addData("Launch Position:: ", "MIDDLE");
        }
        else if(gamepad2.circle && servoPos3ButtonTime.time() >= 500) {
            hardware.flywheelServoOne.setPosition(HIGH_LAUNCH_POSITION);
            hardware.flywheelServoTwo.setPosition(HIGH_LAUNCH_POSITION);
            hardware.launcherServo.setPosition(HIGH_LAUNCH_POSITION);
            servoPos3ButtonTime.reset();
            telemetry.addData("Launch Position:: ", "HIGH");
        }
    }

    private void moveArm() {
        //check for position change
        if(gamepad2.right_bumper && armPosButtonTime.time() >= 500)
            armScoring = !armScoring;

        if(armScoring)
            hardware.armServoOne.setPosition(ARM_SCORE_POSITION);
        else
            hardware.armServoOne.setPosition(ARM_REST_POSITION);
    }

    private void launch() {
        //check for launch
        if(gamepad2.right_trigger > 0.5) {
            hardware.flywheelMotorOne.setPower(1.0);
            hardware.flywheelMotorTwo.setPower(1.0);
            telemetry.addData("Flywheels:: ", "Spinning");
        }
        else {
            hardware.flywheelMotorOne.setPower(0.0);
            hardware.flywheelMotorTwo.setPower(0.0);
            telemetry.addData("Flywheels:: ", "Stopped");
        }
=======
        drivetrain.drive(rightFrontPower, leftFrontPower, rightBackPower, leftBackPower);
    }

    private void intake() {
        //intake on/off
        intake.powerIntake(gamepad2.square, gamepad2.triangle);
    }

    private void moveArm() {
        //check for controller input for arm
        if (gamepad2.dpad_up)
            arm.moveArm(0.05);
        else if (gamepad2.dpad_down)
            arm.moveArm(-0.05);

        //check for claw grab/release
        if(gamepad2.cross)
            arm.swapClawPos();
        arm.moveClaw();
    }

    private void launch() {
        launcher.launch(gamepad2.right_trigger > 0.5, gamepad2.circle);
>>>>>>> 1a006d6696d306b466415a8bafe556a0343cace6
    }
}