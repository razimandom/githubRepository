package com.raziman.petronas.service;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raziman.petronas.model.GitConnection;

@Service
public class ApiCallService {
	
	private static final Logger log = LoggerFactory.getLogger(ApiCallService.class);

	public HttpsURLConnection callAPI(GitConnection gitCon) throws Exception {

		HttpsURLConnection con = null;

		try {

			URL url = getURL(gitCon);

			log.info("*** API STATUS: Execute URL: " + url);

			con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			return con;

		} catch (MalformedURLException e) {
			System.out.println("Invalid hostname");
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			con.disconnect();

		}

		return null;

	}
	
	public String getResponseBody(HttpsURLConnection con) {

		DataInputStream input = null;

		try {
			
			if (con.getResponseCode() == 200) {
				
				log.info("*** API STATUS: Response code: " + con.getResponseCode());
				log.info("*** API STATUS: Response message: " + con.getResponseMessage());

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
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Unable to close input stream");
					e.printStackTrace();
				}
			}
		}
		
		return null;

	}
	
	public URL getURL(GitConnection gitCon) throws MalformedURLException {

		String hostname = gitCon.getHostname();
		String subURL = gitCon.getSubURL();
		String topic = gitCon.getTopic();
		String language = gitCon.getLanguage();
		String sortBy = gitCon.getSortBy();
		String sortOrder = gitCon.getSortOrder();
		String page = gitCon.getPage();
		
		if (topic==null) {
			return new URL(hostname + subURL + "?q=language:" + language + "&sort=" + sortBy + "&order=" + sortOrder + "&page=" + page + "&per_page=10");
		}
		
		if (language==null) {
			return new URL(hostname + subURL + "?q="+ topic + "&sort=" + sortBy + "&order=" + sortOrder + "&page=" + page + "&per_page=10");
		}
		
		return new URL(hostname + subURL + "?q=" + topic + "+language:" + language + "&sort=" + sortBy + "&order=" + sortOrder + "&page=" + page + "&per_page=10");
	}

}
