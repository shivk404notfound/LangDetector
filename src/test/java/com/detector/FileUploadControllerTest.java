package com.detector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileSystemUtils;

import com.detector.services.LanguageDetectorService;

public class FileUploadControllerTest extends AbstractTest {
	@Mock
    ResourceLoader loader;
	
	@Mock 
	LanguageDetectorService langservice;
    private MockMvc mockMvc;
    
    @InjectMocks 
    private FileUploadController uploadController; 
    
    MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "the good guy".getBytes());
    
	  
	@Before
    public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
	     Mockito.when(this.langservice.detectLanguage(firstFile)).thenReturn("English");
	     mockMvc = MockMvcBuilders.standaloneSetup(uploadController).build();  
	}
	
	@After
    public final void after() throws IOException {
		 FileSystemUtils.deleteRecursively(new File(FileUploadController.ROOT));
		 Files.createDirectory(Paths.get(FileUploadController.ROOT));
	}

	@Test
	public void testHandleFileUpload() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.fileUpload("/")
	                      .file(firstFile)) 
	                      .andExpect(flash().attributeExists("message"))
	                      .andExpect(flash().attribute("message", ("Request successfully processed, Detected Language English!")))
	                      .andExpect(flash().attributeCount(1));
	                        
	}
	
	@Test
	public void testProvideUploadInfo() throws Exception {
		mockMvc.perform(get("/")) 
                .andExpect(status().isOk())
                .andExpect(view().name("uploadForm"))
                .andExpect(model().attributeExists("files"));
		
	                        
	}
	
	@Test
	public void testGetFile() throws Exception {
		mockMvc.perform(get("/filename")) 
                .andExpect(status().isOk())
                .andExpect(content().bytes("".getBytes()));            
	}
	
	
}
