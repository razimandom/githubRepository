package com.raziman.petronas.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raziman.petronas.model.GitRepo;

@Service
public class PageService {
 
	private static final Logger log = LoggerFactory.getLogger(PageService.class);
 
    public Page<GitRepo> findPaginated(Pageable pageable, List<GitRepo> gitRepo) {
    	
    	if (gitRepo!=null) {
    		
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<GitRepo> list;
            
            log.info("pageSize: " + pageSize);
            log.info("currentPage: " + (currentPage+1));
            log.info("gitRepo.size(): " + gitRepo.size());
            
            if (gitRepo.size() < startItem) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, gitRepo.size());
                list = gitRepo.subList(startItem, toIndex);
            }
     
            Page<GitRepo> gitRepoPage
              = new PageImpl<GitRepo>(list, PageRequest.of(currentPage, pageSize), gitRepo.size());
    		
            return gitRepoPage;
            
    	}
    	
    	return null;
        
    }
}