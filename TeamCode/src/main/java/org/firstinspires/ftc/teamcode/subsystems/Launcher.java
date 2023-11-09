package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

public class Launcher {

    RavioliHardware hardware;
    static final int INITIAL_POS_SHIFT = -80;
    static final int HIGH_LAUNCH_POS = 60;
    static final int LOW_LAUNCH_POS = 0;
    boolean highLaunch;
    ElapsedTime launchHeightSwapTime;

    public Launcher (RavioliHardware hardwareInput) {
        hardware = hardwareInput;
        highLaunch = false;
        launchHeightSwapTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    public void initialHeightShift() {
        hardware.launcherMotor.setTargetPosition(INITIAL_POS_SHIFT);
        hardware.launcherMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.launcherMotor.setTargetPositionTolerance(3);
        hardware.launcherMotor.setPower(1.0);
        hardware.launcherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void swapHeight() {
        if(launchHeightSwapTime.time() >= 300) {
            highLaunch = !highLaunch;
            launchHeightSwapTime.reset();
        }
    }

    public void launch(boolean flywheelOn, boolean ringPush) {
        if(highLaunch)
            hardware.launcherMotor.setTargetPosition(HIGH_LAUNCH_POS);
        else
            hardware.launcherMotor.setTargetPosition(LOW_LAUNCH_POS);

        hardware.launcherMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.launcherMotor.setPower(1.0);

        if(flywheelOn)
            hardware.flywheelMotor.setPower(1.0);
        else
            hardware.flywheelMotor.setPower(0.0);

        if(ringPush)
            hardware.launcherServo.setPosition(1.0);
        hardware.launcherServo.setPosition(0.0);
    }
}
