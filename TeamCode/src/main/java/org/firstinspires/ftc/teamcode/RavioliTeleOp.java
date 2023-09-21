package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    RavioliHardware hardware;
    final double SLOW_SPEED = 0.5;
    final double FAST_SPEED = 0.8;
    double speedConstant;
    boolean fineControl;
    boolean intakeOn;
    ElapsedTime speedSwapButtonTime = null;
    ElapsedTime fineControlButtonTime = null;
    ElapsedTime intakeButtonTime = null;

    @Override
    public void init() {
        hardware = new RavioliHardware();
        hardware.init(hardwareMap);
        speedConstant = FAST_SPEED;
        fineControl = false;
        intakeOn = false;
        speedSwapButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
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
        if(gamepad1.square && speedSwapButtonTime.time() >= 500) {
            if(speedConstant == FAST_SPEED)
                speedConstant = SLOW_SPEED;
            if(speedConstant == SLOW_SPEED)
                speedConstant = FAST_SPEED;
            speedSwapButtonTime.reset();
        }

        //check for fine control/normal mode swap
        //fine control squares power to make the increase towards 1.0 be more gradual, increasing precision
        //normal mode multiplies power by a speed constant, which the driver can change
        if(gamepad1.triangle && fineControlButtonTime.time() >= 500) {
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
            rightFrontPower = Math.pow(rightFrontPower, 2) * Math.signum(rightFrontPower);
            rightBackPower = Math.pow(rightBackPower, 2) * Math.signum(rightBackPower);
            leftFrontPower = Math.pow(leftFrontPower, 2) * Math.signum(leftFrontPower);
            leftBackPower = Math.pow(leftBackPower, 2) * Math.signum(leftBackPower);
            telemetry.addData("Current Drive Mode:: ", "Fine Control");
        }
        else {
            rightFrontPower = rightFrontPower * speedConstant;
            rightBackPower = rightBackPower * speedConstant;
            leftFrontPower = leftFrontPower * speedConstant;
            leftBackPower = leftBackPower * speedConstant;
            if(speedConstant == FAST_SPEED)
                telemetry.addData("Current Drive Mode:: ", "Fast");
            else if (speedConstant == SLOW_SPEED)
                telemetry.addData("Current Drive Mode:: ", "Slow");
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