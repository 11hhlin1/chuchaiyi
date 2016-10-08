package com.gjj.applibrary.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * 类/接口注释
 * 
 * @author panrq
 * @createDate Dec 29, 2014
 *
 */
public enum NetworkState implements Parcelable {
    // actually it is conbination of network type and network state
    WIFI("wifi", 1), NET_2G("2g", 2), NET_2G_WAP("2g", 2), NET_3G("3g", 3), NET_4G("4g", 4), UNAVAILABLE("unavailable", 0);

    private int code;
    private String name;
    private String operator;
    private String extra;
    private String ip;

    NetworkState(String name, int code) {
        this.name = name;
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    public int getCode() {
        return code;
    }
    public String getOperator() {
        return operator;
    }
    public String getExtra() {
        return extra;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public boolean is234G() {
        boolean ret = false;

        if (NetworkState.NET_4G == this || NetworkState.NET_3G == this || NetworkState.NET_2G == this || NetworkState.NET_2G_WAP == this) {
            ret = true;
        }

        return ret;
    }

    @Override
    public String toString() {
        return "NetworkState{" +
                "code=" + code +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
        dest.writeString(name);
        dest.writeInt(code);
        dest.writeString(operator);
        dest.writeString(extra);
        dest.writeString(ip);
    }
    
    public static final Creator<NetworkState> CREATOR = new Creator<NetworkState>() {
        public NetworkState createFromParcel(Parcel source) {
            NetworkState s = values()[source.readInt()];
            s.name = source.readString();
            s.code = source.readInt();
            s.operator = source.readString();
            s.extra = source.readString();
            s.ip = source.readString();
            return s;
        }

        public NetworkState[] newArray(int size) {
            return new NetworkState[size];
        }
    };
}
