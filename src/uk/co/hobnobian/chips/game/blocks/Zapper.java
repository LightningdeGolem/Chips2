package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class Zapper extends ElectricalComponent {
    private static final long serialVersionUID = 6556018720454317078L;

    @Override
    public boolean canConnectFrom(Direction d) {
        return true;
    }

    @Override
    public boolean[] getPoweringDirections() {
        return new boolean[] {false, false, false, false};
    }

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (poweredFromAnywhere(data.getPos(), data.getGame().getMap())) {
            return EnterLeaveEvent.DEATH;
        }
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        if (poweredFromAnywhere(data.getPos(), data.getMap())) {
            return "zapper_on.png";
        }
        return "zapper_off.png";
    }

}
