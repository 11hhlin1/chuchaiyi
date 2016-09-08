package com.ccy.chuchaiyi.check;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chuck on 2016/9/5.
 */
public class CategoryData  implements Parcelable {
    public final static int MY_APPLY = 0;
    public final static int MY_UN_CHECK = 1;
    public final static int MY_CHECKED = 2;
    public final static int MY_UN_AUDIT = 3;
    public final static int MY_AUDITED = 4;

    public int mCateId;
    public String mCateName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCateId);
        dest.writeString(this.mCateName);
    }

    public CategoryData() {
    }

    private CategoryData(Parcel in) {
        this.mCateId = in.readInt();
        this.mCateName = in.readString();
    }

    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        public CategoryData createFromParcel(Parcel source) {
            return new CategoryData(source);
        }

        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };
}
