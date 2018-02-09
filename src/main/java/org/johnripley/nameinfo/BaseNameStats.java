package org.johnripley.nameinfo;

public class BaseNameStats extends BaseNameInfo implements NameStats {
    private int countMale;
    private int countFemale;
    private int totalMale;
    private int totalFemale;

    public BaseNameStats(String name) {
	setName(name);
    }

    public BaseNameStats(String name, int year) {
	setName(name);
	setYear(year);
    }
       
    @Override
    public int getCountMale() {
	return countMale;
    }

    public void setCountMale(int countMale) {
	this.countMale = countMale;
    }

    @Override
    public int getCountFemale() {
	return countFemale;
    }

    public void setCountFemale(int countFemale) {
	this.countFemale = countFemale;
    }

    @Override
    public int getTotalMale() {
        return totalMale;
    }

    public void setTotalMale(int totalMale) {
        this.totalMale = totalMale;
    }
    @Override
    public int getTotalFemale() {
        return totalFemale;
    }

    public void setTotalFemale(int totalFemale) {
        this.totalFemale = totalFemale;
    }
    
}
