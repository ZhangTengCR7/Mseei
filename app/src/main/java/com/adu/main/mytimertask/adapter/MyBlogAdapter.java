package com.adu.main.mytimertask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.adu.main.mytimertask.R;

import java.util.List;

/**
 * Created by dell on 2016/5/26.
 */
public class MyBlogAdapter extends RecyclerView.Adapter<MyBlogAdapter.MyViewHolder>
{

    private List<String> dataList;

    public MyBlogAdapter(List<String> dataList){
        this.dataList=dataList;
    }

    @Override
    public MyBlogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder myViewHolder=new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_myblog,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyBlogAdapter.MyViewHolder holder, int position)
    {
        holder.tv.setText(dataList.get(position));
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv);
        }
    }


    @Override
    public int getItemCount()
    {
        return (dataList==null||dataList.size()==0)?0:dataList.size();
    }
}
