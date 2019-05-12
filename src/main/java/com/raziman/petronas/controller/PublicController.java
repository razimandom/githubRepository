package com.raziman.petronas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raziman.petronas.model.GitForm;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.response.GitPublicResponse;
import com.raziman.petronas.service.ApiCallService;
import com.raziman.petronas.service.PageService;
import com.raziman.petronas.service.PublicService;

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
    
    @GetMapping("/doc")
    public String viewRepositoryDoc() throws Exception{
        return "repository/doc";
    }
    
	@GetMapping({""})
    public String viewRepository(ModelMap model) {
		model.addAttribute("gitForm", new GitForm());
        return "repository/index";
    }
	
	@RequestMapping({"/list"})
    public String viewRepository(@ModelAttribute GitForm gitForm, 
    		@RequestParam("topic") String topic,
    		@RequestParam("language") String language,
    		@RequestParam("page") String page,
    		ModelMap model) throws Exception {
    	
		PublicService repoService = new PublicService();
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
			
		GitPublicResponse gitPublicResponse = repoService.getRepository(
				apiCallService.createGitConnection(hostname, subURL, topic, language, sortBy, sortOrder));

		if (gitPublicResponse==null) {
			return "repository/404";
		}
		
        model.addAttribute("count", gitPublicResponse.getCount());
        model.addAttribute("repoListContainer", gitPublicResponse.getRepoList());
        
        Page<GitRepo> repoPage = new PageService().findPaginated(PageRequest.of(Integer.parseInt(page)-1, 10), gitPublicResponse.getRepoList());
        
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
	
        return "repository/index";
    }
    

}
