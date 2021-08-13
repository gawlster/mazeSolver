// Connor Gawley
// V00955266

public class A5Stack<T> implements Stack<T> {
	
	private Node<T> head;

	public A5Stack() {
		// TODO: implement this
		head = null;
	}
	
	public void push(T v) {
		// TODO: implement this
		Node<T> n = new Node(v);
		if (head != null) {
			n.next = head;
			head = n;
		} else {
			head = n;
		}
	}
	
	public T pop() {
		// TODO: implement this
		if (head == null) {
			throw new EmptyStackException();
		} else {
			Node<T> temp = head;
			head = head.next;
			return temp.getData();
		}
	}

	public T top() {
		// TODO: implement this	
		if (head == null) {
			throw new EmptyStackException();
		} else {
			return head.getData();
		}
	}	
	
	public void popAll() {
		// TODO: implement this
		head = null;
	}
	
	public boolean isEmpty() {
		// TODO: implement this	
		return head == null;
	}
	
}