package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class Hardware {

    public DcMotorEx rightFront;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx leftBack;
    public DcMotorEx[] driveMotors;
    public DcMotorEx intakeMotor;
    public DcMotorEx flywheel1Motor;
    public DcMotorEx flywheel2Motor;

    public void init(HardwareMap hardwareMap) {
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntakeMotors(hardwareMap);
        initializeLauncherMotors(hardwareMap);
    }

    private void initializeDriveMotors(HardwareMap hardwareMap) {
        //set up drive motors
        rightFront = hardwareMap.get(DcMotorEx.class, HardwareIDs.RIGHT_FRONT_MOTOR);
        rightBack = hardwareMap.get(DcMotorEx.class, HardwareIDs.RIGHT_BACK_MOTOR);
        leftFront = hardwareMap.get(DcMotorEx.class, HardwareIDs.LEFT_FRONT_MOTOR);
        leftBack = hardwareMap.get(DcMotorEx.class, HardwareIDs.LEFT_BACK_MOTOR);

        //set left side to reverse
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //set motors to desired settings
        driveMotors = new DcMotorEx[] {rightFront, rightBack, leftFront, leftBack};
        for(DcMotorEx motor: driveMotors){
            motor.setPower(0.0);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    private void initializeIntakeMotors(HardwareMap hardwareMap) {
        //set up intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.INTAKE_MOTOR);

        //set motor to desired settings
        intakeMotor.setPower(0.0);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initializeLauncherMotors(HardwareMap hardwareMap) {
        //set up launcher motors
        flywheel1Motor = hardwareMap.get(DcMotorEx.class, HardwareIDs.FLYWHEEL_1_MOTOR);
        flywheel2Motor = hardwareMap.get(DcMotorEx.class, HardwareIDs.FLYWHEEL_2_MOTOR);

        //set motors to desired settings
        flywheel1Motor.setPower(0.0);
        flywheel1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel1Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheel2Motor.setPower(0.0);
        flywheel2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
