package cn.edu.jlu.limf.himsc.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by merlin on 17-5-5.
 */

public class UserRegisterInfoBean implements Parcelable {
    private int userId;
    private String userAccountId;
    private String userName;
    private String userAddress;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userAccountId);
        dest.writeString(this.userName);
        dest.writeString(this.userAddress);
        dest.writeString(this.password);
    }

    public UserRegisterInfoBean() {
    }

    protected UserRegisterInfoBean(Parcel in) {
        this.userId = in.readInt();
        this.userAccountId = in.readString();
        this.userName = in.readString();
        this.userAddress = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<UserRegisterInfoBean> CREATOR = new Parcelable.Creator<UserRegisterInfoBean>() {
        @Override
        public UserRegisterInfoBean createFromParcel(Parcel source) {
            return new UserRegisterInfoBean(source);
        }

        @Override
        public UserRegisterInfoBean[] newArray(int size) {
            return new UserRegisterInfoBean[size];
        }
    };
}
