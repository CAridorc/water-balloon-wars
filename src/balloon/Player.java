package balloon;

import java.awt.geom.Point2D;
import java.util.Map;

public abstract class Player {
	private double speed = 0.1, strength = 0.1, luck = 0.1;
	protected abstract Action move(Map<Player, Point2D> map, Balloon balloon);
	public double getSpeed() {
		return speed;
	}
	public double getStrength() {
		return strength;
	}
	public double getLuck() {
		return luck;
	}
	public Player(double speed, double strength, double luck) {
		if (speed <= 0 || strength <= 0 || luck <= 0 || speed + luck + strength > 10) return;
		this.speed = speed;
		this.strength = strength;
		this.luck = luck;
	}
	
}
