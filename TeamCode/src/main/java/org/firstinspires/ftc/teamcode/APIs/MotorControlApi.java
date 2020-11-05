package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorControlApi {

    PidApi pid;
    double encoderTicksPerMotorRevolution;
    DcMotor motor;

    // These variables are for the RPM calculating method
    double rpmLoopPreviousTimeInMillis;
    double rpmLoopPreviousTickCount;
    double previousRpm;
    double rpmLoopMillisToWait = 500;

    private boolean hasRpmBeenInitialized = false;

    /**
     * Instantiate a new MotorControlAPI object
     * @param motor The motor this API is controlling
     * @param encoderTicksPerMotorRevolution The amount of encoder counts per revolution (including gearing)
     */
    public MotorControlApi(DcMotor motor, double encoderTicksPerMotorRevolution){
        this.motor = motor;
        this.encoderTicksPerMotorRevolution = encoderTicksPerMotorRevolution;
    }

    /**
     * Gets the inches traveled since the last encoder reset
     * @param diameterOfWheelInInches The diameter of the output wheel
     * @return The distance the wheel has traveled in inches
     */
    public double getInchesTraveled(double diameterOfWheelInInches) {
        return (motor.getCurrentPosition()/encoderTicksPerMotorRevolution)*diameterOfWheelInInches;
    }

    public void resetEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
     * Gets the current RPM of the motor but assumes the current position of the motor is the position to use
     * @return
     */
    public double getRpm() {

        double currentTickCount = motor.getCurrentPosition();

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
