package ca.retrylife.libepn.util;

import ca.retrylife.libepn.util.QuaternionUtil.EulerAngles;

import static org.junit.Assert.assertEquals;

import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

public class DirectionUtilTest {

    @Test
    public void testLookAt() {
        // This is visualized here: https://www.math3d.org/wYYwSZcR

        // Create the current position
        SimpleMatrix currentPosition = new SimpleMatrix(new double[][] { { 0, 0, 0 } });

        // Create position to look at
        SimpleMatrix lookingAt = new SimpleMatrix(new double[][] { { 1, -1, -1 } });

        // Get the angles
        EulerAngles a = DirectionUtil.getAnglesFromPositions(currentPosition, lookingAt);

        // Check correct angles
        assertEquals("Alpha", -45, Math.toDegrees(a.alpha), 0.001);
        assertEquals("Beta", -45, Math.toDegrees(a.beta), 0.001);
        assertEquals("Gamma", -135, Math.toDegrees(a.gamma), 0.001);

    }

}