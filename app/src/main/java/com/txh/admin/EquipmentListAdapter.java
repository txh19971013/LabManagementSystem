package com.txh.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.txh.R;
import com.txh.json.apply.ApplyDetail;
import com.txh.json.equipment.Equipment;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EquipmentListAdapter extends RecyclerView.Adapter<EquipmentListAdapter.EquipmentListHolder> {

    //需要接收一个申请记录的列表
    List<Equipment> equipmentList = new ArrayList<>();

    private Context context;

    /**
     * 构造方法，从外部接收参数
     *
     * @param equipmentList 器材列表
     */
    EquipmentListAdapter(List<Equipment> equipmentList, Context context) {
        this.equipmentList = equipmentList;
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
    public EquipmentListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new EquipmentListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipmentlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EquipmentListHolder holder, int position) {
        String name = equipmentList.get(position).getName();
        String type = equipmentList.get(position).getType();
        String count = equipmentList.get(position).getCount().toString();
        //渲染item中的组件
        holder.equipmentlist_name.setText(name);
        holder.equipmentlist_type.setText(type);
        holder.equipmentlist_count.setText(count);

        //给item设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用Intent和Bundle给下一个Activity传数据
                Intent intent = new Intent(context, EquipmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", equipmentList.get(position).getName());
                bundle.putString("type", equipmentList.get(position).getType());
                bundle.putString("count", equipmentList.get(position).getCount().toString());
                //用SimpleDateFormat做了时间格式的处理
                bundle.putString("buyDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(equipmentList.get(position).getBuyDate()));
                bundle.putString("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(equipmentList.get(position).getCreateTime()));
                bundle.putString("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(equipmentList.get(position).getUpdateTime()));
                intent.putExtras(bundle);
                //跳转到器材详情页面
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    /**
     * 自己封装一个ViewHolder内部类，用来解析item的xml文件
     */
    class EquipmentListHolder extends RecyclerView.ViewHolder {

        TextView equipmentlist_name, equipmentlist_type, equipmentlist_count;

        public EquipmentListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            equipmentlist_name = itemView.findViewById(R.id.equipmentlist_name);
            equipmentlist_type = itemView.findViewById(R.id.equipmentlist_type);
            equipmentlist_count = itemView.findViewById(R.id.equipmentlist_count);
        }
    }
}
