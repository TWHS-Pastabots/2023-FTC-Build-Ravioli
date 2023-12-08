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
    Trajectory align, align2;
    Trajectory strafeStart;
    Trajectory backUpToRing;
    Trajectory alignShot1;
    Trajectory alignShot2;
    Trajectory park1, park1b, park2, park3;

    Pose2d startPose = new Pose2d(-16,-62,Math.toRadians(180));

    //Pick up ring
    Vector2d initialForward= new Vector2d(-16, 16);
    /*Vector2d alignToRing = new Vector2d(-25, 48);
    Vector2d intakeRing = new Vector2d(-40, 48);*/

    //Align to shoot
   /* Vector2d centerToShoot = new Vector2d(-25,12);
    Vector2d forwardToShoot = new Vector2d(12,12);*/

    //Parking position
    Vector2d parking1 = new Vector2d(-16,36);
    Vector2d parking2 = new Vector2d(-36, 16);
    Vector2d parking3 = new Vector2d(-16,-16);


    public RedSequeneces(RavioliHardware hardware, SampleMecanumDrive drive) {
        this.hardware = hardware;
        intake = new Intake(hardware);
        launcher = new Launcher(hardware);
        utilities = new Utilities();
        this.drive = drive;

        strafeStart = drive.trajectoryBuilder(startPose)
                .strafeLeft(20)
                .build();

        align = drive.trajectoryBuilder(strafeStart.end())
                //.splineTo(initialForward, Math.toRadians(0))
                //.splineTo(alignToRing, Math.toRadians(180))
                .forward(-150)
                .build();

        align2 = drive.trajectoryBuilder(align.end())
                .strafeLeft(18)
                .build();
        /*backUpToRing = drive.trajectoryBuilder(toRing.end())
                .splineTo(intakeRing, Math.toRadians(180))
                .build();*/


        /*alignShot1 = drive.trajectoryBuilder(strafeStart.end())
              *//*  .splineTo(centerToShoot, Math.toRadians(90))
                .splineTo(forwardToShoot, Math.toRadians(80))*//*
                .forward(-120)
                .build();

        alignShot2 = drive.trajectoryBuilder(alignShot1.end())
                .strafeLeft(4)
                .build();*/

        park1 = drive.trajectoryBuilder(align2.end())
                /*.splineTo(parking1, Math.toRadians(180))
                /*.forward(-20)*/
                .strafeRight(25)
                .build();
        park1b = drive.trajectoryBuilder(park1.end())
                .forward(-60)
                .build();



        park2 = drive.trajectoryBuilder(align.end(), Math.toRadians(15))
                .splineTo(parking2, Math.toRadians(180))
                .build();

        park3 = drive.trajectoryBuilder(align.end(), Math.toRadians(45))
                .splineTo(parking3, Math.toRadians(180))
                .build();
    }

    public void runRed1() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);
        drive.followTrajectory(strafeStart);

        //pick up ring
        /*drive.followTrajectory(toRing);
        intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);*/

        //shoot
        /*drive.followTrajectory(strafeStart);
        utilities.wait(2000);
        drive.followTrajectory(alignShot1);*/
        drive.followTrajectory(align);
        drive.followTrajectory(align2);
        //drive.turn(Math.toRadians(-15));

        //drive.followTrajectory(alignShot2);

        /*intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);*/
        launcher.launch(true, false);
        utilities.wait(2000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);



     /*   intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);*/

        //park
        //drive.turn(Math.toRadians(15));
        drive.followTrajectory(park1);
        utilities.wait(500);
        drive.followTrajectory(park1b);
        //drive.turn(Math.toRadians(90));
        //drive.followTrajectory(park1b);
    }

    public void runRed2() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(align);
        /*intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);*/

        //shoot
        //drive.followTrajectory(alignShot1);

        /*intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);*/
        drive.turn(Math.toRadians(15));
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        /*intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);*/

        //park
        drive.followTrajectory(park2);
    }

    public void runRed3() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        //pick up ring
        drive.followTrajectory(align);
        /*intake.powerIntake(true, false);
        drive.followTrajectory(backUpToRing);
        utilities.wait(2000);
        intake.powerIntake(false, false);*/

        //shoot
        //drive.followTrajectory(alignShot1);

        /*intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);*/
        drive.turn(Math.toRadians(-45));
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        /*intake.powerIntake(true, false);
        utilities.wait(1500);
        intake.powerIntake(false, false);
        launcher.launch(true, false);
        utilities.wait(100);
        launcher.launch(true, true);
        utilities.wait(100);
        launcher.launch(false, false);*/

        //park
        drive.followTrajectory(park3);
    }
}
