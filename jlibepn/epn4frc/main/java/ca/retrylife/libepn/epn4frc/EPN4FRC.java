package ca.retrylife.libepn.epn4frc;

import org.ejml.simple.SimpleMatrix;

import ca.retrylife.libepn.Pose;
import ca.retrylife.libepn.util.QuaternionUtil.EulerAngles;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * EPN4FRC is a utility class for converting between LibEPN and WPILib
 * coordinate systems and object types with ease.
 */
public class EPN4FRC {

    /**
     * Coordinate system. (Standard vs. RaiderRobotics)
     */
    public enum CoordinateSystem {
        kRaiderRobotics, kWPILib;
    }

    /**
     * Convert from a WPILib Pose2d to a LibEPN Pose
     * 
     * @param pose   WPILib pose
     * @param system Coordinate system to convert from
     * @return LibEPN pose
     */
    public static Pose pose2dToPose(Pose2d pose, CoordinateSystem system) {

        // Get the y-axis angle, and flip it if needed
        double beta = pose.getRotation().getRadians() * ((system.equals(CoordinateSystem.kRaiderRobotics)) ? -1 : 1);

        // Construct and return a new Pose
        return new Pose(
                new SimpleMatrix(
                        new double[][] { { pose.getTranslation().getX(), 0, pose.getTranslation().getY() * -1 } }),
                new EulerAngles(0, beta, 0));
    }

    /**
     * Convert from a LibEPN Pose2d to a WPILib Pose
     * 
     * @param pose   LibEPN pose
     * @param system Coordinate system to convert to
     * @return WPILib pose
     */
    public static Pose2d poseToPose2d(Pose pose, CoordinateSystem system) {

        // Get the pose's euler angles
        EulerAngles euler = pose.getEulerRotation();

        // Construct and return a new pose
        return new Pose2d(new Translation2d(pose.getX(), pose.getZ() * -1),
                new Rotation2d(euler.beta * ((system.equals(CoordinateSystem.kRaiderRobotics)) ? -1 : 1)));
    }
}