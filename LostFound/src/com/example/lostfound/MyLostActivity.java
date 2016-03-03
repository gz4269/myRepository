package com.example.lostfound;

import java.util.HashMap;
import com.example.main.AddLostActivity;
import com.example.main.DetailedActivity;
import com.example.utils.Lost;
import com.example.utils.MyLostUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.DeleteListener;

public class MyLostActivity extends Activity{

	private TextView tv_title;
	private Button iv_left;
	private ListView list_mylost;
	private Button btn_add;
	private MyLostUtils utils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylost);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		list_mylost = (ListView) findViewById(R.id.list_mylost);
		btn_add = (Button) findViewById(R.id.btn_add);

		tv_title.setText("My Lost");
		iv_left.setVisibility(View.VISIBLE);
		btn_add.setVisibility(View.VISIBLE);

		list_mylost.setOnItemLongClickListener(new myOnItemLongClickListener());
		list_mylost.setOnItemClickListener(new myOnItemClickListener());

		utils = new MyLostUtils(MyLostActivity.this, list_mylost);

		utils.queryMyLostReduce();
	}

	// 当关闭一个Activity时，回到该Activity时调用此方法
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		utils.queryMyLostReduce();
	}

	public void back(View v) {
		finish();
	}

	public void add(View v) {
		Intent intent = new Intent(MyLostActivity.this, AddLostActivity.class);
		startActivity(intent);
	}

	public void sortAdd(View v) {
		utils.queryMyLostAdd();
	}

	public void sortReduce(View v) {
		utils.queryMyLostReduce();
	}

	private class myOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) list_mylost.getItemAtPosition(position);
			String title = map.get("tv_title");
			final String _id = map.get("_id");
			AlertDialog.Builder builder = new AlertDialog.Builder(MyLostActivity.this);
			builder.setTitle("删除提示");
			builder.setMessage("删除title为" + title + "的失物消息？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Lost del = new Lost();
					del.setObjectId(_id);
					del.delete(MyLostActivity.this, new DeleteListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(MyLostActivity.this, "删除失败" + arg1, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(MyLostActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
							onResume();
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
			return false;
		}

	}

	private class myOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) list_mylost.getItemAtPosition(position);
			String title = map.get("tv_title");
			String phone = map.get("tv_photo");
			String describe = map.get("tv_describe");
			String _id = map.get("_id");
			Intent intent = new Intent(MyLostActivity.this, DetailedActivity.class);
			intent.putExtra("flag", "MyLost");
			intent.putExtra("title", title);
			intent.putExtra("phone", phone);
			intent.putExtra("describe", describe);
			intent.putExtra("_id", _id);
			startActivity(intent);
		}

	}
}
