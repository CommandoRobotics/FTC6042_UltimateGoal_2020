package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.ChassisApi;
import org.firstinspires.ftc.teamcode.Constants.PidConstants;

@Autonomous(name="Deliver to C and Navigate V1.0.1")
public class DeliverAndNavigatePID extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ChassisApi chassis = new ChassisApi(hardwareMap);

        waitForStart();

        chassis.startDriveForwardPid(-110, PidConstants.DRIVE_FORWARD_P, PidConstants.DRIVE_FORWARD_I, PidConstants.DRIVE_FORWARD_D);

        while(chassis.isActionRunning() && opModeIsActive()) {
            chassis.updatePositionDriveForward();
        }

    }
}
