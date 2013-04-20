import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class TestGround {
	
	private static void changeEnum (Dir1 d) {
		d.d = DIR.SOUTH;
	}
	
	public static void main (String[] args) {
		
		Dir1 d = new Dir1();
		d.d = DIR.NORTH;
		
		changeEnum(d);
		
		System.out.println(d.d);
		
	}
}

enum DIR {
	NORTH,
	SOUTH
};


class Dir1 {
	DIR d;
}