package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class Lamp extends ElectricalComponent {
    private static final long serialVersionUID = -6101419538327293376L;

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
        return EnterLeaveEvent.NO;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        if (poweredFromAnywhere(data.getPos(), data.getMap())) {
            return "lamp_on.png";
        }
        else {
            return "lamp_off.png";
        }
    }

}
