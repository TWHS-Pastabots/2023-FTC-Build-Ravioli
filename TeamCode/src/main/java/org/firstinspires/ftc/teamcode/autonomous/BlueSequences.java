package org.firstinspires.ftc.teamcode.autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.RavioliHardware;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

public class BlueSequences {

    SampleMecanumDrive drive;
    RavioliHardware hardware;
    Intake intake;
    Launcher launcher;
    Utilities utilities;

    //Trajectories
    Trajectory toRing;
    Trajectory backUpToRing;
    Trajectory alignShot;
    Trajectory park1, park2, park3;

    Pose2d startPose = new Pose2d(-60,-12,Math.toRadians(180));

    //Pick up ring
    Vector2d initialForward= new Vector2d(-35, -12);
    Vector2d alignToRing = new Vector2d(-25, -48);
    Vector2d intakeRing = new Vector2d(-40, -48);

    //Align to shoot
    Vector2d centerToShoot = new Vector2d(-25,-12);
    Vector2d forwardToShoot = new Vector2d(12,-12);

    //Parking position
    Vector2d parking1 = new Vector2d(36,-12);
    Vector2d parking2 = new Vector2d(12, -36);
    Vector2d parking3 = new Vector2d(-12,-12);


    public BlueSequences(RavioliHardware hardware, SampleMecanumDrive drive) {
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

        alignShot = drive.trajectoryBuilder(backUpToRing.end())
                .splineTo(centerToShoot, Math.toRadians(180))
                .splineTo(forwardToShoot, Math.toRadians(190))
                .build();

        park1 = drive.trajectoryBuilder(alignShot.end())
                .splineTo(parking1, Math.toRadians(180))
                .build();

        park2 = drive.trajectoryBuilder(alignShot.end())
                .splineTo(parking2, Math.toRadians(180))
                .build();

        park3 = drive.trajectoryBuilder(alignShot.end())
                .splineTo(parking3, Math.toRadians(180))
                .build();
    }

    public void runBlue1() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);

        //shoot
        drive.followTrajectory(alignShot);

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

    public void runBlue2() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);

        //shoot
        drive.followTrajectory(alignShot);

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

    public void runBlue3() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);

        //shoot
        drive.followTrajectory(alignShot);

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
