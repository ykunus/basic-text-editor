/**
* The Editor class represents a simple text editor by using a doubly linked list.
* It supports operations like insertion, deletion, moving the cursor, and saving the text to a file.
* Known Bugs: Usually the first time I try running the GUI the cursor does not show 
*
* @author Yunus Kocaman
* yunuskocaman@brandeis.edu
* Feb,28, 2024
* COSI 21A PA1
*/
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Editor {
	/**
	 * KEEP THIS PUBLIC : use this to store the number of characters in your Editor
	 */
	public int numChars;
	/**
	 * KEEP THIS PUBLIC : use this to store the current cursor index in [0,
	 * numChars]
	 */
	public int curPos;
	/**
	 * KEEP THIS PUBLIC : use this to reference the node that is after the visual
	 * cursor or null if curPos = numChars
	 */
	public Node cur;
	/**
	 * KEEP THIS PUBLIC : use this to reference the first node in the Editor's
	 * doubly linked list
	 */
	public Node head;
	/**
	 * KEEP THIS PUBLIC : use this to reference the last node in the Editor's doubly
	 * linked list
	 */
	public Node tail;

	/**
	 * This constructor constructs an empty Editor that Initializes the number of
	 * characters and current cursor position to 0 and the doubly linked list
	 * references to null.
	 */
	public Editor() {
		this.numChars = 0;
		this.curPos = 0;
		this.cur = null;
		this.head = null;
		this.tail = null;
	}

	/**
	 * This constructor constructs an Editor and reads text from a file to
	 * initialize its content. The file content is read line by line and inserted
	 * into the editor. After reading, one backspace operation is performed to
	 * remove the extra newline character.
	 * 
	 * @param filepath The path of the file to read the text from.
	 * @throws FileNotFoundException If file is not found
	 */
	public Editor(String filepath) throws FileNotFoundException {
		String text = "";

		File f = new File(filepath);
		Scanner console = new Scanner(f);
		if (console.hasNext()) {
			while (console.hasNextLine()) {
				text += console.nextLine() + "\n";

			}
			for (int i = 0; i < text.length(); i++) {
				insert(text.charAt(i));
			}
		}
		this.backspace();
	}

	/**
	 * Gets the current cursor position.
	 * 
	 * @return The current cursor position.
	 */
	public int getCursorPosition() {
		return curPos;
	}

	/**
	 * Returns the number of characters in the editor.
	 * 
	 * @return The number of characters in the editor.
	 */
	public int size() {
		return numChars;
	}

	/**
	 * Moves the cursor one position to the right. If the cursor is at the end of
	 * the text nothing changes
	 */
	public void moveRight() {
		if (cur != null) {
			cur = cur.next;
			curPos++;
		}
	}

	/**
	 * Moves the cursor one position to the left. If the cursor is at the beginning
	 * of the text nothing changes.
	 */
	public void moveLeft() {
		if (head != null) {
			if (cur == null) {
				if (curPos > 0) {
					cur = tail;
					curPos--;
				}
			} else {
				if (cur.prev != null) {
					cur = cur.prev;
					curPos--;
				}

			}
		}
	}

	/**
	 * Moves the cursor to the beginning of the text.
	 */
	public void moveToHead() {
		cur = head;
		curPos = 0;
	}

	/**
	 * Moves the cursor to the end of the text.
	 */
	public void moveToTail() {
		cur = null;
		curPos = numChars;
	}

	/**
	 * Inserts a character at the current cursor position.
	 * 
	 * @param c The character to insert.
	 */
	public void insert(char c) {
		Node newChar = new Node(c);
		if (head == null) {
			head = newChar;
			tail = newChar;
		} else if (curPos == 0) {
			newChar.next = head;
			head.prev = newChar;
			head = newChar;

		} else if (cur == null) {
			newChar.prev = tail;
			tail.next = newChar;
			tail = newChar;
		} else {
			newChar.next = cur;
			newChar.prev = cur.prev;
			if (cur.prev != null) {
				cur.prev.next = newChar;
			}
			cur.prev = newChar;

		}
		numChars++;
		curPos++;

	}

	/**
	 * Deletes the character at the current cursor position. If the cursor is at the
	 * end of the text or if the list is empty nothing changes.
	 */
	public void delete() {
		if (curPos != numChars) {
			if (curPos == 0) {// Deleting for head
				head = head.next;
				cur = cur.next;
				head.prev = null;
				numChars--;
			} else if (curPos == numChars - 1) {// Deleting for tail
				tail = tail.prev;
				cur = cur.next;
				tail.next = cur;
				numChars--;
			} else {// Deleting for any other
				cur.prev.next = cur.next;
				cur = cur.next;
				cur.prev = cur.prev.prev;
				numChars--;
			}
		}
	}

	/**
	 * Deletes the character before the current cursor position. If the
	 * cursor is at the beginning of the text nothing happens.
	 */
	public void backspace() {
		if (curPos != 0) {
			if (curPos == 1) {// Backspace for head
				head = head.next;
				if (cur != null) {
					head.prev = null;
				}
				curPos--;
				numChars--;
			} else if (curPos == numChars) {// Backspace for tail
				tail = tail.prev;
				tail.next = cur;
				curPos--;
				numChars--;
			} else {// Backspace for any other
				cur.prev.prev.next = cur;
				cur.prev = cur.prev.prev;
				curPos--;
				numChars--;
			}
		}
	}

	/**
	 * Returns the text content of the editor as a string.
	 * 
	 * @return The text content of the editor.
	 */
	public String toString() {
		String output = new String();
		if (head != null) {
			Node x = this.head;
			while (x != null) {
				output += x.data;
				x = x.next;
			}
		}
		return output;
	}

	/**
	 * Clears all of the text in the editor.
	 */
	public void clear() {
		this.tail = null;
		this.head = null;
		this.cur = null;
		this.numChars = 0;
		this.curPos = 0;
	}
	/**
     * Saves the text content of the editor to a file.
     * 
     * @param savepath The path of the file to save the text content to.
     * @throws FileNotFoundException If file is not found.
     */
	public void save(String savepath) throws FileNotFoundException {
		File f = new File(savepath);
		PrintStream saveToFile = new PrintStream(f);
		if (!toString().equals(null)) {
			saveToFile.print(toString());
		}

	}

}
