package com.gjj.applibrary.util;

import com.gjj.applibrary.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Chuck on 2016/8/16.
 */
public class SaveObjUtil {


    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static String serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            // 序列化
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String str = byteArrayOutputStream.toString("ISO-8859-1");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeCloseable(byteArrayOutputStream);
            Util.closeCloseable(objectOutputStream);
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param str
     * @return
     */
    public static Object unSerialize(String str) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            // 反序列化
            if (str != null) {
                byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
                ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeCloseable(byteArrayInputStream);
        }
        return null;
    }
}
