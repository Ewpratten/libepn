package ca.retrylife.libepn;

import java.util.Objects;

import org.apache.commons.math3.complex.Quaternion;
import org.ejml.simple.SimpleMatrix;

import ca.retrylife.libepn.util.DirectionUtil;
import ca.retrylife.libepn.util.QuaternionUtil;
import ca.retrylife.libepn.util.QuaternionUtil.EulerAngles;

/**
 * A pose is an immutable representation of a position in 3D space. Poses are
 * made up of a position vector, and a quaternion to denote rotation.
 */
public final class Pose {

    // Vectors
    private final SimpleMatrix position;
    private final SimpleMatrix normal;

    // Rotations
    private final Quaternion rotation;
    private final EulerAngles eulerAngles;

    /**
     * Create a pose at a position with a single axis of rotation.
     * 
     * @param position Position in 3D space
     * @param yAngle   Angle in radians around the Y axis
     */
    public Pose(SimpleMatrix position, double yAngle) {
        this(position, new EulerAngles(0, yAngle, 0));
    }

    /**
     * Create a pose at a position with an Euler rotation.
     * 
     * @param position Position in 3D space
     * @param rotation Euler rotation in radians
     */
    public Pose(SimpleMatrix position, EulerAngles rotation) {
        this(position, QuaternionUtil.quaternionFromEulerAngles(rotation));
    }

    /**
     * Create a pose at a position facing another position.
     * 
     * @param position  Position in 3D space
     * @param lookingAt Other position in 3D space
     */
    public Pose(SimpleMatrix position, SimpleMatrix lookingAt) {
        this(position, DirectionUtil.getAnglesFromPositions(position, lookingAt));
    }

    /**
     * Create a pose at a position with no rotation.
     * 
     * @param position Position in 3D space
     */
    public Pose(SimpleMatrix position) {
        this(position, new Quaternion(0, 0, 0, 0));
    }

    /**
     * Create a pose at (0,0,0) with a rotation.
     * 
     * @param rotation 3D rotation as a quaternion
     */
    public Pose(Quaternion rotation) {
        this(new SimpleMatrix(new double[][] { { 0, 0, 0 } }), rotation);
    }

    /**
     * Create a pose at a position with a rotation.
     * 
     * @param position Position in 3D space
     * @param rotation 3D rotation as a quaternion
     */
    public Pose(SimpleMatrix position, Quaternion rotation) {

        // Set the position and rotation
        this.position = position;
        this.rotation = rotation;

        // Set the euler angles
        this.eulerAngles = QuaternionUtil.eulerAnglesFromQuaternion(rotation);

        // Calculate the normal
        this.normal = QuaternionUtil.rotateVectorByQuaternion(position, rotation);
    }

    /**
     * Copy constructor for a Pose
     * 
     * @param other Pose to copy from
     */
    public Pose(Pose other) {
        this.position = other.getPosition();
        this.normal = other.getNormal();
        this.rotation = other.getRotation();
        this.eulerAngles = other.getEulerRotation();
    }

    /**
     * Get the normal of this pose. (rotation unit vector + position vector)
     * 
     * @return Pose normal
     */
    public SimpleMatrix getNormal() {
        return normal;
    }

    /**
     * Get the rotation of this pose
     * 
     * @return Pose rotation
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * Get the Euler rotation of this pose. Warning: Precision may be lost in an
     * effort to prevent gimbal lock. Gimbal lock may still occur in some cases.
     * 
     * @return Euler rotation
     */
    public EulerAngles getEulerRotation() {
        return eulerAngles;
    }

    /**
     * Get the 3D position of this pose
     * 
     * @return 3D position
     */
    public SimpleMatrix getPosition() {
        return position;
    }

    /**
     * Get the X coordinate of this pose
     * 
     * @return X coordinate
     */
    public double getX() {
        return getPosition().get(0);
    }

    /**
     * Get the Y coordinate of this pose
     * 
     * @return Y coordinate
     */
    public double getY() {
        return getPosition().get(1);
    }

    /**
     * Get the Z coordinate of this pose
     * 
     * @return Z coordinate
     */
    public double getZ() {
        return getPosition().get(2);
    }

    /**
     * Get the real component of this pose's rotation
     * 
     * @return Quaternion W
     */
    public double getQW() {
        return getRotation().getQ0();
    }

    /**
     * Get the X component of this pose's rotation
     * 
     * @return Quaternion X
     */
    public double getQX() {
        return getRotation().getQ1();
    }

    /**
     * Get the Y component of this pose's rotation
     * 
     * @return Quaternion Y
     */
    public double getQY() {
        return getRotation().getQ2();
    }

    /**
     * Get the Z component of this pose's rotation
     * 
     * @return Quaternion Z
     */
    public double getQZ() {
        return getRotation().getQ3();
    }

    /**
     * Returns the difference between two poses (other - this)
     * 
     * @param other Other pose
     * @return Difference
     */
    public Pose relativeTo(Pose other) {
        return new Pose(other.getPosition().minus(getPosition()), other.getRotation().subtract(getRotation()));
    }

    @Override
    public String toString() {
        return String.format("<Pose: [XYZ: %s, Rads: %s]>", position, eulerAngles);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Pose) {
            return (((Pose) obj).getPosition().equals(getPosition())) && (((Pose) obj).getNormal().equals(getNormal()))
                    && (((Pose) obj).getRotation().equals(getRotation()))
                    && (((Pose) obj).getEulerRotation().equals(getEulerRotation()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, normal, rotation, eulerAngles);
    }

}