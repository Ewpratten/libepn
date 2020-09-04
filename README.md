![LibEPN Logo](./assets/libepn.png)

<!-- Badges -->
![Build Library](https://github.com/Ewpratten/libepn/workflows/Build%20Library/badge.svg) 
[![Maven Repository](https://ultralight.retrylife.ca:/api/artifact/ca.retrylife/libepn/shield?d=ultralight.retrylife.ca)](https://ultralight.retrylife.ca/?a=libepn&g=ca.retrylife)
[![Packages](https://img.shields.io/badge/Release-1.1.0-blue)](https://github.com/Ewpratten/libepn/releases/latest)
[![Documentation](https://img.shields.io/badge/-documentation-blue)](https://ewpratten.retrylife.ca/libepn)
---

LibEPN (*Easy Pose Notation*) is a Java library designed to provide an easy-to-use interface for 3D position and orientation. The library is heavily dependant on [Quaternions](https://en.wikipedia.org/wiki/Quaternion), and makes use of the following dependencies:

 - [Apache Commons Math](https://commons.apache.org/proper/commons-math/)
 - [Efficient Java Matrix Library](http://ejml.org)

I use this library in various personal and school projects relating to pose estimation, robotics, and computer vision. 

## Installation

LibEPN can be installed from:

 - [My maven server](https://ultralight.retrylife.ca/?a=libepn&g=ca.retrylife) (recommended)
 - [The GitHub Packages repository](https://github.com/Ewpratten/libepn/packages)
 - [JitPack](https://jitpack.io/#ewpratten/libepn)
 - [A JAR file](https://github.com/Ewpratten/libepn/releases/latest)

## Coordinates

The following should be noted when working with coordinates:

 - `QuaternionUtil.quaternionFromEulerAngles` and `QuaternionUtil.EulerAngles` take in coordinates in the order `Z, Y, X`.
 - `alpha`, `beta`, and `gamma` refer respectively to the `Z`, `Y` and `X` axes of rotation.
 - The coordinate system assumes that:
   - If you are at `(x: 0, y: x, z: 1)` facing `(0,0,0)`, the `Z` axis will be coming "towards" you, `X` will be increasing to your right, and `Y` will be increasing towards the sky.
 - All angles will increase to the left around any axis assuming you are in line with the axis, looking at `(0,0,0)`

![Orientation graphic](./assets/libepn-orientation.png)

## Basic usage

The following is a basic usage example. See the [JavaDoc](https://ewpratten.retrylife.ca/libepn) for full API reference.

```java
// Create a pose at (10,0,0) rotated around the Z axis to the left by 45 degrees
Pose p = new Pose(
    new SimpleMatrix(new double[][]{{10,0,0}}), 
    QuaternionUtil.quaternionFromEulerAngles(Math.toRadians(45), 0, 0)
);

// Print the pose's quaternion
System.out.println(p.getRotation());

// Print the pose's rotation as euler angles
System.out.println(p.getEulerRotation());

// Print the pose's position
System.out.println(p.getPosition());

// Print the pose's "surface normal
System.out.println(p.getNormal());

```

## EPN4FRC

This library also contains a sub-library called EPN4FRC. This is a small binding between LibEPN and [WPILib](https://github.com/wpilibsuite/allwpilib/)'s [Pose2d](https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/geometry/Pose2d.html) coordinate system for the *FIRST* Robotics Competition. EPN4FRC in-source documentation can be found [here](https://github.com/Ewpratten/libepn/blob/master/epn4frc/src/main/java/ca/retrylife/libepn/epn4frc/EPN4FRC.java).

## Credits

This library was heavily supported by the following sources:
 - Wikipedia: [Quaternions](https://en.wikipedia.org/wiki/Quaternion)
 - Wikipedia: [Conversion between quaternions and Euler angles](https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles)
 - 3blue1brown: [Visualizing Quaternions](https://www.youtube.com/watch?v=d4EgbgTm0Bg)
 - 3blue1brown: [Quaternions and 3d rotation, explained interactively](https://www.youtube.com/watch?v=zjMuIxRvygQ)
