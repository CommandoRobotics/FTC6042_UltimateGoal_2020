package org.firstinspires.ftc.teamcode.APIs;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorControlApi {

    PidApi pid;

    double previousRunTimeInMillis;
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
        previousRunTimeInMillis = System.currentTimeMillis();
        previousTickCount = currentTickCount;

    }


    public double getRpm(double currentTickCount, Telemetry telemetry) {

        double currentTimeInMillis = System.currentTimeMillis();

        if(currentTimeInMillis >= previousTickCount+millisToWait) {

            double changeInTicks;
            double changeInTimeInSeconds = (currentTimeInMillis-previousRunTimeInMillis)/1000;

            if(previousTickCount > currentTickCount) {

                changeInTicks = previousTickCount-currentTickCount;

            } else if(currentTickCount > previousTickCount) {

                changeInTicks = currentTickCount-previousTickCount;

            } else {
                // This means that they're the same, so the RPM is 0
                return 0;
            }

            double changeInRotation = changeInTicks/28;

            telemetry.addLine("Change in rotation: " + changeInRotation);

            double changeInRotationPerMinute = (changeInTicks*60)/changeInTimeInSeconds;

            previousRpm = changeInRotationPerMinute;
            previousTickCount = currentTickCount;
            previousRunTimeInMillis = currentTimeInMillis;

            return changeInRotationPerMinute;

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
