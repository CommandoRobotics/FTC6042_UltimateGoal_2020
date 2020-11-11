package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.APIs.MecanumDriveApi;

@TeleOp(name="Main Teleop")
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

//        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
//        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
//        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
//        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        MecanumDriveApi drive = new MecanumDriveApi(hardwareMap, 1, 2);

        waitForStart();

        while(opModeIsActive()) {

            drive.driveCartesian(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        }

    }
}
