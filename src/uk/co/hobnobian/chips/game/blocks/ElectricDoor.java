package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class ElectricDoor extends ElectricalComponent implements Tickable{
    private static final long serialVersionUID = -1391718377950350177L;

    public ElectricDoor() {
        setInfo(new BlockInfo(1));
        info.set(0, 0);//OPEN
    }
    
    @Override
    public boolean canConnectFrom(Direction d) {
        return true;
    }

    @Override
    public boolean[] getPoweringDirections() {
        return new boolean[] {false, false,false, false};
    }

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (info.get(0) == 1) {
            return EnterLeaveEvent.YES;
        }
        else {
            return EnterLeaveEvent.NO;
        }
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        if (info.get(0) == 1) {
            return "electric_door_open.png";
        }
        else {
            return "electric_door_closed.png";
        }
    }

    @Override
    public void tick(GameTickData g) {
        if (poweredFromAnywhere(g.getPos(), g.getGame().getMap())) {
            info.set(0, 1);
        }
        else {
            info.set(0, 0);
        }
        
    }

}
