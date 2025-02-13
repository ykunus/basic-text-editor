# Basic Text Editor - COSI 21A PA1  

## Overview  

This is a **very basic text editor** built for a **Data Structures class (COSI 21A)** at Brandeis University.  
The editor is implemented using a **doubly linked list** to manage text insertion, deletion, and cursor movement.  
This project helped me **understand how linked lists work** and how to implement a text editor from scratch.  

## Features  
- Insert and delete characters at the cursor position  
- Move the cursor **left and right**  
- Move the cursor **to the beginning or end of the text**  
- Read text from a file into the editor  
- Save the text to a file  

## Limitations  
- ❌ **First time opening the editor, the cursor does not appear** (You may need to reopen it)   
- ❌ **Cursor movement is only left/right** (Cannot move up/down) 
- ❌ **New lines do not always display the cursor** (But the cursor position updates correctly)  
- ❌ **No GUI improvements** (Only implemented `Editor.java`)
- 
## How It Works  
This editor stores characters using a **doubly linked list**, where:  
- **Each character is a node** (`Node.java`).  
- **Cursor movement is tracked using references** to nodes.  
- **Text modifications happen efficiently** without shifting large arrays.  

## Code Structure  
- `Editor.java` → Implements the text editor  
- `Node.java` → Defines a node in the doubly linked list  
- `EditorDisplay.java` → Provides a simple GUI (**not modified**)  
- `EditorMain.java` → Starts the editor GUI (**not modified**)  

## Running the Editor  
To compile and run the editor, use:  
```bash
javac src/main/Editor.java
java main.EditorMain
```


