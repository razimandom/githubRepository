package com.raziman.petronas.logic.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.raziman.petronas.model.GitOwnerModel;
import com.raziman.petronas.model.GitRepoModel;

public class GitHubRepoMap {
	
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
	
	public List<GitRepoModel> getRepoListObject(String responseBody) {
		
		try {
			
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			ArrayNode arrNode = (ArrayNode) jsonResponse.get("items");
			List<GitRepoModel> repoObjList = new ArrayList<>();
			
			if (arrNode.size()!=0) {
			
				for (int x=0; x<arrNode.size(); x++) {
					GitRepoModel repoObj = new GitRepoModel();
					repoObj.setRepoId(arrNode.get(x).get("id").asText());
					repoObj.setRepoName(arrNode.get(x).get("name").asText());
					repoObj.setRepoPrivate(arrNode.get(x).get("private").asText());
					repoObj.setRepoDescription(arrNode.get(x).get("description").asText());
					repoObj.setRepoURL(arrNode.get(x).get("html_url").asText());
					repoObj.setRepoOwnerId(arrNode.get(x).get("owner").get("id").asText());
					repoObjList.add(repoObj);
				}
				
				return repoObjList;
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public GitOwnerModel getOwner(String responseBody, String repoOwnerId) {
		
		try {
			
			JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);
			ArrayNode arrNode = (ArrayNode) jsonResponse.get("items");
			GitOwnerModel ownerObj = new GitOwnerModel();
			
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