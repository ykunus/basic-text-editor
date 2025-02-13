package main;

public class Node {
	
	public Node next;
	public Node prev;
	public char data;
	
	public Node(char c) {
		this.next = null;
		this.prev = null;
		this.data = c;
	}
}
