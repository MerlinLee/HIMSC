package cn.edu.jlu.limf.himsc.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by merlin on 17-5-6.
 */

public class UserHealthRecordBean extends DataSupport implements Parcelable  {
    @Column()
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

    public HeartRateZones getHeartRateZones() {
        return heartRateZones;
    }

    public void setHeartRateZones(HeartRateZones heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    HeartRateZones heartRateZones;

    public UserHealthRecordBean() {
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
        dest.writeParcelable(this.heartRateZones, flags);
    }

    protected UserHealthRecordBean(Parcel in) {
        this.userAccountID = in.readString();
        this.date = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.heartRateZones = in.readParcelable(HeartRateZones.class.getClassLoader());
    }

    public static final Creator<UserHealthRecordBean> CREATOR = new Creator<UserHealthRecordBean>() {
        @Override
        public UserHealthRecordBean createFromParcel(Parcel source) {
            return new UserHealthRecordBean(source);
        }

        @Override
        public UserHealthRecordBean[] newArray(int size) {
            return new UserHealthRecordBean[size];
        }
    };
}
