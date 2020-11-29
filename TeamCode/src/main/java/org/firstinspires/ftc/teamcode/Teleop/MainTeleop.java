package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.APIs.MecanumDriveApi;
import org.firstinspires.ftc.teamcode.APIs.TankDriveApi;

@TeleOp(name="Main Teleop")
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDriveApi drive = new MecanumDriveApi(hardwareMap, 1890, 3);

        waitForStart();

        while(opModeIsActive()) {

            drive.driveCartesian(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

            telemetry.addLine(drive.getFrontLeftSpeed() + "(-----)" + drive.getFrontRightSpeed());
            telemetry.addLine("|       |");
            telemetry.addLine("|       |");
            telemetry.addLine("|       |");
            telemetry.addLine(drive.getRearLeftSpeed() + "(-----)" + drive.getRearRightSpeed());
            telemetry.addLine("X: " + gamepad1.left_stick_x + " Y: " + gamepad1.left_stick_y + " ROT: " + gamepad1.right_stick_x);
            telemetry.update();
        }

    }
}
