package com.example.lostfound;

import com.example.main.FoundActivity;
import com.example.main.LostActivity;
import com.example.utils.Key;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

public class IndexActivity extends Activity {

	private TextView tv_user;
	private TextView tv_title;
	private TextView tv_phone;
	private Button btn_bind;
	private Button btn_reset;
	String TAG = "TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		tv_user = (TextView) findViewById(R.id.tv_user);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_bind = (Button) findViewById(R.id.btn_bind);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		tv_phone = (TextView) findViewById(R.id.tv_phone);

		tv_title.setText("首页");

		Key key = new Key();
		BmobUser data = BmobUser.getCurrentUser(this);
		tv_user.setText(key.decipheringSource(data.getUsername()));
		verifyBind();
	}

	public void back(View v) {
		finish();
	}

	public void toMyLost(View v) {
		Intent intent = new Intent(IndexActivity.this, MyLostActivity.class);
		startActivity(intent);

	}

	public void toMyFound(View v) {
		Intent intent = new Intent(IndexActivity.this, MyFoundActivity.class);
		startActivity(intent);
	}

	public void toLost(View v) { // 跳转到失物招领页面
		Intent intent = new Intent(IndexActivity.this, LostActivity.class);
		startActivity(intent);
	}

	public void toFound(View v) {
		Intent intent = new Intent(IndexActivity.this, FoundActivity.class);
		startActivity(intent);
	}

	public void bind(View v) {
		Intent intent = new Intent(IndexActivity.this, UserBindPhoneActivity.class);
		startActivity(intent);
	}

	public void resetPasswordByCode(View v) {
		Intent intent = new Intent(IndexActivity.this, ResetPasswordActivity.class);
		startActivity(intent);
	}

	public void exit(View v) {
		BmobUser.logOut(this); // 清除缓存用户对象
		@SuppressWarnings("unused")
		BmobUser currentUser = BmobUser.getCurrentUser(this); // 现在的currentUser是null了
		finish();
	}
	
	public void verifyBind(){
		BmobUser user = BmobUser.getCurrentUser(IndexActivity.this);
		if(user != null){
			if (user.getMobilePhoneNumber() == null || user.getMobilePhoneNumberVerified() == false) {
				tv_phone.setText("未绑定");
				btn_bind.setVisibility(View.VISIBLE);
			} else {
				tv_phone.setText(user.getMobilePhoneNumber().toString());
				btn_reset.setVisibility(View.VISIBLE);
			}
			Toast.makeText(IndexActivity.this, "获取用户成功", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(IndexActivity.this, "获取用户失败", Toast.LENGTH_SHORT).show();
		}
	}
}
