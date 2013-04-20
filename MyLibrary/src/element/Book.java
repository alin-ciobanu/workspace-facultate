package element;

import interfaces.IBook;
import interfaces.Clearable;

public class Book implements IBook, Clearable {

	private Body<Chapter> book;
	private String title;
	private String author;
	private String ISBN;
	
	public Book (String title, String author, String ISBN) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		book = new Body<Chapter>();
	}
	
	public Book () {
		this.title = "";
		this.author = "";
		this.ISBN = "";
		book = new Body<Chapter>();
	}
	
	@Override
	public void clear() {
		book.clear();
		title = "";
		author = "";
		ISBN = "";
	}

	@Override
	public void add(Chapter ch) {
		book.add(ch);
	}

	@Override
	public void remove(int i) {
		book.remove(i);
	}

	@Override
	public void renameAuthor(String newAuthorName) {
		author = newAuthorName;
	}

	@Override
	public void renameBook(String newBookName) {
		title = newBookName;
	}

	@Override
	public void changeISBN(String newISBN) {
		ISBN = newISBN;
	}

	@Override
	public int size() {
		return book.size();
	}
	
	public String getTitle () {
		return title;
	}
	
	public Chapter getChapter (int index) {
		return book.get(index);
	}
	
	public String getISBN () {
		return ISBN;
	}
	
	public String getAuthor () {
		return author;
	}
	
	public String toString () {
		return title;
	}

}
