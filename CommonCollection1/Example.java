package serial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Example {

	public static void main(String[] args) {
		Car mazda = new Car("RX-7", 2002);
		
		try {
			writeToFile(mazda);
			readFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeToFile(Car c) throws IOException{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Car.bin"))) {
			oos.writeObject(c);
		}
	}
	
	public static void readFile() throws IOException, ClassNotFoundException{
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Car.bin"))) {
			Car c = (Car) ois.readObject();
			System.out.println(c.toString());
		}
	}
}
