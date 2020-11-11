package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class StackSlapperApi {

    DcMotor stackSlapperMotor;
    TouchSensor sensor1, sensor2, sensor3, sensor4;

    String stackSlapperMotorNameInHardwareMap = "stackSlapper";

    /**
     * Instantiate a new stack slapper API object
     * @param hardwareMap The robot's hardware map
     */
    public StackSlapperApi(HardwareMap hardwareMap) {
        stackSlapperMotor = hardwareMap.get(DcMotor.class, stackSlapperMotorNameInHardwareMap);
        stackSlapperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Reset the stack slapper motor's encoders
     */
    public void resetEncoder() {
        stackSlapperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        stackSlapperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Set the power of the stack slapper motor
     * @param power The power to set the stack slapper motor to
     */
    public void setPower(double power) {
        stackSlapperMotor.setPower(power);
    }

    /**
     * Gets the power of the stack slapper motor
     * @param power The power of the stack slapper motor
     */
    public void getPower(double power) {
        stackSlapperMotor.getPower();
    }

    /**
     * Determines if a ring is detected
     * @return Whether a ring is detected or not
     */
    public boolean isRingDetected() {
        return sensor1.isPressed() || sensor2.isPressed() || sensor3.isPressed() || sensor4.isPressed();
    }

    /**
     * Gets the current position of the stack slapper motor
     * @return The current position in ticks
     */
    public double getCurrentPosition() {
        return stackSlapperMotor.getCurrentPosition();
        //TODO add some logic to this API that allows for entering encoder ticks per rotation and returning rotations rather than total encoder ticks
    }

}
