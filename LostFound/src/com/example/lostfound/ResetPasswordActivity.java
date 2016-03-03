package com.example.lostfound;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

public class ResetPasswordActivity extends Activity {

	private TextView tv_title;
	private Button iv_left;
	private MyCountTimer timer;
	private EditText et_phone;
	private EditText et_code;
	private Button btn_send;
	private EditText et_pwd;
	private String TAG = "TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pwd);

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_code = (EditText) findViewById(R.id.et_verify_code);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		btn_send = (Button) findViewById(R.id.btn_send);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		tv_title.setText("重置密码");
		iv_left.setVisibility(View.VISIBLE);
	}

	class MyCountTimer extends CountDownTimer {

		public MyCountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_send.setText((millisUntilFinished / 1000) + "秒后重发");
		}

		@Override
		public void onFinish() {
			btn_send.setText("重新发送验证码");
		}
	}

	public void back(View v) {
		finish();
	}

	public void reset(View v) {
		resetPwd();
	}

	public void sendCode(View v) {
		requestSMSCode();
	}

	@SuppressLint("InlinedApi")
	private void resetPwd() {
		final String code = et_code.getText().toString();
		final String pwd = et_pwd.getText().toString();
		if (TextUtils.isEmpty(code)) {
			Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progress = new ProgressDialog(ResetPasswordActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("正在重置密码...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		BmobUser.resetPasswordBySMSCode(this, code, pwd, new ResetPasswordByCodeListener() {

			@Override
			public void done(BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if (ex == null) {
					Toast.makeText(ResetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ResetPasswordActivity.this, IndexActivity.class);
					intent.putExtra("user", et_phone.getText().toString());
					startActivity(intent);
				} else {
					Toast.makeText(ResetPasswordActivity.this,
							"重置密码成功失败：code=" + ex.getErrorCode() + "，错误描述：" + ex.getLocalizedMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void requestSMSCode() {
		String number = et_phone.getText().toString();
		BmobUser user = BmobUser.getCurrentUser(this);
		if (!user.getMobilePhoneNumberVerified()) {
			Toast.makeText(this, "手机号码未绑定", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!number.equals(user.getMobilePhoneNumber())) {
			Toast.makeText(this, "输入的手机号码与绑定的手机号不匹配", Toast.LENGTH_SHORT).show();
			Log.i(TAG, user.getMobilePhoneNumber() + "  " + number);
			return;
		}
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
			return;
		} else {
			timer = new MyCountTimer(60000, 1000);
			timer.start();
			BmobSMS.requestSMSCode(ResetPasswordActivity.this, number, "手机号码一键登录", new RequestSMSCodeListener() {

				@Override
				public void done(Integer arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						Log.i("bmob", "短信id :" + arg0);
						Toast.makeText(ResetPasswordActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
					} else {
						timer.cancel();
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
	}
}
