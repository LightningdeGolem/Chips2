package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.inv.FireBoots;

public class FireBootsItem extends Block{
    private static final long serialVersionUID = 1809058559585418040L;

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        if (!data.isPlayer1OnBlock()) {
            return EnterLeaveEvent.YES;
        }
        if (!data.getGame().getOurPlayer().getInventory().contains(FireBoots.class)) {
            data.getGame().getOurPlayer().getInventory().add(new FireBoots());
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
        return "fireboot.png";
    }
    
}
