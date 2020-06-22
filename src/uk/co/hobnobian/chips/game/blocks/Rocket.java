package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class Rocket extends Block implements Tickable{
    private static final long serialVersionUID = -426960872136302919L;

    public Rocket() {
        setInfo(new BlockInfo(2));
        info.set(0, 255);//Store direction rocket is heading
        info.set(1, 4);//Store cooldown until next move
    }
    
    @Override
    public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
        return EnterLeaveEvent.DEATH;
    }

    @Override
    public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
        return EnterLeaveEvent.DEATH;
    }

    @Override
    public String getImage(GetImageData data) {
        if (info.get(0) == 255) {
            return "rocket/e.png";
        }
        String file = "rocket/";
        int direction = info.get(0);
        if (direction == 0) {
            file+="n";
        }
        else if (direction == 1) {
            file+="e";
        }
        else if (direction == 2) {
            file+="s";
        }
        else if (direction == 3) {
            file+="w";
        }
        file +="_on.png";
        return file;
    }

    @Override
    public void tick(GameTickData data) {
        int x = data.getX();
        int y = data.getY();
        
        if (x == data.getP1().getpos()[0] && y == data.getP1().getpos()[1]) {
            data.getP1().kill();
        }
        
        if (info.get(0) == 255) {
            //Check around for player - r = 6
            int xdiff = x-data.getP1().getpos()[0];
            int ydiff = y-data.getP1().getpos()[1];
            
            boolean move = false;
            
            if (xdiff == 0) {
                if (ydiff > 0) {
                    info.set(0, 0);
                    move = true;
                }
                else if (ydiff < 0) {
                    info.set(0, 2);
                    move = true;
                }
            }
            else if (ydiff == 0) {
                if (xdiff > 0) {
                    info.set(0, 3);
                    move = true;
                }
                else if (xdiff < 0) {
                    info.set(0, 1);
                    move = true;
                }
            }
            if (move) {
                data.getGame().setBlockSecondLayer(x, y, this);
                data.getGame().setBlock(x, y, new Air());
            }
            return;
        }
        
        if (info.get(1) > 0) {
            info.set(1, info.get(1)-1);
            return;
        }
        
        Direction d = Direction.NORTH;
        int direction = info.get(0);
        
        if (direction == 0) {
            d = Direction.NORTH;
        }
        else if (direction == 1) {
            d = Direction.EAST;
        }
        else if (direction == 2) {
            d = Direction.SOUTH;
        }
        else if (direction == 3) {
            d = Direction.WEST;
        }
        
        int[] newpos = Direction.move(new int[] {x,y}, d);
        EnterLeaveEvent move = data.move(this, d);
        if (move == EnterLeaveEvent.YES) {
            data.getGame().getMap().setBlockSecondLayer(x, y, new Air());
            data.getGame().getMap().setBlockSecondLayer(newpos[0], newpos[1], this);
        }
        else if (move == EnterLeaveEvent.DEATH){
            data.getGame().getMap().setBlockSecondLayer(x, y, new Air());
        }
        else {
            info.set(0, 255);
        }

        
        
    }
    
}
