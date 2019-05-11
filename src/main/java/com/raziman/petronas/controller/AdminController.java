package com.raziman.petronas.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raziman.petronas.model.GitConnection;
import com.raziman.petronas.model.GitReport;
import com.raziman.petronas.response.GitAdminResponse;
import com.raziman.petronas.service.AdminService;
import com.raziman.petronas.service.FileStorageService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
    @Autowired
    private FileStorageService fileStorageService;
	
	@Value("${github.hostname}")
	private String hostname;

	@Value("${github.subURL}")
	private String subURL;
	
	@Value("${github.sortBy}")
	private String sortBy;
	
	@Value("${github.sortOrder}")
	private String sortOrder;
	
	@RequestMapping("")
    public String viewAdmin() {
        return "admin/index";
    }
    
    @RequestMapping({
    	"/repository/search/topic/{topic}/language/{language}/page/{page}",
    	"/repository/search/topic/{topic}/language/{language}",
    	"/repository/search",
    	})    
    public String doAdminViewRepo(
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
    	
		AdminService adminService = new AdminService();
		GitAdminResponse gitAdminResponse = adminService.getRepositoryAdmin(gitCon);

        createReportValue(model, gitAdminResponse.getReport());
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", gitAdminResponse.getCount());
        model.addAttribute("repoListContainer", gitAdminResponse.getRepoList());

        return "admin/index";
    }
    
    
    @RequestMapping({
    	"/repository/search/topic/{topic}/page/{page}",
    	"/repository/search/topic/{topic}",
    	"/repository/search/topic"
    	})    
    public String doAdminViewRepoByTopic(
    		@PathVariable(name = "topic", required = false) String topicVar, 
    		@PathVariable(name = "page", required = false) String pageVar,
    		ModelMap model) throws Exception{
		
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
    	
		AdminService adminService = new AdminService();
		GitAdminResponse gitAdminResponse = adminService.getRepositoryAdmin(gitCon);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", gitAdminResponse.getCount());
        model.addAttribute("repoListContainer", gitAdminResponse.getRepoList());    

        createReportValue(model, gitAdminResponse.getReport());

        return "admin/index";
    }
    
    @RequestMapping({    	
    	"/repository/search/language/{language}/page/{page}",
    	"/repository/search/language/{language}",
    	"/repository/search/language"	
    	})    
    public String doAdminViewRepoByLanguage(
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
    	
		AdminService adminService = new AdminService();
		GitAdminResponse gitAdminResponse = adminService.getRepositoryAdmin(gitCon);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", gitAdminResponse.getCount());
        model.addAttribute("repoListContainer", gitAdminResponse.getRepoList());
        
        createReportValue(model, gitAdminResponse.getReport());

        return "admin/index";
    }
    
  @RequestMapping( value = "/repository/search/owner/{repoOwnerId}", method = RequestMethod.GET)
  public String doViewOwner(@PathVariable("repoOwnerId") int repoOwnerId){
      return "admin/repo_owner";
  }
  
  
  @GetMapping("/repository/file/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
      // Load file as Resource
      Resource resource = fileStorageService.loadFileAsResource(fileName);

      // Try to determine file's content type
      String contentType = null;
      try {
          contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
    	  log.info("Could not determine file type.");
      }

      // Fallback to the default content type if type could not be determined
      if(contentType == null) {
          contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(contentType))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
              .body(resource);
  }
  
  public void createReportValue(ModelMap model, GitReport gitReport) {
      
      model.addAttribute("topScore", gitReport.getTopScore());
      model.addAttribute("topScoreRepoName", gitReport.getTopScoreRepoName());
      model.addAttribute("topWatcherCount", gitReport.getTopWatcherCount());
      model.addAttribute("topWatcherCountRepoName", gitReport.getTopWatcherCountRepoName());
      model.addAttribute("latestCreatedDate", gitReport.getLatestCreatedDate());
      model.addAttribute("latestCreatedDateRepoName", gitReport.getLatestCreatedDateRepoName());
      model.addAttribute("latestUpdatedDate", gitReport.getLatestUpdatedDate());
      model.addAttribute("latestUpdatedDateRepoName", gitReport.getLatestUpdatedDateRepoName());
      model.addAttribute("latestPushedDate", gitReport.getLatestPushedDate());
      model.addAttribute("latestPushedDateRepoName", gitReport.getLatestPushedDateRepoName());
  	
  }
    

}
