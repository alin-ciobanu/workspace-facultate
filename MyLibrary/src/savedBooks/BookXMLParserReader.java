
package savedBooks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

import element.*;


public class BookXMLParserReader {
	
	String folder; // folderul in care se scriu cartile

	public BookXMLParserReader() {
		folder = "./books/";
	}
	
	public BookXMLParserReader (String folder) {
		this.folder = folder;
	}

	public Book readBookFromFile (String fileName) throws IOException {

		Book book = new Book();
		String bookName = "";
		Chapter chapter = new Chapter();
		String chapterName = "";
		Paragraph paragraph = new Paragraph();
		Sentence sentence = new Sentence();
		String sentenceString = "";
		String author = "";
		String ISBN = "";
		
		boolean bookOpened = false;
		boolean chapterOpened = false;
		boolean paragraphOpened = false;
		boolean sentenceOpened = false;
		boolean bookClosed = false;
		boolean authorOpened = false;
		boolean ISBNOpened = false;
		boolean bookTitleSet = false;
		boolean chapterTitleSet = false;
		
		BufferedReader in = new BufferedReader(new FileReader(folder + fileName));
		String newLine;

		while ((newLine = in.readLine()) != null) {
			String word = "";
			StringTokenizer tok = new StringTokenizer(newLine, " ");

			while (tok.hasMoreTokens()) {
				word = tok.nextToken();

				if (bookOpened && !bookTitleSet) {
					if (!word.equals("<title>") && !word.equals("</title>"))
						bookName = bookName + word + " ";
				}
				
				if (word.equals("<book>"))
					bookOpened = true;

				if (word.equals("</title>")) {
					if (!bookTitleSet)
						bookTitleSet = true;
					if (!chapterTitleSet) {
						chapterTitleSet = true;
						if (!chapterName.equals(""))
							chapter.changeChapterName(chapterName.substring(0, chapterName.length()-1));
					}
				}
				
				if (bookOpened && chapterOpened && !chapterTitleSet) {
					if (!word.equals("<title>") && !word.equals("</title>"))
						chapterName = chapterName + word + " ";
				}

				if (word.equals("<chapter>")) {
					chapterOpened = true;
					chapterTitleSet = false;
				}
	
				if (word.equals("<paragraph>"))
					paragraphOpened = true;
				
				if (word.equals("<sentence>"))
					sentenceOpened = true;
				
				if (bookOpened && chapterOpened && paragraphOpened && sentenceOpened) {
					if (!word.equals("<sentence>") && !word.equals("</sentence>"))
						sentenceString = sentenceString + word + " ";
				}
				
				if (word.equals("</sentence>")) {
					sentenceOpened = false;
					if (!sentenceString.equals("")) {
						sentence.setSentence(sentenceString.substring(0, sentenceString.length()-1));
						paragraph.add(sentence);
					}
					sentence = new Sentence();
					sentenceString = "";
				}
				
				if (word.equals("</paragraph>")) {
					paragraphOpened = false;
					chapter.add(paragraph);
					paragraph = new Paragraph();
				}
	
				if (word.equals("</chapter>")) {
					chapterOpened = false;
					chapter.changeChapterName(chapterName);
					book.add(chapter);
					chapter = new Chapter();
					chapterName = "";
				}
	
				if (word.equals("</book>")) {
					bookOpened = false;
					bookClosed = true;
					if (!bookName.equals(""))
						book.renameBook(bookName.substring(0, bookName.length()-1));
				}
				
				if (word.equals("<author>")) {
					authorOpened = true;
				}
				
				if (authorOpened) {
					if (!word.equals("<author>") && !word.equals("</author>"))
						author = author + word + " ";						
				}
				
				if (word.equals("</author>"))
					authorOpened = false;
				
				if (word.equals("<ISBN>"))
					ISBNOpened = true;
				
				if (ISBNOpened)
					if (!word.equals("<ISBN>") && !word.equals("</ISBN>"))
						ISBN = ISBN + word + " ";
				
			}

		}
		
		in.close();
		
		if (!ISBN.equals(""))
			book.changeISBN(ISBN.substring(0, ISBN.length()-1)); // substringul care nu contine ultimul caracter (care e un spatiu inutil)
		if (!author.equals(""))
			book.renameAuthor(author.substring(0, author.length()-1));
		
		return book;

	}

	
	public static void main (String[] args) throws IOException {
		
		BookXMLParserWriter bkF = new BookXMLParserWriter();
		
		Sentence s1 = new Sentence("prima propozitie");
		Sentence s2 = new Sentence("a doua propozitie");
		Sentence s3 = new Sentence("propozitia nr trei");
		Sentence s4 = new Sentence("a patra propozitie");
		
		Paragraph p1 = new Paragraph();
		p1.add(s1);
		p1.add(s2);
		
		Paragraph p2 = new Paragraph();
		p2.add(s3);
		p2.add(s4);
		
		Chapter ch1 = new Chapter();
		ch1.changeChapterName("singurul capitol");
		ch1.add(p1);
		ch1.add(p2);
		
		Book b1 = new Book();
		b1.add(ch1);
		b1.renameBook("stelute");
		b1.renameAuthor("me myself");
		b1.changeISBN("known12");
	
		bkF.writeBookToFile(b1);

	}
	
}
