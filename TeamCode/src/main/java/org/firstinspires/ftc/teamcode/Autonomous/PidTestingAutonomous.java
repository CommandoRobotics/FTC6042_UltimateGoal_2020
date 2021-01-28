package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.ChassisApi;

@Autonomous(name="PID Testing V2.1.3")
public class PidTestingAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ChassisApi chassis = new ChassisApi(hardwareMap);

        waitForStart();

        chassis.startStrafePid(20, 0.3, 0, 0);

        while(chassis.isActionRunning() && opModeIsActive()) {
            chassis.updatePositionStrafe();
        }

    }
}
