// Another example player
package players;

import java.awt.geom.Point2D;
import java.util.Map;

import balloon.*;

public class Player2 extends Player {

	public Player2() {
		super(1, 1, 8);
	}

	@Override
	protected Action move(Map<Player, Point2D> map, Balloon balloon) {
		return new Action.Hit(1 / Math.sqrt(2), 1 / Math.sqrt(2), 0);
	}

}
