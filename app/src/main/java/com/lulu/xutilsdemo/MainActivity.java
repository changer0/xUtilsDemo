package com.lulu.xutilsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    @ViewInject(R.id.main_image_view)
    private ImageView mImageView;
    @ViewInject(R.id.main_chb_save)
    private CheckBox mCheckBox;
    @ViewInject(R.id.main_text_view)
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        ListView listView = (ListView) findViewById(R.id.main_list);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            urls.add("http://img.blog.csdn.net/20160829134937003");
        }
        ImageAdapter adapter = new ImageAdapter(this, urls);
        listView.setAdapter(adapter);

    }
    @Event(value = {R.id.main_btn_display_img})
    private void btnShowOnClick(View v) {
        Log.d(TAG, "btnShowOnClick: ");
        //TODO: 加载图片
        //x.image().bind(mImageView, "http://img.blog.csdn.net/20160829134937003");
        //1. 下载图片并且指定图像参数的方式 , 圆形图像,圆角矩形
        ImageOptions.Builder builder = new ImageOptions.Builder();
        //ImageOptions options = builder.setCircular(true).setRadius(10).build();//圆的
        ImageOptions options = builder.setSquare(true).setRadius(100)
                .setFadeIn(true)
                .build();//圆角

        x.image().bind(mImageView,
                "http://img.blog.csdn.net/20160829134937003",
                options
                );
    }

    @Event(value = {R.id.main_chb_save},
    type = CompoundButton.OnCheckedChangeListener.class)
    private void chbOnCheckedChange(CompoundButton checkBox, boolean isChecked) {
        mTextView.setText(String.valueOf(isChecked));
    }
}
