package ca.retrylife.libepn;

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

    }

    public Pose(Pose other) {

    }
    
    public SimpleMatrix getNormal() {
        return null;
    }
    
    public Quaternion getQuaternion(){
        return null;
    }

    public EulerAngles getEulerAngles() {
        return QuaternionUtil.eulerAnglesFromQuaternion(getQuaternion());
    }
    
    public SimpleMatrix getPosition(){
        return null;
    }

}