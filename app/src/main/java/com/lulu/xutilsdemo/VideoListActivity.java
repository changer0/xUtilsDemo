package com.lulu.xutilsdemo;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ListView;

import com.lulu.xutilsdemo.adpaters.VideoListAdapter;
import com.lulu.xutilsdemo.model.VideoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_video_list_activiy)
public class VideoListActivity extends AppCompatActivity {

    private static final String TAG = "VLA";

    @ViewInject(R.id.video_floating_view)
    private SurfaceView mFloatingSurfaceView;

    @ViewInject(R.id.video_list)
    private ListView mListView;

    private List<VideoInfo> mVideoInfoList;
    private VideoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        mVideoInfoList = new ArrayList<>();
        mAdapter = new VideoListAdapter(this, mVideoInfoList);
        mListView.setAdapter(mAdapter);


        // 1. HTTP 工具类使用
        RequestParams requestParams =
                new RequestParams("http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-104&message_cursor=-1&loc_time=1432654641&latitude=40.0522901291784&longitude=116.23490963616668&city=北京&count=30&screen_width=800&iid=2767929313&device_id=2757969807&ac=wifi&channel=baidu2&aid=7&app_name=joke_essay&version_code=400&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6");
        //HTTP的使用


        x.http().get(requestParams, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d(TAG, "onSuccess: " + result);

                mVideoInfoList.clear();

                // 解析视频列表
                try {
                    JSONObject outerData = result.getJSONObject("data");
                    JSONArray innerData = outerData.getJSONArray("data");
                    int len = innerData.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject itemJson = innerData.getJSONObject(i);
                        int type = itemJson.getInt("type");
                        if (type == 1) {
                            JSONObject group = itemJson.getJSONObject("group");
                            JSONObject video = group.getJSONObject("360p_video");
                            VideoInfo info = VideoInfo.createFromJson(video);
                            mVideoInfoList.add(info);
                        }
                    }

                    // Adapter notifyDataSetChanged()
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished");
            }
        });
    }
}
