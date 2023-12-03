package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;

public class Drivetrain {

    RavioliHardware hardware;
    static final double SLOW_SPEED = 0.3;
    static final double DRIFT_CONSTANT = 0.7;
    boolean fastMode;
    boolean fineControl;
    ElapsedTime speedSwapTime;
    ElapsedTime fineControlTime;


    public Drivetrain (RavioliHardware hardwareInput) {
        hardware = hardwareInput;
        fastMode = true;
        fineControl = false;
        speedSwapTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    public void swapFineControl() {
        if(fineControlTime.time() >= 300) {
            fineControl = !fineControl;
            fineControlTime.reset();
        }
    }

    public void swapSpeed() {
        if(speedSwapTime.time() >= 300) {
            fastMode = !fastMode;
            speedSwapTime.reset();
        }
    }

    public void drive(double rF, double lF, double rB, double lB) {
        if(fineControl)
            fineControlDrive(rF, lF, rB, lB);
        else
            normalDrive(rF, lF, rB, lB);
    }

    private void normalDrive(double rF, double lF, double rB, double lB) {
        if(!fastMode) {
            rF *= SLOW_SPEED;
            rB *= SLOW_SPEED;
            lF *= SLOW_SPEED;
            lB *= SLOW_SPEED;
        }

        hardware.rightFront.setPower(rF * DRIFT_CONSTANT);
        hardware.rightBack.setPower(rB);
        hardware.leftFront.setPower(lF * DRIFT_CONSTANT);
        hardware.leftBack.setPower(lB);
    }

    private void fineControlDrive(double rF, double lF, double rB, double lB) {
        rF = Math.pow(rF, 7);
        rB = Math.pow(rB, 7);
        lF = Math.pow(lF, 7);
        lB = Math.pow(lB, 7);

        hardware.rightFront.setPower(rF * DRIFT_CONSTANT);
        hardware.rightBack.setPower(rB);
        hardware.leftFront.setPower(lF * DRIFT_CONSTANT);
        hardware.leftBack.setPower(lB);
    }
}
