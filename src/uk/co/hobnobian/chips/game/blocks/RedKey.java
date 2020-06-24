package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.inv.RedKeyItem;

public class RedKey extends Block {
    private static final long serialVersionUID = -613875398409404015L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        data.getGame().getOurPlayer().getInventory().add(new RedKeyItem());
        data.getGame().setBlock(data.getX(), data.getY(), new Air());
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "red_key.png";
    }

}
