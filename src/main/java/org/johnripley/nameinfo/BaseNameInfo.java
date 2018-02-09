package org.johnripley.nameinfo;

public class BaseNameInfo implements NameInfo {
	private String name;
	private int year = 0;;

	public BaseNameInfo() {
	}

	public BaseNameInfo(String name) {
		super();
		this.name = name;
	}

	public BaseNameInfo(String name, int year) {
		super();
		this.name = name;
		this.year = year;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getYear() {
		return year;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
