package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class StackSlapperApi {

    DcMotor stackSlapperMotor;
    TouchSensor sensor1, sensor2, sensor3, sensor4;

    String stackSlapperMotorNameInHardwareMap = "stackSlapper";

    double encoderTicksPerRotation = 28;

    /**
     * Instantiate a new stack slapper API object
     * @param hardwareMap The robot's hardware map
     */
    public StackSlapperApi(HardwareMap hardwareMap) {
        stackSlapperMotor = hardwareMap.get(DcMotor.class, stackSlapperMotorNameInHardwareMap);
        stackSlapperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Instantiate a new stack slapper API object
     * @param hardwareMap The robot's hardware map
     * @param encoderTicksPerRotation The amount of encoder ticks per motor revolution
     */
    public StackSlapperApi(HardwareMap hardwareMap, double encoderTicksPerRotation) {
        stackSlapperMotor = hardwareMap.get(DcMotor.class, stackSlapperMotorNameInHardwareMap);
        stackSlapperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.encoderTicksPerRotation = encoderTicksPerRotation;
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
     * @return The current position in rotations
     */
    public double getCurrentPosition() {
        return stackSlapperMotor.getCurrentPosition()/encoderTicksPerRotation;
    }

}
