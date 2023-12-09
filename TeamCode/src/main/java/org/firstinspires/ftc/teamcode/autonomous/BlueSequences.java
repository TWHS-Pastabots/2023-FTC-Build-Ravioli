package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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
    Trajectory strafeStart, initialForward;
    Trajectory align1A, align1B, align2, align3;
    Trajectory park1A, park1B, park2A, park2B, park3A, park3B;

    Pose2d startPose = new Pose2d(16,-62,Math.toRadians(180));

    //Pick up ring
    //Vector2d initialForward= new Vector2d(16, 16);
    /*Vector2d alignToRing = new Vector2d(-25, -48);
    Vector2d intakeRing = new Vector2d(-40, -48);*/

    //Align to shoot
    /*Vector2d centerToShoot = new Vector2d(-25,-12);
    Vector2d forwardToShoot = new Vector2d(12,-12);*/

    //Parking position
    /*Vector2d parking1 = new Vector2d(16,36);
    Vector2d parking2 = new Vector2d(36, 16);
    Vector2d parking3 = new Vector2d(16,-16);*/


    public BlueSequences(RavioliHardware hardware, SampleMecanumDrive drive) {
        this.hardware = hardware;
        intake = new Intake(hardware);
        launcher = new Launcher(hardware);
        utilities = new Utilities();
        this.drive = drive;

        strafeStart = drive.trajectoryBuilder(startPose)
                .strafeRight(20)
                .build();

        initialForward = drive.trajectoryBuilder(strafeStart.end())
                .forward(-120)
                .build();

        align1A = drive.trajectoryBuilder(strafeStart.end())
                .forward(-30)
                .build();

        align1B = drive.trajectoryBuilder(align1A.end())
                .strafeRight(18)
                .build();

        align2 = drive.trajectoryBuilder(strafeStart.end())
                .forward(-50)
                .build();

        align3 = drive.trajectoryBuilder(initialForward.end())
                .strafeRight(18)
                .build();

        park1A = drive.trajectoryBuilder(align1B.end())
                .strafeLeft(27)
                .build();

        park1B = drive.trajectoryBuilder(park1A.end())
                .forward(-60)
                .build();

        park2A = drive.trajectoryBuilder(align2.end())
                .strafeLeft(54)
                .build();

        park2B = drive.trajectoryBuilder(park2B.end())
                .forward(40)
                .build();

        park3A = drive.trajectoryBuilder(align3.end())
                .forward(40)
                .build();

        park3B = drive.trajectoryBuilder(park3B.end())
                .strafeLeft(20)
                .build();
    }

    public void runBlue1() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align1A);
        drive.followTrajectory(align1B);

        drive.turn(Math.toRadians(-45));
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);
        drive.turn(Math.toRadians(45));

        drive.followTrajectory(park1A);
        utilities.wait(200);
        drive.followTrajectory(park1B);
    }

    public void runBlue2() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align2);

        drive.turn(Math.toRadians(-20));
        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);
        drive.turn(Math.toRadians(20));

        drive.followTrajectory(park2A);
        utilities.wait(200);
        drive.followTrajectory(park2B);
    }

    public void runBlue3() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align3);

        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        drive.followTrajectory(park3A);
        utilities.wait(200);
        drive.followTrajectory(park3B);
    }
}
