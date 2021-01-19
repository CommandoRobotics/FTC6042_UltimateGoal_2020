package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.ChassisApi;
import org.firstinspires.ftc.teamcode.APIs.GyroscopeApi;

@TeleOp(name="Gyro Test V1.0.1")
public class GyroTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        GyroscopeApi gyro = new GyroscopeApi(hardwareMap);
        ChassisApi chassis = new ChassisApi(hardwareMap);

        telemetry.addLine("Robot ready");
        telemetry.update();

        waitForStart();

        chassis.startRotatePid(30, 0.1, 0, 0);

        while(chassis.isActionRunning() && opModeIsActive()) {
            chassis.updatePositionRotate();
        }

    }
}
