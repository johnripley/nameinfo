package org.johnripley.nameinfo;

public class BaseGenderInfo extends BaseNameInfo implements GenderInfo {
	private String gender;
	private double confidence;

	public BaseGenderInfo(String name, int year, String gender, double confidence) {
		super(name, year);
		this.gender = gender;
		this.confidence = confidence;
	}

	@Override
	public String getGender() {
		return gender;
	}

	@Override
	public double getConfidence() {
		return confidence;
	}
}
