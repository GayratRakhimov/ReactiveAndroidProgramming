package com.gayratrakhimov.reactiveandroidprogramming.coinapi.json;

import com.google.gson.annotations.SerializedName;

public class Rate {

	@SerializedName("rate")
	private double rate;

	@SerializedName("asset_id_quote")
	private String assetIdQuote;

	@SerializedName("time")
	private String time;

	public void setRate(double rate){
		this.rate = rate;
	}

	public double getRate(){
		return rate;
	}

	public void setAssetIdQuote(String assetIdQuote){
		this.assetIdQuote = assetIdQuote;
	}

	public String getAssetIdQuote(){
		return assetIdQuote;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	@Override
 	public String toString(){
		return 
			"Rate{" +
			"rate = '" + rate + '\'' + 
			",asset_id_quote = '" + assetIdQuote + '\'' + 
			",time = '" + time + '\'' + 
			"}";
		}

}