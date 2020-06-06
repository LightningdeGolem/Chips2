package uk.co.hobnobian.chips.game.backend;

public class GetImageData {
    private GameVariables vars;
    private Map map;
    private int x;
    private int y;
    private boolean testImage;
    
    public GetImageData(GameVariables v, Map m, int x, int y) {
        this(v,m,x,y,false);
    }

    public GetImageData(GameVariables v, Map m, int x, int y, boolean b) {
        vars = v;
        map = m;
        this.x = x;
        this.y = y;
        this.testImage = b;
    }
    
    public boolean notOnMap() {
        return testImage;
    }

    public GameVariables getVars() {
        return vars;
    }

    public Map getMap() {
        return map;
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
}
