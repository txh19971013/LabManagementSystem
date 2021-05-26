package com.txh.admin;

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
import com.txh.teacher.ApplyDetailActivity;
import com.txh.teacher.ApplyHistoryAdapter;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplyListAdapter extends RecyclerView.Adapter<ApplyListAdapter.ApplyListHolder> {

    //需要接收一个申请记录的列表
    List<ApplyDetail> applyList = new ArrayList<>();

    private Context context;

    /**
     * 构造方法，从外部接收参数
     *
     * @param applyList 申请记录的列表
     */
    ApplyListAdapter(List<ApplyDetail> applyList, Context context) {
        this.applyList = applyList;
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
    public ApplyListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ApplyListAdapter.ApplyListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApplyListHolder holder, int position) {
        String teacherName = applyList.get(position).getRealname();
        String name = applyList.get(position).getName();
        Integer status = applyList.get(position).getBuyStatus();
        //渲染item中的组件
        holder.applylist_teachername.setText(teacherName);
        holder.applylist_name.setText(name);
        switch (status) {
            case 0:
                holder.applylist_status_iv.setImageResource(R.drawable.applyhistory_reviewing);
                holder.applylist_status_tv.setText("审核中");
                holder.applylist_status_tv.setTextColor(Color.parseColor("#FF8C4D"));
                break;
            case 1:
                holder.applylist_status_iv.setImageResource(R.drawable.applyhistory_accept);
                holder.applylist_status_tv.setText("已通过");
                holder.applylist_status_tv.setTextColor(Color.parseColor("#78C06E"));
                break;
            case 2:
                holder.applylist_status_iv.setImageResource(R.drawable.applyhistory_refuse);
                holder.applylist_status_tv.setText("已拒绝");
                holder.applylist_status_tv.setTextColor(Color.parseColor("#FA5A5A"));
                break;
        }

        //给item设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用Intent和Bundle给下一个Activity传数据
                Intent intent = new Intent(context, CheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", applyList.get(position).getId());
                bundle.putString("productName", applyList.get(position).getProductName());
                bundle.putString("productNum", applyList.get(position).getProductNum());
                bundle.putString("name", applyList.get(position).getName());
                bundle.putString("type", applyList.get(position).getType());
                bundle.putString("count", applyList.get(position).getCount().toString());
                bundle.putString("price", applyList.get(position).getPrice().toString());
                bundle.putString("detail", applyList.get(position).getDetail());
                bundle.putString("totalMoney", applyList.get(position).getTotalMoney().toString());
                bundle.putString("teacherName", applyList.get(position).getRealname());
                //用SimpleDateFormat做了时间格式的处理
                bundle.putString("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(applyList.get(position).getCreateTime()));
                bundle.putInt("buyStatus", applyList.get(position).getBuyStatus());
                intent.putExtras(bundle);
                //跳转到申购历史的详情页面
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applyList.size();
    }

    /**
     * 自己封装一个ViewHolder内部类，用来解析item的xml文件
     */
    class ApplyListHolder extends RecyclerView.ViewHolder {

        TextView applylist_teachername, applylist_name, applylist_status_tv;
        ImageView applylist_status_iv;

        public ApplyListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            applylist_teachername = itemView.findViewById(R.id.applylist_teachername);
            applylist_name = itemView.findViewById(R.id.applylist_name);
            applylist_status_tv = itemView.findViewById(R.id.applylist_status_tv);
            applylist_status_iv = itemView.findViewById(R.id.applylist_status_iv);
        }
    }
}
