package uk.co.hobnobian.chips.game.backend;

public class GameTickData {
    private Game game;
    private Player p1;
    private Player p2;
    private int x;
    private int y;
    
    public GameTickData(Game g, Player p1, Player p2, int x, int y) {
        game = g;
        this.p1 = p1;
        this.p2 = p2;
        this.x = x;
        this.y = y;
    }
    
    public boolean isPlayer1OnBlock() {
        return p1.getpos()[0] == x && p1.getpos()[1] == y;
    }
    
    public Game getGame() {
        return game;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
