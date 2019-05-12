package com.raziman.petronas.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.raziman.petronas.model.GitForm;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.model.GitReport;
import com.raziman.petronas.response.GitAdminResponse;
import com.raziman.petronas.response.GitPublicResponse;
import com.raziman.petronas.service.AdminService;
import com.raziman.petronas.service.ApiCallService;
import com.raziman.petronas.service.FileStorageService;
import com.raziman.petronas.service.PageService;
import com.raziman.petronas.service.PublicService;

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
	private String defaultSortBy;
	
	@Value("${github.sortOrder}")
	private String defaultSortOrder;
	
	@Value("${file.upload-dir")
	private String filePath;
	
	@GetMapping("")
    public String viewAdmin(ModelMap model) {
		model.addAttribute("gitForm", new GitForm());
        return "admin/index";
    }
	
	@RequestMapping("/list")
    public String viewAdmin(@ModelAttribute GitForm gitForm,
    		@RequestParam("topic") String topic,
    		@RequestParam("language") String language,
    		@RequestParam("page") String page,
    		ModelMap model) throws Exception {
    	
		AdminService adminService = new AdminService();
		ApiCallService apiCallService = new ApiCallService();
		
		page = Optional.ofNullable(gitForm.getPage()).orElse("1");
		String sortBy = Optional.ofNullable(gitForm.getSortBy()).orElse(defaultSortBy);
		String sortOrder = Optional.ofNullable(gitForm.getSortOrder()).orElse(defaultSortOrder);
		
		log.info("USER INPUT: "
				+ " topic: " + topic
				+ " language: " + language
				+ " page: " + page
				+ " sortBy: " + sortBy
				+ " sortOrder: " + sortOrder	
				);
		
		GitAdminResponse gitAdminResponse = adminService.getRepositoryAdmin(
				apiCallService.createGitConnection(hostname, subURL, topic, language, sortBy, sortOrder));
		
		if (gitAdminResponse==null) {
			return "admin/404";
		}
	
        createReportValue(model, gitAdminResponse.getReport());
		
        model.addAttribute("count", gitAdminResponse.getCount());
        model.addAttribute("repoListContainer", gitAdminResponse.getRepoList());
        
        Page<GitRepo> repoPage = new PageService().findPaginated(PageRequest.of(Integer.parseInt(page)-1, 10), gitAdminResponse.getRepoList());
        
        log.info("Total Repo: " + repoPage.getSize());
        log.info("Total Page: " + repoPage.getTotalPages());
         
        model.addAttribute("repoPage", repoPage);
        
        int totalPages = repoPage.getTotalPages();
        
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/index";

      
    }
    
    
//  @RequestMapping( value = "/repository/search/owner/{repoOwnerId}", method = RequestMethod.GET)
//  public String doViewOwner(@PathVariable("repoOwnerId") int repoOwnerId){
//      return "admin/repo_owner";
//  }
  
  
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
