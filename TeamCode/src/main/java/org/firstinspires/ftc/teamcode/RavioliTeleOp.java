package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    RavioliHardware hardware;
    static final double DRIFT_CONSTANT = 0.8;
    static final double SLOW_SPEED = 0.3;
    static final double FAST_SPEED = 1.0;
    static final int INITIAL_POS_SHIFT = -80;
    static final int HIGH_LAUNCH_POS = 60;
    static final int LOW_LAUNCH_POS = 0;
    double speedConstant;
    double armServo1Pos;
    double armServo2Pos;
    boolean clawGrab;
    boolean fastMode;
    boolean fineControl;
    boolean highLaunch;
    ElapsedTime clawGrabButtonTime = null;
    ElapsedTime speedSwapButtonTime = null;
    ElapsedTime fineControlButtonTime = null;
    ElapsedTime launchHeightButtonTime = null;

    @Override
    public void init() {
        hardware = new RavioliHardware();
        hardware.init(hardwareMap);
        fastMode = true;
        fineControl = false;
        clawGrab = false;
        highLaunch = false;
        armServo1Pos = hardware.armServoOne.getPosition();
        armServo2Pos = hardware.armServoTwo.getPosition();
        speedSwapButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        clawGrabButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        telemetry.addData("Status:: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        //move launcher scaffold down
        hardware.launcherMotor.setTargetPosition(INITIAL_POS_SHIFT);
        hardware.launcherMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.launcherMotor.setTargetPositionTolerance(3);
        hardware.launcherMotor.setPower(1.0);
        hardware.launcherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //telemetry updates
        telemetry.addData("Status:: ", "Started");
        telemetry.addData("Current Drive Mode:: ", "Fast");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("Status:: ", "Started");
        drive();
        intake();
        launch();
        moveArm();
        telemetry.update();
    }

    private void drive() {
        //check for speed swap for normal mode
        if(gamepad1.square && speedSwapButtonTime.time() >= 200) {
            fastMode = !fastMode;
            speedSwapButtonTime.reset();
        }

        //check for fine control/normal mode swap
        //fine control raises power to the power of 5to make the increase towards 1.0 be more gradual, increasing precision
        //normal mode multiplies power by a speed constant, which the driver can change
        if(gamepad1.triangle && fineControlButtonTime.time() >= 200) {
            fineControl = !fineControl;
            fineControlButtonTime.reset();
        }

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

        //adjust power based on mode
        if(fineControl) {
            rightFrontPower = Math.pow(rightFrontPower, 7);
            rightBackPower = Math.pow(rightBackPower, 7);
            leftFrontPower = Math.pow(leftFrontPower, 7);
            leftBackPower = Math.pow(leftBackPower, 7);
            telemetry.addData("Current Drive Mode:: ", "Fine Control");
        }
        else {
            if (fastMode) {
                speedConstant = FAST_SPEED;
                telemetry.addData("Current Drive Mode:: ", "Fast");
            }
            else {
                speedConstant = SLOW_SPEED;
                telemetry.addData("Current Drive Mode:: ", "Slow");
            }
            rightFrontPower = rightFrontPower * speedConstant;
            rightBackPower = rightBackPower * speedConstant;
            leftFrontPower = leftFrontPower * speedConstant;
            leftBackPower = leftBackPower * speedConstant;
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

        //set drive motor power
        hardware.rightFront.setPower(rightFrontPower * DRIFT_CONSTANT);
        hardware.rightBack.setPower(rightBackPower * DRIFT_CONSTANT);
        hardware.leftFront.setPower(leftFrontPower);
        hardware.leftBack.setPower(leftBackPower);
    }

    private void intake() {
        //check for intake on/off
        if (gamepad2.square) {
            hardware.intakeMotor.setPower(1.0);
            telemetry.addData("Intake Status:: ", "On");
        }
        else {
            hardware.intakeMotor.setPower(0.0);
            telemetry.addData("Intake Status:: ", "Off");
        }

    }

    private void moveArm() {
        //check for controller input for arm
        armServo1Pos += gamepad2.left_stick_y;
        armServo2Pos -= gamepad2.right_stick_x;

        //ensure arm servo position limits
        if(armServo1Pos > 1.0)
            armServo1Pos = 1.0;
        else if(armServo1Pos < 0.0)
            armServo1Pos = 0.0;
        else if (armServo2Pos > 1.0)
            armServo2Pos = 1.0;
        else if (armServo2Pos < 0.0)
            armServo2Pos = 0.0;

        //set arm servo positions
        hardware.armServoOne.setPosition(armServo1Pos);
        hardware.armServoTwo.setPosition(armServo2Pos);


        //check for claw grab/release
        if(gamepad2.circle && clawGrabButtonTime.time() >= 500) {
            clawGrab = !clawGrab;
            clawGrabButtonTime.reset();
        }

        //set claw servo position
        if(clawGrab)
            hardware.clawServo.setPosition(1.0);
        else
            hardware.clawServo.setPosition(0.0);
    }

    private void launch() {
        //check for scaffold height change
        if(gamepad2.triangle && launchHeightButtonTime.time() >= 500) {
            highLaunch = !highLaunch;
            launchHeightButtonTime.reset();
        }

        //move scaffold
        if(highLaunch) {
            hardware.launcherMotor.setTargetPosition(HIGH_LAUNCH_POS);
            telemetry.addData("Launcher Pos:: ", "High");
        }
        else {
            hardware.launcherMotor.setTargetPosition(LOW_LAUNCH_POS);
            telemetry.addData("Launcher Pos:: ", "Low");
        }

        hardware.launcherMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.launcherMotor.setPower(1.0);


        //check for flywheel spin-up
        if(gamepad2.right_trigger > 0.5) {
            hardware.flywheelMotorOne.setPower(1.0);
            telemetry.addData("Flywheels:: ", "Spinning");
        }
        else {
            hardware.flywheelMotorOne.setPower(0.0);
            telemetry.addData("Flywheels:: ", "Stopped");
        }

        //set launch servo position
        if(gamepad2.circle)
            hardware.launcherServo.setPosition(1.0);
        else
            hardware.launcherServo.setPosition(0.0);
    }
}