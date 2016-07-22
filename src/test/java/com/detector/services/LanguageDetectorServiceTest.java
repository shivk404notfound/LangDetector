package com.detector.services;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import com.detector.AbstractTest;

public class LanguageDetectorServiceTest extends AbstractTest {
	
	@Autowired
	LanguageDetectorService langDetector;
	
	@Test
	public void testDetectLanguage() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "The good guy the the guy".getBytes());
	    Assert.assertNotNull(langDetector.detectLanguage(firstFile));
	}
	
	@Test
	public void testIsEnglish() throws Exception {
		String distinctWords[] = {"the","is","are"};
	    Assert.assertTrue(langDetector.isEnglish(Arrays.asList(distinctWords))); 
	}
	
	@Test
	public void testIsEnglishNonEnglishWord() throws Exception {
		String distinctWords[] = {"namste","mai","hu"};
	    Assert.assertFalse((langDetector.isEnglish(Arrays.asList(distinctWords)))); 
	}
	
	@Test
	public void testIsIrish() throws Exception {
		String distinctWords[] = {"siad","bi","ta"};
	    Assert.assertTrue((langDetector.isIrish(Arrays.asList(distinctWords)))); 
	}
	
	@Test
	public void testIsIrishNonirishWord() throws Exception {
		String distinctWords[] = {"hello","how","are"};
	    Assert.assertFalse((langDetector.isIrish(Arrays.asList(distinctWords)))); 
	}
	
	@Test
	public void getLinesFromFile() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "The good guy the the guy".getBytes());
	    Assert.assertEquals(3,langDetector.getDistinctWordsFromFile((firstFile)).size());
	}
	  
}
