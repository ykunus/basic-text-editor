/**
* This is the main class that runs the text editor.
* You can run it in three ways: Empty, with a line of text, and with multiple lines of text.
* Known Bugs: Usually the first time I try running the GUI the cursor does not show 
*
* @author Yunus Kocaman
* yunuskocaman@brandeis.edu
* Feb,28, 2024
* COSI 21A PA1
*/
package main;

public class EditorMain {

	public static void main(String[] args) {

		try {
			// Uncomment the line below to open the editor with no input file
//			new EditorDisplay();

			// Uncomment the line below to open the editor with an input file that has a
			// single line of text
//			new EditorDisplay("single_line_file.txt");

			// Uncomment the line below to open the editor with an input file that has
			// multiple lines of text
			new EditorDisplay("multi_line_file.txt");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
