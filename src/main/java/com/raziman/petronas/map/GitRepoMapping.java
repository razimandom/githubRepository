package com.raziman.petronas.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.raziman.petronas.model.GitOwner;
import com.raziman.petronas.model.GitRepo;

public class GitRepoMapping {
	
	public String getRepoCount(String responseBody) {
		
		try {
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			return jsonResponse.get("total_count").asText();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getRepoName(String responseBody) {
		
		try {
			
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			ArrayNode arrNode = (ArrayNode) jsonResponse.get("items");
			
			if (arrNode.size()!=0) {
			
				for (int x=0; x<arrNode.size(); x++) {
					String repoName = arrNode.get(x).get("name").asText();
					System.out.println(repoName);
				}
				
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<GitRepo> getRepoListObject(String responseBody) {
		
		try {
			
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			ArrayNode arrNode = (ArrayNode) jsonResponse.get("items");
			List<GitRepo> repoObjList = new ArrayList<>();
			
			if (arrNode.size()!=0) {
			
				for (int x=0; x<arrNode.size(); x++) {
					GitRepo repoObj = new GitRepo();
					repoObj.setRepoNo(x+1);
					repoObj.setRepoId(arrNode.get(x).get("id").asText());
					repoObj.setRepoName(arrNode.get(x).get("name").asText());
					repoObj.setRepoPrivate(arrNode.get(x).get("private").asText());
					repoObj.setRepoDescription(arrNode.get(x).get("description").asText());
					repoObj.setRepoURL(arrNode.get(x).get("html_url").asText());
					repoObj.setRepoOwnerId(arrNode.get(x).get("owner").get("id").asText());
					repoObj.setRepoHomePage(arrNode.get(x).get("homepage").asText());
					repoObj.setRepoDateCreated(arrNode.get(x).get("created_at").asText());
					repoObj.setRepoDateUpdated(arrNode.get(x).get("updated_at").asText());
					repoObj.setRepoDatePushed(arrNode.get(x).get("pushed_at").asText());
					repoObj.setRepoSize(arrNode.get(x).get("size").asText());
					repoObj.setRepoWatcherCount(arrNode.get(x).get("watchers_count").asText());
					repoObj.setRepoScore(arrNode.get(x).get("score").asText());
					repoObj.setRepoLanguage(arrNode.get(x).get("language").asText());
					repoObjList.add(repoObj);
				}
				
				return repoObjList;
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public GitOwner getOwner(String responseBody, String repoOwnerId) {
		
		try {
			
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			ArrayNode arrNode = (ArrayNode) jsonResponse.get("items");
			GitOwner ownerObj = new GitOwner();
			
			if (arrNode.size()==0)
				throw new Exception("No item found");
			
			for (int x=0; x<arrNode.size(); x++) {
				if (arrNode.get(x).get("owner").get("id").asText().equals(repoOwnerId)) {
					ownerObj.setOwnerid(arrNode.get(x).get("owner").get("id").asText());
					ownerObj.setOwnerURL(arrNode.get(x).get("owner").get("url").asText());
					ownerObj.setOwnerType(arrNode.get(x).get("owner").get("type").asText());
					
					return ownerObj;
				}
				
				
			}
	
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}