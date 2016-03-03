package com.example.lostfound;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.utils.Key;
import com.example.utils.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class MainActivity extends Activity {

	private EditText et_account;
	private EditText et_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);

		Intent intent = getIntent();
		if (intent != null) {
			et_account.setText(intent.getStringExtra("account"));
			et_password.setText(intent.getStringExtra("password"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ����
	public void back(View v) {
		finish();
	}

	// �˻������¼
	public void login(View v) {
		login();
	}

	// �ֻ��ŵ�¼
	public void oneKey(View v) {
		Intent intent = new Intent(MainActivity.this, LoginOneKeyActivity.class);
		startActivity(intent);
	}

	// ע��
	public void register(View v) {
		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	@SuppressLint("InlinedApi")
	private void login() {
		Key key = new Key();
		final String account = et_account.getText().toString();
		String password = et_password.getText().toString();
		if (TextUtils.isEmpty(account)) {
			Toast.makeText(this, "�˺Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progress = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("�������ڼ�����...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		// V3.3.9�ṩ���µĵ�¼��ʽ���ɴ��û���/����/�ֻ�����
		BmobUser.loginByAccount(this, key.encryptSource(account), password, new LogInListener<User>() {

			@Override
			public void done(User user, BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if (ex == null) {
					Toast.makeText(MainActivity.this, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MainActivity.this, IndexActivity.class);
					startActivity(intent);
					finish();
				} else {
					progress.dismiss();
					Toast.makeText(MainActivity.this,
							"��¼ʧ�ܣ�code=" + ex.getErrorCode() + "������������" + ex.getLocalizedMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
}
