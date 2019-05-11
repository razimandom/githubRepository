//package com.raziman.petronas.util;
//
//import java.awt.print.Book;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//import com.raziman.petronas.model.GitRepo;
//
//public class GitRepoUtil {
//	
//    private static List<GitRepo> gitRepo = new ArrayList<GitRepo>();
//
//    private static final int NUM_REPO = 30;
//        
//    private static final int MIN_REPO_NUM = 1000;
//
//    public static List<GitRepo> buildBooks() {
//        if (gitRepo.isEmpty()) {
//            IntStream.range(0, NUM_REPO).forEach(n -> {
//            	gitRepo.add(new GitRepo(MIN_REPO_NUM + n + 1, "Spring in Action"));
//            });
//            
//        }
//
//        return gitRepo;
//    }
//
//}
