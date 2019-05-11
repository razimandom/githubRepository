package com.raziman.petronas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitRepo;
//import com.raziman.petronas.service.AdminService;
import com.raziman.petronas.service.RepoService;

//import com.raziman.petronas.logic.RepoLogic;

@Controller
@RequestMapping("/repository")
public class PublicController {
	
//	@Autowired
//	private Environment env;
//	
//	String hostname = env.getProperty("github.hostname");
//	String subURL = env.getProperty("github.hostname");;
//	String sortBy = env.getProperty("github.hostname");;
//	String sortOrder = env.getProperty("github.hostname");;

	@Value("${github.hostname}")
	private String hostname;

	@Value("${github.subURL}")
	private String subURL;
	
	@Value("${github.sortBy}")
	private String sortBy;
	
	@Value("${github.sortOrder}")
	private String sortOrder;
	
//    final AdminService adminService;
//
//    @Autowired
//    HomeController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//    
//    @RequestMapping("/")
//    String home() {
//        return "index";
//    }
//    
//    @RequestMapping("/restricted")
//    String restricted() {
//        return "restricted";
//    }
//
//    @RequestMapping("/admin")
//    String admin() {
//        adminService.ensureAdmin();
//        return "admin";
//    }
    
    @RequestMapping("")
    public String viewRepository() throws Exception{
        return "repository/index";
        
    }

    @RequestMapping({
    	"/search/topic/{topic}/language/{language}/page/{page}",
    	"/search/topic/{topic}/language/{language}",
    	"/search",
    	})    
    public String doViewRepo(
    		@PathVariable(name = "topic", required = false) String topicVar, 
    		@PathVariable(name = "language", required = false) String languageVar,
    		@PathVariable(name = "page", required = false) String pageVar,
    		ModelMap model) throws Exception{
		
    	String topic = StringUtils.isEmpty(topicVar) ? "githubRepository" : topicVar;
    	String language = StringUtils.isEmpty(languageVar) ? "java" : languageVar;
    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
    	
		GitConnection gitCon = new GitConnection();
		gitCon.setHostname(hostname);
		gitCon.setSubURL(subURL);
		gitCon.setTopic(topic);
		gitCon.setLanguage(language);
		gitCon.setPage(page);
		gitCon.setSortBy(sortBy);
		gitCon.setSortOrder(sortOrder);
    	
		RepoService repoLogic = new RepoService();
		List<GitRepo> repoList = repoLogic.getRepository(gitCon);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", repoLogic.getCount(gitCon));
        model.addAttribute("repoListContainer", repoList);

        return "repository/index";
    }
    
    @RequestMapping({
    	"/search/language/{language}/page/{page}",
    	"/search/language/{language}",
    	"/search/language"
    	})    
    public String doViewRepoByLanguage(
    		@PathVariable(name = "language", required = false) String languageVar,
    		@PathVariable(name = "page", required = false) String pageVar,
    		ModelMap model) throws Exception{
		
    	String topic = null;
    	String language = StringUtils.isEmpty(languageVar) ? "java" : languageVar;
    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
    	
		GitConnection gitCon = new GitConnection();
		gitCon.setHostname(hostname);
		gitCon.setSubURL(subURL);
		gitCon.setTopic(topic);
		gitCon.setLanguage(language);
		gitCon.setPage(page);
		gitCon.setSortBy(sortBy);
		gitCon.setSortOrder(sortOrder);
    	
		RepoService repoLogic = new RepoService();
		List<GitRepo> repoList = repoLogic.getRepository(gitCon);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", repoLogic.getCount(gitCon));
        model.addAttribute("repoListContainer", repoList);

        return "repository/index";
    }
    
    @RequestMapping({
    	"/search/topic/{topic}/page/{page}",
    	"/search/topic/{topic}",
    	"/search/topic"
    	})    
//    @RequestMapping(value="/repository/search/topic/{topic}", method = RequestMethod.POST) 
    public String doViewRepoByTopic(
    		@PathVariable(name = "topic", required = false) String topicVar,
    		@PathVariable(name = "page", required = false) String pageVar,
    		ModelMap model) throws Exception{
    	
    	System.out.println("Test: " + topicVar);
		
    	String topic = StringUtils.isEmpty(topicVar) ? "githubRepository" : topicVar;
    	String language = null;
    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
    	
		GitConnection gitCon = new GitConnection();
		gitCon.setHostname(hostname);
		gitCon.setSubURL(subURL);
		gitCon.setTopic(topic);
		gitCon.setLanguage(language);
		gitCon.setPage(page);
		gitCon.setSortBy(sortBy);
		gitCon.setSortOrder(sortOrder);
    	
		RepoService repoLogic = new RepoService();
		List<GitRepo> repoList = repoLogic.getRepository(gitCon);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", repoLogic.getCount(gitCon));
        model.addAttribute("repoListContainer", repoList);

        return "repository/index";
    }

}
