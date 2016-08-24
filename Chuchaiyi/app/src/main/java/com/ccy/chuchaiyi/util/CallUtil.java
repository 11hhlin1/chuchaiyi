package com.ccy.chuchaiyi.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.ccy.chuchaiyi.R;
import com.ccy.chuchaiyi.widget.CallDialog;
import com.ccy.chuchaiyi.widget.ConfirmDialog;

/**
 * Created by Chuck on 2016/8/24.
 */
public class CallUtil {
    public static void askForMakeCall(final Context act, String name, final String phoneNum) {
        CallDialog confirmDialog = new CallDialog(act, R.style.white_bg_dialog);
        confirmDialog.setCancelable(true);
        confirmDialog.setCanceledOnTouchOutside(false);
        if (TextUtils.isEmpty(name)) {
            confirmDialog.setContent(phoneNum);
        } else {
            confirmDialog.setContent(name + "\n" + phoneNum);
        }
        confirmDialog.setConfirm(R.string.call);
        confirmDialog.setConfirmClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                if (ActivityCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                act.startActivity(intent);
            }
        });
        confirmDialog.show();
    }
}
