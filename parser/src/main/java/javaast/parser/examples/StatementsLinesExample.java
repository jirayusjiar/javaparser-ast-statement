// 3

package javaast.parser.examples;

import java.io.File;
import java.io.IOException;

import javaast.parser.support.DirExplorer;
import javaast.parser.support.NodeIterator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import com.google.common.base.Strings;

public class StatementsLinesExample {

   public static void statementsByLine(File projectDir) {
	  new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
			path, file) -> {
		 System.out.println(path);
		 System.out.println(Strings.repeat("=", path.length()));
		 try {
			new NodeIterator(new NodeIterator.NodeHandler() {
			   @Override
			   public boolean handle(Node node) {
				  if (node instanceof IfStmt) {
					 System.out.println(" [" + node.getBeginLine() + "]"
						   + " if statement");
					 return false;
				  } // TODO else if for, while

				  else {
					 return true;
				  }
			   }
			}).explore(JavaParser.parse(file));
			System.out.println(); // empty line
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	  }).explore(projectDir);
   }

   public static void main(String[] args) {
	  File projectDir = new File("data");
	  statementsByLine(projectDir);
   }
}
