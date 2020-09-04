package ca.retrylife.libepn.util;

import org.ejml.simple.SimpleMatrix;

import ca.retrylife.libepn.util.QuaternionUtil.EulerAngles;

/**
 * Utilities for dealing with direction and "pointing at things"
 */
public class DirectionUtil {

    /**
     * Calculate the Euler angles between two positions
     * 
     * @param position  Current position
     * @param lookingAt Position we are "looking at"
     * @return Euler angles
     */
    public static EulerAngles getAnglesFromPositions(SimpleMatrix position, SimpleMatrix lookingAt) {

        // Get the difference between the positions
        SimpleMatrix dp = lookingAt.minus(position);

        // Calculate angle around X (on the Y/Z plane)
        double xTheta = Math.atan2(dp.get(2), dp.get(1));

        // Calculate angle around Y (on the X/Z plane)
        double yTheta = Math.atan2(dp.get(2), dp.get(0));

        // Calculate angle around Z (on the X/Y plane)
        double zTheta = Math.atan2(dp.get(1), dp.get(0));

        // Return angles
        return new EulerAngles(zTheta, yTheta, xTheta);
    }

}