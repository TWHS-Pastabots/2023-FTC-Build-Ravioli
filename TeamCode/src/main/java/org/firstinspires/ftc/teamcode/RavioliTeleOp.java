package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    RavioliHardware hardware;
    Drivetrain drivetrain;
    Intake intake;
    Arm arm;
    Launcher launcher;

    @Override
    public void init() {
        hardware = new RavioliHardware();
        hardware.init(hardwareMap);

        drivetrain = new Drivetrain(hardware);
        intake = new Intake(hardware);
        arm = new Arm(hardware);
        launcher = new Launcher(hardware);

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
    }
}