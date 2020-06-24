package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.inv.RedKeyItem;

public class RedKeyhole extends Block {
    private static final long serialVersionUID = -6502871369457780201L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (data.isPlayer1OnBlock() && data.getGame().getOurPlayer().getInventory().contains(RedKeyItem.class)) {
            data.getGame().getOurPlayer().getInventory().remove(RedKeyItem.class);
            data.getGame().setBlock(data.getX(), data.getY(), new Air());
            return EnterLeaveEvent.YES;
        }
        return EnterLeaveEvent.NO;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "red_keyhole.png";
    }

}
