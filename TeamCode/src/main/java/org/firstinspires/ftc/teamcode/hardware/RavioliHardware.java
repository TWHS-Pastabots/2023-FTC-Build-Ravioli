package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class RavioliHardware {

    public DcMotorEx rightFront;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx leftBack;
    public DcMotorEx[] driveMotors;
    public DcMotorEx intakeMotor;
    public DcMotorEx intakeRampMotor;
    public DcMotorEx flywheelMotorOne;
    public DcMotorEx flywheelMotorTwo;
    public Servo scaffoldServoOne;
    public Servo scaffoldServoTwo;

    public RavioliHardware() {
    }

    public void init(HardwareMap hardwareMap) {
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntakeMotors(hardwareMap);
        initializeFlywheelMotors(hardwareMap);
        initializeScaffoldServos(hardwareMap);
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
        //set up intake motors
        intakeMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.INTAKE_MOTOR);
        intakeRampMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.INTAKE_MOTOR);

        //set motors to desired settings
        intakeMotor.setPower(0.0);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeRampMotor.setPower(0.0);
        intakeRampMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeRampMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initializeFlywheelMotors(HardwareMap hardwareMap) {
        //set up launcher motors
        flywheelMotorOne = hardwareMap.get(DcMotorEx.class, HardwareIDs.FLYWHEEL_1_MOTOR);
        flywheelMotorTwo = hardwareMap.get(DcMotorEx.class, HardwareIDs.FLYWHEEL_2_MOTOR);

        //set motors to desired settings
        flywheelMotorOne.setPower(0.0);
        flywheelMotorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotorOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheelMotorTwo.setPower(0.0);
        flywheelMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotorTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initializeScaffoldServos(HardwareMap hardwareMap) {
        //set up scaffold servos
        scaffoldServoOne = hardwareMap.get(Servo.class, HardwareIDs.SCAFFOLD_SERVO_ONE);
        scaffoldServoTwo = hardwareMap.get(Servo.class, HardwareIDs.SCAFFOLD_SERVO_TWO);

        //set servos to desired settings
        scaffoldServoOne.setPosition(0.0);
        scaffoldServoTwo.setPosition(0.0);
    }
}
