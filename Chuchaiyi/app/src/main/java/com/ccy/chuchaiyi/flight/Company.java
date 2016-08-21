package com.ccy.chuchaiyi.flight;

/**
 * Created by Administrator on 2016/8/20.
 */
public class Company {
    public String mAirlineName;
    public boolean mIsSel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return mAirlineName.equals(company.mAirlineName);

    }

    @Override
    public int hashCode() {
        return mAirlineName.hashCode();
    }
}
