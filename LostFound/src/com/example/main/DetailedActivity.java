package com.example.main;

import com.example.lostfound.R;
import com.example.lostfound.UpdateLostActivity;
import com.example.utils.Found;
import com.example.utils.Lost;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.DeleteListener;

public class DetailedActivity extends Activity {

	private TextView title;
	private TextView phone;
	private TextView describe;
	@SuppressWarnings("unused")
	private TextView tv_title;
	private Button iv_left;
	private Button btn_delete;
	private String _id;
	private String flag;
	private Button btn_updateInfo;
	private Button delete;
	private Button btn_call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed);

		title = (TextView) findViewById(R.id.title);
		phone = (TextView) findViewById(R.id.phone);
		describe = (TextView) findViewById(R.id.describe);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		btn_delete = (Button) findViewById(R.id.btn_delete);
		btn_updateInfo = (Button) findViewById(R.id.btn_updateInfo);
		delete = (Button) findViewById(R.id.delete);
		btn_call = (Button) findViewById(R.id.btn_call);

		iv_left.setVisibility(View.VISIBLE);

		Intent intent = getIntent();
		flag = intent.getStringExtra("flag");

		if ("MyLost".equals(flag) || "MyFound".equals(flag)) {
			btn_delete.setVisibility(View.VISIBLE);
			btn_updateInfo.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
			btn_call.setVisibility(View.INVISIBLE);
		}
		_id = intent.getStringExtra("_id");
		title.setText(intent.getStringExtra("title"));
		phone.setText(intent.getStringExtra("phone"));
		describe.setText(intent.getStringExtra("describe"));
	}

	public void back(View v) {
		finish();
	}

	public void delete(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(DetailedActivity.this);
		builder.setTitle("删除提示");
		builder.setMessage("删除该发布信息？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Lost del = new Lost();
				del.setObjectId(_id);
				del.delete(DetailedActivity.this, new DeleteListener() {

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(DetailedActivity.this, "删除失败" + arg1, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(DetailedActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
						finish();
					}
				});
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public void updateInfo(View v) {
		Intent intent = new Intent(DetailedActivity.this, UpdateLostActivity.class);
		intent.putExtra("flag", flag);
		intent.putExtra("title", title.getText());
		intent.putExtra("phone", phone.getText());
		intent.putExtra("describe", describe.getText());
		intent.putExtra("_id", _id);
		startActivity(intent);
	}

	public void confirm(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(DetailedActivity.this);
		builder.setTitle("提示");
		builder.setMessage("确认找回？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if ("MyLost".equals(flag)) {
					Lost del = new Lost();
					del.setObjectId(_id);
					del.delete(DetailedActivity.this, new DeleteListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(DetailedActivity.this, "删除失败" + arg1, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(DetailedActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				} else {
					Found del = new Found();
					del.setObjectId(_id);
					del.delete(DetailedActivity.this, new DeleteListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(DetailedActivity.this, "删除失败" + arg1, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(DetailedActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	public void call(View v) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.getText().toString()));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
