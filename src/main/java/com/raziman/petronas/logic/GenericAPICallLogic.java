package com.raziman.petronas.logic;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GenericAPICallLogic {


	public String getRepository(String hostname, String subURL, String topic, String language, String sortBy, String sortOrder) throws Exception {

		if (hostname == null && subURL == null && topic == null && language == null && sortBy == null && sortOrder == null)
			throw new Exception("Unable to process! Some of the value is null");

		HttpsURLConnection con = null;
		DataInputStream input = null;

		try {

			URL url = new URL(hostname + subURL + "?q=" + topic + "+language:" + language + "&sort=" + sortBy
					+ "&order=" + sortOrder);

			System.out.println("URL: " + url);

			con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			if (con.getResponseCode() == 200) {

				System.out.println("Response code: " + con.getResponseCode());
				System.out.println("Response message: " + con.getResponseMessage());
				
				input = new DataInputStream(con.getInputStream());
				StringBuilder responseBody = new StringBuilder();

				int c;

				while ((c = input.read()) != -1) {
					responseBody.append((char) c);
				}

				return responseBody.toString();

			} else {

				return Integer.toString(con.getResponseCode());

			}

		} catch (MalformedURLException e) {
			System.out.println("Invalid hostname");
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			con.disconnect();

			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Unable to close input stream");
				}
			}

		}

		return null;

	}

}
