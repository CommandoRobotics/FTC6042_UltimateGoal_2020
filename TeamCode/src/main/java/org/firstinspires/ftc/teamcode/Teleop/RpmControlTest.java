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

        MotorControlApi motorControlApi = new MotorControlApi(motor, 28);

        telemetry.addLine("Version 5");
        telemetry.update();

        waitForStart();

        double currentPower = 0.3;

        motorControlApi.initializeRpmCounter(0);

        while(opModeIsActive()) {

            //motor.setPower(motorControlApi.getNewMotorSpeed(motorControlApi.getRpm(motor.getCurrentPosition()), 2000, motor.getPower()));

            double error = 3000-motorControlApi.getRpm(motor.getCurrentPosition());

            if(motor.getPower() >= 0) {
                motor.setPower(currentPower + (error * 0.0000005));
            } else if (motor.getPower() < 0) {
                motor.setPower(-(currentPower + (error * 0.0000005)));
            }

            currentPower = motor.getPower();

            double currentRpm = motorControlApi.getRpm(motor.getCurrentPosition());

            telemetry.addLine("Current RPM: " + currentRpm);
            telemetry.addLine("Current Tick Count: " + motor.getCurrentPosition());
            telemetry.addLine("Current set power: " + motor.getPower());
            telemetry.update();

        }

    }
}
