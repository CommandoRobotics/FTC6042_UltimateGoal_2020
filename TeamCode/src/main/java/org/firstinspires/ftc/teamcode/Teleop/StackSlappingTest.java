package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.PidApi;
import org.firstinspires.ftc.teamcode.APIs.StackSlapperApi;

@TeleOp(name = "Stack slapper testing V1.2.1.8")
public class StackSlappingTest extends LinearOpMode {

    StackSlapperApi stack;
    PidApi hoverPid;

    @Override
    public void runOpMode() throws InterruptedException {

        StackSlapperApi stack = new StackSlapperApi(hardwareMap);
        hoverPid = new PidApi(1.5, 0, 0);

        waitForStart();

        stack.resetEncoder();

        while(opModeIsActive()) {


            double currentPosition = stack.getCurrentRotation();
            double pidOutput = hoverPid.getOutput(currentPosition, 0.4);
            if(pidOutput > 1) {
                pidOutput = 1;
            } else if(pidOutput < -1) {
                pidOutput = -1;
            }
            telemetry.addLine("PID Output: " + pidOutput);
            telemetry.addLine("Current position: " + currentPosition);
            telemetry.update();
            stack.setPower(pidOutput);
        }

    }

}
