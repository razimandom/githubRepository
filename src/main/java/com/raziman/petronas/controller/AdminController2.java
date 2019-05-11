//package com.raziman.petronas.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.raziman.petronas.model.GitRepo;
//import com.raziman.petronas.service.RepoService;
//
//@Controller
//public class AdminController2 {
//	
//	@Value("${github.hostname}")
//	private String hostname;
//
//	@Value("${github.subURL}")
//	private String subURL;
//	
//	@Value("${github.sortBy}")
//	private String sortBy;
//	
//	@Value("${github.sortOrder}")
//	private String sortOrder;
//	
//    @GetMapping("/admin")
//    public String viewAdmin(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "admin/index";
//    }
//    
//    @RequestMapping({
//    	"/admin/repository/search/topic/{topic}/language/{language}/page/{page}",
//    	"/admin/repository/search/topic/{topic}/language/{language}",
//    	"/admin/repository/search/topic/{topic}",
//    	"/admin/repository/search",
//    	})    
//    public String doAdminViewRepo(
//    		@PathVariable(name = "topic", required = false) String topicVar, 
//    		@PathVariable(name = "language", required = false) String languageVar,
//    		@PathVariable(name = "page", required = false) String pageVar,
//    		ModelMap model) throws Exception{
//		
//    	String topic = StringUtils.isEmpty(topicVar) ? "githubRepository" : topicVar;
//    	String language = StringUtils.isEmpty(languageVar) ? "java" : languageVar;
//    	String page = StringUtils.isEmpty(pageVar) ? "1" : pageVar;
//    	
//		RepoService repoLogic = new RepoService();
//		List<GitRepo> repoList = repoLogic.getRepository(hostname, subURL, sortBy, sortOrder, topic, language, page);
//		
//        model.addAttribute("topic", topic);
//        model.addAttribute("language", language);
//        model.addAttribute("count", repoLogic.getCount(hostname, subURL, sortBy, sortOrder,topic, language, page));
//        model.addAttribute("repoListContainer", repoList);
//
//        return "admin/index";
//    }
//
//}
