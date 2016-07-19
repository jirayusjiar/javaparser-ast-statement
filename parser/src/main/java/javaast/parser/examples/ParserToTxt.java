package javaast.parser.examples;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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

public class ParserToTxt {

   public static void statementsByLine(File projectDir) {
	  new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
			path, file) -> {
		 try {
			 String charArray = file.getName();
			 System.out.println(charArray);
			 charArray = charArray.substring(0,charArray.length()-5);
			PrintWriter writer = new PrintWriter(charArray+"_AstList.txt", "UTF-8");
			 //writer.println(path);
			 //writer.println(Strings.repeat("=", path.length()));
			new NodeIterator(new NodeIterator.NodeHandler() {
			   @Override
			   public boolean handle(Node node) {
				  return check(node,writer);
			   }
			}).explore(JavaParser.parse(file));
			new VoidVisitorAdapter<Object>() {
				   @Override
				   public void visit(MethodCallExpr n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine() + ":" +n.getName());
				   }
				   @Override
				   public void visit(MethodDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " MethodDeclaration" );
				   }
				   @Override
				   public void visit(AnnotationDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " AnnotationDeclaration" );
				   }
				   @Override
				   public void visit(AnnotationMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " AnnotationMemberDeclaration" );
				   }
				   /*@Override
				   public void visit(BodyDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println("["+n.getBeginLine()+"]" + n.toString() + " BodyDeclaration" );
				   }*/
				   @Override
				   public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " ClassOrInterfaceDeclaration" );
				   }
				   @Override
				   public void visit(ConstructorDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " ConstructorDeclaration" );
				   }
				   @Override
				   public void visit(EmptyMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.toString() + " EmptyMemberDeclaration" );
				   }
				   @Override
				   public void visit(EnumDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " EnumDeclaration" );
				   }
				   @Override
				   public void visit(EmptyTypeDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " EmptyTypeDeclaration" );
				   }
				   @Override
				   public void visit(EnumConstantDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getName() + " EnumConstantDeclaration" );
				   }
				   @Override
				   public void visit(FieldDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getType() + " FieldDeclaration" );
				   }
				   @Override
				   public void visit(InitializerDeclaration n, Object arg) {
					  super.visit(n, arg);
					  check(n.getBlock(), writer);
					  writer.println(n.getBeginLine()+":" + n.toString().replaceAll("\n", "") + " InitializerDeclaration" );
				   }
				   /*
				   @Override
				   public void visit(TypeDeclaration n, Object arg) {
					  super.visit(n, arg);
					  writer.println("["+n.getBeginLine()+"]" + n.getName() + " TypeDeclaration" );
				   }*/
				   @Override
				   public void visit(VariableDeclarator n, Object arg) {
					  super.visit(n, arg);
					  writer.println(n.getBeginLine()+":" + n.getId() + " VariableDeclarator" );
				   }
				}.visit(JavaParser.parse(file), null);
			//writer.println(); // empty line
			writer.close();
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	  }).explore(projectDir);
   }
   
   public static boolean check(Node node,PrintWriter writer) { //Check statement Type //filter out statement that don,t use in this work
	  if (node instanceof IfStmt) {
		 writer.println(node.getBeginLine() + ":" + "if statement");
		 check(((IfStmt) node).getThenStmt(),writer);
		 check(((IfStmt) node).getElseStmt(),writer);
		 return false;
	  } /*else if (node instanceof AssertStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " Assert statement");
		 return false;
	  }*/ else if (node instanceof BreakStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Break statement");
		 return false;
	  } else if (node instanceof ContinueStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Continue statement");
		 return false;
	  } else if (node instanceof DoStmt) {
		 writer.println(node.getBeginLine() + ":" + "Do statement");
		 check(((DoStmt) node).getBody(),writer);
		 return false;
	  } /*else if (node instanceof EmptyStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " Empty statement");
		 return false;
	  } else if (node instanceof ExplicitConstructorInvocationStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " ExplicitConstructorInvocation statement" + " "+ ((ExplicitConstructorInvocationStmt) node).getExpr());
		 return false;
	  } else if (node instanceof ExpressionStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " Expression statement" + " " +((ExpressionStmt) node).getExpression().getClass());
		 return false;
	  }*/ else if (node instanceof ForeachStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Foreach statement");
		 check(((ForeachStmt) node).getBody(),writer);
		 return false;
	  } else if (node instanceof ForStmt) {
		 writer
			   .println(node.getBeginLine() + ":" + "For statement");
		 check(((ForStmt) node).getBody(),writer);
		 return false;
	  } /*else if (node instanceof LabeledStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " Labeled statement");
		 check(((LabeledStmt) node).getStmt());
		 return false;
	  }*/ else if (node instanceof ReturnStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Return statement");
		 return false;
	  } else if (node instanceof SwitchEntryStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " SwitchEntry statement");
		 for (Node childNode : ((SwitchEntryStmt) node).getStmts()) {
				check(childNode,writer);
			 }
		 return false;
	  } else if (node instanceof SwitchStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Switch statement");
		 for (Node childNode : ((SwitchStmt) node).getEntries()) {
				check(childNode,writer);
			 }
		 return false;
	  } /*else if (node instanceof SynchronizedStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " Synchronized statement");
		 check(((SynchronizedStmt) node).getBlock());
		 return false;
	  }*/ else if (node instanceof ThrowStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " Throw statement");
		 return false;
	  } else if (node instanceof TryStmt) {
		 writer.println(node.getBeginLine() + ":" + "Try statement");
		 check(((TryStmt) node).getTryBlock(),writer);
		 for (CatchClause childNode : ((TryStmt) node).getCatchs()) {
			 check(childNode.getCatchBlock(),writer);
			 }
		 check(((TryStmt) node).getFinallyBlock(),writer);
		 return false;
	  } /*else if (node instanceof TypeDeclarationStmt) {
		 writer.println(" [" + node.getBeginLine() + "]"
			   + " TypeDeclaration statement"+((TypeDeclarationStmt) node).getTypeDeclaration());
		 return false;
	  }*/ else if (node instanceof WhileStmt) {
		 writer.println(node.getBeginLine() + ":"
			   + " While statement");
		 check(((WhileStmt) node).getBody(),writer);
		 return false;
	  } else if (node instanceof BlockStmt) {
		 for (Node childNode : node.getChildrenNodes()) {
			check(childNode,writer);
		 }// TODO Get all children nodes within if else for while blah2
		 return false;
	  } else
		 return true;
   }

   public static void main(String[] args) {
	  File projectDir = new File("data");
	  statementsByLine(projectDir);
   }
}
