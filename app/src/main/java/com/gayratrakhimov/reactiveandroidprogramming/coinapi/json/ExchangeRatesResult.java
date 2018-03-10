package com.gayratrakhimov.reactiveandroidprogramming.coinapi.json;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ExchangeRatesResult{

	@SerializedName("asset_id_base")
	private String assetIdBase;

	@SerializedName("rates")
	private List<Rate> rates;

	public void setAssetIdBase(String assetIdBase){
		this.assetIdBase = assetIdBase;
	}

	public String getAssetIdBase(){
		return assetIdBase;
	}

	public void setRates(List<Rate> rates){
		this.rates = rates;
	}

	public List<Rate> getRates(){
		return rates;
	}

	@Override
 	public String toString(){
		return 
			"ExchangeRatesResult{" + 
			"asset_id_base = '" + assetIdBase + '\'' + 
			",rates = '" + rates + '\'' + 
			"}";
		}
}