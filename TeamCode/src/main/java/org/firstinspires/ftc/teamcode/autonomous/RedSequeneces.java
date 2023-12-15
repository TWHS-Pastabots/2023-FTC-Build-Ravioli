package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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
    Trajectory strafeStart, initialForward;
    Trajectory align1, align2, align3A, align3B;
    Trajectory park1A, park1B, park2A, park2B, park3A, park3B;

    Pose2d startPose = new Pose2d(-16,-62,Math.toRadians(180));

    //Pick up ring
    //Vector2d initialForward= new Vector2d(-16, 16);
    /*Vector2d alignToRing = new Vector2d(-25, 48);
    Vector2d intakeRing = new Vector2d(-40, 48);*/

    //Align to shoot
    /*Vector2d centerToShoot = new Vector2d(-25,12);
    Vector2d forwardToShoot = new Vector2d(12,12);*/

    //Parking position
    /*Vector2d parking1 = new Vector2d(-16,36);
    Vector2d parking2 = new Vector2d(-36, 16);
    Vector2d parking3 = new Vector2d(-16,-16);*/


    public RedSequeneces(RavioliHardware hardware, SampleMecanumDrive drive) {
        this.hardware = hardware;
        intake = new Intake(hardware);
        launcher = new Launcher(hardware);
        utilities = new Utilities();
        this.drive = drive;

        strafeStart = drive.trajectoryBuilder(startPose)
                .strafeLeft(20)
                .build();

        initialForward = drive.trajectoryBuilder(strafeStart.end())
                .forward(-150)
                .build();

        align1 = drive.trajectoryBuilder(initialForward.end())
                .strafeLeft(18)
                .build();

        align2 = drive.trajectoryBuilder(initialForward.end())
                .forward(-40)
                .build();

        align3A = drive.trajectoryBuilder(initialForward.end())
                .strafeLeft(18)
                .build();

        align3B = drive.trajectoryBuilder(align3A.end())
                .forward(20)
                .build();

        park1A = drive.trajectoryBuilder(align1.end())
                .strafeRight(27)
                .build();

        park1B = drive.trajectoryBuilder(park1A.end())
                .forward(-60)
                .build();

        park2A = drive.trajectoryBuilder(align2.end())
                .forward(35)
                .build();

        park2B = drive.trajectoryBuilder(park2A.end())
                .strafeRight(66)
                .build();

        park3A = drive.trajectoryBuilder(align3B.end())
                .forward(25)
                .build();

        park3B = drive.trajectoryBuilder(park3A.end())
                .strafeRight(25)
                .build();
    }

    public void runRed1() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align1);

        launcher.launch(true, false);
        utilities.wait(2000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        drive.followTrajectory(park1A);
        utilities.wait(200);
        drive.followTrajectory(park1B);
    }

    public void runRed2() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align2);
        drive.turn(Math.toRadians(-39));

        launcher.launch(true, false);
        utilities.wait(1000);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        drive.turn(Math.toRadians(39));
        utilities.wait(200);
        drive.followTrajectory(park2A);
        utilities.wait(200);
        drive.followTrajectory(park2B);
    }

    public void runRed3() {
        drive.setPoseEstimate(startPose);
        launcher.launch(false, false);

        drive.followTrajectory(strafeStart);
        drive.followTrajectory(initialForward);
        drive.followTrajectory(align3A);
        drive.followTrajectory(align3B);

        drive.turn(Math.toRadians(-36.5));
        launcher.launch(true, false);
        utilities.wait(1500);
        launcher.launch(true, true);
        utilities.wait(1000);
        launcher.launch(false, false);

        drive.turn(Math.toRadians(36.5));
        utilities.wait(200);
        drive.followTrajectory(park3A);
        utilities.wait(200);
        drive.followTrajectory(park3B);
    }
}
