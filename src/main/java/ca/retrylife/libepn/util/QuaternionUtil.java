package ca.retrylife.libepn.util;

import org.apache.commons.math3.complex.Quaternion;
import org.ejml.simple.SimpleMatrix;

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
         * @param beta  Beta / Pitch / X angle
         * @param gamma Gamma / Roll / Y angle
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

        /**
         * Get a string containing the angles in degrees
         * 
         * @return Angles in degrees string
         */
        public String toStringDegrees() {
            return String.format("<[%.4f, %.4f, %.4f]>", Math.toDegrees(alpha), Math.toDegrees(beta),
                    Math.toDegrees(gamma));
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof EulerAngles) {
                return (((EulerAngles) obj).alpha == this.alpha) && (((EulerAngles) obj).beta == this.beta)
                        && (((EulerAngles) obj).gamma == this.gamma);
            }
            return false;
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
     * Create a quaternion from Euler angles. (ZXY)
     * 
     * This function uses an algorithm from Wikipedia:
     * https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles#Euler_Angles_to_Quaternion_Conversion
     * 
     * @param alpha Alpha / Yaw / Z angle (radians)
     * @param beta  Beta / Pitch / X angle (radians)
     * @param gamma Gamma / Roll / Y angle (radians)
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

    /**
     * Create a new vector that is the result of a vector times a quaternion. This
     * is a direct translation of the operator* function from Unity's Quaternion
     * class.
     * 
     * @param vec A vector
     * @param q   A quaternion
     * @return A rotated version of the vector
     */
    public static SimpleMatrix rotateVectorByQuaternion(SimpleMatrix vec, Quaternion q) {

        // Calculate coeffs
        double num = q.getQ1() * 2.0;
        double num2 = q.getQ2() * 2.0;
        double num3 = q.getQ3() * 2.0;
        double num4 = q.getQ1() * num;
        double num5 = q.getQ2() * num2;
        double num6 = q.getQ3() * num3;
        double num7 = q.getQ1() * num2;
        double num8 = q.getQ1() * num3;
        double num9 = q.getQ2() * num3;
        double num10 = q.getQ0() * num;
        double num11 = q.getQ0() * num2;
        double num12 = q.getQ0() * num3;

        // Determine new X Y and Z components of vector
        double x = (1.0 - (num5 + num6)) * vec.get(0) + (num7 - num12) * vec.get(1) + (num8 + num11) * vec.get(2);
        double y = (num7 + num12) * vec.get(0) + (1.0 - (num4 + num6)) * vec.get(1) + (num9 - num10) * vec.get(2);
        double z = (num8 - num11) * vec.get(0) + (num9 + num10) * vec.get(1) + (1.0 - (num4 + num5)) * vec.get(2);

        // Build new vector
        return new SimpleMatrix(new double[][] { { x, y, z } });
    }

}
