package com.raziman.petronas.response;

import java.util.List;

import com.raziman.petronas.model.GitRepo;

public class GitPublicResponse {
	
	private String count;
	private List<GitRepo> repoList;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<GitRepo> getRepoList() {
		return repoList;
	}
	public void setRepoList(List<GitRepo> repoList) {
		this.repoList = repoList;
	}
	
	public GitPublicResponse(String count, List<GitRepo> repoList) {
		this.count = count;
		this.repoList = repoList;
	}
	
	@Override
	public String toString() {
		return "GitPublicResponse [count=" + count + ", repoList=" + repoList + "]";
	}
	
	
	
	

}
