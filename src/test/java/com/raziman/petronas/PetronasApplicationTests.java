package com.raziman.petronas;

import static org.junit.Assert.assertEquals;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.service.ApiCallService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetronasApplicationTests extends ApiCallService{
	
	private MockMvc mockMvc;
	
	String hostname = "https://api.github.com";
	String subURL = "/search/repositories";
	String sortBy = "created";
	String sortOrder = "desc";
	String topic = "GoSign";
	String language = "java";
	String page = "1";
	
	@Test
	public void testHttpResponse() throws Exception {
		
		GitConnection gitCon = new GitConnection();
		gitCon.setHostname(hostname);
		gitCon.setSubURL(subURL);
		gitCon.setTopic(topic);
		gitCon.setLanguage(language);
		gitCon.setPage(page);
		gitCon.setSortBy(sortBy);
		gitCon.setSortOrder(sortOrder);
		
		HttpsURLConnection response = callAPI(gitCon);
		
		System.out.println(response.getResponseCode());
		System.out.println(response.getResponseCode()==200);
		
		assert response.getResponseCode()==200 : "Unable to connect to GitHub";
	}
	
	@Test
	public void testCount() throws Exception {
		
		GitConnection gitCon = new GitConnection();
		gitCon.setHostname(hostname);
		gitCon.setSubURL(subURL);
		gitCon.setTopic(topic);
		gitCon.setLanguage(language);
		gitCon.setPage(page);
		gitCon.setSortBy(sortBy);
		gitCon.setSortOrder(sortOrder);
		
		HttpsURLConnection response = callAPI(gitCon);
		GitRepoMapping repoMap = new GitRepoMapping();
		
		if (response.getResponseCode()==200) {
			
			String responseBody = getResponseBody(response);
			String count = repoMap.getRepoCount(responseBody);
			
			assert Integer.parseInt(count) >= 0 : "Unable to count records";
			

		}
		
		
		
	}


}
