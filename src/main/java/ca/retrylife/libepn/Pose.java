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

    private final SimpleMatrix position;
    private final SimpleMatrix normal;

    private final Quaternion rotation;
    private final EulerAngles eulerAngles;

    public Pose(SimpleMatrix position, double yAngle) {
        this(position, new EulerAngles(0, yAngle, 0));
    }

    public Pose(SimpleMatrix position, EulerAngles rotation) {
        this(position, QuaternionUtil.quaternionFromEulerAngles(rotation));
    }

    public Pose(SimpleMatrix position, SimpleMatrix lookingAt) {
        this(position, DirectionUtil.getAnglesFromPositions(position, lookingAt));
    }

    public Pose(SimpleMatrix position) {
        this(position, new Quaternion(0, 0, 0, 0));
    }

    public Pose(Quaternion rotation) {
        this(new SimpleMatrix(new double[][] { { 0, 0, 0 } }), rotation);
    }

    public Pose(SimpleMatrix position, Quaternion rotation) {

        // Set the position and rotation
        this.position = position;
        this.rotation = rotation;

        // Set the euler angles
        this.eulerAngles = QuaternionUtil.eulerAnglesFromQuaternion(rotation);

        // Calculate the normal
        this.normal = QuaternionUtil.rotateVectorByQuaternion(position, rotation);
    }

    public Pose(Pose other) {
        this.position = other.getPosition();
        this.normal = other.getNormal();
        this.rotation = other.getRotation();
        this.eulerAngles = other.getEulerRotation();
    }

    public SimpleMatrix getNormal() {
        return normal;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public EulerAngles getEulerRotation() {
        return eulerAngles;
    }

    public SimpleMatrix getPosition() {
        return position;
    }

    public double getX() {
        return getPosition().get(0);
    }

    public double getY() {
        return getPosition().get(1);
    }

    public double getZ() {
        return getPosition().get(2);
    }

    public double getQW() {
        return getRotation().getQ0();
    }

    public double getQX() {
        return getRotation().getQ1();
    }

    public double getQY() {
        return getRotation().getQ2();
    }

    public double getQZ() {
        return getRotation().getQ3();
    }

    public Pose relativeTo(Pose newOrigin) {
        return new Pose(newOrigin.getPosition().minus(getPosition()), newOrigin.getRotation().subtract(getRotation()));
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