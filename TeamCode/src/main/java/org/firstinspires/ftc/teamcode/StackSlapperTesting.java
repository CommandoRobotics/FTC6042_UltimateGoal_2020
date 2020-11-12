package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class StackSlapperTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.get(Servo.class, "servo");
        TouchSensor ts1 = hardwareMap.get(TouchSensor.class, "ts1");
        TouchSensor ts2 = hardwareMap.get(TouchSensor.class, "ts2");
        TouchSensor ts3 = hardwareMap.get(TouchSensor.class, "ts3");
        TouchSensor ts4 = hardwareMap.get(TouchSensor.class, "ts4");


        waitForStart();


        while(opModeIsActive()) {
            servo.setPosition(-180);

            if(ts1.isPressed() || ts2.isPressed() || ts3.isPressed() || ts4.isPressed()) {
                telemetry.addLine("Ring Detected");
                telemetry.update();
            } else {
                telemetry.addLine("No ring detected");
                telemetry.update();
            }

        }

    }
}
