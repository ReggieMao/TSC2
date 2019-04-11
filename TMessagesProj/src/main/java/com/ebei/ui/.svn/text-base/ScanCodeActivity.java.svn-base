package com.ebei.ui;

import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.ToastUtil;
import com.ebei.tsc.R;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by MaoLJ on 2018/11/14.
 * 扫码页面
 */

public class ScanCodeActivity extends BaseActivity {

    private static final String TAG = "ScanCodeActivity";
    @Bind(R.id.zxingview)
    QRCodeView mQRCodeView;
    @Bind(R.id.img_light)
    ImageView mImgLight;
    @Bind(R.id.tv_light)
    TextView mTvLight;
    private boolean isOpen = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initView() {
        mQRCodeView.changeToScanQRCodeStyle();
        mQRCodeView.startSpot();
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                vibrate();
                mQRCodeView.stopSpot();
                Intent intent = getIntent();
                intent.putExtra("get_scan_result", result);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                ToastUtil.toast(ScanCodeActivity.this, getString(R.string.camera_permission));
            }
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @OnClick({R.id.img_back, R.id.ll_light})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_light:
                if (!isOpen) {
                    mQRCodeView.openFlashlight();
                    mTvLight.setText(getString(R.string.close_flight));
                    mImgLight.setImageResource(R.drawable.ic_open);
                    isOpen = true;
                } else {
                    mQRCodeView.closeFlashlight();
                    mTvLight.setText(getString(R.string.open_flight));
                    mImgLight.setImageResource(R.drawable.ic_close);
                    isOpen = false;
                }
                break;
        }
    }

}
