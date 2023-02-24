package com.skilldistillery.filmquery.entities;

import java.util.Objects;

public class Film {
	
	private int id;
	private String title;
	private String desc;
	private int releaseYear;
	private int langId;
	private int rentDur;
	private double rentRate;
	private Integer length;
	private double repCost;
	private String rating;
	private String features;
	
	
	public Film(int id, String title, String desc, int releaseYear, int langId, int rentDur, double rentRate,
			Integer length, double repCost, String rating, String features) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.releaseYear = releaseYear;
		this.langId = langId;
		this.rentDur = rentDur;
		this.rentRate = rentRate;
		this.length = length;
		this.repCost = repCost;
		this.rating = rating;
		this.features = features;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public int getReleaseYear() {
		return releaseYear;
	}


	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}


	public int getLangId() {
		return langId;
	}


	public void setLangId(int langId) {
		this.langId = langId;
	}


	public int getRentDur() {
		return rentDur;
	}


	public void setRentDur(int rentDur) {
		this.rentDur = rentDur;
	}


	public double getRentRate() {
		return rentRate;
	}


	public void setRentRate(double rentRate) {
		this.rentRate = rentRate;
	}


	public Integer getLength() {
		return length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}


	public double getRepCost() {
		return repCost;
	}


	public void setRepCost(double repCost) {
		this.repCost = repCost;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public String getFeatures() {
		return features;
	}


	public void setFeatures(String features) {
		this.features = features;
	}


	
	@Override	
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", desc=" + desc + ", releaseYear=" + releaseYear + ", langId="
				+ langId + ", rentDur=" + rentDur + ", rentRate=" + rentRate + ", length=" + length + ", repCost="
				+ repCost + ", rating=" + rating + ", features=" + features + "]";
	}


	
	@Override
	public int hashCode() {
		return Objects.hash(desc, features, id, langId, length, rating, releaseYear, rentDur, rentRate, repCost, title);
	}


	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(features, other.features) && id == other.id
				&& langId == other.langId && Objects.equals(length, other.length)
				&& Objects.equals(rating, other.rating) && releaseYear == other.releaseYear && rentDur == other.rentDur
				&& Double.doubleToLongBits(rentRate) == Double.doubleToLongBits(other.rentRate)
				&& Double.doubleToLongBits(repCost) == Double.doubleToLongBits(other.repCost)
				&& Objects.equals(title, other.title);
	}
	
	
}
