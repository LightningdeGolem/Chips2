package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public abstract class Wire extends ElectricalComponent implements Tickable{
    private static final long serialVersionUID = 4645413774403271952L;
    
    public Wire() {
        setInfo(new BlockInfo(5));
        info.set(0, 0);
    }

    @Override
    public boolean canConnectFrom(Direction d) {
        return true;
    }

    @Override
    public boolean[] getPoweringDirections() {
        boolean p = info.get(0) == 1;
        
        boolean[] r = {p,p,p,p};
        
        if (info.get(1) == 1) {
            r[0] = false;
        }
        if (info.get(2) == 1) {
            r[1] = false;    
        }
        if (info.get(3) == 1) {
            r[2] = false;
        }
        if (info.get(4) == 1) {
            r[3] = false;
        }
        
        return r;
    }

    @Override
    public void tick(GameTickData g) {
        boolean[] powering = getPoweredFrom(g.getPos(), g.getGame().getMap());
        for (int i = 0; i< 4; i++) {
            if (powering[i]) {
                info.set(i+1, 1);
            }
            else {
                info.set(i+1, 0);
            }
        }
        if (poweredFromAnywhere(powering)) {
            info.set(0, 1);
        }
        else {
            info.set(0, 0);
        }
    }

}
