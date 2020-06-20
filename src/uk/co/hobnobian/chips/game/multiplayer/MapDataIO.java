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
        //Write map title size
        bytes.add(map.title.length());
        //Write map title
        for (int i = 0; i<map.title.length(); i++) {
            bytes.add((int) map.title.charAt(i));
        }
        //Write map author size
        bytes.add(map.author.length());
        //Write map author
        for (int i = 0; i<map.author.length(); i++) {
            bytes.add((int)map.author.charAt(i));
        }
        
        //Write start positions
        bytes.add(map.getP1StartPos()[0]);
        bytes.add(map.getP1StartPos()[1]);
        bytes.add(map.getP2StartPos()[0]);
        bytes.add(map.getP2StartPos()[1]);
        
       
        //-------- WRITE MAP ---------
        for (int y = 0; y < 256; y++) {
            for (int x =0; x < 256; x++) {
                Block block = map.getAt(x, y);
                if (block.getClass().equals(Air.class)) {
                    bytes.add(0);
                }
                else {
                    int[] data = encodeBlock(block);
                    for (int b : data) {
                        bytes.add(b);
                    }
                }
                
                
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
        }

        int i = 0;
        
        //Read map title length
        int titleLength = data[i];
        i++;
        String mapTitle = "";
        for (int ii = 0; ii < titleLength; ii++) {
            mapTitle+=(char)data[i];
            i++;
        }
        map.title = mapTitle;
        
        int authorLength = data[i];
        i++;
        String mapAuthor = "";
        for (int ii = 0; ii < authorLength; ii++) {
            mapAuthor+=(char)data[i];
            i++;
        }
        map.author = mapAuthor;
        
        int p1x = data[i];
        i++;
        int p1y = data[i];
        i++;
        int p2x = data[i];
        i++;
        int p2y = data[i];
        i++;
        map.setP1StartingPosition(new int[] {p1x,p1y});
        map.setP2StartingPosition(new int[] {p2x,p2y});
        
        
        
        
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                if (data[i] == 0) {
                    map.setBlockNoRecord(x, y, new Air());
                    i++;
                }
                else {
                    int blockid = data[i];
                    i++;
                    int blockdatalen = data[i];
                    i++;
                    int[] blockdata = null;
                    if (blockdatalen > 0) {
                        blockdata = new int[blockdatalen];
                        for (int n = 0; n < blockdatalen; n++) {
                            blockdata[n] = data[i];
                            i++;
                        }
                    }
                    
                    
                    Block b = decodeBlock(blockid, blockdata);
                    map.setBlock(x, y, b);
                }
            }
        }
        
        
        
        return map;
    }
}
