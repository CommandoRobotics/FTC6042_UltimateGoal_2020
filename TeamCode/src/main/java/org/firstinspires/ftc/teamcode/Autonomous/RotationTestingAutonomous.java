package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.ChassisApi;
import org.firstinspires.ftc.teamcode.APIs.GyroscopeApi;
import org.firstinspires.ftc.teamcode.APIs.PidApi;

@TeleOp(name="Rotation Test V1.0.3")
@Disabled
/**
 * This autonomous is meant to test rotating the robot to a specific position using the gyro
 */
public class RotationTestingAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        PidApi pid = new PidApi(0.1, 0, 0);
        ChassisApi chassis = new ChassisApi(hardwareMap);
        GyroscopeApi gyro = new GyroscopeApi(hardwareMap);

        telemetry.addLine("Initialized");
        gyro.update();
        telemetry.addLine("Gyro current pos: " + gyro.getRawY());
        telemetry.update();

        waitForStart();

        double originalGyroPosition = gyro.getRawY();

        while(opModeIsActive()) {

            gyro.update();
            double currentPos = gyro.getRawY();
            double pidOutput = pid.getOutput(currentPos, 90);

            if(pidOutput > 1) {
                pidOutput = 1;
            } else if(pidOutput < -1) {
                pidOutput = -1;
            }

            telemetry.addLine("Gyro pos: " + currentPos);
            telemetry.addLine("PID output: " + pidOutput);
            telemetry.update();

            chassis.driveTank(-pidOutput, pidOutput);

        }

    }
}
