package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class SolidWire extends Wire {
    private static final long serialVersionUID = 8105808234650365067L;

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
        return "solid_wire.png";
    }

}
