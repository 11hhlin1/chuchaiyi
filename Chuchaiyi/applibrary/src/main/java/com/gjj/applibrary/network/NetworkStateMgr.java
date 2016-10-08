package com.gjj.applibrary.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.log.L;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

/**
 * 
 * 类/接口注释
 * 
 * @author panrq
 * @createDate Dec 29, 2014
 * 
 */
public class NetworkStateMgr {

    private static volatile NetworkStateMgr mInstance;

    NetworkState mNetworkState;
    private NetworkStateBroadcastReceiver mNetworkBroadcastReceiver;

    public static NetworkStateMgr getInstance() {
        if (mInstance == null) {
            synchronized (NetworkStateMgr.class) {
                if (mInstance == null) {
                    mInstance = new NetworkStateMgr();
                }
            }
        }
        return mInstance;
    }

    private NetworkStateMgr() {
        Context app = AppLib.getContext();

        // 注册广播监听网络变化，如果收到变化，则用EventBus广播给前台
        mNetworkBroadcastReceiver = new NetworkStateBroadcastReceiver();
        app.registerReceiver(mNetworkBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void onEventBackgroundThread(NetworkState event) {
        mNetworkState = event;
    }

    class NetworkStateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNetworkState = getNetworkStateForce(AppLib.getContext());
        }
    }

    public NetworkState getNetworkState() {
        if (mNetworkState == null) {
            mNetworkState = getNetworkStateForce(AppLib.getContext());
        }
        return mNetworkState;
    }

    public boolean isNetworkAvailable() {
        return getNetworkState().ordinal() != NetworkState.UNAVAILABLE.ordinal();
    }

    /**
     * 注意，因为EventBus注册有先后，故无法保证接受顺序；如果有注册EventBus监听NetworkState， 这时收到事件，想判断网络是否可用，需调用此接口
     * 
     * @param networkState
     */
    public static boolean isThisNetworkAvailable(NetworkState networkState) {
        return networkState.ordinal() != NetworkState.UNAVAILABLE.ordinal();
    }

    private NetworkState getNetworkStateForce(Context context) {
        NetworkState state = null;
        NetworkInfo info = null;

        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = manager.getActiveNetworkInfo();

            if (info == null) {// android2.2 sometimes getActiveNetworkInfo always return null, but network is available
                NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (wifiInfo != null && wifiInfo.isAvailable()) {
                    info = wifiInfo;
                } else if (mobileInfo != null && mobileInfo.isAvailable()) {
                    info = mobileInfo;
                }
            }

            if (info != null && (info.isConnectedOrConnecting() || info.isRoaming())) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    state = NetworkState.WIFI;
                    state.setIp(getWifiIP(context));
                }

                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (info.getSubtype() <= 4) {
                        state = isWapConnection(info.getExtraInfo()) ? NetworkState.NET_2G_WAP : NetworkState.NET_2G; // ~ 50-100 kbps

                    } else {
                        state = NetworkState.NET_3G;
                    }
                    state.setIp(getMobileIP());
                }
            }
        } catch (Exception e) {
            L.w(e);
        }

        if (state == null) {
            state = NetworkState.UNAVAILABLE;
        }

        if (state == NetworkState.WIFI) {
            state.setExtra("wifi");
        } else if (info != null) {
            state.setExtra(getExtra(info));
        }

        String operator = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            operator = tm.getNetworkOperatorName();
        } catch (Exception e) {
            L.w(e);
        }

        if (operator == null || operator.length() == 0) {
            state.setOperator("unknown");
        } else {
            state.setOperator(operator);
        }

        return state;
    }

    private static String getExtra(NetworkInfo info) {
        String extra = info.getExtraInfo();

        if (extra == null) {
            extra = info.getSubtypeName();
        }

        return extra;
    }

    private static boolean isWapConnection(String extraInfo) {
        return extraInfo != null && extraInfo.contains("wap");
    }

    private static String getWifiIP(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return String.format(Locale.getDefault(), "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));
        } catch (Exception e) {
            L.e(e);
        }
        return null;
    }

    private static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            L.e(e);
        }
        return null;
    }

}
