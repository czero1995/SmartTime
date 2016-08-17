package com.example.czero.szzj.SZZJView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.czero.szzj.R;
import com.example.czero.szzj.SZZJData.AmuseTalkListAdapter;
import com.example.czero.szzj.SZZJModel.LiuYanBan;
import com.example.czero.szzj.SZZJUtil.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by zake on 5/28/16.
 */
public class AmuseTalkActivity extends Activity {
    private ListView lvamusetalk;
    private Button btn_amusetalk;
    private EditText etamusetalk;
    private SwipeRefreshLayout refreshamusetalk;
    private AmuseTalkListAdapter amuseTalkListAdapter;
    private List<LiuYanBan> liuYanBen = new ArrayList<LiuYanBan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuyanban);
        lvamusetalk = (ListView) findViewById(R.id.lv_amusetalk);
        etamusetalk = (EditText) findViewById(R.id.etamusetalk);
        btn_amusetalk = (Button) findViewById(R.id.btn_amusetalk);

        amuseTalkListAdapter = new AmuseTalkListAdapter(AmuseTalkActivity.this, (ArrayList<LiuYanBan>) liuYanBen);
        lvamusetalk.setAdapter(amuseTalkListAdapter);
        refreshamusetalk = (SwipeRefreshLayout) findViewById(R.id.refresh_amusetalk);
        refreshamusetalk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshamusetalk.setColorSchemeResources(android.R.color.holo_orange_light);
                if (!Util.isNetworkConnected(getBaseContext())) {
                    Toast.makeText(AmuseTalkActivity.this, "请查询网络连接", Toast.LENGTH_SHORT).show();
                    refreshamusetalk.setRefreshing(false);
                } else {
                    Toast.makeText(AmuseTalkActivity.this, "正在刷新 ", Toast.LENGTH_SHORT).show();
                    getAmuseTalkData();
                    refreshamusetalk.setRefreshing(false);
                }
            }
        });
        getAmuseTalkData();
        btn_amusetalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LiuYanBan liuYanBan = new LiuYanBan();
                String content = etamusetalk.getText().toString();
                if (content.equals("")) {
                    toast("输入点什么吧!(*^__^*) ");
                } else {
                liuYanBan.setAmusecontent(content);
                    liuYanBan.save(AmuseTalkActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("留言成功");
                            etamusetalk.setText("");
                            getAmuseTalkData();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast(s);
                        }
                    });
                    getAmuseTalkData();
                }

            }
        });
    }

    private void getAmuseTalkData() {
        BmobQuery<LiuYanBan> query = new BmobQuery<LiuYanBan>();
        query.order("-createdAt");
        query.findObjects(this, new FindListener<LiuYanBan>() {

            @Override
            public void onSuccess(List<LiuYanBan> object) {
                //toast("查询成功. 共计" + object.size());
                if (object.size() == 0)
                    toast("亲, 你来得太早了点哦");
                else {
                    liuYanBen = object;
                    // 通知Adapter数据更新
                    amuseTalkListAdapter.refresh((ArrayList<LiuYanBan>) liuYanBen);
                    //tradeItemListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int arg0, String msg) {
                toast("查询失败:" + msg);
            }

        });
    }


    private void toast(String toast) {
        Toast.makeText(this, toast,  Toast.LENGTH_SHORT).show();
    }


}
