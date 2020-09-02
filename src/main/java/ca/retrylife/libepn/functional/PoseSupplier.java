package ca.retrylife.libepn.functional;

import ca.retrylife.libepn.Pose;

/**
 * Supplies a pose
 */
@FunctionalInterface
public interface PoseSupplier {
    
    /**
     * Get a pose
     * @return Pose
     */
    public Pose get();

}