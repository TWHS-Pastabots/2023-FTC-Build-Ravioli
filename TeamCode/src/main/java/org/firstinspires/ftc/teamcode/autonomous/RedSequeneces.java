package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

public class RedSequeneces {

    SampleMecanumDrive drive;
    RavioliHardware hardware;
    Intake intake;
    Launcher launcher;
    Utilities utilities;

    //Trajectories
    Trajectory toRing;
    Trajectory strafeStart;
    Trajectory backUpToRing;
    Trajectory alignShot1;
    Trajectory alignShot2;
    Trajectory park1, park2, park3;

    Pose2d startPose = new Pose2d(-60,12,Math.toRadians(90));

    //Pick up ring
    Vector2d initialForward= new Vector2d(-35, 12);
    Vector2d alignToRing = new Vector2d(-25, 48);
    Vector2d intakeRing = new Vector2d(-40, 48);

    //Align to shoot
    Vector2d centerToShoot = new Vector2d(-25,12);
    Vector2d forwardToShoot = new Vector2d(12,12);

    //Parking position
    Vector2d parking1 = new Vector2d(36,12);
    Vector2d parking2 = new Vector2d(12, 36);
    Vector2d parking3 = new Vector2d(-12,12);


    public RedSequeneces(RavioliHardware hardware, SampleMecanumDrive drive) {
        this.hardware = hardware;
        intake = new Intake(hardware);
        launcher = new Launcher(hardware);
        utilities = new Utilities();
        this.drive = drive;

        toRing = drive.trajectoryBuilder(startPose)
                .splineTo(initialForward, Math.toRadians(180))
                .splineTo(alignToRing, Math.toRadians(180))
                .build();

        backUpToRing = drive.trajectoryBuilder(toRing.end())
                .splineTo(intakeRing, Math.toRadians(180))
                .build();

        strafeStart = drive.trajectoryBuilder(startPose)
                .strafeLeft(4)
                .build();
        alignShot1 = drive.trajectoryBuilder(strafeStart.end())
              /*  .splineTo(centerToShoot, Math.toRadians(90))
                .splineTo(forwardToShoot, Math.toRadians(80))*/
                .forward(-120)
                .build();

        alignShot2 = drive.trajectoryBuilder(alignShot1.end())
                .strafeLeft(12)
                .build();

        park1 = drive.trajectoryBuilder(alignShot2.end())
                .splineTo(parking1, Math.toRadians(90))
                .build();

        park2 = drive.trajectoryBuilder(alignShot1.end())
                .splineTo(parking2, Math.toRadians(180))
                .build();

        park3 = drive.trajectoryBuilder(alignShot1.end())
                .splineTo(parking3, Math.toRadians(180))
                .build();
    }

    public void runRed1() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        /*drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);*/

        //shoot
        drive.followTrajectory(alignShot1);
        drive.followTrajectory(alignShot2);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);

        //park
        drive.followTrajectory(park1);
    }

    public void runRed2() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);

        //shoot
        drive.followTrajectory(alignShot1);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);

        //park
        drive.followTrajectory(park2);
    }

    public void runRed3() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);

        //shoot
        drive.followTrajectory(alignShot1);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);

        //park
        drive.followTrajectory(park3);
    }
}
