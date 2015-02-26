// Example player
package players;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Random;

import balloon.*;
import balloon.Action.*;

public class Player1 extends Player {
	private static Random r = new Random(100);
	public Player1() {
		super(3, 3, 4);
	}

	@Override
	protected Action move(Map<Player, Point2D> map, Balloon b) {
		Point2D myself = map.get(this);
		if (myself.distanceSq(b.getLocation()) <= 16) {
			double d = (r.nextDouble() - 0.5) * 3;
			return new Hit(0, d, Math.sqrt(9 - d*d));
		} else {
			double diffX = b.getLocation().getX() - myself.getX(),
					diffY = b.getLocation().getY() - myself.getY();
			return new Movement(Math.signum(diffX)*3/Math.sqrt(2), Math.signum(diffY)*3/Math.sqrt(2));
		}
	}

}
