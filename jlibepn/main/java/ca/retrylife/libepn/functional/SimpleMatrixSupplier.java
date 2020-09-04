package ca.retrylife.libepn.functional;

import org.ejml.simple.SimpleMatrix;

@FunctionalInterface
public interface SimpleMatrixSupplier {

    /**
     * Get the matrix
     * 
     * @return SimpleMatrix
     */
    public SimpleMatrix get();

}