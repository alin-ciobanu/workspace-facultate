package element;

import interfaces.ILibrary;
import interfaces.Clearable;

public class Library implements ILibrary, Clearable {

	Body<Book> lib;
	
	public Library () {
		lib = new Body<Book>();
	}
	
	@Override
	public void clear() {
		lib.clear();
	}

	@Override
	public void add(Book book) {
		lib.add(book);
	}

	@Override
	public void remove(Book book) {
		lib.remove(book);
	}
	
	public Book get(int index) {
		return lib.get(index);
	}
	
	public int size () {
		return lib.size();
	}

}
