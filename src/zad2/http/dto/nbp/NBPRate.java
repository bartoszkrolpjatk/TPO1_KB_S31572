package zad2.http.dto.nbp;

import com.google.gson.annotations.SerializedName;

public class NBPRate {
    @SerializedName("mid")
    private double rate;

    public double rate() {
        return rate;
    }

    @Override
    public String toString() {
        return "NBPRate{" +
                "rate=" + rate +
                '}';
    }
}
