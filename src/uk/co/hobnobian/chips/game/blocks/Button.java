package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class Button extends ElectricalComponent implements Tickable{
    private static final long serialVersionUID = -951613422094873832L;

    public Button() {
        setInfo(new BlockInfo(1));
        info.set(0, 0);//Powered
    }

    @Override
    public boolean canConnectFrom(Direction d) {
        return true;
    }

    @Override
    public boolean[] getPoweringDirections() {
        boolean p = info.get(0) == 1;
        return new boolean[] {p,p,p,p};
    }

    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        return "button.png";
    }

    @Override
    public void tick(GameTickData g) {
        if (g.isPlayer1OnBlock() || g.isPlayer2OnBlock()) {
            info.set(0, 1);
        }
        else if (g.getGame().getBlockSecondLayer(g.getX(), g.getY()) != null) {
            info.set(0, 1);
        }
        else {
            info.set(0, 0);
        }
        
        
    }

}
