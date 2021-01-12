package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.GrabberApi;
import org.firstinspires.ftc.teamcode.APIs.ChassisApi;
import org.firstinspires.ftc.teamcode.Constants.Constants;

@TeleOp(name="Main Teleop V2.5.4")
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {

        ChassisApi chassis = new ChassisApi(hardwareMap);

        boolean debugMode = true;

        // Booleans for button pressed
        boolean previousA = false;

        waitForStart();

        chassis.closeClaw();

        while(opModeIsActive()) {

            // Drive based on joystick inputs
            chassis.driveCartesian(0.5*gamepad1.left_stick_x, 0.5*gamepad1.left_stick_y, 0.5*gamepad1.right_stick_x);

            // Set the power of the grabber
            chassis.setGrabberPower(0.1*(gamepad1.right_trigger-gamepad1.left_trigger));

            // Do some logic for A being pressed and toggling the position of the claw
            if(gamepad1.a && previousA == false) {
                if(chassis.getClawPosition() < Constants.CLAW_OPEN_POSITION + Constants.CLAW_DEAD_ZONE && chassis.getClawPosition() > Constants.CLAW_OPEN_POSITION-Constants.CLAW_DEAD_ZONE) {
                    chassis.closeClaw();
                    previousA = true;
                } else {
                    chassis.openClaw();
                    previousA = true;
                }
            } else if(!gamepad1.a) {
                previousA = false;
            }

            if(debugMode) {
                telemetry.addLine(chassis.getFrontLeftSpeed() + "(-----)" + chassis.getFrontRightSpeed());
                telemetry.addLine("|       |");
                telemetry.addLine("|       |");
                telemetry.addLine("|       |");
                telemetry.addLine(chassis.getRearLeftSpeed() + "(-----)" + chassis.getRearRightSpeed());
                telemetry.addLine("X: " + gamepad1.left_stick_x + " Y: " + gamepad1.left_stick_y + " ROT: " + gamepad1.right_stick_x);
            }
            telemetry.update();
        }

    }
}
