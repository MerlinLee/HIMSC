package cn.edu.jlu.limf.himsc.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by merlin on 17-5-4.
 */

public class UserLoginInfoBean implements Parcelable {
    String userName;
    String userPsw;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userPsw);
    }

    public UserLoginInfoBean() {
    }

    protected UserLoginInfoBean(Parcel in) {
        this.userName = in.readString();
        this.userPsw = in.readString();
    }

    public static final Parcelable.Creator<UserLoginInfoBean> CREATOR = new Parcelable.Creator<UserLoginInfoBean>() {
        @Override
        public UserLoginInfoBean createFromParcel(Parcel source) {
            return new UserLoginInfoBean(source);
        }

        @Override
        public UserLoginInfoBean[] newArray(int size) {
            return new UserLoginInfoBean[size];
        }
    };
}
