package cn.edu.jlu.limf.himsc.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by merlin on 17-5-6.
 */

public class HeartRateZones extends DataSupport implements Parcelable {
    private String userAccountID;
    private String date;
    private String start_time;
    private String end_time;

    public String getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(String userAccountID) {
        this.userAccountID = userAccountID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    private String CaloriesOut;
    private String max;
    private String min;
    private String minutes;
    private String name;

    public HeartRateZones() {
    }

    public String getCaloriesOut() {
        return CaloriesOut;
    }

    public void setCaloriesOut(String caloriesOut) {
        CaloriesOut = caloriesOut;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userAccountID);
        dest.writeString(this.date);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.CaloriesOut);
        dest.writeString(this.max);
        dest.writeString(this.min);
        dest.writeString(this.minutes);
        dest.writeString(this.name);
    }

    protected HeartRateZones(Parcel in) {
        this.userAccountID = in.readString();
        this.date = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.CaloriesOut = in.readString();
        this.max = in.readString();
        this.min = in.readString();
        this.minutes = in.readString();
        this.name = in.readString();
    }

    public static final Creator<HeartRateZones> CREATOR = new Creator<HeartRateZones>() {
        @Override
        public HeartRateZones createFromParcel(Parcel source) {
            return new HeartRateZones(source);
        }

        @Override
        public HeartRateZones[] newArray(int size) {
            return new HeartRateZones[size];
        }
    };
}
