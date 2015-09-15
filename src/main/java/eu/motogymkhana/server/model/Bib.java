package eu.motogymkhana.server.model;

/**
 * Created by christine on 31-5-15.
 */
public enum Bib {

	Y(""), G("G"), R("R");

	private String string;

	Bib(String string) {
		this.string = string;
	}

	public String toString() {
		return string;
	}
}
