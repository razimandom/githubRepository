package com.raziman.petronas.service;

import java.io.FileWriter;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitOwner;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.model.GitReport;

@Service
public class RepoService {
	
	private static final Logger log = LoggerFactory.getLogger(RepoService.class);
	
//	@Autowired
//	private Environment env;
	
//	@Value("${github.hostname}")
//	private static String aaa;
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

//	String hostname = "https://api.github.com";
//	String subURL = "/search/repositories";
//	String sortBy = "created";
//	String sortOrder = "desc";
	
	public String getCount(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			return repoMap.getRepoCount(responseBody);
		}
		
		return null;
		
	}
	
	public List<GitRepo> getRepository(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		
//		PrintStream fileStream = new PrintStream("filename.txt");
//		System.out.println("Hi");
//		
//		System.setOut(fileStream);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			
			List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
			
			Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);

			if (optRepoList.isPresent()) {
				

				
//				System.out.printf("%-10s ", "RepoId");
//				System.out.printf("%-10s ", "OwnerId");
//				System.out.printf("%-30s ", "RepoName");
//				System.out.printf("%-10s ", "Private");
//				System.out.printf("%-80s ", "RepoDescription");
//				System.out.printf("%-100s ", "RepoURL");
//				System.out.printf("%-100s ", "repoHomePage");
//				System.out.printf("%-80s ", "repoDateCreated");
//				System.out.printf("%-80s ", "repoDateUpdated");
//				System.out.printf("%-80s ", "repoDatePushed");
//				System.out.printf("%-80s ", "repoSize");
//				System.out.printf("%-80s ", "repoWatcherCount");
//				System.out.printf("%-80s ", "repoScore");
//				System.out.printf("%n");
				
				try (FileWriter file = new FileWriter("report.txt")) {

					String reportHeader = String.format("%-10s %-10s %-30s %-10s %-80s %n %n", "RepoId", "OwnerId",
							"RepoName", "Private", "RepoDescription");

					file.write(reportHeader);

					for (int x = 0; x < repoObjList.size(); x++) {

						String reportOutput = String.format("%-10s %-10s %-30s %-10s %-80s %n",
								repoObjList.get(x).getRepoId(), repoObjList.get(x).getRepoOwnerId(),
								repoObjList.get(x).getRepoName(), repoObjList.get(x).getRepoPrivate(),
								repoObjList.get(x).getRepoDescription());

						file.write(reportOutput);

						System.out.printf("%-10s ", repoObjList.get(x).getRepoId());
						System.out.printf("%-10s ", repoObjList.get(x).getRepoOwnerId());
						System.out.printf("%-30s ", repoObjList.get(x).getRepoName());
						System.out.printf("%-10s ", repoObjList.get(x).getRepoPrivate());
						System.out.printf("%-80s ", repoObjList.get(x).getRepoDescription());
//						System.out.printf("%-100s ", repoObjList.get(x).getRepoURL());
//						System.out.printf("%-100s ", repoObjList.get(x).getRepoHomePage());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoDateCreated());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoDateUpdated());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoDatePushed());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoSize());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoWatcherCount());
//						System.out.printf("%-80s ", repoObjList.get(x).getRepoScore());
						System.out.printf("%n");
					}

				}
				

//				
			}
			
//			if (optRepoList.isPresent()) {
//			
//				System.out.printf("%-10s ", "RepoId");
//				System.out.printf("%-10s ", "OwnerId");
//				System.out.printf("%-30s ", "RepoName");
//				System.out.printf("%-10s ", "Private");
//				System.out.printf("%-80s ", "RepoDescription");
//				System.out.printf("%-100s ", "RepoURL");
//				System.out.printf("%-100s ", "repoHomePage");
//				System.out.printf("%-80s ", "repoDateCreated");
//				System.out.printf("%-80s ", "repoDateUpdated");
//				System.out.printf("%-80s ", "repoDatePushed");
//				System.out.printf("%-80s ", "repoSize");
//				System.out.printf("%-80s ", "repoWatcherCount");
//				System.out.printf("%-80s ", "repoScore");
//				System.out.printf("%n");
//				
//				for (int x=0;x<repoObjList.size();x++) {
//					
//					System.out.printf("%-10s ", repoObjList.get(x).getRepoId());
//					System.out.printf("%-10s ", repoObjList.get(x).getRepoOwnerId());
//					System.out.printf("%-30s ", repoObjList.get(x).getRepoName());
//					System.out.printf("%-10s ", repoObjList.get(x).getRepoPrivate());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoDescription());
//					System.out.printf("%-100s ", repoObjList.get(x).getRepoURL());
//					System.out.printf("%-100s ", repoObjList.get(x).getRepoHomePage());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoDateCreated());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoDateUpdated());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoDatePushed());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoSize());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoWatcherCount());
//					System.out.printf("%-80s ", repoObjList.get(x).getRepoScore());
//					System.out.printf("%n");
//				}
//				
//				try(PrintWriter out = new PrintWriter("texts.txt")  ){
//				    out.println(text);
//				}
//				
//			}
			
			if (optRepoList.isPresent()) {
				return repoObjList;
			}

		}
		
		return null;
		
	}
	

	
//	public void viewRepo(String topic, String language, String page, String pageTotal) throws Exception {
//		
//		GenericAPICallLogic apiCall = new GenericAPICallLogic();
//		GitHubRepoMap repoMap = new GitHubRepoMap();
//		
//		System.out.println(hostname);
//		System.out.println(subURL);
//		
//		String responseBody = apiCall.getRepository(hostname, subURL, topic, language, sortBy, sortOrder, page, pageTotal);
//
//		System.out.println("Total Found: " + repoMap.getRepoCount(responseBody));
//		
//		List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
//		
//		Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
//		
//		if (optRepoList.isPresent()) {
//		
//			System.out.printf("%-10s ", "RepoId");
//			System.out.printf("%-10s ", "OwnerId");
//			System.out.printf("%-30s ", "RepoName");
//			System.out.printf("%-10s ", "Private");
//			System.out.printf("%-80s ", "RepoDescription");
//			System.out.printf("%-20s ", "RepoURL");
//			System.out.printf("%n");
//			
//			for (int x=0;x<repoObjList.size();x++) {
//				
//				System.out.printf("%-10s ", repoObjList.get(x).getRepoId());
//				System.out.printf("%-10s ", repoObjList.get(x).getRepoOwnerId());
//				System.out.printf("%-30s ", repoObjList.get(x).getRepoName());
//				System.out.printf("%-10s ", repoObjList.get(x).getRepoPrivate());
//				System.out.printf("%-80s ", repoObjList.get(x).getRepoDescription());
//				System.out.printf("%-20s ", repoObjList.get(x).getRepoURL());
//				System.out.printf("%n");
//			}
//			
//		}
//		
//	}
	
//	public GitOwner getRepositoryOwner(GitConnection gitCon) throws Exception {
//		
//		ApiCallService apiCall = new ApiCallService();
//		GitRepoMapping repoMap = new GitRepoMapping();
//		
//		HttpsURLConnection response = apiCall.callAPI(gitCon);
//		
//		if (response.getResponseCode()==200) {
//			String responseBody = apiCall.getResponseBody(response);
//			
//			GitOwner repoOwner = repoMap.getOwner(responseBody, repoOwnerId);
//			
//			Optional<GitOwner> optRepoList = Optional.ofNullable(repoOwner);
//			
//			if (optRepoList.isPresent()) {
//				return repoOwner;
//			}
//
//		}
//		
//		return null;
//		
//	}


	
//	public Page<?> toPage(List<?> list, Pageable pageable) {
////		if (pageable.getOffset() >= list.size()) {
////			return Page.empty();
////		}
//		
//		int startIndex = (int) pageable.getOffset();
//		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
//		
//		List<?> subList = list.subList(startIndex, endIndex);
//		return new PageImpl(subList, pageable, list.size());
//	}
	
//	
//    public Page<GitRepo> findPaginated(Pageable pageable) {
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        List<GitRepo> list;
// 
//        if (books.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, books.size());
//            list = books.subList(startItem, toIndex);
//        }
// 
//        Page<GitRepo> bookPage = new PageImpl<GitRepo>(list, PageRequest.of(currentPage, pageSize), 0);
// 
//        return bookPage;
//    }
	
}
