package javaast.parser.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import javaast.parser.support.DirExplorer;

public class tokenVectorCreater {
	
	public static void TokenCreater(File projectDir) {
		  new DirExplorer((level, path, file) -> path.endsWith("_AstList.txt"), (level,
				path, file) -> {
			 try {
				 String charArray = file.getName();
				 String line = null;
				 System.out.println(charArray);
				 charArray = charArray.substring(0,charArray.length()-4);
				PrintWriter writer = new PrintWriter(charArray+"_TokenVector.txt", "UTF-8");
				
				FileReader fileReader = new FileReader("."+path);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
					
	                System.out.println(line);
	                
	            }
				
				System.out.println(); // empty line
			 } catch (IOException e) {
				new RuntimeException(e);
			 }
		  }).explore(projectDir);
	   }
	
	public static void main(String[] args) {
		  File AstList = new File(".");
		  TokenCreater(AstList);
	   }
}