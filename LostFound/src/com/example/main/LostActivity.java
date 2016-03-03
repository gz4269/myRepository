package com.example.main;

import java.util.HashMap;
import com.example.lostfound.R;
import com.example.utils.LostUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LostActivity extends Activity{

	private TextView tv_title;
	private Button iv_left;
	private Button btn_add;
	private ListView list_lost;
	private LostUtils utils;
	float x1 = 0;
	float x2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lost_activity);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		btn_add = (Button) findViewById(R.id.btn_add);
		list_lost = (ListView) findViewById(R.id.list_lost);

		tv_title.setText("Lost");
		iv_left.setVisibility(View.VISIBLE);
		btn_add.setVisibility(View.VISIBLE);

		list_lost.setOnItemClickListener(new myOnItemClickListener());

		utils = new LostUtils(LostActivity.this, list_lost);

		utils.queryLostReduce();
	}

	public void back(View v) {
		finish();
	}

	public void add(View v) {
		Intent intent = new Intent(LostActivity.this, AddLostActivity.class);
		startActivity(intent);
	}

	public void sortAdd(View v) {
		utils.queryLostAdd();
	}

	public void sortReduce(View v) {
		utils.queryLostReduce();
	}

	private class myOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) list_lost.getItemAtPosition(position);
			String title = map.get("tv_title");
			String phone = map.get("tv_photo");
			String describe = map.get("tv_describe");
			String _id = map.get("_id");
			Intent intent = new Intent(LostActivity.this, DetailedActivity.class);
			intent.putExtra("flag", "Lost");
			intent.putExtra("title", title);
			intent.putExtra("phone", phone);
			intent.putExtra("describe", describe);
			intent.putExtra("_id", _id);
			startActivity(intent);
		}
	}
}
