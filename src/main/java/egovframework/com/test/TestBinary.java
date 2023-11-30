package egovframework.com.test;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestBinary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String empoleImgData = "";
		
		// Remove the leading "b'" and trailing "'" from the data
		empoleImgData = empoleImgData.substring(2, empoleImgData.length() - 1);

		// Convert the hexadecimal string to a byte array
		byte[] empoleImgBytes = hexStringToByteArray(empoleImgData);

		// Save the byte array to a file
		try (FileOutputStream fos = new FileOutputStream("d:/empoleImg.png")) {
		    fos.write(empoleImgBytes);
		    System.out.println("Data saved successfully.");
		} catch (IOException e) {
		    System.out.println("Failed to save the data: " + e.getMessage());
		}


	}

	// Helper method to convert a hexadecimal string to a byte array
	public static byte[] hexStringToByteArray(String hexString) {
	    int length = hexString.length();
	    byte[] byteArray = new byte[length / 2];
	    for (int i = 0; i < length; i += 2) {
	        byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
	                                 + Character.digit(hexString.charAt(i + 1), 16));
	    }
	    return byteArray;
	}	
	
}