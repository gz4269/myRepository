package com.example.lostfound;

import com.example.utils.Key;
import com.example.utils.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity {

	private EditText et_account;
	private EditText et_password;
	private EditText et_pwd_again;
	private EditText et_qq;
	private TextView tv_title;
	private Button iv_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		et_pwd_again = (EditText) findViewById(R.id.et_pwd_again);
		et_qq = (EditText) findViewById(R.id.et_qq);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		tv_title.setText("ע��");
		iv_left.setVisibility(View.VISIBLE);
	}

	// ע��
	public void register(View v) {
		register();
	}

	// ����
	public void back(View v) {
		finish();
	}

	@SuppressLint("InlinedApi")
	private void register() {
		Key key = new Key();
		final String account = et_account.getText().toString();
		final String password = et_password.getText().toString();
		String pwdagain = et_pwd_again.getText().toString();
		int qq = Integer.parseInt(et_qq.getText().toString());
		if (TextUtils.isEmpty(account)) {
			Toast.makeText(this, "�˺Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(pwdagain)) {
			Toast.makeText(this, "ȷ�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!password.equals(pwdagain)) {
			Toast.makeText(this, "ǰ��������������벻��ȷ������������", Toast.LENGTH_SHORT).show();
			et_password.setText("");
			et_pwd_again.setText("");
			return;
		}
		if (TextUtils.isEmpty(qq + "")) {
			Toast.makeText(this, "qq�Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("����ע����...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		User user = new User();
		user.setUsername(key.encryptSource(account));
		user.setPassword(password);
		user.setQq(qq);
		user.signUp(RegisterActivity.this, new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(RegisterActivity.this, "ע��ʧ��" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
				intent.putExtra("account", account);
				intent.putExtra("password", password);
				startActivity(intent);
				finish();
			}
		});
	}
}
