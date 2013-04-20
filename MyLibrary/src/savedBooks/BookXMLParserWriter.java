
package savedBooks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import element.*;


public class BookXMLParserWriter {
	
	String folder; // folderul in care se scriu cartile

	public BookXMLParserWriter() {
		folder = "./books/";
	}
	
	public BookXMLParserWriter (String folder) {
		this.folder = folder;
	}

	public void writeBookToFile (Book book) throws IOException {

		File dir = new File(folder);
		if (!dir.exists()) {
			dir.mkdir();
		}

		BufferedWriter out = new BufferedWriter(new FileWriter(folder + book.getTitle()));

		out.write("<book> <title> ");
		out.write(book.getTitle());
		out.write(" </title> \n");
		
		int indexChapter = 0; // contor pentru capitole
		while (indexChapter < book.size()) {
			out.write(" <chapter> ");
			out.write(" <title> ");
			out.write(book.getChapter(indexChapter).getChapterName());
			out.write(" </title> \n");
			
			int indexParagraph = 0;
			while (indexParagraph < book.getChapter(indexChapter).size()) {
				out.write(" <paragraph> ");

				int indexSentence = 0;
				while (indexSentence < book.getChapter(indexChapter).getParagraph(indexParagraph).size()) {
					out.write(" <sentence> ");
					out.write(book.getChapter(indexChapter).getParagraph(indexParagraph).getStringSentence(indexSentence));
					out.write(" </sentence> \n");
					indexSentence++;
				}
				
				out.write(" </paragraph> \n ");
				indexParagraph++;
			}
			
			out.write(" </chapter> \n");
			indexChapter++;
		}
		
		out.write(" </book> \n");
		
		out.write(" <author> ");
		out.write(book.getAuthor());
		out.write(" </author> \n");
		
		out.write(" <ISBN> ");
		out.write(book.getISBN());
		out.write(" </ISBN> \n");

		// le-am scris la sfarsit pentru ca uitasem si nu am vrut sa mai modific metoda
		// e un xml mai nonconformist :D

		out.close();
	}

	public void deleteBook (Book book) {
		
		File bookFile = new File(folder + book.getTitle());
		bookFile.delete();
		
	}
	
	public static void main (String[] args) throws IOException {
		
		BookXMLParserReader bkF = new BookXMLParserReader();
		
		Sentence s1, s2, s3, s4 = new Sentence();
		
		Paragraph p1 = new Paragraph();		
		Paragraph p2 = new Paragraph();
		Chapter ch1 = new Chapter();	
		Book b1 = new Book();
		
		b1 = bkF.readBookFromFile("stele");

		ch1 = b1.getChapter(0);

		p1 = ch1.getParagraph(0);
		p2 = ch1.getParagraph(1);

		s1 = p1.getSentence(0);
		s2 = p1.getSentence(1);
		s3 = p2.getSentence(0);
		s4 = p2.getSentence(1);

		String ss1, ss2, ss3, ss4 = "";
		ss1 = s1.getSentence();
		ss2 = s2.getSentence();
		ss3 = s3.getSentence();
		ss4 = s4.getSentence();

		System.out.println(ss1 + ss2 + ss3 + ss4);
		System.out.println("Author = " + b1.getAuthor());
		System.out.println("ISBN = " + b1.getISBN());
		
	}
	
}
