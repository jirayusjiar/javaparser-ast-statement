package javaast.parser.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.lang.Character;
import javaast.parser.support.DirExplorer;
import javaast.parser.support.NodeIterator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EmptyMemberDeclaration;
import com.github.javaparser.ast.body.EmptyTypeDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.google.common.base.Strings;

public class CSVforTestProj {
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
   public static void statementsByLine(File projectDir) {
	   Map<String, Integer> TokenKey = new HashMap();
	   int[] count = {1,1};
	   PrintWriter writer;
	   String line ="";
	   try(BufferedReader br = new BufferedReader(new FileReader("/home/poompoowit/workspace/javaparser-ast-statement/parser/Map.csv")))
	   {
		   
		   while((line = br.readLine()) != null)
		   {
			   String[] country = line.split(",");
			   if(isInteger(country[1]))
			   {
				   TokenKey.put(country[0], Integer.parseInt(country[1]));
				   if(count[0]<Integer.parseInt(country[1]))
				   {
					   count[0]=Integer.parseInt(country[1]);
				   }
			   }
		   }
		   
	   }catch(IOException e)
	   {
		   e.printStackTrace();
	   }
		try {
			
			writer = new PrintWriter("Test.csv", "UTF-8");
			writer.println("\"FileName\",\"TokenVector\"");
	  new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
			path, file) -> {
		 try {
			 PriorityQueue<Entry> q = new PriorityQueue<>();
			 Entry A;
			 String charArray = path;
			 System.out.println(charArray);
			 charArray = charArray.substring(1,charArray.length());
			 writer.print("\""+charArray+"\",\"");
			new NodeIterator(new NodeIterator.NodeHandler() {
			   @Override
			   public boolean handle(Node node) {
				  return check(node,writer,q,TokenKey,count);
			   }
			}).explore(JavaParser.parse(file));
			new VoidVisitorAdapter<Object>() {
				   @Override
				   public void visit(MethodCallExpr n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine() + ":" +n.getName());
					  QandM_Handle(n.getBeginLine() + ":" +n.getName(),q,TokenKey,count);
				   }
				   @Override
				   public void visit(MethodDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " MethodDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " MethodDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(AnnotationDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " AnnotationDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " AnnotationDeclaration" ,q,TokenKey,count);
				   }
				   @Override
				   public void visit(AnnotationMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " AnnotationMemberDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " AnnotationMemberDeclaration" ,q,TokenKey,count);
				   }
				   @Override
				   public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " ClassOrInterfaceDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " ClassOrInterfaceDeclaration" ,q,TokenKey,count);
				   }
				   @Override
				   public void visit(ConstructorDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " ConstructorDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " ConstructorDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(EmptyMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.toString() + " EmptyMemberDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.toString() + " EmptyMemberDeclaration" ,q,TokenKey,count);
				   }
				   @Override
				   public void visit(EnumDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " EnumDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " EnumDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(EmptyTypeDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " EmptyTypeDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " EmptyTypeDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(EnumConstantDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getName() + " EnumConstantDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getName() + " EnumConstantDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(FieldDeclaration n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getType() + " FieldDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.getType() + " FieldDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(InitializerDeclaration n, Object arg) {
					  super.visit(n, arg);
					  check(n.getBlock(), writer, q, TokenKey, count);
					  //writer.println(n.getBeginLine()+":" + n.toString().replaceAll("\n", "") + " InitializerDeclaration" );
					  QandM_Handle(n.getBeginLine()+":" + n.toString().replaceAll("\n", "") + " InitializerDeclaration",q,TokenKey,count);
				   }
				   @Override
				   public void visit(VariableDeclarator n, Object arg) {
					  super.visit(n, arg);
					  //writer.println(n.getBeginLine()+":" + n.getId() + " VariableDeclarator" );
					  QandM_Handle(n.getBeginLine()+":" + n.getId() + " VariableDeclarator",q,TokenKey,count);
				   }
				}.visit(JavaParser.parse(file), null);
				A = q.poll();
				writer.print(TokenKey.get((A).getKey()));
				while(!q.isEmpty())
				{
					A = q.poll();
					writer.print(","+TokenKey.get((A).getKey()));
				}
				writer.println("\"");
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	  }).explore(projectDir);
	  writer.close();
	  PrintWriter writer_two = new PrintWriter("Map_Test.csv", "UTF-8");
	  writer_two.println("\"Key\",\"Token\"");
	  for (String name: TokenKey.keySet()){

          String key =name.toString();
          String value = TokenKey.get(name).toString();  
          writer_two.println("\""+key+"\",\""+value+"\"");
	  }
	  writer_two.close();
   } catch (FileNotFoundException | UnsupportedEncodingException e1) {
		e1.printStackTrace();
	}
   }
   
   public static boolean check(Node node,PrintWriter writer,PriorityQueue<Entry> q,Map<String, Integer> TokenKey,int[] count) { //Check statement Type //filter out statement that don,t use in this work
	  if (node instanceof IfStmt) {
		 //writer.println(node.getBeginLine() + ":" + "if statement");
		 QandM_Handle(node.getBeginLine() + ":" + "if statement",q,TokenKey,count);
		 check(((IfStmt) node).getThenStmt(),writer,q,TokenKey,count);
		 check(((IfStmt) node).getElseStmt(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof BreakStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Break statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Break statement",q,TokenKey,count);
		 return false;
	  } else if (node instanceof ContinueStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Continue statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Continue statement",q,TokenKey,count);
		 return false;
	  } else if (node instanceof DoStmt) {
		 //writer.println(node.getBeginLine() + ":" + "Do statement");
		 QandM_Handle(node.getBeginLine() + ":" + "Do statement",q,TokenKey,count);
		 check(((DoStmt) node).getBody(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof ForeachStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Foreach statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Foreach statement",q,TokenKey,count);
		 check(((ForeachStmt) node).getBody(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof ForStmt) {
		 //writer.println(node.getBeginLine() + ":" + "For statement");
		 QandM_Handle(node.getBeginLine() + ":" + "For statement",q,TokenKey,count);
		 check(((ForStmt) node).getBody(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof ReturnStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Return statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Return statement",q,TokenKey,count);
		 return false;
	  } else if (node instanceof SwitchEntryStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " SwitchEntry statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " SwitchEntry statement",q,TokenKey,count);
		 for (Node childNode : ((SwitchEntryStmt) node).getStmts()) {
				check(childNode,writer,q,TokenKey,count);
			 }
		 return false;
	  } else if (node instanceof SwitchStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Switch statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Switch statement",q,TokenKey,count);
		 for (Node childNode : ((SwitchStmt) node).getEntries()) {
				check(childNode,writer,q,TokenKey,count);
			 }
		 return false;
	  } else if (node instanceof ThrowStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " Throw statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " Throw statement",q,TokenKey,count);
		 return false;
	  } else if (node instanceof TryStmt) {
		 //writer.println(node.getBeginLine() + ":" + "Try statement");
		 QandM_Handle(node.getBeginLine() + ":" + "Try statement",q,TokenKey,count);
		 check(((TryStmt) node).getTryBlock(),writer,q,TokenKey,count);
		 for (CatchClause childNode : ((TryStmt) node).getCatchs()) {
			 check(childNode.getCatchBlock(),writer,q,TokenKey,count);
			 }
		 check(((TryStmt) node).getFinallyBlock(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof WhileStmt) {
		 //writer.println(node.getBeginLine() + ":"+ " While statement");
		 QandM_Handle(node.getBeginLine() + ":"+ " While statement",q,TokenKey,count);
		 check(((WhileStmt) node).getBody(),writer,q,TokenKey,count);
		 return false;
	  } else if (node instanceof BlockStmt) {
		 for (Node childNode : node.getChildrenNodes()) {
			check(childNode,writer,q,TokenKey,count);
		 }// TODO Get all children nodes within if else for while blah2
		 return false;
	  } else
		 return true;
   }
   
   public static void QandM_Handle(String line,PriorityQueue<Entry> q,Map<String, Integer> TokenKey,int[] count)
   {
	   String[] array = line.split(":", -1);
       if(!TokenKey.containsKey(array[1]))
       {
       	TokenKey.put(array[1], count[0]);
       	count[0]++;
       }
       q.add(new Entry(array[1],Integer.parseInt(array[0])));
   }
   

   public static void main(String[] args) {
	  File projectDir = new File("Test_Proj");
	  statementsByLine(projectDir);
   }
}