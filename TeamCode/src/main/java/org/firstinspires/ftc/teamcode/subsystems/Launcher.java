package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

public class Launcher {

    RavioliHardware hardware;

    public Launcher (RavioliHardware hardwareInput) {
        hardware = hardwareInput;
    }

    public void launch(boolean flywheelOn, boolean ringPush) {

        if(flywheelOn)
            hardware.flywheelMotor.setPower(1.0);
        else
            hardware.flywheelMotor.setPower(0.0);

        if(ringPush)
            hardware.launcherServo.setPosition(1.0);
        else
            hardware.launcherServo.setPosition(0.2);
    }
}
