package com.example.quocthao.supership;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.example.quocthao.supership.login_register.LoginActivity;

/**
 * Created by Quoc Thao on 5/7/2017.
 */
public class CustomDialog extends Dialog {

    private View view;
    private Context context;

    public CustomDialog(Context context, int layout) {
        super(context);
        this.context = context;
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setCancelable(false);
        view = LayoutInflater.from(context)
                .inflate(layout, null);
        setContentView(view);
    }

    public View getView(){
        return view;
    }
}
