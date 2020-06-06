package uk.co.hobnobian.chips.game.backend;

public abstract class ElectricalComponent extends Block{
    private static final long serialVersionUID = 919059682182586319L;

    public abstract boolean canConnectFrom(Direction d);
    
    public abstract boolean[] getPoweringDirections();
    
    protected final boolean[] getPoweredFrom(int[] pos, Map map) {
        Block bn,be,bs,bw;
        
        bn = map.getAt(Direction.move(pos, Direction.NORTH)[0], Direction.move(pos, Direction.NORTH)[1]);
        be = map.getAt(Direction.move(pos, Direction.EAST)[0], Direction.move(pos, Direction.EAST)[1]);
        bs = map.getAt(Direction.move(pos, Direction.SOUTH)[0], Direction.move(pos, Direction.SOUTH)[1]);
        bw = map.getAt(Direction.move(pos, Direction.WEST)[0], Direction.move(pos, Direction.WEST)[1]);
        
        boolean n,e,s,w;
        n=e=s=w=false;
        
        n = bn instanceof ElectricalComponent && ((ElectricalComponent) bn).getPoweringDirections()[2];
        e = be instanceof ElectricalComponent && ((ElectricalComponent) be).getPoweringDirections()[3];
        s = bs instanceof ElectricalComponent && ((ElectricalComponent) bs).getPoweringDirections()[0];
        w = bw instanceof ElectricalComponent && ((ElectricalComponent) bw).getPoweringDirections()[1];
        
        return new boolean[] {n,e,s,w};
    }
}
