package com.example.main;

import java.util.Calendar;
import com.example.lostfound.MyFoundActivity;
import com.example.lostfound.R;
import com.example.utils.Found;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class AddFoundActivity extends Activity {

	private TextView tv_title;
	private Button iv_left;
	private EditText edit_title;
	private EditText edit_photo;
	private EditText edit_describe;
	private Button bt_founddate;
	private DatePickerDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_found);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		edit_title = (EditText) findViewById(R.id.edit_title);
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		bt_founddate = (Button) findViewById(R.id.bt_founddate);

		tv_title.setText("Add My Found");
		iv_left.setVisibility(View.VISIBLE);
	}

	public void back(View v) {
		finish();
	}

	public void foundDateChoose(View v) {
		final Calendar calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener datestartListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
				// Calendar月份是从0开始,所以month要加1
				bt_founddate.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
			}
		};
		dialog = new DatePickerDialog(this, ProgressDialog.THEME_HOLO_LIGHT, datestartListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	public void uploadFound(View v) {
		BmobUser data = BmobUser.getCurrentUser(this);
		String title = edit_title.getText().toString();
		String describe = edit_describe.getText().toString();
		String photo = edit_photo.getText().toString();
		if (TextUtils.isEmpty(title)) {
			Toast.makeText(AddFoundActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(describe)) {
			Toast.makeText(AddFoundActivity.this, "请填写描述", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(photo)) {
			Toast.makeText(AddFoundActivity.this, "请填写手机", Toast.LENGTH_SHORT).show();
			return;
		}
		Found found = new Found();
		found.setTitle(title);
		found.setDescribe(describe);
		found.setPhone(photo);
		found.setName(data.getUsername());
		found.save(AddFoundActivity.this, new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(AddFoundActivity.this, "添加招领信息失败" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(AddFoundActivity.this, "添加招领信息成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(AddFoundActivity.this, MyFoundActivity.class);
				startActivity(intent);
			}
		});
	}
}
