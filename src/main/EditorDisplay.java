package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * This class creates a simple text editor GUI. In fact, 2 Editor objects are
 * used by this class. One is used for the main body of the editor where the
 * user can edit or create files. The other is used to read the user's input for
 * the save file name.
 * 
 * @author Chami
 */
public class EditorDisplay extends JFrame {

	private static final long serialVersionUID = 1L;

	/** Text area for main text body where user creates / edits files */
	private JTextArea text;

	/** label that separates main text area from save text area */
	private JLabel saveLabel;

	/** Text area for save path input */
	private JTextArea save;

	/** Editor for main text body */
	private Editor textEditor;

	/** Editor for save path input */
	private Editor saveEditor;

	/**
	 * Helper constructor that creates frame given constructed Editor for the main
	 * text area
	 * 
	 * @param text the Editor for the main text area
	 */
	private EditorDisplay(Editor text) {
		this.textEditor = text;
		this.saveEditor = new Editor();
		this.setTitle("Simple Text Editor");
		this.setUpText();
		this.setUpSaveLabel();
		this.setUpSave();
		this.setUpLayout();

		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Constructs an Editor display with no file pre-loaded.
	 */
	public EditorDisplay() {
		this(new Editor());
	}

	/**
	 * Constructs an Editor display with the specified file's text loaded into the
	 * main text body
	 * 
	 * @param arg the (String) path to the specified input file
	 * @throws FileNotFoundException (from Editor constructor)
	 */
	public EditorDisplay(String arg) throws FileNotFoundException {
		this(new Editor(arg));
	}

	/**
	 * Sets up the text area for the main text in the editor frame
	 */
	public void setUpText() {
		this.text = new JTextArea();
		this.text.setEditable(false);
		this.text.addKeyListener(getListener(textEditor, text, false));
		this.text.getCaret().setVisible(true);
		this.text.setCaretPosition(0);
		this.text.setFont(new Font("Courier", Font.PLAIN, 16));
		this.text.setFocusTraversalKeysEnabled(false);
		disableKeys(text);
		updateText(text, textEditor);
	}

	/**
	 * Sets up the label separating main text and save input areas
	 */
	public void setUpSaveLabel() {
		this.saveLabel = new JLabel("<html><p>Ctrl+S to save</p></html>");
		this.saveLabel.setFont(new Font("Courier", Font.PLAIN, 16));
	}

	/**
	 * Sets up the text area for the save file input in the editor frame
	 */
	public void setUpSave() {
		this.save = new JTextArea();
		this.save.setEditable(false);
		this.save.addKeyListener(getListener(saveEditor, save, true));
		this.save.setCaretPosition(0);
		this.save.setFocusable(false);
		this.save.setFont(new Font("Courier", Font.PLAIN, 16));
		this.save.setFocusTraversalKeysEnabled(false);
		disableKeys(save);
	}

	/**
	 * Sets up the frame's layout using GridBagLayout. This places the 2 text areas
	 * and label on to the frame in their appropriate spots. Large text area at the
	 * top followed by label and save input area.
	 */
	public void setUpLayout() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setLayout(gridbag);

		c.weightx = 1.0;
		c.weighty = 1.0;

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		gridbag.setConstraints(text, c);
		add(text);

		c.weightx = 0.0;
		c.weighty = 0.0;

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		gridbag.setConstraints(saveLabel, c);
		add(saveLabel);

		c.gridx = 0;
		c.gridy = 2;
		gridbag.setConstraints(save, c);
		add(save);

		setSize(300, 300);
		setPreferredSize(getSize());

		setLayout(gridbag);
	}

	/**
	 * Since there are two very similar text areas (and Editors) used by this class,
	 * this method serves to reduce redundancy by creating a KeyListener for a
	 * specified text area and Editor.
	 * 
	 * @param editor        the Editor for which the listener will be operating on
	 * @param area          the JTextArea for which the listener will be added
	 * @param enterToSubmit a boolean flag that says whether pressing "Enter"
	 *                      submits info (in the case of the save path text area -
	 *                      this triggers the main body text to be saved to a file)
	 *                      or if "Enter" just means move to next lien (in the case
	 *                      of the main text body)
	 * @return the constructed KeyListener
	 */
	public KeyListener getListener(Editor editor, JTextArea area, boolean enterToSubmit) {
		return new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() || e.isAltDown()) {
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (e.isShiftDown()) {
						return;
					}
					editor.moveLeft();
					area.setCaretPosition(editor.getCursorPosition());
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (e.isShiftDown()) {
						return;
					}
					editor.moveRight();
					area.setCaretPosition(editor.getCursorPosition());
				} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					editor.delete();
					updateText(area, editor);
				} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					editor.backspace();
					updateText(area, editor);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (enterToSubmit) {
						try {
							textEditor.save(saveEditor.toString());
							focusText();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						textEditor.insert('\n');
					}
				} else {
					insert(e, editor);
					updateText(area, editor);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.isControlDown()) {
					if (e.getKeyCode() == KeyEvent.VK_H) {
						editor.moveToHead();
						area.setCaretPosition(editor.getCursorPosition());
					} else if (e.getKeyCode() == KeyEvent.VK_E) {
						editor.moveToTail();
						area.setCaretPosition(editor.getCursorPosition());
					} else if (e.getKeyCode() == KeyEvent.VK_S && !enterToSubmit) {
						focusSave();
					} else if (e.getKeyCode() == KeyEvent.VK_K) {
						editor.clear();
						updateText(area, editor);
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						editor.delete();
						updateText(area, editor);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && enterToSubmit) {
					focusText();
				}

			}
		};
	}

	/**
	 * Sets the frame focus to the save text input
	 */
	public void focusSave() {
		save.getCaret().setVisible(true);
		text.getCaret().setVisible(false);
		text.setFocusable(false);
		save.setFocusable(true);
		saveLabel.setText("<html><p>Type save file name then Enter. (Esc to cancel)</p></html>");
	}

	/**
	 * Sets the frame focus to the main text body
	 */
	public void focusText() {
		saveLabel.setText("<html><p>Ctrl+S to save</p></html>");
		saveEditor.clear();
		save.setFocusable(false);
		text.setFocusable(true);
		save.getCaret().setVisible(false);
		text.getCaret().setVisible(true);
	}

	// UTILITY FUNCTIONS

	/**
	 * Updates the text of a specific JTextArea with an Editor's information
	 * 
	 * @param area a text area
	 * @param e    an Editor
	 */
	public static void updateText(JTextArea area, Editor e) {
		area.setText(e.toString());
		area.setCaretPosition(e.getCursorPosition());
	}

	/**
	 * Disables the arrow keys for a provided text area
	 * 
	 * @param a a text area
	 */
	public static void disableKeys(JTextArea a) {
		InputMap m = a.getInputMap();
		m.put(KeyStroke.getKeyStroke("UP"), "none");
		m.put(KeyStroke.getKeyStroke("DOWN"), "none");
		m.put(KeyStroke.getKeyStroke("LEFT"), "none");
		m.put(KeyStroke.getKeyStroke("RIGHT"), "none");
	}

	/**
	 * Given a KeyEvent, determines whether it should be inserted into an Editor as
	 * a character given a set of allowed characters.
	 * 
	 * @param e      a KeyEvent holding a keyboard input
	 * @param editor an Editor to be updated
	 */
	public static void insert(KeyEvent e, Editor editor) {
		if (e.isAltDown()) {
			return;
		}

		Set<Character> allowed = new HashSet<Character>(
				Arrays.asList(new Character[] { '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
						'[', ']', '{', '}', ',', '.', '\\', '|', ':', ';', '<', '>', '/', '?', '`', '~', '\'' }));

		char chr = e.getKeyChar();

		if (Character.isAlphabetic(chr) || Character.isDigit(chr) || Character.isWhitespace(chr)
				|| allowed.contains(chr)) {
			editor.insert(chr);
		}
	}

}
