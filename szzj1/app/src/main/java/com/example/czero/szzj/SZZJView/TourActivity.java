package com.example.czero.szzj.SZZJView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.czero.szzj.R;
import com.example.czero.szzj.SZZJData.TourListAdapter;
import com.example.czero.szzj.SZZJModel.Tour;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;



public class TourActivity extends Activity implements AdapterView.OnItemClickListener {


	private ListView lvtour;
	private TourListAdapter tourListAdapter;

	private List<Tour> tours = new ArrayList<Tour>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);

		lvtour = (ListView) findViewById(R.id.lv_tour);
		tourListAdapter = new TourListAdapter(TourActivity.this, (ArrayList<Tour>) tours);
		lvtour.setAdapter(tourListAdapter);
		getTourData();
		lvtour.setOnItemClickListener(this);

	}
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent toTourItem = new Intent(TourActivity.this, TourItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("tour", tours.get(position) );
		bundle.putString("tourID", tours.get(position).getObjectId()); //商铺的ID需要单独传递,否则获取到的是null
		toTourItem.putExtras(bundle);
		startActivity(toTourItem);
	}
	private void getTourData(){
		BmobQuery<Tour> query = new BmobQuery<Tour>();
		query.order("-updatedAt");
		query.findObjects(this, new FindListener<Tour>() {
			
		    @Override
		    public void onSuccess(List<Tour> object) {

		        //toast("查询成功. 共计" + object.size());
		    	if(object.size()==0)
		    		toast("亲, 你来得太早了点哦");
		    	else {
		    		tours = object;
			        // 通知Adapter数据更新
			    	tourListAdapter.refresh((ArrayList<Tour>) tours);
			    	//tradeItemListAdapter.notifyDataSetChanged();
				}
		    }
		    
			@Override
			public void onError(int arg0, String msg) {
				toast("查询失败:"+msg);
			}
			
		});
	}
	
	private void toast(String toast) {
		Toast.makeText(this, toast,  Toast.LENGTH_SHORT).show();
	}


}
