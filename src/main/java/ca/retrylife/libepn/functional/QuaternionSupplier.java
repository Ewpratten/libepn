package ca.retrylife.libepn.functional;

import org.apache.commons.math3.complex.Quaternion;

@FunctionalInterface
public interface QuaternionSupplier {

    /**
     * Get the quaternion
     * 
     * @return Quaternion
     */
    public Quaternion get();

}