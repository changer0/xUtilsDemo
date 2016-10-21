package com.lulu.xutilsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Lulu on 2016/10/21.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mImageUrl;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<String> strings) {
        mContext = context;
        mImageUrl = strings;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mImageUrl.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_image, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.bindView(position, mImageUrl.get(position), mContext);

        return convertView;
    }

    private static class ViewHolder {
        @ViewInject(R.id.item_icon)
        private ImageView mImageView;
        public ViewHolder(View itemView) {
            x.view().inject(this, itemView);
        }

        private void bindView(int position, String url, Context context) {
            ImageOptions.Builder builder = new ImageOptions.Builder();
            //图片加载中动画
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_translate);
            animation.setDuration(200 * position);
            builder.setAnimation(animation);
            ImageOptions options = builder.setFadeIn(true).build();

            x.image().bind(mImageView, url, options);
        }
    }
}
