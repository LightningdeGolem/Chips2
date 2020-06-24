package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.ElectricalComponent;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class Lever extends ElectricalComponent {
    private static final long serialVersionUID = 5186638166202219801L;

    public Lever() {
        setInfo(new BlockInfo(1));
        info.set(0, 0);//Filpped
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
        if (data.test()) {
            return EnterLeaveEvent.YES;
        }
        if (info.get(0) == 1) {
            info.set(0, 0);
        }
        else {
            info.set(0, 1);
        }
        return EnterLeaveEvent.YES;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.YES;
    }

    @Override
    public String getImage(GetImageData data) {
        if (info.get(0) == 1) {
            return "lever_on.png";
        }
        else {
            return "lever_off.png";
        }
    }

}
