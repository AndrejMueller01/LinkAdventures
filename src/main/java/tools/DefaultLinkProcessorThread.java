/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */
package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * DefaultLinkProcessorThread.java
 * 
 * Processes an URL as specified in the specifications of the willhaben task
 * Implements the LinkProcessor Interface extends Thread for concurrency
 * 
 * @author Andrej Mueller
 */
public class DefaultLinkProcessorThread extends Thread implements LinkProcessor<String> {

	public URLConnection connection;
	public String link;

	// Constructor
	public DefaultLinkProcessorThread(String link) {
		super("DefaultLinkProcessor");
		this.link = link;
	}

	/**
	 * opens a HTTP connection
	 *
	 * @param linkString,
	 *            the URL for the connection
	 */
	public void openConnection(String linkString) throws MalformedURLException, IOException {
		URL link;
		link = new URL(linkString);
		connection = link.openConnection();
	}

	/**
	 * for threading
	 */
	public void run() {
		//System.out.println("Thread started with ID: " + this.getId());
		process(link);
	}

	/**
	 * reads the data from a HTTP connection
	 * 
	 * @return byte[], the data from the HTTP reply
	 */
	public byte[] readData() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		int bytesRead = 0;

		is = connection.getInputStream();
		byte[] byteChunk = new byte[10000]; // 10kB
		int n;
		while ((n = is.read(byteChunk)) > 0) {
			baos.write(byteChunk, 0, n);
			bytesRead += n;
		}
		// System.out.println(link + " - MD5: " + md5(baos.toByteArray()) + " size: " +
		// baos.size());
		is.close();

		return baos.toByteArray();
	}

	/**
	 * calculates md5 hash
	 * 
	 * @param byte[], data for hashing
	 * @return byte[], the hash base64 encoded
	 */
	private String md5(byte[] data) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Can't find the algorithm.");
			e.printStackTrace();
		}
		md.reset();
		md.update(data);
		return Base64.getEncoder().encodeToString(md.digest());
	}

	/**
	 * implementation of the link processing
	 * 
	 * usually open, read, hash
	 * 
	 * @param String, the link for processing purposes
	 * @return boolean, false in case of an error.
	 */
	@Override
	public boolean process(String link) {
		byte[] dataToHash = null;

		try {
			openConnection(link);
		} catch (MalformedURLException e) {
			System.err.println("The url you've entered is invalid! " + link);
			e.printStackTrace();
		} catch (IOException e1) {
			try {
				System.err.println("Something went wrong with opening the connection. Trying again.");
				e1.printStackTrace();
				openConnection(link);
			} catch (IOException e) {
				System.err.println("Can't open connection. Shutting down.");
				e.printStackTrace();
				return false;
			}
		}
		try {
			dataToHash = readData();
		} catch (IOException e) {
			try {
				System.err.println("Something went wrong with reading the data. Trying again. Err: " + e.getMessage());
				readData();
			} catch (IOException e1) {
				System.err.println("Can't read data. Shutting down. Err: " + e1.getMessage());
				return false;
			}
			e.printStackTrace();
		}
		String hash = md5(dataToHash);
		if(hash.equals("RbwRYKKAw0uSMwmukf8oOg==") || hash.equals("5zAjHAcCDH/HsNXfEoVeMA==")) {
			System.out.println("The response from the URL " + link + " has the hash-value: '" + hash + "'");
		}
		return true;
	}

}
