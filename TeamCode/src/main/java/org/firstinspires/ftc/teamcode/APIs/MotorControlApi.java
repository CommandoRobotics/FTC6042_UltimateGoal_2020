package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorControlApi {

    PidApi pid;
    double encoderTicksPerMotorRevolution;
    DcMotor motor;

    double rpmLoopPreviousTimeInMillis;
    double rpmLoopPreviousTickCount;
    double previousRpm;
    double rpmLoopMillisToWait = 500;

    private boolean hasRpmBeenInitialized = false;

    public MotorControlApi(DcMotor motor, double encoderTicksPerMotorRevolution){
        this.motor = motor;
    }

    public double getInchesTraveled(double diameterOfWheelInInches) {
        return (motor.getCurrentPosition()/encoderTicksPerMotorRevolution)*diameterOfWheelInInches;
    }

    /**
     * Initializes the RPM counter. Note: This must be run before running the getRpm method
     * @param currentTickCount
     */
    public void initializeRpmCounter(double currentTickCount) {

        previousRpm = 0;
        rpmLoopPreviousTimeInMillis = System.currentTimeMillis();
        rpmLoopPreviousTickCount = currentTickCount;
        hasRpmBeenInitialized = true;

    }

    /**
     * Gets the current RPM of the motor
     * @param currentTickCount The current position of the motor
     * @return
     */
    public double getRpm(double currentTickCount) {

        if(!hasRpmBeenInitialized) {
            return 0;
        }

        double currentTimeInMillis = System.currentTimeMillis();

        if(currentTimeInMillis >= rpmLoopPreviousTimeInMillis + rpmLoopMillisToWait) {

            double previousRotation = rpmLoopPreviousTickCount /encoderTicksPerMotorRevolution;
            double currentRotation = currentTickCount/encoderTicksPerMotorRevolution;
            double changeInRotation = 0;

            if(previousRotation > currentRotation) {
                changeInRotation = previousRotation-currentRotation;
            } else if(currentRotation > previousRotation) {
                changeInRotation = currentRotation-previousRotation;
            }

            double changeInTimeInSeconds = (currentTimeInMillis- rpmLoopPreviousTimeInMillis)/1000;

            double rotationsPerMinute = (changeInRotation*60)/changeInTimeInSeconds;

            previousRpm = rotationsPerMinute;

            rpmLoopPreviousTimeInMillis = currentTimeInMillis;

            rpmLoopPreviousTickCount = currentTickCount;

            return rotationsPerMinute;

        } else {
            return previousRpm;
        }

    }

    /**
     * Reset the amount of ticks counted to -0
     */
    public void resetTicks() {

        rpmLoopPreviousTickCount = 0;

    }

    /**
     * Set the P gain
     * @param newPGain The new P gain
     */
    public void setPGain(double newPGain) {
        pid.setPGain(newPGain);
    }

    /**
     * Set the I gain
     * @param newIGain The new I gain
     */
    public void setIGain(double newIGain) {
        pid.setIGain(newIGain);
    }

    /**
     * Set the D gain
     * @param newDGain The new D gain
     */
    public void setDGain(double newDGain) {
        pid.setDGain(newDGain);
    }

    /**
     * Get the P gain
     * @return The P gain
     */
    public double getPGain() {
        return pid.getPGain();
    }

    /**
     * Get the I gain
     * @return The I gain
     */
    public double getIGain() {
        return pid.getIGain();
    }

    /**
     * Get the D gain
     * @return The D gain
     */
    public double getDGain() {
        return pid.getDGain();
    }

}
