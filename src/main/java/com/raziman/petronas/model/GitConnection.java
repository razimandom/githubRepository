package com.raziman.petronas.model;

public class GitConnection {
	
	private String hostname;
	private String subURL;
	private String topic;
	private String language;
	private String sortBy;
	private String sortOrder;
	private String repoOwnerId;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getSubURL() {
		return subURL;
	}
	public void setSubURL(String subURL) {
		this.subURL = subURL;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getRepoOwnerId() {
		return repoOwnerId;
	}
	public void setRepoOwnerId(String repoOwnerId) {
		this.repoOwnerId = repoOwnerId;
	}
	
	@Override
	public String toString() {
		return "GitConnection [hostname=" + hostname + ", subURL=" + subURL + ", topic=" + topic + ", language="
				+ language + ", sortBy=" + sortBy + ", sortOrder=" + sortOrder + ", repoOwnerId=" + repoOwnerId + "]";
	}

	
	

}
