/**
* Tests edge cases. 
* Known Bugs: none
* @author Yunus Kocaman
* yunuskocaman@brandeis.edu
* Feb,28, 2024
* COSI 21A PA1
*/
package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import main.Editor;

class StudentEditorTests {
	Editor e;
	Editor e2;

	@Test
	void dedaultConstructor() {
		e = new Editor();
		e.insert('b');
		assertEquals('b', e.head.data);
	}

	@Test
	void parameterConstructor() throws FileNotFoundException {
		e2 = new Editor("multi_line_file.txt");
		assertNotNull(e2);
	}

	@Test
	void insertingAllPositions() {
		e = new Editor();

		// inserting at tail
		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(4, e.curPos);

		// inserting at middle
		e.moveLeft();
		assertEquals('e', e.cur.data);
		assertEquals(3, e.curPos);
		e.moveLeft();

		assertEquals(2, e.curPos);
		e.insert('s');
		assertEquals('u', e.cur.data);
		assertEquals(3, e.curPos);
		e.insert('s');

		// inserting at head
		e.moveToHead();
		e.insert('b');
		assertEquals('b', e.head.data);
	}

	@Test
	void size() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		assertEquals(4, e.size());
	}

	@Test
	void moveToHead() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		e.moveToHead();
		assertEquals(0, e.curPos);
	}

	@Test
	void delete() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		e.moveToHead();
		e.delete();
		assertEquals(0, e.curPos);
		assertEquals(3, e.size());
	}

	@Test
	void moveToTail() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		e.moveToHead();
		e.moveToTail();
		assertEquals(e.numChars, e.curPos);
	}

	@Test
	void moveRight() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		// check if method does anything even if there are no more nodes to the right.
		for (int i = 0; i < 10; i++) {
			e.moveRight();
		}
	}

	@Test
	void moveLeft() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		// check if method does anything even if there are no more nodes to the left.
		for (int i = 0; i < 10; i++) {
			e.moveLeft();
		}
	}

	@Test
	void deleteAtEnd() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		e.moveToTail();
		e.delete();
	}

	@Test
	void backSpace() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		// check for backspace tail
		e.insert('u');
		e.backspace();
		assertEquals('e', e.tail.data);

		// backspace from curPos 0
		e.moveToHead();
		e.backspace();

		// removing head
		e.moveRight();
		e.backspace();
		assertEquals('l', e.head.data);
		// adding back b
		e.moveLeft();
		e.insert('b');
		assertEquals('b', e.head.data);

		// remove from middle
		e.moveRight();
		e.moveRight();
		assertEquals("blue", e.toString());
		e.backspace();
		assertEquals("ble", e.toString());
	}

	@Test
	void testingToString() {
		e = new Editor();

		e.insert('b');
		e.insert('l');
		e.insert('u');
		e.insert('e');
		assertEquals("blue", e.toString());
	}

	@Test
	void testClear() throws FileNotFoundException {
		e2 = new Editor("multi_line_file.txt");
		assertEquals('I', e2.head.data);
		e2.clear();
		assertNull(e2.head);
	}

}
