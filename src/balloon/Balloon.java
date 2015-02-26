package balloon;

import java.awt.geom.Point2D;
import java.util.Random;

import balloon.Action.Hit;

public class Balloon {
	private Point2D location = new Point2D.Double();
	private double height = 30;
	private double velocityX = 0, velocityY = 0, velocityZ = 0;
	void changeVelocity(Hit h, double luck, Random rnd) {
		velocityX += h.dirX * rnd.nextDouble() * luck;
		velocityY += h.dirY * rnd.nextDouble() * luck;
		velocityZ += h.dirZ * rnd.nextDouble() * luck;
	}
	void resetHeight() {
		height = 30;
	}
	void step(Random rnd) {
		double resistance = (rnd.nextDouble() / 5 + 0.6);
		velocityX = (velocityX + (rnd.nextDouble() - 0.5) * 2) * resistance;
		velocityY = (velocityY + (rnd.nextDouble() - 0.5) * 2) * resistance;
		velocityZ = (velocityZ - 3 + (rnd.nextDouble() - 0.5) * 2) * resistance;
		location.setLocation(location.getX() + velocityX, location.getY() + velocityY);
		height += velocityZ;
		if (height < 0) {
			height = 0;
			velocityX = velocityY = velocityZ = 0;
		}
	}
	public double getHeight() {
		return height;
	}
	public Point2D getLocation() {
		return (Point2D) location.clone();
	}
	public double getVelocityX() {
		return velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public double getVelocityZ() {
		return velocityZ;
	}
	public String toString() {
		return "Balloon at " + location.toString() + " height " + height + "\n"
				+ "Velocities: " + velocityX + ", " + velocityY + ", " + velocityZ;
	}
}
