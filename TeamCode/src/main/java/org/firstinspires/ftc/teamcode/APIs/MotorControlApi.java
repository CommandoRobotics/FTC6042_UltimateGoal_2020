package org.firstinspires.ftc.teamcode.APIs;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorControlApi {

    PidApi pid;

    double previousTimeInMillis;
    double previousTickCount;
    double encoderTicksPerMotorRevolution;
    double previousRpm;
    double millisToWait = 500;

    private boolean hasRpmBeenInitialized = false;

    /**
     * Instantiates a new MotorControlApi
     * @param pGain
     * @param iGain
     * @param dGain
     */
    public MotorControlApi(double pGain, double iGain, double dGain) {

        pid = new PidApi(pGain, iGain, dGain);

    }

    /**
     * Instantiates a new MotorControlApi
     * @param pGain
     * @param iGain
     * @param dGain
     * @param encoderTicksPerMotorRevolution
     */
    public MotorControlApi(double pGain, double iGain, double dGain, double encoderTicksPerMotorRevolution) {

        this.encoderTicksPerMotorRevolution = encoderTicksPerMotorRevolution;

        pid = new PidApi(pGain, iGain, dGain);

    }

    /**
     * Instantiates a new MotorControlApi
     * @param pGain
     * @param iGain
     * @param dGain
     * @param encoderTicksPerMotorRevolution
     * @param millisToWait
     */
    public MotorControlApi(double pGain, double iGain, double dGain, double encoderTicksPerMotorRevolution, double millisToWait) {

        this.millisToWait = millisToWait;
        this.encoderTicksPerMotorRevolution = encoderTicksPerMotorRevolution;

        pid = new PidApi(pGain, iGain, dGain);

    }

    /**
     * Gets the new motor speed based on a PID formula
     * @param currentRpm
     * @param targetRpm
     * @param currentMotorSpeed
     * @return
     */
    public double getNewMotorSpeed(double currentRpm, double targetRpm, double currentMotorSpeed) {

        return currentMotorSpeed + pid.getOutput(currentRpm, targetRpm);

    }

    /**
     * Initializes the RPM counter. Note: This must be run before running the getRpm method
     * @param currentTickCount
     */
    public void initializeRpmCounter(double currentTickCount) {

        previousRpm = 0;
        previousTimeInMillis = System.currentTimeMillis();
        previousTickCount = currentTickCount;
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

        if(currentTimeInMillis >= previousTimeInMillis+millisToWait) {

            double previousRotation = previousTickCount/encoderTicksPerMotorRevolution;
            double currentRotation = currentTickCount/encoderTicksPerMotorRevolution;
            double changeInRotation = 0;

            if(previousRotation > currentRotation) {
                changeInRotation = previousRotation-currentRotation;
            } else if(currentRotation > previousRotation) {
                changeInRotation = currentRotation-previousRotation;
            }

            double changeInTimeInSeconds = (currentTimeInMillis-previousTimeInMillis)/1000;

            double rotationsPerMinute = (changeInRotation*60)/changeInTimeInSeconds;

            previousRpm = rotationsPerMinute;

            previousTimeInMillis = currentTimeInMillis;

            previousTickCount = currentTickCount;

            return rotationsPerMinute;

        } else {
            return previousRpm;
        }

    }

    /**
     * Reset the amount of ticks counted to -0
     */
    public void resetTicks() {

        previousTickCount = 0;

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
