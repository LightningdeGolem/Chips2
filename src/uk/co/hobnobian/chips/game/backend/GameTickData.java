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
    
    public boolean isPlayer2OnBlock() {
        if (p2 == null) {
            return false;
        }
        return p2.getpos()[0] == x && p2.getpos()[1] == y;
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

    public int[] getPos() {
        return new int[] {x,y};
    }
    
    
    public EnterLeaveEvent move(Block b, Direction d) {
        int[] newpos = Direction.move(new int[] {x,y}, d);
        Block from = getGame().getMap().getAt(newpos[0], newpos[1]);
        Block movingTo = getGame().getMap().getAt(newpos[0], newpos[1]);
        Block movingTo2 = getGame().getMap().getBlockSecondLayer(newpos[0], newpos[1]);
        
        EnterLeaveEvent leaving;
        if (from.equals(b)) {
            leaving = EnterLeaveEvent.YES;
        }else {
           leaving = from.onLeave(new PlayerMoveEventData(x,y,d, getGame().getVars(), getGame()));
        }
        
        
        if (leaving == EnterLeaveEvent.YES) {
            EnterLeaveEvent enter1 = movingTo.onEnter(new PlayerMoveEventData(x,y,d, getGame().getVars(), getGame()));
            if (enter1 == EnterLeaveEvent.YES) {
                if (movingTo2 == null) {
                    return EnterLeaveEvent.YES;
                }
                EnterLeaveEvent enter2 = movingTo2.onEnter(new PlayerMoveEventData(x,y,d, getGame().getVars(), getGame()));
                return enter2;
            }
            else {
                return enter1;
            }
        }
        else {
            return leaving;
        }
    }
}
