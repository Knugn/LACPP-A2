import java.io.PrintStream;


public class MatlabUtil {
	public static void printMatlabArray(Iterable<? extends Object> l, int elementsPerLine, PrintStream out) {
		boolean first = true;
		int eCount = 0;
		out.print("[");
		for (Object d : l) {
			if (first)
				first = false;
			else
				out.print(", ");
			if (eCount++ >= elementsPerLine)
				out.println("");
			out.print(d);
		}
		out.print("]");
	}
	
	public static void printMatlabArray2D(Iterable<? extends Iterable<? extends Object>> ll, int elementsPerLine, PrintStream out) {
		boolean first = true;
		out.println("[");
		for (Iterable<? extends Object> l : ll) {
			if (first)
				first = false;
			else
				out.println("; ");
			printMatlabArray(l, elementsPerLine, out);
		}
		out.println();
		out.println("]");
	}
}
