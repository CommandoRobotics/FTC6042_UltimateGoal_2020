package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.APIs.FeedForwardLoopApi;
import org.firstinspires.ftc.teamcode.APIs.RpmApi;

@TeleOp(name="run this one 5")
public class RpmLoopTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");

        RpmApi rpmApi = new RpmApi(motor, 28);

        FeedForwardLoopApi ffl = new FeedForwardLoopApi(0.000001, 0, 0.05, 6500);

        rpmApi.initializeRpmCounter(motor.getCurrentPosition());

        waitForStart();

        motor.setPower(0.5);

        while(opModeIsActive()) {

            double newPower = ffl.getAdjustedMotorSpeed(motor.getPower(), rpmApi.getRpm(motor.getCurrentPosition()), 1000);

            motor.setPower(newPower);

            telemetry.addLine("RPM:" + rpmApi.getRpm(motor.getCurrentPosition()));
            telemetry.addLine("New power:" + newPower);
            telemetry.update();

        }

    }
}
