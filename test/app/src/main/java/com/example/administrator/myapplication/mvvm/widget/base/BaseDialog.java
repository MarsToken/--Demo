package com.example.administrator.myapplication.mvvm.widget.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by wangmaobo on 2018/4/4.
 */
public class BaseDialog extends AlertDialog {
    private AlertDialogListener mListener;
    private Builder mBuilder;

    public BaseDialog(Context context) {
        super(context);
        if (context instanceof AlertDialogListener) {
            mListener = (AlertDialogListener) context;
        }
        mBuilder = new Builder(context);
    }

    /**
     * @param message
     * @param title
     * @param negative
     * @param positive
     */
    public void show(String message, String title, String negative, String positive) {
        mBuilder.setMessage(message);
        mBuilder.setTitle(title);
        mBuilder.setNegativeButton(negative, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != mListener) {
                    mListener.onCancelDialog();
                }
            }
        });
        mBuilder.setPositiveButton(positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != mListener) {
                    mListener.onCertainDialog();
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.create().show();
    }

    public BaseDialog setAlertDialogListener(AlertDialogListener listener) {
        mListener = listener;
        return this;
    }

    public interface AlertDialogListener {
        void onCancelDialog();

        void onCertainDialog();

    }
}
