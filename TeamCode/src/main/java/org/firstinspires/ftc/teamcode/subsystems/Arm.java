package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

public class Arm {

    RavioliHardware hardware;
    double armServo1Pos;
    double armServo2Pos;
    boolean clawGrab;
    ElapsedTime armUpdateTime = null;
    ElapsedTime clawGrabTime = null;

    public Arm(RavioliHardware hardwareInput) {
        hardware = hardwareInput;
        clawGrab = false;
        armServo1Pos = hardware.armServoOne.getPosition();
        armServo2Pos = hardware.armServoTwo.getPosition();
        clawGrabTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        armUpdateTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }
    public void moveArm(double input) {
        if (armUpdateTime.time() >= 100) {
            armServo1Pos -= input;
            armServo2Pos += input;
            armUpdateTime.reset();
        }
        if(armServo1Pos > 1.0)
            armServo1Pos = 1.0;
        if(armServo1Pos < 0.0)
            armServo1Pos = 0.0;
        if (armServo2Pos > 1.0)
            armServo2Pos = 1.0;
        if (armServo2Pos < 0.0)
            armServo2Pos = 0.0;

        //set arm servo positions
        hardware.armServoOne.setPosition(armServo1Pos);
        hardware.armServoTwo.setPosition(armServo2Pos);
    }
    public void swapClawPos () {
        if(clawGrabTime.time() >= 300) {
            clawGrab = !clawGrab;
            clawGrabTime.reset();
        }
    }
    public void moveClaw() {
        if(clawGrab)
            hardware.clawServo.setPosition(1.0);
        else
            hardware.clawServo.setPosition(0.0);
    }
}
