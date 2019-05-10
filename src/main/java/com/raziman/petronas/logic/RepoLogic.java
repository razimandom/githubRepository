package com.raziman.petronas.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.raziman.petronas.logic.map.GitHubRepoMap;
import com.raziman.petronas.model.GitOwnerModel;
import com.raziman.petronas.model.GitRepoModel;

public class RepoLogic {
	
	@Autowired
	private Environment env;
	
//	@Value("${github.hostname}")
//	private String hostname;
//
//	@Value("${welcome.message}")
//	private String subURL;
//	
//	@Value("${github.sortBy}")
//	private String sortBy;
//	
//	@Value("${github.sortOrder}")
//	private String sortOrder;
	
//	String hostname = env.getProperty("github.hostname");
//	String subURL = env.getProperty("github.hostname");;
//	String sortBy = env.getProperty("github.hostname");;
//	String sortOrder = env.getProperty("github.hostname");;

	String hostname = "https://api.github.com";
	String subURL = "/search/repositories";
	String sortBy = "created";
	String sortOrder = "desc";
	
	public String getCount(String topic, String language) throws Exception {
		
		GenericAPICallLogic apiCall = new GenericAPICallLogic();
		GitHubRepoMap repoMap = new GitHubRepoMap();
		
		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder);
		
		return repoMap.getRepoCount(responseBody);
	}
	
	public List<GitRepoModel> getRepository(String topic, String language) throws Exception {
		
		GenericAPICallLogic apiCall = new GenericAPICallLogic();
		GitHubRepoMap repoMap = new GitHubRepoMap();
		
		System.out.println(hostname);
		System.out.println(subURL);
		
		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder);

		System.out.println("Total Found: " + repoMap.getRepoCount(responseBody));
		
		List<GitRepoModel> repoObjList = repoMap.getRepoListObject(responseBody);
		
		Optional<List<GitRepoModel>> optRepoList = Optional.ofNullable(repoObjList);
		
		if (optRepoList.isPresent()) {
			return repoObjList;
		}
		
		return null;
		
	}
	
	public void viewRepo(String topic, String language) throws Exception {
		
		GenericAPICallLogic apiCall = new GenericAPICallLogic();
		GitHubRepoMap repoMap = new GitHubRepoMap();
		
		System.out.println(hostname);
		System.out.println(subURL);
		
		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder);

		System.out.println("Total Found: " + repoMap.getRepoCount(responseBody));
		
		List<GitRepoModel> repoObjList = repoMap.getRepoListObject(responseBody);
		
		Optional<List<GitRepoModel>> optRepoList = Optional.ofNullable(repoObjList);
		
		if (optRepoList.isPresent()) {
		
			System.out.printf("%-10s ", "RepoId");
			System.out.printf("%-10s ", "OwnerId");
			System.out.printf("%-30s ", "RepoName");
			System.out.printf("%-10s ", "Private");
			System.out.printf("%-80s ", "RepoDescription");
			System.out.printf("%-20s ", "RepoURL");
			System.out.printf("%n");
			
			for (int x=0;x<repoObjList.size();x++) {
				
				System.out.printf("%-10s ", repoObjList.get(x).getRepoId());
				System.out.printf("%-10s ", repoObjList.get(x).getRepoOwnerId());
				System.out.printf("%-30s ", repoObjList.get(x).getRepoName());
				System.out.printf("%-10s ", repoObjList.get(x).getRepoPrivate());
				System.out.printf("%-80s ", repoObjList.get(x).getRepoDescription());
				System.out.printf("%-20s ", repoObjList.get(x).getRepoURL());
				System.out.printf("%n");
			}
			
		}
		
	}
	
	public void viewOwner(String topic, String language, String repoOwnerId) throws Exception {
		
		GenericAPICallLogic apiCall = new GenericAPICallLogic();
		GitHubRepoMap repoMap = new GitHubRepoMap();
		
		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder);
		
		GitOwnerModel ownerObj = repoMap.getOwner(responseBody, repoOwnerId);
		
		Optional<GitOwnerModel> opt = Optional.ofNullable(ownerObj);
		
		if(opt.isPresent()) {
			System.out.println("ownerType: " + ownerObj.getOwnerType());
			
		} else {
			System.out.println("No owner found for ownerId: " + repoOwnerId);
		}
		
	}
	
}
