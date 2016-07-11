package javaast.parser.examples;

public class Entry implements Comparable<Entry> {
    private String key;
    private int value;

    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    // getters

    @Override
    public int compareTo(Entry other) {
    	return this.value - other.value;
    }

	private String getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}
	public String toString() {
        return this.key + ":" + this.value;
    }
}