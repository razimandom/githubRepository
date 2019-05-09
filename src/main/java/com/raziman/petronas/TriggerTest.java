package com.raziman.petronas;

import java.util.List;
import java.util.Optional;

import com.raziman.petronas.logic.GenericAPICallLogic;
import com.raziman.petronas.logic.map.GitHubRepoMap;
import com.raziman.petronas.model.GitOwnerModel;
import com.raziman.petronas.model.GitRepoModel;

public class TriggerTest {
	
	public static void main(String[] args) throws Exception {
		
		String hostname = "https://api.github.com";
		String subURL = "/search/repositories";
		String topic = "Signature";
		String language = "Java";
		String sortBy = "stars";
		String sortOrder = "desc";
		
		
		GenericAPICallLogic apiCall = new GenericAPICallLogic();
		GitHubRepoMap repoMap = new GitHubRepoMap();
		
		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder);

		System.out.println("Total Found: " + repoMap.getRepoCount(responseBody));
		
		List<GitRepoModel> repoObjList = repoMap.getRepoListObject(responseBody);
//		System.out.printf("Found %s repositories", repoObjList.size());
		
//		System.out.printf("RepoId");
//		System.out.printf("RepoName");
//		System.out.printf("RepoPrivate");
//		System.out.printf("RepoDescription");
//		System.out.printf("RepoURL");
		
		String repoOwnerId = "8274990";
		
		GitOwnerModel ownerObj = repoMap.getOwner(responseBody, repoOwnerId);
		
		Optional<GitOwnerModel> opt = Optional.ofNullable(ownerObj);
		
		if(opt.isPresent()) {
			System.out.println("ownerType: " + ownerObj.getOwnerType());
			
		} else {
			System.out.println("No owner found for ownerId: " + repoOwnerId);
		}
		
		for (int x=0;x<repoObjList.size();x++) {
			
			System.out.printf("RepoId: %-10s ", repoObjList.get(x).getRepoId());
			System.out.printf("RepoOwnerId: %-10s ", repoObjList.get(x).getRepoOwnerId());
			System.out.printf("RepoName: %-30s ", repoObjList.get(x).getRepoName());
			System.out.printf("RepoPrivate: %-5s ", repoObjList.get(x).getRepoPrivate());
			System.out.printf("RepoDescription: %-80s ", repoObjList.get(x).getRepoDescription());
			System.out.printf("RepoURL: %-20s ", repoObjList.get(x).getRepoURL());
			System.out.printf("%n");
		}
		

		
		
	}
	
}