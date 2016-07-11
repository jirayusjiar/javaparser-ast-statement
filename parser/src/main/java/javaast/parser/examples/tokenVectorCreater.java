package javaast.parser.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import javaast.parser.support.DirExplorer;

public class tokenVectorCreater {
	
	public static void TokenCreater(File projectDir) {
		  new DirExplorer((level, path, file) -> path.endsWith("_AstList.txt"), (level,
				path, file) -> {
			 try {
				 PriorityQueue<Entry> q = new PriorityQueue<>();
				 Entry A;
				 String charArray = file.getName();
				 String line = null;
				 System.out.println(charArray);
				 int count = 1;
				 Map<String, Integer> TokenKey = new HashMap();
				 charArray = charArray.substring(0,charArray.length()-4);
				PrintWriter writer = new PrintWriter(charArray+"_TokenVector.txt", "UTF-8");
				
				FileReader fileReader = new FileReader("."+path);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
					String[] array = line.split(":", -1);
	                if(!TokenKey.containsKey(array[1]))
	                {
	                	TokenKey.put(array[1], count);
	                	count++;
	                }
	                q.add(new Entry(array[1],Integer.parseInt(array[0])));
	            }
				while(!q.isEmpty())
				{
					A = q.poll();
					writer.println(TokenKey.get((A).getKey()));
				}
				writer.close();
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