package it.uniroma1.di.simulejos;

import it.uniroma1.di.simulejos.Robot.Sensor;
import it.uniroma1.di.simulejos.math.Matrix3;
import it.uniroma1.di.simulejos.math.Vector2;
import it.uniroma1.di.simulejos.math.Vector3;

final class CompassSensor extends Sensor implements
		it.uniroma1.di.simulejos.bridge.SimulatorInterface.CompassSensor {
	private final Matrix3 heading;
	private volatile double zero;

	public CompassSensor(Robot robot, Matrix3 heading) {
		robot.super();
		if (heading != null) {
			this.heading = heading;
		} else {
			this.heading = Matrix3.IDENTITY;
		}
	}

	private double getAbsoluteAngle() {
		final Vector3 needle = transform(heading.by(Vector3.K));
		final Vector2 flatNeedle = new Vector2(needle.z, -needle.x);
		return (Math.atan2(flatNeedle.y, flatNeedle.x) + Math.PI * 2)
				% (Math.PI * 2);
	}

	@Override
	public double getAngle() {
		return (zero + getAbsoluteAngle()) % 360;
	}

	@Override
	public double getCartesianAngle() {
		return (zero + 360 - getAbsoluteAngle()) % 360;
	}

	@Override
	public void setZero() {
		zero = getAbsoluteAngle();
	}

	@Override
	public void resetZero() {
		zero = 0;
	}
}
