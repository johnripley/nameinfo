package org.johnripley.nameinfo;

public class BaseNameLookupInfo implements NameLookupInfo {
	private int startYear;
	private int endYear;

	public BaseNameLookupInfo(int startYear, int endYear) {
		this.startYear = startYear;
		this.endYear = endYear;
	}

	@Override
	public int getStartYear() {
		return startYear;
	}

	@Override
	public int getEndYear() {
		return endYear;
	}
}
