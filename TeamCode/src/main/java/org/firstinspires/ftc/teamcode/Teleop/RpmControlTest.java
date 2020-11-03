package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.APIs.MotorControlApi;

@TeleOp
public class RpmControlTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorControlApi motorControlApi = new MotorControlApi(0, 0, 0, 28, 500);

        double previousTimeInMillis = 0;
        double previousEncoderValue = 0;

        telemetry.addLine("Version 5");
        telemetry.update();

        waitForStart();

        motorControlApi.initializeRpmCounter(0);

        while(opModeIsActive()) {

            motor.setPower(0.2);

            double currentRpm = motorControlApi.getRpm(motor.getCurrentPosition());

            telemetry.addLine("Current RPM: " + currentRpm);
            telemetry.addLine("Current Tick Count: " + motor.getCurrentPosition());
            telemetry.update();

        }

    }
}
