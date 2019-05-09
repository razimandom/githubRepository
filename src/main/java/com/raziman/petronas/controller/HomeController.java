package com.raziman.petronas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
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
////        mv.addObject("lists", docRepo.findOne(id));
//        return mv;
//    }

    @RequestMapping( value = "/view/repo/topic/", method = RequestMethod.GET)
    public ModelAndView doViewRepotAll() throws Exception{
    	
		String topic = "Signature";
		String language = "Java";
		
		RepoLogic repo = new RepoLogic();
		
		repo.viewRepo(topic, language);
    	
        ModelAndView mv = new ModelAndView("view");
//        mv.addObject("lists", docRepo.findOne(id));
        return mv;
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
