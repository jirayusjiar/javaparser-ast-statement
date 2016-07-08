package javaast.parser.examples;

import java.io.File;
import java.io.IOException;

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

public class Full_Parser {

   public static void statementsByLine(File projectDir) {
	  new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
			path, file) -> {
		 System.out.println(path.toCharArray());
		 System.out.println(Strings.repeat("=", path.length()));
		 try {
			new NodeIterator(new NodeIterator.NodeHandler() {
			   @Override
			   public boolean handle(Node node) {
				  return check(node);
			   }
			}).explore(JavaParser.parse(file));
			new VoidVisitorAdapter<Object>() {
				   @Override
				   public void visit(MethodCallExpr n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("[" + n.getBeginLine() + "] " + n.getName());
				   }
				   @Override
				   public void visit(MethodDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " MethodDeclaration" );
				   }
				   @Override
				   public void visit(AnnotationDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " AnnotationDeclaration" );
				   }
				   @Override
				   public void visit(AnnotationMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " AnnotationMemberDeclaration" );
				   }
				   /*@Override
				   public void visit(BodyDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.toString() + " BodyDeclaration" );
				   }*/
				   @Override
				   public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " ClassOrInterfaceDeclaration" );
				   }
				   @Override
				   public void visit(ConstructorDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " ConstructorDeclaration" );
				   }
				   @Override
				   public void visit(EmptyMemberDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.toString() + " EmptyMemberDeclaration" );
				   }
				   @Override
				   public void visit(EnumDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " EnumDeclaration" );
				   }
				   @Override
				   public void visit(EmptyTypeDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " EmptyTypeDeclaration" );
				   }
				   @Override
				   public void visit(EnumConstantDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " EnumConstantDeclaration" );
				   }
				   @Override
				   public void visit(FieldDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getType() + " FieldDeclaration" );
				   }
				   @Override
				   public void visit(InitializerDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.toString() + " InitializerDeclaration" );
				   }
				   /*
				   @Override
				   public void visit(TypeDeclaration n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getName() + " TypeDeclaration" );
				   }*/
				   @Override
				   public void visit(VariableDeclarator n, Object arg) {
					  super.visit(n, arg);
					  System.out.println("["+n.getBeginLine()+"]" + n.getId() + " VariableDeclarator" );
				   }
				}.visit(JavaParser.parse(file), null);
			System.out.println(); // empty line
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	  }).explore(projectDir);
   }
   
   public static boolean check(Node node) { //Check statement Type //filter out statement that don,t use in this work
	  if (node instanceof IfStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]" + " if statement");
		 check(((IfStmt) node).getThenStmt());
		 check(((IfStmt) node).getElseStmt());
		 return false;
	  } /*else if (node instanceof AssertStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Assert statement");
		 return false;
	  }*/ else if (node instanceof BreakStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Break statement");
		 return false;
	  } else if (node instanceof ContinueStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Continue statement");
		 return false;
	  } else if (node instanceof DoStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]" + " Do statement");
		 check(((DoStmt) node).getBody());
		 return false;
	  } /*else if (node instanceof EmptyStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Empty statement");
		 return false;
	  } else if (node instanceof ExplicitConstructorInvocationStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " ExplicitConstructorInvocation statement" + " "+ ((ExplicitConstructorInvocationStmt) node).getExpr());
		 return false;
	  } else if (node instanceof ExpressionStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Expression statement" + " " +((ExpressionStmt) node).getExpression().getClass());
		 return false;
	  }*/ else if (node instanceof ForeachStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Foreach statement");
		 check(((ForeachStmt) node).getBody());
		 return false;
	  } else if (node instanceof ForStmt) {
		 System.out
			   .println(" [" + node.getBeginLine() + "]" + " For statement");
		 check(((ForStmt) node).getBody());
		 return false;
	  } /*else if (node instanceof LabeledStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Labeled statement");
		 check(((LabeledStmt) node).getStmt());
		 return false;
	  }*/ else if (node instanceof ReturnStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Return statement");
		 return false;
	  } else if (node instanceof SwitchEntryStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " SwitchEntry statement");
		 for (Node childNode : ((SwitchEntryStmt) node).getStmts()) {
				check(childNode);
			 }
		 return false;
	  } else if (node instanceof SwitchStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Switch statement");
		 for (Node childNode : ((SwitchStmt) node).getEntries()) {
				check(childNode);
			 }
		 return false;
	  } /*else if (node instanceof SynchronizedStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Synchronized statement");
		 check(((SynchronizedStmt) node).getBlock());
		 return false;
	  }*/ else if (node instanceof ThrowStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " Throw statement");
		 return false;
	  } else if (node instanceof TryStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]" + " Try statement");
		 check(((TryStmt) node).getTryBlock());
		 for (CatchClause childNode : ((TryStmt) node).getCatchs()) {
			 check(childNode.getCatchBlock());
			 }
		 check(((TryStmt) node).getFinallyBlock());
		 return false;
	  } /*else if (node instanceof TypeDeclarationStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " TypeDeclaration statement"+((TypeDeclarationStmt) node).getTypeDeclaration());
		 return false;
	  }*/ else if (node instanceof WhileStmt) {
		 System.out.println(" [" + node.getBeginLine() + "]"
			   + " While statement");
		 check(((WhileStmt) node).getBody());
		 return false;
	  } else if (node instanceof BlockStmt) {
		 for (Node childNode : node.getChildrenNodes()) {
			check(childNode);
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
