package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class NoItem extends Block {
    private static final long serialVersionUID = 2380315541138387535L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        data.getGame().getOurPlayer().getInventory().clear();
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "noitems.png";
    }

}
