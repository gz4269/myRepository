package com.example.main;

import java.util.HashMap;
import com.example.lostfound.R;
import com.example.utils.FoundUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FoundActivity extends Activity {

	private TextView tv_title;
	private Button iv_left;
	private Button btn_add;
	private ListView list_found;
	private FoundUtils utils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.found_activity);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_left = (Button) findViewById(R.id.iv_left);
		btn_add = (Button) findViewById(R.id.btn_add);
		list_found = (ListView) findViewById(R.id.list_found);

		tv_title.setText("Found");
		iv_left.setVisibility(View.VISIBLE);
		btn_add.setVisibility(View.VISIBLE);

		list_found.setOnItemClickListener(new myOnItemClickListener());

		utils = new FoundUtils(FoundActivity.this, list_found);

		utils.queryFoundReduce();
	}

	public void back(View v) {
		finish();
	}

	public void add(View v) {
		Intent intent = new Intent(FoundActivity.this, AddFoundActivity.class);
		startActivity(intent);
	}

	public void sortAdd(View v) {
		utils.queryFoundAdd();
	}

	public void sortReduce(View v) {
		utils.queryFoundReduce();
	}

	private class myOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) list_found.getItemAtPosition(position);
			String title = map.get("tv_title");
			String phone = map.get("tv_photo");
			String describe = map.get("tv_describe");
			String _id = map.get("_id");
			Intent intent = new Intent(FoundActivity.this, DetailedActivity.class);
			intent.putExtra("flag", "Found");
			intent.putExtra("title", title);
			intent.putExtra("phone", phone);
			intent.putExtra("describe", describe);
			intent.putExtra("_id", _id);
			startActivity(intent);
		}
	}
}
