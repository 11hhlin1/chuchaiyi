package com.ccy.chuchaiyi.contact;

import com.ccy.chuchaiyi.base.BaseRecyclerItemData;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/8/24.
 */
public class PassengerData extends BaseRecyclerItemData implements Serializable{
    private static final long serialVersionUID = 3589499735461833561L;
    public String name;//显示的数据
    public String sortLetters;//显示数据拼音的首字母
    public PassengerInfo mPassengerInfo;
    public StoreUser mStoreUser;



}
