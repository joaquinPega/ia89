package test.ia89.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class FirstTest {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.println("public class Hello {");
		out.println("  public static void main(String args[]) {");
		out.println("    System.out.println(\"This is in another java file\");");
		out.println("  }");
		out.println("}");
		out.close();
		JavaFileObject file = new JavaSourceFromString("Hello",writer.toString());

		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
		List<String> options = new ArrayList<String>();
		options.add("-d");
		options.add( "target/classes/test/ia89/tests");
		CompilationTask task = compiler.getTask(null, null, diagnostics, options,
				null, compilationUnits);

		boolean success = task.call();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
				.getDiagnostics()) {
			System.out.println(diagnostic.getCode());
			System.out.println(diagnostic.getKind());
			System.out.println(diagnostic.getPosition());
			System.out.println(diagnostic.getStartPosition());
			System.out.println(diagnostic.getEndPosition());
			System.out.println(diagnostic.getSource());
			System.out.println(diagnostic.getMessage(null));

		}
		System.out.println("Success: " + success);
		if (success) {
			Runtime.getRuntime().exec("java test.ia89.tests.Hello");
			/*try {
				Class<?> noparams[] = {};
				Class<?> c = Class.forName("test.ia89.tests.Hello");
//				Object obj = c.newInstance();
//				Method m = c.getDeclaredMethod("clickme", noparams);
//				m.invoke(obj, null);
			} catch (ClassNotFoundException e) {
				System.err.println("Class not found: " + e);
			} */
		}
	}

	public void clickme() {
		System.out.println( "hola");;
	}
}
