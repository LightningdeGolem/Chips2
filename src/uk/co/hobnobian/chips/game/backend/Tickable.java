package uk.co.hobnobian.chips.game.backend;

public interface Tickable {
	public void tick(Game game, Player p1, Player p2, int x, int y);
}
