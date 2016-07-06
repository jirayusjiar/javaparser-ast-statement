package javaast.parser;

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
        for(int x = 0;x<1;++x){
			System.out.println("Inside for statement");
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
			break;
		}
	}
}
