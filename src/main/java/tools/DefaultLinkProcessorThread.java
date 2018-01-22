/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */
package tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
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
public class DefaultLinkProcessorThread extends Thread implements LinkProcessor<String, String> {

	public URLConnection connection;
	public String link;
	public String[] hashesToCompare;

	// Constructor
	public DefaultLinkProcessorThread(String link, String[] hashesToCompare) {
		super("DefaultLinkProcessor");
		this.link = link;
		if (hashesToCompare != null) {
			this.hashesToCompare = hashesToCompare;
		} else {
			// if there is no hash parameter in cl arguments
			this.hashesToCompare = new String[2];
			this.hashesToCompare[0] = "RbwRYKKAw0uSMwmukf8oOg==";
			this.hashesToCompare[1] = "5zAjHAcCDH/HsNXfEoVeMA==";
		}
	}

	/**
	 * opens a HTTP connection
	 *
	 * @param linkString,
	 *            the URL for the connection
	 */
	private void openConnection(String linkString) throws MalformedURLException, IOException {
		URL link;
		link = new URL(linkString);
		connection = link.openConnection();
	}

	/**
	 * for threading
	 */
	public void run() {
		// System.out.println("Thread started with ID: " + this.getId());
		process(link);
	}

	/**
	 * reads the data from a HTTP connection and computes the MD5 hash
	 * 
	 * @return String, the hash
	 */
	private String computeHashOfHTTPResponse() throws IOException {

		InputStream is = connection.getInputStream();
		DigestInputStream dis = null;
		
		try {
			dis = new DigestInputStream(is, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		byte[] buffer = new byte[10000];
		while (dis.read(buffer) != -1);
		
		dis.close();
		is.close();

		return Base64.getEncoder().encodeToString(dis.getMessageDigest().digest());
	}

	/**
	 * calculates md5 hash
	 * 
	 * @param byte[],
	 *            data for hashing
	 * @return byte[], the hash base64 encoded
	 */
	/*private String md5(byte[] data) {
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
	}*/

	/**
	 * implementation of the link processing
	 * 
	 * usually open, read, hash
	 * 
	 * @param String,
	 *            the link for processing purposes
	 * @return boolean, false in case of an error.
	 */
	@Override
	public String process(String linkString) {
		String hash = null;

		try {
			openConnection(linkString);
		} catch (MalformedURLException e) {
			System.err.println("The url you've entered is invalid! " + linkString);
			e.printStackTrace();
		} catch (IOException e1) {
			try {
				System.err.println("Something went wrong with opening the connection. Trying again.");
				e1.printStackTrace();
				openConnection(linkString);
			} catch (IOException e) {
				System.err.println("Can't open connection. Shutting down.");
				e.printStackTrace();
				return null;
			}
		}
		try {
			hash = computeHashOfHTTPResponse();
		} catch (IOException e) {
			try {
				System.err.println("Something went wrong with reading the data from " + linkString
						+ ". Trying again. Err: " + e.getMessage());
				hash = computeHashOfHTTPResponse();
			} catch (IOException e1) {
				System.err.println("Can't read data from " + linkString + ". Shutting down. Err: " + e1.getMessage());
				return null;
			}
			e.printStackTrace();
		}
		for (int i = 0; i < hashesToCompare.length; i++) {
			if (hashesToCompare[i].equals(hash))
				System.out.println("The response of " + linkString + " has the hash-value '" + hash + "'");
		}
		return hash;
	}

}
