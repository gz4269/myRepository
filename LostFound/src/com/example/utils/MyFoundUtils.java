package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.lostfound.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyFoundUtils {

	private Context context;
	private ListView listView;

	public MyFoundUtils(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
	}

	// 按时间降序排列MyFound
	public void queryMyFoundAdd() {
		BmobQuery<Found> query = new BmobQuery<Found>();
		BmobUser data = BmobUser.getCurrentUser(context);
		final ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("数据正在加载中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		query.addWhereEqualTo("name", data.getUsername());
		query.order("-createdAt");// 按照时间降序
		query.findObjects(context, new FindListener<Found>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(context, "获取我的招领信息失败" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<Found> founds) {
				// TODO Auto-generated method stub
				progress.dismiss();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < founds.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("tv_title", founds.get(i).getTitle());
					map.put("tv_describe", founds.get(i).getDescribe());
					map.put("tv_time", founds.get(i).getCreatedAt());
					map.put("tv_photo", founds.get(i).getPhone());
					map.put("_id", founds.get(i).getObjectId());
					list.add(map);

				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.item_list,
						new String[] { "tv_title", "tv_describe", "tv_time", "tv_photo", "_id" },
						new int[] { R.id.tv_title, R.id.tv_describe, R.id.tv_time, R.id.tv_photo, R.id._id });
				listView.setAdapter(simpleAdapter);
				Toast.makeText(context, "获取我的招领信息成功，共" + founds.size() + "条数据", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// 按时间降序排列MyFound
	public void queryMyFoundReduce() {
		BmobQuery<Found> query = new BmobQuery<Found>();
		BmobUser data = BmobUser.getCurrentUser(context);
		final ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("数据正在加载中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		query.addWhereEqualTo("name", data.getUsername());
		query.order("+createdAt");// 按照时间降序
		query.findObjects(context, new FindListener<Found>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(context, "获取我的招领信息失败" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<Found> founds) {
				// TODO Auto-generated method stub
				progress.dismiss();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < founds.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("tv_title", founds.get(i).getTitle());
					map.put("tv_describe", founds.get(i).getDescribe());
					map.put("tv_time", founds.get(i).getCreatedAt());
					map.put("tv_photo", founds.get(i).getPhone());
					map.put("_id", founds.get(i).getObjectId());
					list.add(map);

				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.item_list,
						new String[] { "tv_title", "tv_describe", "tv_time", "tv_photo", "_id" },
						new int[] { R.id.tv_title, R.id.tv_describe, R.id.tv_time, R.id.tv_photo, R.id._id });
				listView.setAdapter(simpleAdapter);
				Toast.makeText(context, "获取我的招领信息成功，共" + founds.size() + "条数据", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
