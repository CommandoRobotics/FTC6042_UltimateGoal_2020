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

        TankDriveApi drive = new TankDriveApi(hardwareMap, 0, 0);

        waitForStart();

        while(opModeIsActive()) {

            drive.driveTank(gamepad1.left_stick_y, gamepad1.right_stick_y);

        }

    }
}
