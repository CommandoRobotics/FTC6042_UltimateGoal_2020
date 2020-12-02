package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Testing V7")
public class ServoTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo grabberServo = hardwareMap.get(Servo.class, "grabberServo");

        waitForStart();

        while(opModeIsActive()) {

            boolean previousA = false;
            boolean previousB = false;

            if(gamepad1.a && gamepad1.a != previousA){
                grabberServo.setPosition(90);
            }

            if(gamepad1.b && gamepad1.b != previousB) {
                grabberServo.setPosition(10);
            }

            previousA = gamepad1.a;
            previousB = gamepad1.b;

            telemetry.addLine("pos " + grabberServo.getPosition());
            telemetry.update();
        }

    }
}
