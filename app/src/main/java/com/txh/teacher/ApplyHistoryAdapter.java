package com.txh.teacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplyHistoryAdapter extends RecyclerView.Adapter<ApplyHistoryAdapter.ApplyHistoryHolder> {

    //需要接收一个申请记录的列表
    List<ApplyDetail> applyDetailList = new ArrayList<>();

    private Context context;

    /**
     * 构造方法，从外部接收参数
     *
     * @param applyDetailList 申请记录的列表
     */
    ApplyHistoryAdapter(List<ApplyDetail> applyDetailList, Context context) {
        this.applyDetailList = applyDetailList;
        this.context = context;
    }

    /**
     * 创建ViewHolder时要展示什么数据？所以这个方法返回值是一个ViewHolder，告诉系统要展示什么数据
     * 而我们要展示的东西是一个xml文件：item_viewpager2.xml，不是一个Java类！
     * 所以我们要解析xml文件：
     * 用LayoutInflater.from(parent.getContext())这个解析器来解析item的xml
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @NotNull
    @Override
    public ApplyHistoryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ApplyHistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applyhistory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApplyHistoryHolder holder, int position) {
        String name = applyDetailList.get(position).getName();
        //用SimpleDateFormat做了时间格式的处理
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(applyDetailList.get(position).getCreateTime());
        Integer status = applyDetailList.get(position).getBuyStatus();
        //渲染item中的组件
        holder.applyhistory_name.setText(name);
        holder.applyhistory_createtime.setText(createTime);
        switch (status) {
            case 0:
                holder.applyhistory_status_iv.setImageResource(R.drawable.applyhistory_reviewing);
                holder.applyhistory_status_tv.setText("审核中");
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
                //用Intent和Bundle给下一个Activity传数据
                Intent intent = new Intent(context, ApplyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", applyDetailList.get(position).getProductName());
                bundle.putString("productNum", applyDetailList.get(position).getProductNum());
                bundle.putString("name", applyDetailList.get(position).getName());
                bundle.putString("type", applyDetailList.get(position).getType());
                bundle.putString("count", applyDetailList.get(position).getCount().toString());
                bundle.putString("price", applyDetailList.get(position).getPrice().toString());
                bundle.putString("detail", applyDetailList.get(position).getDetail());
                bundle.putString("totalMoney", applyDetailList.get(position).getTotalMoney().toString());
                //用SimpleDateFormat做了时间格式的处理
                bundle.putString("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(applyDetailList.get(position).getCreateTime()));
                bundle.putInt("buyStatus", applyDetailList.get(position).getBuyStatus());
                intent.putExtras(bundle);
                //跳转到申购历史的详情页面
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applyDetailList.size();
    }

    /**
     * 自己封装一个ViewHolder内部类，用来解析item的xml文件
     */
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
