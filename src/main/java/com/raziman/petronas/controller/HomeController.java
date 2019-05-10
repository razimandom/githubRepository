package com.raziman.petronas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.raziman.petronas.logic.RepoListContainer;
import com.raziman.petronas.logic.RepoLogic;
import com.raziman.petronas.model.GitRepoModel;

//import com.raziman.petronas.logic.RepoLogic;

@Controller
public class HomeController {
	
//	@Autowired
//	private GitRepoModel gitRepoModel;
	
    @GetMapping("/admin")
    public String viewAdmin(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "admin";
    }
    
    @RequestMapping("/repository")
    public String viewRepository(Model model) throws Exception{
        return "repository/main";
        
    }

    @RequestMapping( value = "/repository/search/topic/{topic}/language/{language}", method = RequestMethod.GET)
    public String doViewRepotAll(@PathVariable("topic") String topic, @PathVariable("language") String language, ModelMap model) throws Exception{
		
		RepoLogic repoLogic = new RepoLogic();
		
		List<GitRepoModel> repoList = repoLogic.getRepository(topic, language);

		System.out.println(topic);
		System.out.println(language);
		
        model.addAttribute("topic", topic);
        model.addAttribute("language", language);
        model.addAttribute("count", repoLogic.getCount(topic, language));
        model.addAttribute("repoListContainer", repoList);

        return "repository/view";
    }

//    @RequestMapping( value = "/repository/view", method = RequestMethod.GET)
//    public String viewRepositoryBrowse(Model model) throws Exception{
//    	
//		String topic = "Signature";
//		String language = "Java";
//		
//		RepoLogic repo = new RepoLogic();
//		
//		List<GitRepoModel> repoObjList = repo.getRepository(topic, language);
//		
////		repo.viewRepo(topic, language);
//
//        model.addAttribute("name", repoObjList.get(0).getRepoName());
//
//        return "repository/view";
//        
//    }
	
	
    @RequestMapping( value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView doViewRepo(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("view");
//        mv.addObject("lists", docRepo.findOne(id));
        return mv;
    }
    
    @RequestMapping( value = "/view/owner/{id}", method = RequestMethod.GET)
    public ModelAndView doViewOwner(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("view");
//        mv.addObject("lists", docRepo.findOne(id));
        return mv;
    }
    
    
    
    
    
	// inject via application.properties
//	@Value("${welcome.message:test}")
//	private String message = "Hello World";

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String showLoginPage(ModelMap model) {
//        model.put("name", "in28Minutes");
//        return "welcome";
//    }

    
    
}
