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
    public DcMotorEx launcherMotor;
    public DcMotorEx flywheelMotor;
    public Servo launcherServo;
    public Servo armServoOne;
    public Servo armServoTwo;
    public Servo clawServo;

    public RavioliHardware() {
    }

    public void init(HardwareMap hardwareMap) {
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntake(hardwareMap);
        initializeFlywheels(hardwareMap);
        initializeScaffold(hardwareMap);
        initializeArm(hardwareMap);
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
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setPower(0.0);
        }
    }

    private void initializeIntake(HardwareMap hardwareMap) {
        //set up intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.INTAKE_MOTOR);

        //set motor to desired settings
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setPower(0.0);
    }

    private void initializeFlywheels(HardwareMap hardwareMap) {
        //set up launcher motors
        flywheelMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.FLYWHEEL_MOTOR);

        //set motors to desired settings
        flywheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheelMotor.setPower(0.0);
    }

    private void initializeScaffold(HardwareMap hardwareMap) {
        //set up scaffold motors
        launcherMotor = hardwareMap.get(DcMotorEx.class, HardwareIDs.LAUNCHER_MOTOR);

        //set motor to desired settings
        launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherMotor.setPower(1.0);

        //set up scaffold servos
        launcherServo = hardwareMap.get(Servo.class, HardwareIDs.LAUNCHER_SERVO);

        //set servos to desired settings
        launcherServo.setPosition(0.8);
    }

    private void initializeArm(HardwareMap hardwareMap) {
        //set up arm servos
        armServoOne = hardwareMap.get(Servo.class, HardwareIDs.ARM_SERVO_1);
        armServoTwo = hardwareMap.get(Servo.class, HardwareIDs.ARM_SERVO_2);

        //set arm servos to desired settings
        armServoOne.setPosition(1.0);
        armServoTwo.setPosition(0.0);

        //set up claw servo
        clawServo = hardwareMap.get(Servo.class, HardwareIDs.CLAW_SERVO);

        //set claw servo to desired settings
        clawServo.setPosition(0.0);
    }
}
