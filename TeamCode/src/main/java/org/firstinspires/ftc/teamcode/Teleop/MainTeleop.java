package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.APIs.GrabberApi;
import org.firstinspires.ftc.teamcode.APIs.MecanumDriveApi;
import org.firstinspires.ftc.teamcode.APIs.TankDriveApi;

@TeleOp(name="Main Teleop V2.4.6")
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDriveApi drive = new MecanumDriveApi(hardwareMap, 1890, 3);
        GrabberApi grabber = new GrabberApi(hardwareMap);

        double clawClosedPosition = 0.7;
        double clawOpenPosition = 0.15;

        boolean debugMode = true;
        boolean previousA = false;

        grabber.setClawPosition(0.15);

        waitForStart();

        while(opModeIsActive()) {

            // Drive based on joystick inputs
            drive.driveCartesian(0.5*gamepad1.left_stick_x, 0.5*gamepad1.left_stick_y, 0.5*gamepad1.right_stick_x);

            // Set the power of the grabber
            grabber.setGrabberPower(0.1*(gamepad1.right_trigger-gamepad1.left_trigger));

            // Do some logic for A being pressed and toggling the position of the claw
            if(gamepad1.a && previousA == false) {
                if(grabber.getClawPosition() < clawOpenPosition + 0.05 && grabber.getClawPosition() > clawOpenPosition-0.05) {
                    grabber.setClawPosition(clawClosedPosition);
                    previousA = true;
                } else {
                    grabber.setClawPosition(clawOpenPosition);
                    previousA = true;
                }
            } else if(!gamepad1.a) {
                previousA = false;
            }

            if(debugMode) {
                telemetry.addLine(drive.getFrontLeftSpeed() + "(-----)" + drive.getFrontRightSpeed());
                telemetry.addLine("|       |");
                telemetry.addLine("|       |");
                telemetry.addLine("|       |");
                telemetry.addLine(drive.getRearLeftSpeed() + "(-----)" + drive.getRearRightSpeed());
                telemetry.addLine("X: " + gamepad1.left_stick_x + " Y: " + gamepad1.left_stick_y + " ROT: " + gamepad1.right_stick_x);
            }
            telemetry.update();
        }

    }
}
