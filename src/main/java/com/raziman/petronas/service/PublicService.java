package com.raziman.petronas.service;

import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.response.GitPublicResponse;

@Service
public class PublicService {
	
	private static final Logger log = LoggerFactory.getLogger(PublicService.class);
	
	public GitPublicResponse getRepository(GitConnection gitCon) throws Exception {
		
		log.info("*** Method Start " + new Object(){}.getClass().getEnclosingMethod().getName());
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			
			List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
			
			Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
			
			if (optRepoList.isPresent()) {
				log.info("*** Method End " + new Object(){}.getClass().getEnclosingMethod().getName());
				
				return new GitPublicResponse(repoMap.getRepoCount(responseBody), repoMap.getRepoListObject(responseBody));
			}

		}
		log.error("*** Method End " + new Object(){}.getClass().getEnclosingMethod().getName());
		
		return null;
		
	}
	
	
}
