package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class NormalWire extends Wire{
    private static final long serialVersionUID = 4645413774403271952L;
    
    public NormalWire() {
        super();
    }
    
    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        if (data.notOnMap()) {
            return "wire/nesw.png";
        }
        boolean n,e,s,w;
        n=e=s=w=false;
        
        Block bn,be,bs,bw;
        
        bn = data.getMap().getAt(Direction.move(data.getPos(), Direction.NORTH)[0], Direction.move(data.getPos(), Direction.NORTH)[1]);
        be = data.getMap().getAt(Direction.move(data.getPos(), Direction.EAST)[0], Direction.move(data.getPos(), Direction.EAST)[1]);
        bs = data.getMap().getAt(Direction.move(data.getPos(), Direction.SOUTH)[0], Direction.move(data.getPos(), Direction.SOUTH)[1]);
        bw = data.getMap().getAt(Direction.move(data.getPos(), Direction.WEST)[0], Direction.move(data.getPos(), Direction.WEST)[1]);
        
        if (bn instanceof ElectricalComponent && ((ElectricalComponent) bn).canConnectFrom(Direction.SOUTH)) {
            n = true;
        }
        if (be instanceof ElectricalComponent && ((ElectricalComponent) be).canConnectFrom(Direction.WEST)) {
            e = true;
        }
        if (bs instanceof ElectricalComponent && ((ElectricalComponent) bs).canConnectFrom(Direction.NORTH)) {
            s = true;
        }
        if (bw instanceof ElectricalComponent && ((ElectricalComponent) bw).canConnectFrom(Direction.EAST)) {
            w = true;
        }
        
        
        if (!(n || s || e || w)) {
            return "wire/nesw.png";
        }
        String file = "wire/";
        if (n) {
            file+="n";
        }
        if (e) {
            file+="e";
        }
        if (s) {
            file+="s";
        }
        if (w) {
            file+="w";
        }
        if (file.equals("wire/n") || file.equals("wire/s")) {
            file = "wire/ns";
        }
        if (file.equals("wire/e") || file.equals("wire/w")) {
            file = "wire/ew";
        }
        
        file+=".png";
        return file;
    }

}
