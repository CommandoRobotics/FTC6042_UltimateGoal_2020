package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.TankDriveApi;

@Autonomous(name = "Lets score some points yo")
public class SuperBasicAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        TankDriveApi drive = new TankDriveApi(hardwareMap, 0, 0);

        waitForStart();

        double startTimeOne = System.currentTimeMillis();

        while(System.currentTimeMillis() < startTimeOne+8000 && opModeIsActive()) {
            drive.driveTank(-0.5, -0.5);
        }

        drive.stopMotors();

    }

}
