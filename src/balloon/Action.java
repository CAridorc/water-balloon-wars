package balloon;

public abstract class Action {
	public static class Movement extends Action {
		public double x, y;
		public Movement(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	public static class Hit extends Action {
		public double dirX, dirY, dirZ;
		public Hit(double dirX, double dirY, double dirZ) {
			this.dirX = dirX;
			this.dirY = dirY;
			this.dirZ = dirZ;
		}
	}
}
