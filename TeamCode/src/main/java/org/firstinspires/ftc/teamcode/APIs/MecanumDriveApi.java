package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveApi {

    DcMotor frontLeft, frontRight, rearLeft, rearRight;

    // The names of the motors in the hardware map
    String frontLeftMotorNameInHardwareMap = "frontLeft";
    String frontRightMotorNameInHardwareMap = "frontRight";
    String rearLeftMotorNameInHardwareMap = "rearLeft";
    String rearRightMotorNameInHardwareMap = "rearRight";

    double wheelSpeeds[] = new double[4];

    double ticksPerWheelRevolution;
    double wheelCircumferenceInInches;

    /**
     * This creates a new Mecanum API object which can be used to calculate values or drive the robot
     * @param frontLeft The front left motor object
     * @param frontRight The front right motor object
     * @param rearLeft The rear left motor object
     * @param rearRight The rear right motor object
     */
    public MecanumDriveApi(DcMotor frontLeft, DcMotor frontRight, DcMotor rearLeft, DcMotor rearRight, double ticksPerWheelRevolution, double wheelDiameterInInches) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.ticksPerWheelRevolution = ticksPerWheelRevolution;
        wheelCircumferenceInInches = Math.PI*wheelDiameterInInches;

        // Set the motors on the right to run in reverse
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * This creates a new Mecanum API object which can be used to calculate values or drive the robot
     * @param hardwareMap The robot's hardware map
     * @param ticksPerWheelRevolution The amount of encoder ticks per one revolution of the wheel (this needs to take into account all gearing)
     * @param wheelDiameterInInches The diameter of the wheel in inches
     */
    public MecanumDriveApi(HardwareMap hardwareMap, double ticksPerWheelRevolution, double wheelDiameterInInches) {
        frontLeft = hardwareMap.get(DcMotor.class, frontLeftMotorNameInHardwareMap);
        frontRight = hardwareMap.get(DcMotor.class, frontRightMotorNameInHardwareMap);
        rearLeft = hardwareMap.get(DcMotor.class, rearLeftMotorNameInHardwareMap);
        rearRight = hardwareMap.get(DcMotor.class, rearRightMotorNameInHardwareMap);
        this.ticksPerWheelRevolution = ticksPerWheelRevolution;
        wheelCircumferenceInInches = Math.PI*wheelDiameterInInches;

        // Set the motors on the right to run in reverse
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Calculates the speed of wheel given the cartesian inputs. These values can be retrieved by using the get[wheel]Speed() methods.
     * @param x The X value of the left stick (or strafing value)
     * @param y The Y value of the left stick (or forward value)
     * @param rotation The X value of the right stick (or rotation value)
     */
    public void calculateCartesianValues(double x, double y, double rotation) {

        // Reverse forward value
        y = -y;

        wheelSpeeds[0] = y + x + rotation; // Front left
        wheelSpeeds[1] = y - x + rotation; // Front right
        wheelSpeeds[2] = y - x + rotation; // Rear left
        wheelSpeeds[3] = y + x + rotation; // Rear right

        normalizeWheelSpeeds(wheelSpeeds);
    }

    /**
     * Calculates the speed of each wheel given the cartesian inputs, and actually drives the robot based on those values via the HardwareMap fed through the constructor.
     * @param x The X value of the left stick (or strafing value)
     * @param y The Y value of the left stick (or forward value)
     * @param rotation The X value of the right stick (or rotation value)
     */
    public void driveCartesian(double x, double y, double rotation) {

        // Reverse the forward value
        y = -y;

        wheelSpeeds[0] = y + x + rotation; // Front left
        wheelSpeeds[1] = y - x + rotation; // Front right
        wheelSpeeds[2] = y - x + rotation; // Rear left
        wheelSpeeds[3] = y + x + rotation; // Rear right

        normalizeWheelSpeeds(wheelSpeeds);

        frontLeft.setPower(wheelSpeeds[0]);
        frontRight.setPower(wheelSpeeds[1]);
        rearLeft.setPower(wheelSpeeds[2]);
        rearRight.setPower(wheelSpeeds[3]);

    }

    /**
     * Drive the robot forward at the speed specified
     * @param power The power to move forward
     */
    public void driveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        rearLeft.setPower(power);
        rearRight.setPower(power);
    }

    /**
     * Drives forward the specified distance using PID
     * @param distanceToTravelInInches The distance to travel in inches
     * @param p The P value
     * @param i The I value
     * @param d The D value
     * @return Returns true when the target has been reached
     */
    public boolean driveForwardPid(double distanceToTravelInInches, double p, double i, double d) {

        PidApi pid = new PidApi(p, i, d);

        resetEncoders();

        double averageDistanceTraveledInTicks = 0;
        double distanceToTravelInTicks = inchesToTicks(distanceToTravelInInches);

        while(averageDistanceTraveledInTicks != distanceToTravelInInches) {

            averageDistanceTraveledInTicks = (frontLeft.getCurrentPosition()+frontRight.getCurrentPosition()+rearLeft.getCurrentPosition()+rearRight.getCurrentPosition())/4;

            double power = pid.getOutput(averageDistanceTraveledInTicks, distanceToTravelInTicks);

            driveForward(power);

        }

        return true;

    }

    /**
     * Strafe at the power specified
     * @param power The power to strafe
     */
    public void strafe(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        rearLeft.setPower(-power);
        rearRight.setPower(power);
    }

    public boolean strafePid(double distanceToTravelInInches, double p, double i, double d) {

        boolean areWeTravelingRight;

        if(distanceToTravelInInches > 0) {
            areWeTravelingRight = true;
        } else if(distanceToTravelInInches < 0) {
            areWeTravelingRight = false;
        } else {
            return true;
        }

        PidApi pid = new PidApi(p, i, d);

        resetEncoders();

        double averageDistanceTraveled = 0;

        while(averageDistanceTraveled != distanceToTravelInInches) {

            averageDistanceTraveled = (Math.abs(frontLeft.getCurrentPosition())+Math.abs(frontRight.getCurrentPosition())+Math.abs(rearLeft.getCurrentPosition())+Math.abs(rearRight.getCurrentPosition()))/4;
            double distanceToTravelInTicks = inchesToTicks(distanceToTravelInInches);

            // If we're moving left so the distance traveled needs to be a negative number
            if(!areWeTravelingRight) {
                averageDistanceTraveled = -averageDistanceTraveled;
            }

            double power = pid.getOutput(averageDistanceTraveled, distanceToTravelInTicks);

            strafe(power);

        }

        return true;

    }

    /**
     * Reset all of the drive motor encoders
     */
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Changes inches to ticks
     * @param inches The amount of inches
     * @return That amount of inches in ticks
     */
    private double inchesToTicks(double inches) {
        return (inches/wheelCircumferenceInInches)*ticksPerWheelRevolution;
    }

    /**
     * Change ticks to inches
     * @param ticks The amount of ticks
     * @return That amount of ticks as inches
     */
    private double ticksToInches(double ticks) {
        return (ticks/ticksPerWheelRevolution)*wheelCircumferenceInInches;
    }

    private void normalizeWheelSpeeds(double[] wheelSpeeds) {

        double maxMagnitude = Math.abs(wheelSpeeds[0]);

        for (int i = 1; i < wheelSpeeds.length; i++) {

            double magnitude = Math.abs(wheelSpeeds[i]);

            if (magnitude > maxMagnitude) {
                maxMagnitude = magnitude;
            }

        }

        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }

    }

    /**
     * Get the calculated speed of the front left wheel
     * @return The speed of the front left wheel
     */
    public double getFrontLeftSpeed() {
        return wheelSpeeds[0];
    }

    /**
     * Get the calculated speed of the front right wheel
     * @return The speed of the front right wheel
     */
    public double getFrontRightSpeed() {
        return wheelSpeeds[1];
    }

    /**
     * Get the calculated speed of the rear left wheel
     * @return The speed of the rear left wheel
     */
    public double getRearLeftSpeed() {
        return wheelSpeeds[2];
    }

    /**
     * Get the calculated speed of the rear right wheel
     * @return The speed of the rear right wheel
     */
    public double getRearRightSpeed() {
        return wheelSpeeds[3];
    }

}
