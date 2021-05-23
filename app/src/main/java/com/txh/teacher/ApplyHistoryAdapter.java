package com.txh.teacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.txh.R;
import com.txh.json.apply.ApplyDetail;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApplyHistoryAdapter extends RecyclerView.Adapter<ApplyHistoryAdapter.ApplyHistoryHolder> {

    //需要接收一个申请记录的列表
    List<ApplyDetail> applyHistoryList = new ArrayList<>();

    private Context context;

    /**
     * 构造方法，从外部接收参数
     *
     * @param applyHistoryList 申请记录的列表
     */
    ApplyHistoryAdapter(List<ApplyDetail> applyHistoryList, Context context) {
        this.applyHistoryList = applyHistoryList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ApplyHistoryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ApplyHistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applyhistory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApplyHistoryHolder holder, int position) {
        String name = applyHistoryList.get(position).getName();
        String createTime = applyHistoryList.get(position).getCreateTime().toString();
        Integer status = applyHistoryList.get(position).getBuyStatus();
        //渲染item中的组件
        holder.applyhistory_name.setText(name);
        holder.applyhistory_createtime.setText(createTime);
        switch (status) {
            case 0:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_reviewing);
                holder.applyhistory_status_tv.setText("审核中……");
                holder.applyhistory_status_tv.setTextColor(Color.parseColor("#FF8C4D"));
                break;
            case 1:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_accept);
                holder.applyhistory_status_tv.setText("已通过");
                holder.applyhistory_status_tv.setTextColor(Color.parseColor("#78C06E"));
                break;
            case 2:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_refuse);
                holder.applyhistory_status_tv.setText("已拒绝");
                holder.applyhistory_status_tv.setTextColor(Color.parseColor("#FA5A5A"));
                break;
        }

        //给item设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到申购历史的详情页面
                context.startActivity(new Intent(context, ApplyDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return applyHistoryList.size();
    }

    class ApplyHistoryHolder extends RecyclerView.ViewHolder {

        TextView applyhistory_name, applyhistory_createtime, applyhistory_status_tv;
        ImageView applyhistory_status_iv;

        public ApplyHistoryHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            applyhistory_name = itemView.findViewById(R.id.applyhistory_name);
            applyhistory_createtime = itemView.findViewById(R.id.applyhistory_createtime);
            applyhistory_status_tv = itemView.findViewById(R.id.applyhistory_status_tv);
            applyhistory_status_iv = itemView.findViewById(R.id.applyhistory_status_iv);
        }
    }
}
