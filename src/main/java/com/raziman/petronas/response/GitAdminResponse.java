package com.raziman.petronas.response;

import java.util.List;

import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.model.GitReport;

public class GitAdminResponse {
	
	private String count;
	private List<GitRepo> repoList;
	private GitReport report;
	
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
	public GitReport getReport() {
		return report;
	}
	public void setReport(GitReport report) {
		this.report = report;
	}
	
	public GitAdminResponse(String count, List<GitRepo> repoList, GitReport report) {
		super();
		this.count = count;
		this.repoList = repoList;
		this.report = report;
	}
	
	
	

}
