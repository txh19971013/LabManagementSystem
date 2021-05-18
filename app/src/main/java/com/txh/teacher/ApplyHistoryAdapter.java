package com.txh.teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.txh.R;
import com.txh.json.apply.ApplyHistory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplyHistoryAdapter extends RecyclerView.Adapter<ApplyHistoryAdapter.ApplyHistoryHolder> {

    //需要接收一个申请记录的列表
    List<ApplyHistory> applyHistoryList = new ArrayList<>();

    /**
     * 构造方法，从外部接收参数
     *
     * @param applyHistoryList 申请记录的列表
     */
    ApplyHistoryAdapter(List<ApplyHistory> applyHistoryList) {
        this.applyHistoryList = applyHistoryList;
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
        String createtime = applyHistoryList.get(position).getCreateTime().toString();
        Integer status = applyHistoryList.get(position).getBuyStatus();
        //渲染组件
        holder.applyhistory_name.setText(name);
        holder.applyhistory_createtime.setText(createtime);
        switch (status) {
            case 0:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_reviewing);
                holder.applyhistory_status_tv.setText("审核中……");
                holder.applyhistory_status_tv.setBackgroundResource(R.color.colorReviewing);
                break;
            case 1:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_accept);
                holder.applyhistory_status_tv.setText("已通过");
                holder.applyhistory_status_tv.setBackgroundResource(R.color.colorAccept);
                break;
            case 2:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_refuse);
                holder.applyhistory_status_tv.setText("已拒绝");
                holder.applyhistory_status_tv.setBackgroundResource(R.color.colorRefuse);
                break;
        }
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
