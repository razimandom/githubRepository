package com.raziman.petronas.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.raziman.petronas.logic.RepoLogic;

//import com.raziman.petronas.logic.RepoLogic;

@Controller
public class HomeController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String showHomePage(ModelMap model) {
		model.put("message", this.message);
		return "welcome";
	}

//    @RequestMapping( value = "/view/repo/topic/{topic}/language/{language}", method = RequestMethod.GET)
//    public ModelAndView doViewRepotAll(@PathVariable("topic") String topic, @PathVariable("language") String language) throws Exception{
//    	
////		String topic = "Signature";
////		String language = "Java";
//		
//		RepoLogic repo = new RepoLogic();
//		
//		repo.viewRepo(topic, language);
//    	
//        ModelAndView mv = new ModelAndView("view");
//
//        return mv;
//    }

    @RequestMapping( value = "/repository", method = RequestMethod.GET)
    public String doViewRepotAll(Model model) throws Exception{
    	
		String topic = "Signature";
		String language = "Java";
		
		RepoLogic repo = new RepoLogic();
		
		repo.viewRepo(topic, language);

        model.addAttribute("repository", repo);

        return "repository";
        
    }
	
	
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
}
