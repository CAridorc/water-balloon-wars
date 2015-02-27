package balloon;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import players.*;
import balloon.Action.*;

public class Field {
	private Map<Player, Point2D> arena = new HashMap<>();
	private Map<Player, Integer> scores = new HashMap<>();
	private Player lastHitBalloon = null;
	private Random rnd = new Random(32);
	private Balloon balloon = new Balloon();
	private static final Class<?>[] players = {
		AngryPenguin.class,
		LuckyLoser.class,
		Hydrophobe.class,
		BackAndForth.class
	};
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		Field f = new Field();
		double sector = 2*Math.PI / players.length;
		double place = 0;
		for (Class<?> pClass : players) {
			f.arena.put((Player) pClass.newInstance(), new Point2D.Double(Math.cos(place) * 10, Math.sin(place) * 10));
			place += sector;
		}
		BufferedWriter rw = new BufferedWriter(new FileWriter("log.out"));
		for (Player p : f.arena.keySet()) {
			f.scores.put(p, 0);
		}
		rw.append(f.toString());
		for (int i = 0; i < 1000; ++i) {
			f.step();
			rw.append(f.toString());
		}
		rw.flush();
		rw.close();
		System.out.println(f.scores);
	}
	public void step() {
		List<Player> hitBalloon = new ArrayList<Player>();
		for (Entry<Player, Point2D> e : arena.entrySet()) {
			Player p = e.getKey();
			Point2D location = e.getValue();
			Action playerAction = p.move(cloneArena(), balloon);
			double speed = p.getSpeed(), strength = p.getStrength(), luck = p.getLuck();
			if (playerAction instanceof Movement) {
				Movement m = (Movement) playerAction;
				if (m.x * m.x + m.y * m.y > speed*speed + 0.001) continue;
				location.setLocation(location.getX() + m.x, location.getY() + m.y);
			} else if (playerAction instanceof Hit) {
				if (location.distanceSq(balloon.getLocation()) <= 16 && balloon.getHeight() <= 10) {
					Hit h = (Hit) playerAction;
					if (h.dirX * h.dirX + h.dirY * h.dirY + h.dirZ * h.dirZ > strength*strength + 0.001) continue;
					balloon.changeVelocity(h, luck, rnd);
					hitBalloon.add(p);
				}
			}
		}
		balloon.step(rnd);
		double total = 0;
		Map<Player, Double> luckMap = new HashMap<>();
		for (Player p : hitBalloon) {
			double d = rnd.nextDouble() * p.getLuck();
			luckMap.put(p, d);
			total += d;
		}
		double rand = rnd.nextDouble() * total;
		for (Player p : hitBalloon) {
			rand -= luckMap.get(p);
			if (rand <= 0) {
				lastHitBalloon = p;
				break;
			}
 		}
		if (balloon.getHeight() == 0) {
			losePoints();
			if (scores.containsKey(lastHitBalloon)) {
				scores.put(lastHitBalloon, scores.get(lastHitBalloon) + 3);
			}
			balloon.resetHeight();
		}
	}
	private void losePoints() {
		Map<Player, Double> distanceMap = new HashMap<>();
		double totalDistance = 0;
		for (Entry<Player, Point2D> e : arena.entrySet()) {
			double distance = 1/(e.getValue().distance(balloon.getLocation()) + rnd.nextDouble()*e.getKey().getLuck());
			totalDistance += distance;
			distanceMap.put(e.getKey(), distance);
		}
		double rand = rnd.nextDouble() * totalDistance;
		for (Entry<Player, Double> distance : distanceMap.entrySet()) {
			double d = distance.getValue();
			rand -= d;
			if (rand <= 0) {
				Player p = distance.getKey();
				scores.put(p, scores.get(p) - 4);
				break;
			}
		}
	}
	private Map<Player, Point2D> cloneArena() {
		Map<Player, Point2D> ret = new HashMap<Player, Point2D>();
		for (Entry<Player, Point2D> e : arena.entrySet()) {
			ret.put(e.getKey(), (Point2D) e.getValue().clone());
		}
		return ret;
	}
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.####");
		StringBuilder r = new StringBuilder();
		for (Entry<Player, Point2D> e : arena.entrySet()) {
			String name = e.getKey().getClass().getCanonicalName();
			Point2D p = e.getValue();
			r.append(name);
			r.append(':');
			r.append(df.format(p.getX()));
			r.append(',');
			r.append(df.format(p.getY()));
			r.append(':');
			r.append(scores.get(e.getKey()));
			r.append(';');
		}
		r.append('!');
		r.append(df.format(balloon.getLocation().getX()));
		r.append(',');
		r.append(df.format(balloon.getLocation().getY()));
		r.append(',');
		r.append(df.format(balloon.getHeight()));
		r.append('\n');
		return r.toString();
	}
}
