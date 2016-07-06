package javaast.parser;

import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;

import javaast.parser.support.NodeIterator;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void testBeforeMain(){
		System.out.println("Before");
		if(true){
			System.out.println("Inside if statement");
		}
	}
    public static void main( String[] args )
    {
        System.out.println( "MAIN" );
        testBeforeMain();
        testAfterMain();
        int a =10;
        char c = 22;
        double d =34;
        for(int x = 0;x<1;++x){
			System.out.println("Inside for statement");
			break;
		}
		while(true){
			System.out.println("Inside while statement");
			break;
		}
		if(true){
			System.out.println("Inside if statement");
		}
		new App();
    }
	public static void testAfterMain(){
		System.out.println("Before");
		for(int x = 0;x<1;++x){
			System.out.println("Inside for statement");
		}
		while(true){
			System.out.println("Inside while statement");
			if(true){
				while(true){
					System.out.println("Inside while statement");
					if(true){
					}
					break;
				}
			}
			break;
		}
		try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
		
		try {
			new NodeIterator(new NodeIterator.NodeHandler() {
			   @Override
			   public boolean handle(Node node) {
				  return check(node);
			   }
			}).explore(JavaParser.parse(file));
			System.out.println(); // empty line
		 } catch (ParseException | IOException e) {
			new RuntimeException(e);
		 }
	}
}
