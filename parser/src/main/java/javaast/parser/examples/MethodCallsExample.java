// 2

package javaast.parser.examples;
import java.io.File;
import java.io.IOException;

import javaast.parser.support.DirExplorer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;


public class MethodCallsExample {

   public static void listMethodCalls(File projectDir) {
	  new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
			path, file) -> {
		 System.out.println(path);
		 System.out.println(Strings.repeat("=", path.length()));
		 try {
			new VoidVisitorAdapter<Object>() {
			   @Override
			   public void visit(MethodCallExpr n, Object arg) {
				  super.visit(n, arg);
				  System.out.println(" [L " + n.getBeginLine() + "] " + n);
			   }
			}.visit(JavaParser.parse(file), null);
			System.out.println(); // empty line
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	  }).explore(projectDir);
   }

   public static void main(String[] args) {
	  File projectDir = new File("data");
	  listMethodCalls(projectDir);
   }
}
