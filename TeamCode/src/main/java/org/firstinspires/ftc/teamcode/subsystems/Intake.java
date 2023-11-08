package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

public class Intake {
    RavioliHardware hardware;

    public Intake (RavioliHardware hardwareInput) {
        hardware = hardwareInput;
    }

    public void powerIntake(boolean intakeOn) {
        if(intakeOn)
            hardware.intakeMotor.setPower(1.0);
        else
            hardware.intakeMotor.setPower(0.0);
    }
}
