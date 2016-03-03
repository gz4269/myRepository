package com.example.lostfound;

import com.example.utils.User;
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
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class LoginOneKeyActivity extends Activity {

	private EditText et_phone;
	private EditText et_verify_code;
	private Button btn_send;
	private TextView tv_title;
	private Button iv_left;
	private MyCountTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_onekey);

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_verify_code = (EditText) findViewById(R.id.et_verify_code);
		btn_send = (Button) findViewById(R.id.btn_send);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);

		tv_title.setText("手机号码一键登录");
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

	// 返回上一级
	public void back(View v) {
		finish();
	}

	// 发送验证码
	public void sendCode(View v) {
		requestSMSCode();
	}

	// 登录
	public void login(View v) {
		oneKeyLogin();
	}

	@SuppressLint("InlinedApi")
	private void oneKeyLogin() {
		final String phone = et_phone.getText().toString();
		final String code = et_verify_code.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(code)) {
			Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progress = new ProgressDialog(LoginOneKeyActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("正在验证短信验证码...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		BmobUser.signOrLoginByMobilePhone(this, phone, code, new LogInListener<User>() {

			@Override
			public void done(User user, BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if (ex == null) {
					Toast.makeText(LoginOneKeyActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginOneKeyActivity.this, IndexActivity.class);
					// SharedPreferences.Editor editor =
					// getSharedPreferences("data",0).edit();
					// BmobUser user1 =
					// BmobUser.getCurrentUser(LoginOneKeyActivity.this);
					// editor.putString("name", user1.getUsername());
					// editor.commit();
					startActivity(intent);
					finish();
				} else {
					progress.dismiss();
					Toast.makeText(LoginOneKeyActivity.this,
							"登录失败：code=" + ex.getErrorCode() + "，错误描述：" + ex.getLocalizedMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			}

		});
	}

	// 登录请求
	private void requestSMSCode() {
		String number = et_phone.getText().toString();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
			return;
		} else {
			timer = new MyCountTimer(60000, 1000);
			timer.start();
			BmobSMS.requestSMSCode(LoginOneKeyActivity.this, number, "手机号码一键登录", new RequestSMSCodeListener() {

				@Override
				public void done(Integer arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						Log.i("bmob", "短信id :" + arg0);
						Toast.makeText(LoginOneKeyActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
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
