package uk.co.hobnobian.chips.main.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
}
