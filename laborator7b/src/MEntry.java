import java.util.Map;


public class MEntry implements Map.Entry {

	Object key;
	Object value;

	public MEntry(Object key) {
		this.key = key;
	}
	
	public Object getKey() {
		return this.key;
	}

	public Object getValue() {
		return this.value;
	}

	public Object setValue(Object arg0) {
		Object old = this.getValue();
		value = arg0;
		return old;
	}
	
	public String toString () {
		return this.key + "=" + this.value;
	}
	
	public boolean equals (Object o) {
		MEntry cp = (MEntry) o;
		if (this.key == cp.key && this.value == cp.value)
			return true;
		else
			return false;
	}

}
