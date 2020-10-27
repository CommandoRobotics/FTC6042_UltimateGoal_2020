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

    public void getCartesianValues(double x, double y, double rotation) {

        // Reverse the strafe and rotation values
        y = -y;
        rotation = -rotation;

        wheelSpeeds[0] = y + x + rotation; // Front left
        wheelSpeeds[1] = -y + x + rotation; // Front right
        wheelSpeeds[2] = -y + x - rotation; // Rear left
        wheelSpeeds[3] = y + x - rotation; // Rear right

        normalizeWheelSpeeds(wheelSpeeds);
    }

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

    public void driveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        rearLeft.setPower(power);
        rearRight.setPower(power);
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

    public double getFrontLeftSpeed() {
        return wheelSpeeds[0];
    }

    public double getFrontRightSpeed() {
        return wheelSpeeds[1];
    }

    public double getRearLeftSpeed() {
        return wheelSpeeds[2];
    }

    public double getRearRightSpeed() {
        return wheelSpeeds[3];
    }

}
