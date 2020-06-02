package uk.co.hobnobian.chips.game.multiplayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.blocks.Air;

public class MapDataIO {
    private static int[] encodeBlock(Block b) {
        List<Integer> bytes = new ArrayList<Integer>();
        
        int blockId = Block.inverseBlockIds.get(b.getClass());
        bytes.add(blockId);
        
        BlockInfo bi = b.getInfo();
        if (bi == null) {
            bytes.add(0);
        }
        else {
            bytes.add(bi.getArray().length);
            for (int data : bi.getArray()) {
                bytes.add(data);
            }
        }
        
        int[] actual = new int[bytes.size()];
        for (int i = 0; i< bytes.size(); i++) {
            actual[i] = bytes.get(i);
        }
        return actual;
    }
    
    private static byte intToByte(int i) {
        return (byte)i;
    }
    
    private static int byteToInt(byte b) {
        return b&0xFF;
    }
    
    public static byte[] mapToBytes(Map map) {
        
        List<Integer> bytes = new ArrayList<Integer>();
        int aircount = -1;
        for (int y = 0; y < 256; y++) {
            for (int x =0; x < 256; x++) {
                Block block = map.getAt(x, y);
                boolean air = false;
                
                if (aircount == 65535) {
                    bytes.add(255);
                    bytes.add(255);
                    bytes.add(255);
                    aircount = 0;
                }
                
                if (block.getClass().equals(Air.class)) {
                    air = true;
                    aircount++;
                }
                else if (aircount > -1){
                    if (aircount > 2) {
                        int firstbyte = (aircount+1)/256;
                        int secondbyte = (aircount+1)%256;
                        bytes.add(255);
                        bytes.add(firstbyte);
                        bytes.add(secondbyte);
                    }
                    else {
                        for (int i = 0; i<aircount+1;i++) {
                            bytes.add(0);
                        }
                    }
                    aircount = 0;
                }
                if (!air) {
                    int[] data = encodeBlock(block);
                    for (int b : data) {
                        bytes.add(b);
                    }
                }
            }
        }
        
        if (aircount > 3) {
            int firstbyte = aircount/256;
            int secondbyte = aircount%256;
            bytes.add(255);
            bytes.add(firstbyte);
            bytes.add(secondbyte);
        }
        else if (aircount > 0){
            for (int i = 0; i<aircount;i++) {
                bytes.add(0);
            }
        }
        
        byte[] actual = new byte[bytes.size()];
        for (int i = 0; i<bytes.size(); i++) {
            actual[i] = intToByte(bytes.get(i));
        }
        
        return actual;
    }
    
    private static Block decodeBlock(int id, int[] data) {
        try {
            Block b = Block.blockIds.get(id).getConstructor().newInstance();
            
            if (data != null) {
                b.setInfo(new BlockInfo(data));
            }
            return b;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Air();
    }
    
    public static Map decodeBytes(byte[] raw) {
        Map map = new Map();
        
        int[] data = new int[raw.length];
        for (int i = 0; i < raw.length; i++) {
            data[i] = byteToInt(raw[i]);
            System.out.println(data[i]);
        }
        
        int x = 0;
        int y = 0;
        for (int i = 0; i<data.length; i++) {
            int b = data[i];
            if (b == 255) {
                i++;
                int firstByte = data[i];
                i++;
                int secondByte = data[i];
                int total = firstByte*256 + secondByte;
                for (int n = 0; n < total; n++) {
                    map.setBlock(x, y, new Air());
                    x++;
                    
                    if (x > 255) {
                        x = 0;
                        y++;
                    }
                    if (y > 255) {
                        break;
                    }
                }
                x--;
            }else if (b == 0) {
                map.setBlock(x, y, new Air());
            }
            else {
                int blockid = b;
                i++;
                int datalen = data[i];
                i++;
                int[] blockinfo = null;
                if (datalen > 0) {
                    blockinfo = new int[datalen];
                    for (int n = 0; n < datalen; n++) {
                        blockinfo[n] = data[i];
                        i++;
                    }
                }
                
                Block bl = decodeBlock(blockid, blockinfo);
                map.setBlock(x, y, bl);
                i--;
            }
            x++;
            if (x > 255) {
                x = 0;
                y++;
            }
            if (y > 255) {
                break;
            }
        }
        return map;
    }
}
