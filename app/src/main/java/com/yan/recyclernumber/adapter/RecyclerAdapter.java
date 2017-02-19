package com.yan.recyclernumber.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.recyclernumber.R;
import com.yan.recyclernumber.bean.Bean;

import java.util.List;

/**
 * 适配器
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<Bean.DataBeanHeadline> data;
    //图片在右侧
    private static final int TYPE_LISTVIEW_NORMAL = 1;
    //没有图片
    private static final int TYPE_LISTVIEW_NO_PIC = 2;
    //视频
    private static final int TYPE_LISTVIEW_VIODE = 3;
    //三张图片
    private static final int TYPE_LISTVIEW_MORE_PIC = 4;
    //大图片
    private static final int TYPE_LISTVIEW_LARGE_PIC = 5;

    public RecyclerAdapter(Context context, List<Bean.DataBeanHeadline> data) {
        this.context = context;
        this.data = data;
    }

    /**
     *  添加的方法
     */
    public void add(List<Bean.DataBeanHeadline> list){
        data.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断返回那个类型的ViewHodler
        View view;
        if (viewType == TYPE_LISTVIEW_LARGE_PIC) {
            view = LayoutInflater.from(context).inflate(R.layout.type_large_pic_item, parent, false);
            return new ImageBigViewHolder(view);
        } else if (viewType == TYPE_LISTVIEW_MORE_PIC) {
            view = LayoutInflater.from(context).inflate(R.layout.type_multiple_pic_item, parent, false);
            return new ImageMoreViewHolder(view);
        } else if (viewType == TYPE_LISTVIEW_NO_PIC) {
            view = LayoutInflater.from(context).inflate(R.layout.type_no_pic_item, parent, false);
            return new TitleViewHolder(view);
        } else if (viewType == TYPE_LISTVIEW_NORMAL) {
            view = LayoutInflater.from(context).inflate(R.layout.type_normal_item, parent, false);
            return new ImageRightViewHolder(view);
        } else if (viewType == TYPE_LISTVIEW_VIODE) {
            view = LayoutInflater.from(context).inflate(R.layout.type_video_item, parent, false);
            return new VideoViewHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.type_normal_item, parent, false);
            return new ImageRightViewHolder(view);
        }
    }
    //进行赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ImageBigViewHolder){
                ImageBigViewHolder viewHolder = (ImageBigViewHolder)holder;
                viewHolder.tv_news_title.setText(data.get(position).getTitle());
                viewHolder.iv_news_pic.setImageResource(R.mipmap.ic_launcher);
            }else if(holder instanceof ImageMoreViewHolder){
                ImageMoreViewHolder viewHolder = (ImageMoreViewHolder)holder;
                viewHolder.tv_news_title.setText(data.get(position).getTitle());
                viewHolder.iv_news_pic1.setImageResource(R.mipmap.ic_launcher);
                viewHolder.iv_news_pic2.setImageResource(R.mipmap.ic_launcher);
                viewHolder.iv_news_pic3.setImageResource(R.mipmap.ic_launcher);
            }else if(holder instanceof TitleViewHolder){
                TitleViewHolder viewHolder = (TitleViewHolder)holder;
                viewHolder.tv_news_title.setText(data.get(position).getTitle());

            }else if(holder instanceof ImageRightViewHolder){
                ImageRightViewHolder viewHolder = (ImageRightViewHolder)holder;
                viewHolder.tv_news_title.setText(data.get(position).getTitle());
                viewHolder.iv_news_pic.setImageResource(R.mipmap.ic_launcher);
            }else if(holder instanceof VideoViewHolder){
                VideoViewHolder viewHolder = (VideoViewHolder)holder;
                viewHolder.tv_news_title.setText(data.get(position).getTitle());
                viewHolder.iv_news_pic.setImageResource(R.mipmap.ic_launcher);
            }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).isHas_video()) {
            //视频类型
            return TYPE_LISTVIEW_VIODE;
        }
        if (!data.get(position).isHas_image()) {
            //没有图片的
            return TYPE_LISTVIEW_NO_PIC;
        }
        if (data.get(position).getLarge_image_list() != null
                && data.get(position).getLarge_image_list().size() > 0) {
            //大图的
            return TYPE_LISTVIEW_LARGE_PIC;
        }
        if (data.get(position).getImage_list() != null
                && data.get(position).getImage_list().size() > 1) {
            //多张图片
            return TYPE_LISTVIEW_MORE_PIC;
        }
        //默认返回正常的
        return TYPE_LISTVIEW_NORMAL;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 共有五种情况
     */
    //没有图片
    static class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
        }
    }

    //图片在右侧
    static class ImageRightViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        ImageView iv_news_pic;

        public ImageRightViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            iv_news_pic = (ImageView) itemView.findViewById(R.id.iv_news_pic);
        }
    }

    //视频类
    static class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        ImageView iv_news_pic;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            iv_news_pic = (ImageView) itemView.findViewById(R.id.iv_news_pic);
        }
    }

    //大图
    static class ImageBigViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        ImageView iv_news_pic;

        public ImageBigViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            iv_news_pic = (ImageView) itemView.findViewById(R.id.iv_news_pic);
        }
    }

    //多张图
    static class ImageMoreViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        ImageView iv_news_pic2;
        ImageView iv_news_pic1;
        ImageView iv_news_pic3;

        public ImageMoreViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            iv_news_pic2 = (ImageView) itemView.findViewById(R.id.iv_news_pic2);
            iv_news_pic1 = (ImageView) itemView.findViewById(R.id.iv_news_pic1);
            iv_news_pic3 = (ImageView) itemView.findViewById(R.id.iv_news_pic3);
        }
    }
}
