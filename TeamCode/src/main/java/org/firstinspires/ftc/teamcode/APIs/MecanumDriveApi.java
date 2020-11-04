package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveApi {

    DcMotor frontLeft, frontRight, rearLeft, rearRight;

    // The names of the motors in the hardware map
    String frontLeftMotorNameInHardwareMap = "frontLeft";
    String frontRightMotorNameInHardwareMap = "frontRight";
    String rearLeftMotorNameInHardwareMap = "rearLeft";
    String rearRightMotorNameInHardwareMap = "rearRight";

    double wheelSpeeds[] = new double[4];

    /**
     * This creates a new Mecanum API object which can be used to calculate values or drive the robot
     * @param frontLeft The front left motor object
     * @param frontRight The front right motor object
     * @param rearLeft The rear left motor object
     * @param rearRight The rear right motor object
     */
    public MecanumDriveApi(DcMotor frontLeft, DcMotor frontRight, DcMotor rearLeft, DcMotor rearRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
    }

    /**
     * This creates a new Mecanum API object which can be used to calculate values or drive the robot
     * @param hardwareMap The robot's hardware map
     */
    public MecanumDriveApi(HardwareMap hardwareMap) {

        frontLeft = hardwareMap.get(DcMotor.class, frontLeftMotorNameInHardwareMap);
        frontRight = hardwareMap.get(DcMotor.class, frontRightMotorNameInHardwareMap);
        rearLeft = hardwareMap.get(DcMotor.class, rearLeftMotorNameInHardwareMap);
        rearRight = hardwareMap.get(DcMotor.class, rearRightMotorNameInHardwareMap);

    }

    /**
     * Calculates the speed of wheel given the cartesian inputs. These values can be retrieved by using the get[wheel]Speed() methods.
     * @param x The X value of the left stick (or strafing value)
     * @param y The Y value of the left stick (or forward value)
     * @param rotation The X value of the right stick (or rotation value)
     */
    public void calculateCartesianValues(double x, double y, double rotation) {

        // Reverse the strafe and rotation values
        y = -y;
        rotation = -rotation;

        wheelSpeeds[0] = y + x + rotation; // Front left
        wheelSpeeds[1] = -y + x + rotation; // Front right
        wheelSpeeds[2] = -y + x - rotation; // Rear left
        wheelSpeeds[3] = y + x - rotation; // Rear right

        normalizeWheelSpeeds(wheelSpeeds);
    }

    /**
     * Calculates the speed of each wheel given the cartesian inputs, and actually drives the robot based on those values via the HardwareMap fed through the constructor.
     * @param x The X value of the left stick (or strafing value)
     * @param y The Y value of the left stick (or forward value)
     * @param rotation The X value of the right stick (or rotation value)
     */
    public void driveCartesian(double x, double y, double rotation) {

        // Reverse the strafe and rotation values
        y = -y;
        rotation = -rotation;

        wheelSpeeds[0] = y + x + rotation; // Front left
        wheelSpeeds[1] = -y + x + rotation; // Front right
        wheelSpeeds[2] = -y + x - rotation; // Rear left
        wheelSpeeds[3] = y + x - rotation; // Rear right

        normalizeWheelSpeeds(wheelSpeeds);

        frontLeft.setPower(wheelSpeeds[0]);
        frontRight.setPower(wheelSpeeds[1]);
        rearLeft.setPower(wheelSpeeds[2]);
        rearRight.setPower(wheelSpeeds[3]);

    }

    /**
     * Drive the robot forward at the speed specified
     * @param power
     */
    public void driveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        rearLeft.setPower(power);
        rearRight.setPower(power);
    }

    public void strafe(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        rearLeft.setPower(power);
        rearRight.setPower(-power);
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
