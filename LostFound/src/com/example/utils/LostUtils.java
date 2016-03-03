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
import cn.bmob.v3.listener.FindListener;

public class LostUtils {

	private Context context;
	private ListView listView;

	public LostUtils(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
	}

	// ��ʱ����������Lost
	public void queryLostAdd() {
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		final ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("�������ڼ�����...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		query.order("+createdAt");// ����ʱ�併��
		query.findObjects(context, new FindListener<Lost>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "��ȡʧ����Ϣʧ��" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<Lost> losts) {
				// TODO Auto-generated method stub
				progress.dismiss();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < losts.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("tv_title", losts.get(i).getTitle());
					map.put("tv_describe", losts.get(i).getDescribe());
					map.put("tv_time", losts.get(i).getCreatedAt());
					map.put("tv_photo", losts.get(i).getPhone());
					map.put("_id", losts.get(i).getObjectId());
					list.add(map);

				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.item_list,
						new String[] { "tv_title", "tv_describe", "tv_time", "tv_photo", "_id" },
						new int[] { R.id.tv_title, R.id.tv_describe, R.id.tv_time, R.id.tv_photo, R.id._id });
				listView.setAdapter(simpleAdapter);
				Toast.makeText(context, "��ȡʧ����Ϣ�ɹ�����" + losts.size() + "������", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// ��ʱ�併������Lost
	public void queryLostReduce() {
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		final ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		progress.setMessage("�������ڼ�����...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		query.order("-createdAt");// ����ʱ�併��
		query.findObjects(context, new FindListener<Lost>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(context, "��ȡʧ����Ϣʧ��" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<Lost> losts) {
				// TODO Auto-generated method stub
				progress.dismiss();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < losts.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("tv_title", losts.get(i).getTitle());
					map.put("tv_describe", losts.get(i).getDescribe());
					map.put("tv_time", losts.get(i).getCreatedAt());
					map.put("tv_photo", losts.get(i).getPhone());
					map.put("_id", losts.get(i).getObjectId());
					list.add(map);

				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.item_list,
						new String[] { "tv_title", "tv_describe", "tv_time", "tv_photo", "_id" },
						new int[] { R.id.tv_title, R.id.tv_describe, R.id.tv_time, R.id.tv_photo, R.id._id });
				listView.setAdapter(simpleAdapter);
				Toast.makeText(context, "��ȡʧ����Ϣ�ɹ�����" + losts.size() + "������", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
