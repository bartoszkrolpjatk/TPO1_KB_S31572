package zad2.http.dto;

import com.google.gson.annotations.SerializedName;

public class ExchangeRateResponse {
    @SerializedName("conversion_rate")
    private double rate;

    public ExchangeRateResponse() {}

    public double rate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateDto{" +
                "rate='" + rate + '\'' +
                '}';
    }
}
