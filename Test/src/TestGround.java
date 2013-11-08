import java.util.Observable;
import java.util.Observer;


class Observed extends Observable {

	String s;

	public void changeString (String s) {
		this.s = s;
		setChanged();
		notifyObservers(s);
	}

}


class Observing implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println((String) arg1);
	}

}

public class TestGround {

	public static void main (String[] args) {

		Observed ob = new Observed();
		Observer o = new Observing();

		ob.addObserver(o);
		ob.changeString("test");



	}

}