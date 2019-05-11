package com.raziman.petronas.service;

import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raziman.petronas.map.GitRepoMapping;
import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.response.GitPublicResponse;

@Service
public class RepoService {
	
	private static final Logger log = LoggerFactory.getLogger(RepoService.class);
	
//	public String getCount(GitConnection gitCon) throws Exception {
//		
//		ApiCallService apiCall = new ApiCallService();
//		GitRepoMapping repoMap = new GitRepoMapping();
//		
//		HttpsURLConnection response = apiCall.callAPI(gitCon);
//		
//		if (response.getResponseCode()==200) {
//			String responseBody = apiCall.getResponseBody(response);
//			return repoMap.getRepoCount(responseBody);
//		}
//		
//		return null;
//		
//	}
	
	public GitPublicResponse getRepository(GitConnection gitCon) throws Exception {
		
		ApiCallService apiCall = new ApiCallService();
		GitRepoMapping repoMap = new GitRepoMapping();
		
		HttpsURLConnection response = apiCall.callAPI(gitCon);
		
		if (response.getResponseCode()==200) {
			String responseBody = apiCall.getResponseBody(response);
			
			List<GitRepo> repoObjList = repoMap.getRepoListObject(responseBody);
			
			Optional<List<GitRepo>> optRepoList = Optional.ofNullable(repoObjList);
			
//			log.info("GitPublicResponse: " + repoMap.getRepoCount(responseBody) + " " + repoMap.getRepoListObject(responseBody));
			
			if (optRepoList.isPresent()) {
				return new GitPublicResponse(repoMap.getRepoCount(responseBody), repoMap.getRepoListObject(responseBody));
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
