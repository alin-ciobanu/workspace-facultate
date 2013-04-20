package element;

import interfaces.IChapter;
import interfaces.Clearable;

public class Chapter implements IChapter, Clearable {

	private String title;
	private Body<Paragraph> chapter;
	
	public Chapter () {
		title = "";
		chapter = new Body<Paragraph>();
	}
	
	@Override
	public void clear() {
		chapter.clear();
		title = "";
	}

	@Override
	public void add(Paragraph p) {
		chapter.add(p);
	}
	
	public void add (int index, Paragraph p) {
		chapter.add (index, p);
	}

	@Override
	public void remove(int i) {
		chapter.remove(i);
	}

	@Override
	public void changeChapterName(String newChapterName) {
		title = newChapterName;
	}
	
	public String getChapterName () {
		return title;
	}

	@Override
	public int size() {
		return chapter.size();
	}
	
	public Paragraph getParagraph (int index) {
		return chapter.get(index);
	}
	
	public String getTitle () {
		return title;
	}

}
