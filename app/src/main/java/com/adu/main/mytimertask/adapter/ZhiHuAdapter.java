package com.adu.main.mytimertask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.adu.main.mytimertask.R;
import com.adu.main.mytimertask.bean.ZhiHuBean;
import com.adu.main.mytimertask.utils.LruImageCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by dell on 2016/6/3.
 */
public class ZhiHuAdapter extends BaseAdapter
{

    private Context context;
    private LayoutInflater inflater;
    private List<ZhiHuBean> mList;
    private NetworkImageView networkImageView;

    private RequestQueue queue;
    private ZhiHuBean bean;


    public ZhiHuAdapter(Context context,List<ZhiHuBean> mList){
        this.context = context;
        this.mList = mList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_zhihu,null);
            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.iv_url);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        bean = mList.get(position);

        queue = Volley.newRequestQueue(context);
        LruImageCache imageCache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(queue,imageCache);

        holder.imageView.setDefaultImageResId(R.drawable.lod);
        holder.imageView.setErrorImageResId(R.drawable.iv_error);
        holder.imageView.setImageUrl(bean.getUrl(),loader);

        holder.tv_type.setText(bean.getType());
        holder.tv_title.setText(bean.getTitle());


        return convertView;
    }

    class ViewHolder{
        private NetworkImageView imageView;
        private TextView tv_title;
        private TextView tv_type;
    }
}
