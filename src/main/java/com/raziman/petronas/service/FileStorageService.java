package com.raziman.petronas.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.raziman.petronas.exception.FileStorageException;
import com.raziman.petronas.exception.MyFileNotFoundException;
import com.raziman.petronas.model.GitRepo;
import com.raziman.petronas.properties.FileStorageProperties;

@Component
@Service
public class FileStorageService {

	private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
	
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            
            log.info("Downloading path: " + filePath);
            
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
	public void generateReportFile(List<GitRepo> repoObjList) {
		
		try {
			try (FileWriter file = new FileWriter(new File(fileStorageLocation.toString(), "admin-report.txt"))) {

				String reportHeader = String.format(
						"%-5s %-10s %-50s %-15s %-80s %-10s %n %n", 
						"No", "Id", "Name","Language", "URL", "Owner Id");

				file.write(reportHeader);

				for (int x = 0; x < repoObjList.size(); x++) {

					String reportOutput = String.format("%-5d %-10s %-50s %-15s %-80s %-10s %n",
							x+1,
							repoObjList.get(x).getRepoId(), 
							repoObjList.get(x).getRepoName(),
							repoObjList.get(x).getRepoLanguage(),
							repoObjList.get(x).getRepoURL(),
							repoObjList.get(x).getRepoOwnerId()
							);

					file.write(reportOutput);

				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
    
}