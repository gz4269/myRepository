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
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class UserBindPhoneActivity extends Activity {

	private TextView tv_title;
	private Button iv_left;
	private TextView tv_send;
	private MyCountTimer timer;
	private EditText et_number;
	private EditText et_input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		tv_send = (TextView) findViewById(R.id.tv_send);
		et_number = (EditText) findViewById(R.id.et_number);
		et_input = (EditText) findViewById(R.id.et_input);

		tv_title.setText("�󶨵绰����");
		iv_left.setVisibility(View.VISIBLE);
	}

	class MyCountTimer extends CountDownTimer {

		public MyCountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			tv_send.setText((millisUntilFinished / 1000) + "����ط�");
		}

		@Override
		public void onFinish() {
			tv_send.setText("���·�����֤��");
		}
	}

	public void back(View v) {
		finish();
	}

	public void send(View v) {
		requestSMSCode();
	}

	public void bind(View v) {
		verifyOrBind();
	}

	private void requestSMSCode() {
		String number = et_number.getText().toString();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "�ֻ����벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		} else {
			timer = new MyCountTimer(60000, 1000);
			timer.start();
			BmobSMS.requestSMSCode(UserBindPhoneActivity.this, number, "�ֻ�����һ����¼", new RequestSMSCodeListener() {

				@Override
				public void done(Integer arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						Log.i("bmob", "����id :" + arg0);
						Toast.makeText(UserBindPhoneActivity.this, "��֤�뷢�ͳɹ�", Toast.LENGTH_SHORT).show();
					} else {
						timer.cancel();
					}
				}
			});
		}
	}

	@SuppressLint("InlinedApi")
	private void verifyOrBind() {
		final String phone = et_number.getText().toString();
		String code = et_input.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "�ֻ����벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(code)) {
			Toast.makeText(this, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progress = new ProgressDialog(UserBindPhoneActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("������֤������֤��...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		BmobSMS.verifySmsCode(this, phone, code, new VerifySMSCodeListener() {

			@Override
			public void done(BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if (ex == null) {
					Toast.makeText(UserBindPhoneActivity.this, "�ֻ���������֤", Toast.LENGTH_LONG).show();
					bindMobilePhone(phone);
				} else {
					Toast.makeText(UserBindPhoneActivity.this,
							"��֤ʧ�ܣ�code=" + ex.getErrorCode() + "������������" + ex.getLocalizedMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	private void bindMobilePhone(String phone) {
		final User user = new User();
		user.setMobilePhoneNumber(phone);
		user.setMobilePhoneNumberVerified(true);
		User cur = BmobUser.getCurrentUser(UserBindPhoneActivity.this, User.class);
		user.update(UserBindPhoneActivity.this, cur.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(UserBindPhoneActivity.this, "�ֻ��Ű󶨳ɹ�", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(UserBindPhoneActivity.this, IndexActivity.class);
				startActivity(intent);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(UserBindPhoneActivity.this, "�ֻ��Ű�ʧ��:" + arg0 + "-" + arg1, Toast.LENGTH_LONG).show();
			}
		});
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
