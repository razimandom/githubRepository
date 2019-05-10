package com.raziman.petronas.logic;

import java.util.List;

import com.raziman.petronas.model.GitRepoModel;

public class RepoListContainer {
	
    private List<GitRepoModel> repo;

	public List<GitRepoModel> getRepo() {
		return repo;
	}

	public void setRepo(List<GitRepoModel> repo) {
		this.repo = repo;
	}


}
