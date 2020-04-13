package uk.co.hobnobian.chips.main.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Serializer {

	   public static Object fromString( String s ){
	        byte [] data = Base64.getDecoder().decode( s );
	        ObjectInputStream ois;
			try {
				ois = new ObjectInputStream( 
				                                new ByteArrayInputStream(  data ) );
				Object o  = ois.readObject();
		        ois.close();
		        return o;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	        
	   }

	    public static String toString(Serializable o) {
	        
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        ObjectOutputStream oos;
				oos = new ObjectOutputStream( baos );
				oos.writeObject( o );
		        oos.close();
		        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	        
	    }
	    
	    public static boolean serializeEqual(Serializable i, Serializable i2) {
	    	return (toString(i).equals(toString(i2)));
	    }
	    
	    public static byte[] toByteArray(Serializable o) {
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	ObjectOutputStream out = null;
	    	try {
	    	  out = new ObjectOutputStream(bos);   
	    	  out.writeObject(o);
	    	  out.flush();
	    	  byte[] yourBytes = bos.toByteArray();
	    	  return yourBytes;
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	    	  try {
	    	    bos.close();
	    	  } catch (IOException ex) {
	    	    // ignore close exception
	    	  }
	    	}
	    	
	    	return null;
	    }
	    
	    public static Object fromByteArray(byte[] b) {
	    	ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    	ObjectInput in = null;
	    	try {
	    	  in = new ObjectInputStream(bis);
	    	  Object o = in.readObject();
	    	  return o;
	    	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	    	  try {
	    	    if (in != null) {
	    	      in.close();
	    	    }
	    	  } catch (IOException ex) {
	    	    // ignore close exception
	    	  }
	    	}
	    	
	    	return null;
	    }
}
