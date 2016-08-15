package com.ccy.chuchaiyi.city;

import java.io.Serializable;

/**
 * Created by Chuck on 2016/8/15.
 */
public class CitySort implements Serializable{
    private static final long serialVersionUID = -7734609036084263191L;

    /**
     * Code : YIE
     * Name : 阿尔山
     * IsCity : true
     * Pinyin : aershan
     * PinyinShort : AES
     * Value : null
     */

    private String Code;
    private String Name;
    private boolean IsCity;
    private String Pinyin;
    private String PinyinShort;
    private Object Value;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isIsCity() {
        return IsCity;
    }

    public void setIsCity(boolean IsCity) {
        this.IsCity = IsCity;
    }

    public String getPinyin() {
        return Pinyin;
    }

    public void setPinyin(String Pinyin) {
        this.Pinyin = Pinyin;
    }

    public String getPinyinShort() {
        return PinyinShort;
    }

    public void setPinyinShort(String PinyinShort) {
        this.PinyinShort = PinyinShort;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object Value) {
        this.Value = Value;
    }
}
