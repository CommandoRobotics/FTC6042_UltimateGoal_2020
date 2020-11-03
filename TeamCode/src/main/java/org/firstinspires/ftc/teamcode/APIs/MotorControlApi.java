package org.firstinspires.ftc.teamcode.APIs;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorControlApi {

    PidApi pid;

    double previousTimeInMillis;
    double previousTickCount;
    double encoderTicksPerMotorRevolution;
    double previousRpm;
    double millisToWait = 500;

    public MotorControlApi(double pGain, double iGain, double dGain) {

        pid = new PidApi(pGain, iGain, dGain);

    }

    public MotorControlApi(double pGain, double iGain, double dGain, double encoderTicksPerMotorRevolution) {

        this.encoderTicksPerMotorRevolution = encoderTicksPerMotorRevolution;

        pid = new PidApi(pGain, iGain, dGain);

    }

    public MotorControlApi(double pGain, double iGain, double dGain, double encoderTicksPerMotorRevolution, double millisToWait) {

        this.millisToWait = millisToWait;
        this.encoderTicksPerMotorRevolution = encoderTicksPerMotorRevolution;

        pid = new PidApi(pGain, iGain, dGain);

    }

    public double getNewMotorSpeed(double currentRpm, double targetRpm, double currentMotorSpeed) {

        return currentMotorSpeed + pid.getOutput(currentRpm, targetRpm);

    }

    public void initializeRpmCounter(double currentTickCount) {

        previousRpm = 0;
        previousTimeInMillis = System.currentTimeMillis();
        previousTickCount = currentTickCount;

    }


    public double getRpm(double currentTickCount, Telemetry telemetry) {

        double currentTimeInMillis = System.currentTimeMillis();

        if(currentTimeInMillis >= previousTimeInMillis+millisToWait) {

            double previousRotation = previousTickCount/28;
            double currentRotation = currentTickCount/28;
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

    public void resetTicks() {

        previousTickCount = 0;

    }

    public void setPGain(double newPGain) {
        pid.setPGain(newPGain);
    }

    public void setIGain(double newIGain) {
        pid.setIGain(newIGain);
    }

    public void setDGain(double newDGain) {
        pid.setDGain(newDGain);
    }

    public double getPGain() {
        return pid.getPGain();
    }

    public double getIGain() {
        return pid.getIGain();
    }

    public double getDGain() {
        return pid.getDGain();
    }

}
