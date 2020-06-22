package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.inv.FireBoots;

public class Fire extends Block {
    private static final long serialVersionUID = 4374925556333031939L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (data.isPlayer1OnBlock()) {
            if (data.getGame().getOurPlayer().getInventory().contains(FireBoots.class)) {
                return EnterLeaveEvent.YES;
            }
        }
        
        return EnterLeaveEvent.DEATH;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "fire.png";
    }

}
