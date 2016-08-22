package com.ccy.chuchaiyi.flight;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class PolicyResultInfo implements Serializable{
    private static final long serialVersionUID = -3L;

    /**
     * DiscountLimitWarningMsg : string
     * LowPriceWarningMsg : string
     * NotLowPriceReason : string
     * NotPreNDaysReason : string
     * PreNDaysWarningMsg : string
     * TwoCabinWarningMsg : string
     */

    private String DiscountLimitWarningMsg;
    private String LowPriceWarningMsg;
    private String NotLowPriceReason;
    private String NotPreNDaysReason;
    private String PreNDaysWarningMsg;
    private String TwoCabinWarningMsg;

    public String getDiscountLimitWarningMsg() {
        return DiscountLimitWarningMsg;
    }

    public void setDiscountLimitWarningMsg(String DiscountLimitWarningMsg) {
        this.DiscountLimitWarningMsg = DiscountLimitWarningMsg;
    }

    public String getLowPriceWarningMsg() {
        return LowPriceWarningMsg;
    }

    public void setLowPriceWarningMsg(String LowPriceWarningMsg) {
        this.LowPriceWarningMsg = LowPriceWarningMsg;
    }

    public String getNotLowPriceReason() {
        return NotLowPriceReason;
    }

    public void setNotLowPriceReason(String NotLowPriceReason) {
        this.NotLowPriceReason = NotLowPriceReason;
    }

    public String getNotPreNDaysReason() {
        return NotPreNDaysReason;
    }

    public void setNotPreNDaysReason(String NotPreNDaysReason) {
        this.NotPreNDaysReason = NotPreNDaysReason;
    }

    public String getPreNDaysWarningMsg() {
        return PreNDaysWarningMsg;
    }

    public void setPreNDaysWarningMsg(String PreNDaysWarningMsg) {
        this.PreNDaysWarningMsg = PreNDaysWarningMsg;
    }

    public String getTwoCabinWarningMsg() {
        return TwoCabinWarningMsg;
    }

    public void setTwoCabinWarningMsg(String TwoCabinWarningMsg) {
        this.TwoCabinWarningMsg = TwoCabinWarningMsg;
    }
}
