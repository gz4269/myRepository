package com.example.lostfound;

import java.util.Calendar;
import com.example.utils.Found;
import com.example.utils.Lost;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateLostActivity extends Activity {

	private TextView tv_title;
	private Button iv_left;
	private EditText edit_title;
	private EditText edit_photo;
	private EditText edit_describe;
	private Intent intent;
	private Button bt_lostdate;
	private DatePickerDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_info);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		edit_title = (EditText) findViewById(R.id.edit_title);
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		bt_lostdate = (Button) findViewById(R.id.bt_lostdate);

		tv_title.setText("修改发布信息");
		iv_left.setVisibility(View.VISIBLE);

		intent = getIntent();
		String title = intent.getStringExtra("title");
		String phone = intent.getStringExtra("phone");
		String describe = intent.getStringExtra("describe");

		edit_title.setText(title);
		edit_photo.setText(phone);
		edit_describe.setText(describe);

	}

	public void back(View v) {
		finish();
	}
	public void lostDateChoose(View v) {
		final Calendar calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener datestartListener = new DatePickerDialog.OnDateSetListener() {
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
				// Calendar月份是从0开始,所以month要加1
				bt_lostdate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
			}
		};
		dialog = new DatePickerDialog(this, ProgressDialog.THEME_HOLO_LIGHT, datestartListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	public void update(View v) {
		String _id = intent.getStringExtra("_id");
		Log.i("TAG",_id);
		String flag = intent.getStringExtra("flag");
		Log.i("flag", flag);
		if (flag.equals("MyLost")) {
			Lost lost = new Lost();
			lost.setTitle(edit_title.getText().toString());
			lost.setPhone(edit_photo.getText().toString());
			lost.setDescribe(edit_describe.getText().toString());
			lost.update(this, _id, new UpdateListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(UpdateLostActivity.this, "修改发布信息成功", Toast.LENGTH_SHORT).show();
					finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(UpdateLostActivity.this, "修改发布信息失败" + arg1, Toast.LENGTH_SHORT).show();
				}
			});

		} else {
			Found found = new Found();
			found.setTitle(edit_title.getText().toString());
			found.setPhone(edit_photo.getText().toString());
			found.setDescribe(edit_describe.getText().toString());
			found.update(this, _id, new UpdateListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(UpdateLostActivity.this, "修改发布信息成功", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(UpdateLostActivity.this, "修改发布信息失败" + arg1, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
