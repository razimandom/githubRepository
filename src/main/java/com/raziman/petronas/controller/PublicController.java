package com.raziman.petronas.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raziman.petronas.model.GitForm;
import com.raziman.petronas.response.GitAdminResponse;
import com.raziman.petronas.response.GitPublicResponse;
import com.raziman.petronas.service.AdminService;
import com.raziman.petronas.service.ApiCallService;
import com.raziman.petronas.service.RepoService;

@Controller
@RequestMapping("/repository")
public class PublicController {
	
	private static final Logger log = LoggerFactory.getLogger(PublicController.class);

	@Value("${github.hostname}")
	private String hostname;

	@Value("${github.subURL}")
	private String subURL;
	
	@Value("${github.sortBy}")
	private String defaultSortBy;
	
	@Value("${github.sortOrder}")
	private String defaultSortOrder;
    
//    @GetMapping("")
//    public String viewRepository() throws Exception{
//        return "repository/index";
//    }
    
    @GetMapping("/doc")
    public String viewRepositoryDoc() throws Exception{
        return "repository/doc";
    }
    
    
	@GetMapping("")
    public String viewRepository(ModelMap model) {
		model.addAttribute("gitForm", new GitForm());
        return "repository/index";
    }
	
    @PostMapping("")
    public String viewRepository(@ModelAttribute GitForm gitForm, ModelMap model) throws Exception {
    	
		RepoService repoService = new RepoService();
		ApiCallService apiCallService = new ApiCallService();
		
		String topic = Optional.ofNullable(gitForm.getTopic()).orElse("petronas");
		String language = Optional.ofNullable(gitForm.getLanguage()).orElse("java");
		String page = Optional.ofNullable(gitForm.getPage()).orElse("1");
		String sortBy = Optional.ofNullable(gitForm.getSortBy()).orElse(defaultSortBy);
		String sortOrder = Optional.ofNullable(gitForm.getSortOrder()).orElse(defaultSortOrder);
		String pageSize  = Optional.ofNullable(gitForm.getPageSize()).orElse("15");
		
		log.info("USER INPUT: "
				+ " topic: " + topic
				+ " language: " + language
				+ " page: " + page
				+ " sortBy: " + sortBy
				+ " sortOrder: " + sortOrder
				+ " pageSize: " + pageSize
				
				);
			
		GitPublicResponse gitPublicResponse = repoService.getRepository(
				apiCallService.createGitConnection(hostname, subURL, topic, language, page, sortBy, sortOrder, pageSize));
		
        model.addAttribute("count", gitPublicResponse.getCount());
        model.addAttribute("repoListContainer", gitPublicResponse.getRepoList());
    	
        return "repository/index";
    }
    
    

//    @RequestMapping({
//    	"/search/topic/{topic}/language/{language}/page/{page}/per_page/{per_page}",
//    	"/search/topic/{topic}/language/{language}/page/{page}",
//    	"/search/topic/{topic}/language/{language}",
//    	"/search",
//    	})    
//    public String doViewRepo(
//    		@PathVariable(name = "topic", required = false) String topicVar, 
//    		@PathVariable(name = "language", required = false) String languageVar,
//    		@PathVariable(name = "page", required = false) String pageVar,
//    		@PathVariable(name = "per_page", required = false) String per_pageVar,
//    		ModelMap model) throws Exception{
//		
//    	String topic = StringUtils.isEmpty(topicVar) ? "githubRepository" : topicVar;
//    	String language = StringUtils.isEmpty(languageVar) ? "java" : languageVar;
//    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
//    	String per_page = StringUtils.isEmpty(per_pageVar) ? "10" : per_pageVar;
//    	
//		RepoService repoLogic = new RepoService();
//		ApiCallService apiCallService = new ApiCallService();
//		GitPublicResponse gitPublicResponse = repoLogic.getRepository(apiCallService.createGitConnection(hostname, subURL, topic, language, page, sortBy, sortOrder, per_page));
//		
//        model.addAttribute("topic", topic);
//        model.addAttribute("language", language);
//        model.addAttribute("count", gitPublicResponse.getCount());
//        model.addAttribute("repoListContainer", gitPublicResponse.getRepoList());
//
//        return "repository/index";
//    }
//    
//    @RequestMapping({
//    	"/search/language/{language}/page/{page}/per_page/{per_page}",
//    	"/search/language/{language}/page/{page}",
//    	"/search/language/{language}",
//    	"/search/language"
//    	})    
//    public String doViewRepoByLanguage(
//    		@PathVariable(name = "language", required = false) String languageVar,
//    		@PathVariable(name = "page", required = false) String pageVar,
//    		@PathVariable(name = "per_page", required = false) String per_pageVar,
//    		ModelMap model) throws Exception{
//		
//    	String topic = null;
//    	String language = StringUtils.isEmpty(languageVar) ? "java" : languageVar;
//    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
//    	String per_page = StringUtils.isEmpty(per_pageVar) ? "10" : per_pageVar;
//    	
//		RepoService repoLogic = new RepoService();
//		ApiCallService apiCallService = new ApiCallService();
//		GitPublicResponse gitPublicResponse = repoLogic.getRepository(apiCallService.createGitConnection(hostname, subURL, topic, language, page, sortBy, sortOrder, per_page));
//		
//        model.addAttribute("topic", topic);
//        model.addAttribute("language", language);
//        model.addAttribute("count", gitPublicResponse.getCount());
//        model.addAttribute("repoListContainer", gitPublicResponse.getRepoList());
//
//        return "repository/index";
//    }
//    
//    @RequestMapping({
//    	"/search/topic/{topic}/page/{page}/per_page/{per_page}",
//    	"/search/topic/{topic}/page/{page}",
//    	"/search/topic/{topic}",
//    	"/search/topic"
//    	})    
//    public String doViewRepoByTopic(
//    		@PathVariable(name = "topic", required = false) String topicVar,
//    		@PathVariable(name = "page", required = false) String pageVar,
//    		@PathVariable(name = "per_page", required = false) String per_pageVar,
//    		ModelMap model) throws Exception{
//		
//    	String topic = StringUtils.isEmpty(topicVar) ? "githubRepository" : topicVar;
//    	String language = null;
//    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
//    	String per_page = StringUtils.isEmpty(per_pageVar) ? "10" : per_pageVar;
//    	
//		RepoService repoLogic = new RepoService();
//		ApiCallService apiCallService = new ApiCallService();
//		GitPublicResponse gitPublicResponse = repoLogic.getRepository(apiCallService.createGitConnection(hostname, subURL, topic, language, page, sortBy, sortOrder, per_page));
//		
//        model.addAttribute("topic", topic);
//        model.addAttribute("language", language);
//        model.addAttribute("count", gitPublicResponse.getCount());
//        model.addAttribute("repoListContainer", gitPublicResponse.getRepoList());
//
//        return "repository/index";
//    }

}
