package com.ebei.library;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.Components.BackupImageView;

import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by MaoLJ on 2018/7/18.
 * 提示框工具类
 */

public class DialogUtil {

    private static final String TAG = "DialogUtil";

    /**
     * 回调方法的接口1
     */
    public interface OnResultListener0 {
        public void onOK();
    }

    /**
     * 回调方法的接口2
     */
    public interface OnResultListener2 {
        public void onOk(String... params);

        public void onForget();
    }

    /**
     * 回调方法的接口3
     */
    public interface OnResultListener3 {
        public void select1();

        public void select2();

        public void select3();

        public void select4();

        public void select5();
    }

    /**
     * 回调方法的接口14
     */
    public interface OnResultListener4 {
        public void select1();

        public void select2();

        public void select3();
    }

    /**
     * 交易提示
     */
    public static void transactionDialog(Context context, final OnResultListener2 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_transaction, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout sub = view.findViewById(R.id.sub_layout);
        PasswordInputEdt pwd = view.findViewById(R.id.et_pwd);
        ImageView close = view.findViewById(R.id.img_close);
        TextView forget = view.findViewById(R.id.tv_forget);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(pwd, 0);
            }
        }, 200);

        pwd.setOnInputOverListener(new PasswordInputEdt.onInputOverListener() {
            @Override
            public void onInputOver(String text) {
                if (text.length() == 6) {
                    onResListener.onOk(new String[]{text});
                    mPopupWindow.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.onForget();
                mPopupWindow.dismiss();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click sub layout");
            }
        });
    }

    /**
     * 分享提示
     */
    public static void shareDialog(Context context, final OnResultListener3 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        LinearLayout layout1 = view.findViewById(R.id.ll1);
        LinearLayout layout2 = view.findViewById(R.id.ll2);
        LinearLayout layout3 = view.findViewById(R.id.ll3);
        LinearLayout layout4 = view.findViewById(R.id.ll4);
        LinearLayout layout5 = view.findViewById(R.id.ll5);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select2();
                mPopupWindow.dismiss();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select3();
                mPopupWindow.dismiss();
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select4();
                mPopupWindow.dismiss();
            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select5();
                mPopupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 设置支付密码提示
     */
    public static void setPayPwdDialog(Context context, final OnResultListener0 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_pay_pwd, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView sure = view.findViewById(R.id.tv_sure);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.onOK();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 钱包地址提示
     */
    public static void walletAddressDialog(Context context, String address, String coinType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wallet_address, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout view1 = view.findViewById(R.id.view1);
        ImageView view2 = view.findViewById(R.id.view2);
        ImageView imgAddress = view.findViewById(R.id.img_address);
        TextView tvAddress = view.findViewById(R.id.tv_address);
        TextView tvCopy = view.findViewById(R.id.tv_copy);

        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(address, 400);
        imgAddress.setImageBitmap(bitmap);
        if (address.startsWith("TSC "))
            tvAddress.setText(address.substring(4, address.length()));
        else if (address.startsWith("T5C-T "))
            tvAddress.setText(address.substring(6, address.length()));
        else if (address.startsWith("NTT "))
            tvAddress.setText(address.substring(4, address.length()));
        switch (coinType) {
            case "2":
                view2.setImageResource(R.mipmap.logo1);
                break;
            case "10":
                view2.setImageResource(R.mipmap.logo2);
                break;
            case "11":
                view2.setImageResource(R.mipmap.logo3);
                break;
        }
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(ClipData.newPlainText(null, tvAddress.getText().toString()));
                    if (clipboard.hasPrimaryClip()) {
                        clipboard.getPrimaryClip().getItemAt(0).getText();
                    }
                }
                ToastUtil.toast(context, context.getString(R.string.toast_copy_success));
                mPopupWindow.dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "");
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "");
            }
        });
    }

    /**
     * 确认转账提示
     */
    public static void transferConfirmDialog(Context context, String coin, String count, final OnResultListener0 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_transfer_confirm, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout view1 = view.findViewById(R.id.view);
        ImageView imgClose = view.findViewById(R.id.img_close);
        TextView tvCount = view.findViewById(R.id.tv_count);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvCoin = view.findViewById(R.id.tv_coin);

        tvCount.setText(count);
        tvCoin.setText(coin);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.onOK();
                mPopupWindow.dismiss();

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "");
            }
        });
    }

    /**
     * 点开红包消息提示
     */
    public static void openRedPagMsgDialog(Context context, String uid, String contentInfo, final OnResultListener0 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_open_redpag_msg, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        RelativeLayout close = view.findViewById(R.id.img_close);
        BackupImageView avatar = view.findViewById(R.id.img_avatar);
        TextView nickname = view.findViewById(R.id.tv_nickname);
        TextView content = view.findViewById(R.id.tv_content);
        TextView info = view.findViewById(R.id.tv_info);

        if (UserPreference.getInt(UserPreference.HAD_SHOW_RED_DIALOG, 0) == 1) {
            mPopupWindow.dismiss();
        }
        UserPreference.putInt(UserPreference.HAD_SHOW_RED_DIALOG, 1);
        if (!Util.isEmpty(uid)) {
            Util.setTeleInfo(uid, avatar);
            TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(uid));
            nickname.setText(UserObject.getUserName(user));
        }
        content.setText(contentInfo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.onOK();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 选择币种提示
     */
    public static void selectCoinDialog(Context context, final OnResultListener4 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_coin, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        LinearLayout layout = view.findViewById(R.id.layout);
        TextView tsc = view.findViewById(R.id.tv_tsc);
        TextView t5c = view.findViewById(R.id.tv_t5c);
        TextView ntt = view.findViewById(R.id.tv_ntt);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        tsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
        t5c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select2();
                mPopupWindow.dismiss();
            }
        });
        ntt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select3();
                mPopupWindow.dismiss();
            }
        });
    }

}
