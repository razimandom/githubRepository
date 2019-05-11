package com.raziman.petronas.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.model.GitReport;

@Service
public class AdminService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminService.class);
	
//    @PreAuthorize("hasRole(@roles.ADMIN)")
//    public boolean ensureAdmin() {
//        return true;
//    }
	
	public void generateReportFile(List<GitRepo> repoObjList) {
		
		try {
			try (FileWriter file = new FileWriter(new File("src\\main\\resources\\templates\\report", "report.txt"))) {

				String reportHeader = String.format(
						"%-5s %-10s %-50s %-15s %-80s %-10s %n %n", 
						"No", "Id", "Name","Language", "URL", "Owner Id");

				file.write(reportHeader);

				for (int x = 0; x < repoObjList.size(); x++) {

					String reportOutput = String.format("%-5d %-10s %-50s %-15s %-80s %-10s %n",
							x+1,
							repoObjList.get(x).getRepoId(), 
							repoObjList.get(x).getRepoName(),
							repoObjList.get(x).getRepoLanguage(),
							repoObjList.get(x).getRepoURL(),
							repoObjList.get(x).getRepoOwnerId()
							);

					file.write(reportOutput);

				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public Optional<TreeMap<String, String>> getTopAttribute(List<GitRepo> repoObjList, String attribute) {

		TreeMap<String, String> map = new TreeMap<>();
		
		for (GitRepo gitRepo : repoObjList) {
			
			if (attribute.equals("score"))
				map.put(gitRepo.getRepoScore(), gitRepo.getRepoName());
			
			if (attribute.equals("watchers_count"))
				map.put(gitRepo.getRepoWatcherCount(), gitRepo.getRepoName());
			
			if (attribute.equals("created_at"))
				map.put(gitRepo.getRepoDateCreated(), gitRepo.getRepoName());
			
			if (attribute.equals("updated_at"))
				map.put(gitRepo.getRepoDateUpdated(), gitRepo.getRepoName());
			
			if (attribute.equals("pushed_at"))
				map.put(gitRepo.getRepoDatePushed(), gitRepo.getRepoName());
			
		}
		
		return Optional.ofNullable(map);
		
	}
		
	public GitReport getAdminReport(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()!=200)
			throw new Exception("Error generating admin report. Response " + response.getResponseCode() + " " + response.getResponseMessage());
			

		String responseBody = apiCall.getResponseBody(response);
		List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
		
		Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
		
		if (optRepoList.isPresent()) {
			Optional<TreeMap<String, String>> mapScore = getTopAttribute(repoObjList, "score");
			Optional<TreeMap<String, String>> mapWatchersCount = getTopAttribute(repoObjList, "watchers_count");
			Optional<TreeMap<String, String>> mapDateCreated = getTopAttribute(repoObjList, "created_at");
			Optional<TreeMap<String, String>> mapDateUpdated = getTopAttribute(repoObjList, "updated_at");
			Optional<TreeMap<String, String>> mapDatePushed = getTopAttribute(repoObjList, "pushed_at");
			
			if (mapScore.isPresent()) {
				
				GitReport gitReport = new GitReport();
				gitReport.setTopScore(mapScore.get().firstKey());
				gitReport.setTopScoreRepoName(mapScore.get().get(mapScore.get().firstKey()));
				gitReport.setTopWatcherCount(mapWatchersCount.get().firstKey());
				gitReport.setTopWatcherCountRepoName(mapWatchersCount.get().get(mapWatchersCount.get().firstKey()));
				gitReport.setLatestCreatedDate(mapDateCreated.get().firstKey());
				gitReport.setLatestCreatedDateRepoName(mapDateCreated.get().get(mapDateCreated.get().firstKey()));
				gitReport.setLatestUpdatedDate(mapDateUpdated.get().firstKey());
				gitReport.setLatestUpdatedDateRepoName(mapDateUpdated.get().get(mapDateUpdated.get().firstKey()));
				gitReport.setLatestPushedDate(mapDatePushed.get().firstKey());
				gitReport.setLatestPushedDateRepoName(mapDatePushed.get().get(mapDatePushed.get().firstKey()));
				
				return gitReport;
			}
			
		}

		
		return null;
		
	}
	
	
	public List<GitRepo> getRepositoryAdmin(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			
			List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
			
			Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
			
			if (optRepoList.isPresent()) {
				generateReportFile(repoObjList);
				
				return repoObjList;
			}

		}
		
		return null;
		
	}
	
}