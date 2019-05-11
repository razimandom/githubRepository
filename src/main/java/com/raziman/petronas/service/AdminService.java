package com.raziman.petronas.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.model.GitReport;
import com.raziman.petronas.response.GitAdminResponse;

@Service
public class AdminService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminService.class);
	
	public void generateReportFile(List<GitRepo> repoObjList) {
		
        try {
        	
//            String filename = "F:/Workspace - Eclipse/petronas/src/main/resources/templates/report/admin-report.xls" ;
        	String filename = "/home/ec2-user/report/admin-report.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  
            
            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Id");
            rowhead.createCell(2).setCellValue("Name");
            rowhead.createCell(3).setCellValue("Language");
            rowhead.createCell(4).setCellValue("URL");
            rowhead.createCell(5).setCellValue("Owner Id");
            
			for (int x = 0; x < repoObjList.size(); x++) {
				
	            HSSFRow row = sheet.createRow((short)x+1);
	            row.createCell(0).setCellValue(x+1);
	            row.createCell(1).setCellValue(repoObjList.get(x).getRepoId());
	            row.createCell(2).setCellValue(repoObjList.get(x).getRepoName());
	            row.createCell(3).setCellValue(repoObjList.get(x).getRepoLanguage());
	            row.createCell(4).setCellValue(repoObjList.get(x).getRepoURL());
	            row.createCell(5).setCellValue(repoObjList.get(x).getRepoOwnerId());


			}

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            log.info("Excel file has been generated!");
            
        } catch (Exception e) {
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
		
	public GitReport getAdminReport(String responseBody) throws Exception {
		
		GitRepoMapping repoMap = new GitRepoMapping();

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
	
	
	public GitAdminResponse getRepositoryAdmin(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			
			List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
			
			Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
			
			if (optRepoList.isPresent()) {
				generateReportFile(repoObjList);
				
				return new GitAdminResponse(
						repoMap.getRepoCount(responseBody), 
						repoMap.getRepoListObject(responseBody),
						getAdminReport(responseBody));
			}

		}
		
		return null;
		
	}
	
}