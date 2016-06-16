package com.adu.main.mytimertask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.adu.main.mytimertask.R;


import java.util.List;

/**
 * Created by zly on 2016/2/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private Context context;
    private List<Integer> list;

    public MyAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.iv.setBackgroundResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return (list == null || list.size() == 0) ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

}
