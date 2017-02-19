package com.yan.recyclernumber;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yan.recyclernumber.adapter.RecyclerAdapter;
import com.yan.recyclernumber.bean.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  多条目加载  上拉刷新
 */
public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener{
    private String path = "http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1457659690&bd_latitude=4.9E-324&bd_longitude=4.9E-324&bd_loc_time=1457672153&loc_mode=5&lac=4527&cid=28883&iid=3839760160&device_id=12246291682&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=iToolsAVM&os_api=19&os_version=4.4.4&uuid=352284045861006&openudid=84c1c7b192991cc6";
    private XRecyclerView recycler_view;
    private List<Bean.DataBeanHeadline> listData = new ArrayList<>();
    private int STATE_LOAD = 1;
    private int STATE_Re = 1;
    private int count = 0;
    private Handler han  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    stop();
                    Bean bean = (Bean) msg.obj;
                    List<Bean.DataBeanHeadline> data = bean.getData();
                    Log.i("TAG", "handleMessage: =============="+data.size());
                    setAdapter(data);
                    break;
            }
        }
    };
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = (XRecyclerView) findViewById(R.id.recycler_view);
        getData();
    }
    //进行是配
    private void setAdapter( List<Bean.DataBeanHeadline> data){
        if(STATE_Re == count){
            listData.clear();
        }
        listData.addAll(data);
        if(recyclerAdapter == null){
            recyclerAdapter = new RecyclerAdapter(this,listData);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(layoutManager);
            recycler_view.setAdapter(recyclerAdapter);
        }else{
            Log.i("TAG", "setAdapter: =======走了====");
            recyclerAdapter.add(listData);
        }
        recycler_view.setLoadingListener(this);
    }
    //获取数据
    private void getData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "没有获取到数据", Toast.LENGTH_SHORT).show();
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                Log.i("TAG", "onResponse: lllllllllllll00"+s);
                Bean bean = gson.fromJson(s, Bean.class);
                Message message = new Message();
                message.obj = bean;
                han.sendMessage(message);
            }
        });
    }

    @Override
    public void onRefresh() {
        count = STATE_Re;
        getData();
    }

    @Override
    public void onLoadMore() {
        count = STATE_LOAD;
        getData();
    }
    //停止刷新动作
    private void stop(){
        recycler_view.loadMoreComplete();
        recycler_view.refreshComplete();
    }
}
