package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.inv.IceBoots;

public class IceBootsItem extends Block{
    private static final long serialVersionUID = 2554481199878266781L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (!data.isPlayer1OnBlock()) {
            return EnterLeaveEvent.YES;
        }
        if (!data.getGame().getOurPlayer().getInventory().contains(IceBoots.class)) {
            data.getGame().getOurPlayer().getInventory().add(new IceBoots());
            data.getGame().setBlock(data.getX(), data.getY(), new Air());
        }
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "iceboot.png";
    }
    
}
