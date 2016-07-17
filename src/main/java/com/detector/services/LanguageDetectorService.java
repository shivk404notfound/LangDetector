package com.detector.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@Component

public class LanguageDetectorService {

    //most common English words
	public static String englishWord[] = {"Word", "the", "be", "to", "of", "and", "a ", "in", "that", "have", "I ",
			"it", "for", "not", "on", "with", "he", "as", "you", "do", "at", "Word", "this", "but", "his", "by",
			"from", "they", "we", "say", "her","she", "or", "an", "will","my", "one", "all", "would", "there","their",
			"what", "Word", "so", "up", "out", "if", "about", "who", "get", "which", "go", "me", "when", "make", "can", 
			"like", "time", "no", "just", "him", "know", "take", "Word", "people", "into", "year","your", "good", "some",
			"could", "them", "see", "other", "than", "then", "now", "look", "only", "come", "its", "over", "think", "also",
			"Word", "back", "after", "use", "two", "how", "our", "work", "first", "well", "way", "even", "new", "want", 
			"because", "any", "these", "give", "day", "most", "us"};
	
	public static String irishWord[] = {"mar", "me", "se", "bhi", "do", "ar", "ta", "le", "siad", "bheith", "ag", "amhain","ta", "seo", };

	public String detectLanguage(MultipartFile file)  {
		List<String> disinctWord = getDistinctWordsFromFile(file);
		if(isEnglish(disinctWord)) { 
			return "English"; 
		} else if(isIrish(disinctWord)) {
			return "Irish";
			
		}
		return "English";
	
	}

	public List<String> getDistinctWordsFromFile(MultipartFile file) {

		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(file.getInputStream()));
			return buffer.lines()
			.map(line -> line.split("\\s+")) // Stream<String[]> 
			.flatMap(Arrays::stream) // Stream<String> 
			.map(String::toLowerCase)
			.distinct()
			.collect(Collectors.toList());
		} catch (IOException e) {

		}
		return null;

	}
	
	public boolean isEnglish(List<String> distinctSourceStream) { 
		List<String> englishword = Arrays.asList(englishWord);
		Stream<String> loacalStream = distinctSourceStream.stream();
		return loacalStream.filter(item -> englishword.contains(item)).count() > 0; 
	}
	
	public boolean isIrish(List<String> distinctSourceStream) { 
		List<String> irishWordList = Arrays.asList(irishWord);
		Stream<String> loacalStream = distinctSourceStream.stream();
		return loacalStream.filter(item -> irishWordList.contains(item)).count() > 0; 
	}

}
