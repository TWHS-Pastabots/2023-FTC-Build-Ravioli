package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    RavioliHardware hardware;
    final double SLOW_SPEED = 0.3;
    final double FAST_SPEED = 1.0;
    double speedConstant;
    boolean fastMode;
    boolean fineControl;
    boolean intakeOn;
    ElapsedTime speedSwapButtonTime = null;
    ElapsedTime fineControlButtonTime = null;
    ElapsedTime intakeButtonTime = null;
    ElapsedTime servoPos1ButtonTime = null;
    ElapsedTime servoPos2ButtonTime = null;
    ElapsedTime servoPos3ButtonTime = null;

    @Override
    public void init() {
        hardware = new RavioliHardware();
        hardware.init(hardwareMap);
        fastMode = true;
        fineControl = false;
        intakeOn = false;
        speedSwapButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos1ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos2ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        servoPos3ButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        telemetry.addData("Status:: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
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
            rightFrontPower = Math.pow(rightFrontPower, 5);
            rightBackPower = Math.pow(rightBackPower, 5);
            leftFrontPower = Math.pow(leftFrontPower, 5);
            leftBackPower = Math.pow(leftBackPower, 5);
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
        telemetry.update();

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
        hardware.rightFront.setPower(rightFrontPower);
        hardware.rightBack.setPower(rightBackPower);
        hardware.leftFront.setPower(leftFrontPower);
        hardware.leftBack.setPower(leftBackPower);
    }

    private void intake() {
        //check for intake on/off
        if(gamepad2.square && intakeButtonTime.time() >= 500) {
            intakeOn = !intakeOn;
            intakeButtonTime.reset();
        }

        //check for servo position changes
        if(gamepad2.triangle && servoPos1ButtonTime.time() >= 500) {
            hardware.scaffoldServoOne.setPosition(1.0);
            hardware.scaffoldServoTwo.setPosition(1.0);
            servoPos1ButtonTime.reset();
        }
        else if(gamepad2.cross && servoPos2ButtonTime.time() >= 500) {
            hardware.scaffoldServoOne.setPosition(2.0);
            hardware.scaffoldServoTwo.setPosition(2.0);
            servoPos2ButtonTime.reset();
        }
        else if(gamepad2.circle && servoPos3ButtonTime.time() >= 500) {
            hardware.scaffoldServoOne.setPosition(3.0);
            hardware.scaffoldServoTwo.setPosition(3.0);
            servoPos3ButtonTime.reset();
        }
    }

    private void launch() {
        //check for launch
        if(gamepad2.right_trigger > 0.5) {
            hardware.flywheelMotorOne.setPower(1.0);
            hardware.flywheelMotorTwo.setPower(1.0);
        }
        else {
            hardware.flywheelMotorOne.setPower(0.0);
            hardware.flywheelMotorTwo.setPower(0.0);
        }
    }
}