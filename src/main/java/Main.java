
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

public class Main {
	
	
	public static void main(String args[]) {
		
			
		ClassOrInterfaceDeclaration ci = parse("src/main/java/MyTest.java");	
		ClassOrInterfaceDeclaration ci2 = parse("src/main/java/MyTest2.java");
		generate(ci, ci2);	
	}
	
	public static ClassOrInterfaceDeclaration parse(String fileName)  {
		
//		String filePath = Main.class.getResource("/MyTest.class").getPath();
//		System.out.println(filePath);
		
		File file = new File(fileName);
		
		System.out.println("Processing " + file);
	    ParseResult<CompilationUnit> ret = null;
		try {
			JavaParser javaParser = new JavaParser();
			ret = javaParser.parse(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		CompilationUnit cu = ret.getResult().get();
		
	    List<Node> nodes = cu.getChildNodes();
	    
	    for(Node node: nodes) {
	    	
	    	if(node instanceof ClassOrInterfaceDeclaration) {
	    		return (ClassOrInterfaceDeclaration) node;	
	    		
//	    		List<Node> subNodes = node.getChildNodes();
//	    		
//	    		for(Node node2: subNodes) {
//	    			
//	    			System.out.println(node2.getClass());
//	    			System.out.println(node2);
//	    		
//	    		}
	    		
	    	}
	    }
//		    			    	
//		    	ClassOrInterfaceDeclaration myTest = (ClassOrInterfaceDeclaration)node;
//		    	myTest.addConstructor(Modifier.Keyword.PUBLIC)
//		        .addParameter("String", "title")
//		        .addParameter("Person", "author")
//		        .setBody(new BlockStmt()
//		                .addStatement(new ExpressionStmt(new AssignExpr(
//		                        new FieldAccessExpr(new ThisExpr(), "title"),
//		                        new NameExpr("title"),
//		                        AssignExpr.Operator.ASSIGN)))
//		                .addStatement(new ExpressionStmt(new AssignExpr(
//		                        new FieldAccessExpr(new ThisExpr(), "author"),
//		                        new NameExpr("author"),
//		                        AssignExpr.Operator.ASSIGN))));
//	    	}
//	    }
//	    
//	    System.out.println(cu);
	    
		return null;
	}
	
	public static void generate(ClassOrInterfaceDeclaration ci, ClassOrInterfaceDeclaration ci2) {
		
		
		
		Map<String, MethodDeclaration> methods = new HashMap<>();
		
		// 从ci中获取出method，放进map中
		List<Node> nodes = ci.getChildNodes();
	    for(Node node: nodes) {
	    	if(node instanceof MethodDeclaration) {
	    		
	    		MethodDeclaration md = (MethodDeclaration)node;
	    		String key = md.getDeclarationAsString(true, true, true);
	    		methods.put(key , md);
	    		
	    	}
	    }
	    
	    // 从ci2中获取出method，放进map中，会覆盖之前相同sig的method
	    nodes = ci2.getChildNodes();
	    for(Node node: nodes) {
	    	if(node instanceof MethodDeclaration) {
	    		MethodDeclaration md = (MethodDeclaration)node;
	    		String key = md.getDeclarationAsString(true, true, true);
	    		methods.put(key , md);
	    			    		
	    	}
	    }
	    
	    
//	    String decl = md.getDeclarationAsString(true, true, true);
//	    System.out.println(decl);
	    
		
		CompilationUnit cu = new CompilationUnit();
		 
		cu.setPackageDeclaration("jpexample.model");
		 
		ClassOrInterfaceDeclaration book = cu.addClass("Book");
		book.addField("String", "title");
		book.addField("Person", "author");
		 
		book.addConstructor(Modifier.Keyword.PUBLIC)
		        .addParameter("String", "title")
		        .addParameter("Person", "author")
		        .setBody(new BlockStmt()
		                .addStatement(new ExpressionStmt(new AssignExpr(
		                        new FieldAccessExpr(new ThisExpr(), "title"),
		                        new NameExpr("title"),
		                        AssignExpr.Operator.ASSIGN)))
		                .addStatement(new ExpressionStmt(new AssignExpr(
		                        new FieldAccessExpr(new ThisExpr(), "author"),
		                        new NameExpr("author"),
		                        AssignExpr.Operator.ASSIGN))));
		 
		book.addMethod("getTitle", Modifier.Keyword.PUBLIC).setBody(
		        new BlockStmt().addStatement(new ReturnStmt(new NameExpr("title"))));
		 
		book.addMethod("getAuthor", Modifier.Keyword.PUBLIC).setBody(
		        new BlockStmt().addStatement(new ReturnStmt(new NameExpr("author"))));
				
		
		for(MethodDeclaration md: methods.values()) {
			book.addMember(md);
		}
		 
		System.out.println(cu.toString());
	}
	
}














