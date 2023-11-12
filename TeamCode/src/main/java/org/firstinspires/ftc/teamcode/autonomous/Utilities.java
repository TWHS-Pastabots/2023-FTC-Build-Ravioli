package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Utilities {

    public void wait(int millis, Telemetry telemetry)
    {
        ElapsedTime waitTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (waitTime.time() < millis)
        {
            telemetry.addData("Status", "Waiting");
            telemetry.addData("Time Left", "" + (millis - waitTime.time()));
            telemetry.update();
        }
    }

    public void wait(int millis)
    {
        ElapsedTime waitTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (waitTime.time() < millis)
        {
            continue;
        }
    }
}
