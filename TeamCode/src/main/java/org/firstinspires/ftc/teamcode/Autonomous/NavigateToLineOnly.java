package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.TankDriveApi;

@Autonomous(name = "Only drive to line")
public class NavigateToLineOnly extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        TankDriveApi drive = new TankDriveApi(hardwareMap, 0, 0);

        waitForStart();

        double startTimeOne = System.currentTimeMillis();

        while(System.currentTimeMillis() < startTimeOne+7500 && opModeIsActive()) {
            drive.driveTank(-0.5, -0.5);
        }

        drive.stopMotors();

    }

}
