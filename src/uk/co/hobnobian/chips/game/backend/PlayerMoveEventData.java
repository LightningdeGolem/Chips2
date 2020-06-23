package uk.co.hobnobian.chips.game.backend;

public class PlayerMoveEventData {
    private int x;
    private int y;
    private Direction direction;
    private GameVariables vars;
    private Game game;
    
    public PlayerMoveEventData(int x, int y, Direction d, GameVariables vars, Game g) {
        this.x = x;
        this.y = y;
        this.direction = d;
        this.vars = vars;
        this.game = g;
    }
    
    public boolean isPlayer1OnBlock() {
        int[] newpos = Direction.move(game.getOurPlayer().getpos(), Direction.invert(direction));
        return newpos[0] == x && newpos[1] == y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public GameVariables getVars() {
        return vars;
    }

    public Game getGame() {
        return game;
    }

    public int[] getPos() {
        return new int[] {x,y};
    }
    
}
