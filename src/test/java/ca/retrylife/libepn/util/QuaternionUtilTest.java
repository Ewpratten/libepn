package ca.retrylife.libepn.util;

import org.apache.commons.math3.complex.Quaternion;

import ca.retrylife.libepn.util.QuaternionUtil.EulerAngles;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuaternionUtilTest {

    @Test
    public void testQuaternionToEuler() {

        // Make a quaternion
        Quaternion q = new Quaternion(-0.2706, 0.65328, 0.2706, -0.65328);

        // Convert to euler angles
        EulerAngles a = QuaternionUtil.eulerAnglesFromQuaternion(q);

        // Check correct angles
        assertEquals("Alpha", 45, Math.toDegrees(a.alpha), 0.001);
        assertEquals("Beta", 90, Math.toDegrees(a.beta), 0.001);
        assertEquals("Gamma", 180, Math.toDegrees(a.gamma), 0.001);

    }

    @Test
    public void testEulerToQuaternion() {
        Quaternion q = QuaternionUtil.quaternionFromEulerAngles(Math.toRadians(90), 0, 0);
        assertEquals("Quaternion", true, new Quaternion(0.7071065431725605, 0, 0, 0.7071070192004544).equals(q, 0.0001));
    }
}