package ca.retrylife.libepn.util;

import org.apache.commons.math3.complex.Quaternion;

/**
 * Utilities for working with quaternions
 */
public class QuaternionUtil {

    /**
     * Struct for containing Euler angles
     */
    public static class EulerAngles {
        public double alpha;
        public double beta;
        public double gamma;

        /**
         * Construct an EulerAngles object
         * 
         * @param alpha Alpha / Yaw / Z angle
         * @param beta  Beta / Pitch / Y angle
         * @param gamma Gamma / Roll / X angle
         */
        public EulerAngles(double alpha, double beta, double gamma) {
            this.alpha = alpha;
            this.beta = beta;
            this.gamma = gamma;
        }

        @Override
        public String toString() {
            return String.format("<[%.4f, %.4f, %.4f]>", alpha, beta, gamma);
        }

        public String toStringDegrees() {
            return String.format("<[%.4f, %.4f, %.4f]>", Math.toDegrees(alpha), Math.toDegrees(beta), Math.toDegrees(gamma));
        }
    }

    /**
     * Create a quaternion from Euler angles.
     * 
     * @param angles Angles in radians
     * @return Generated Quaternion
     */
    public static Quaternion quaternionFromEulerAngles(EulerAngles angles) {
        return quaternionFromEulerAngles(angles.alpha, angles.beta, angles.gamma);
    }

    /**
     * Create a quaternion from Euler angles. (ZYX)
     * 
     * This function uses an algorithm from Wikipedia:
     * https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles#Euler_Angles_to_Quaternion_Conversion
     * 
     * @param alpha Alpha / Yaw / Z angle (radians)
     * @param beta  Beta / Pitch / Y angle (radians)
     * @param gamma Gamma / Roll / X angle (radians)
     * @return Generated Quaternion
     */
    public static Quaternion quaternionFromEulerAngles(double alpha, double beta, double gamma) {

        // Create shorthands for values
        double cy = Math.cos(alpha * 0.5);
        double sy = Math.sin(alpha * 0.5);
        double cp = Math.cos(beta * 0.5);
        double sp = Math.sin(beta * 0.5);
        double cr = Math.cos(gamma * 0.5);
        double sr = Math.sin(gamma * 0.5);

        // Construct a quaternion
        return new Quaternion(cr * cp * cy + sr * sp * sy, sr * cp * cy - cr * sp * sy, cr * sp * cy + sr * cp * sy,
                cr * cp * sy - sr * sp * cy);
    }

    /**
     * Get a quaternion as Euler angles.
     * 
     * This implementation is based on:
     * http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/
     * 
     * @param q Quaternion
     * @return Euler angles in radians
     */
    public static EulerAngles eulerAnglesFromQuaternion(Quaternion q) {

        // Square every component
        double sqw = Math.pow(q.getQ0(), 2);
        double sqx = Math.pow(q.getQ1(), 2);
        double sqy = Math.pow(q.getQ2(), 2);
        double sqz = Math.pow(q.getQ3(), 2);

        // Correction factor if this is a non-normalized quaternion
        double correctionFactor = sqx + sqy + sqz + sqw;

        // Testing value to check if there is a singularity to deal with
        double testValue = q.getQ1() * q.getQ2() + q.getQ3() * q.getQ0();

        // Handle north pole singularity
        if (testValue > 0.499 * correctionFactor) {
            return new EulerAngles(Math.PI / 2, 2 * Math.atan2(q.getQ1(), q.getQ0()), 0);
        }

        // Handle south pole singularity
        if (testValue < -0.499 * correctionFactor) {
            return new EulerAngles(Math.PI / 2, -2 * Math.atan2(q.getQ1(), q.getQ0()), 0);
        }

        // Calculate euler angles
        double beta = Math.atan2(2 * q.getQ2() * q.getQ0() - 2 * q.getQ1() * q.getQ3(), sqx - sqy - sqz + sqw);
        double alpha = Math.asin(2 * testValue / correctionFactor);
        double gamma = Math.atan2(2 * q.getQ1() * q.getQ0() - 2 * q.getQ2() * q.getQ3(), -sqx + sqy - sqz + sqw);

        // Return the angles
        return new EulerAngles(alpha, beta, gamma);
    }

}