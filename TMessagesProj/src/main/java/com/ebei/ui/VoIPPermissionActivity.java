package com.ebei.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import com.ebei.tsc.voip.VoIPService;
import com.ebei.ui.Components.voip.VoIPHelper;

public class VoIPPermissionActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 101);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
		if(requestCode==101){
			if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
				if(VoIPService.getSharedInstance()!=null)
					VoIPService.getSharedInstance().acceptIncomingCall();
				finish();
				startActivity(new Intent(this, VoIPActivity.class));
			}else{
				if(!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)){
					if(VoIPService.getSharedInstance()!=null)
						VoIPService.getSharedInstance().declineIncomingCall();
					VoIPHelper.permissionDenied(this, new Runnable(){
						@Override
						public void run(){
							finish();
						}
					});
					return;
				}else{
					finish();
				}
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
