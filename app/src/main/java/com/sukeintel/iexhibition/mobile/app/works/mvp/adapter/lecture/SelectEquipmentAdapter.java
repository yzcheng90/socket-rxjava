package com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.lecture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by czx on 2017/1/13.
 */

public class SelectEquipmentAdapter extends SimpleRecAdapter<EquipmentEntity, SelectEquipmentAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;
    public List<ViewHolder> viewHolders = new ArrayList<>();
    public EquipmentEntity entity = null;


    public SelectEquipmentAdapter(Context context) {
        super(context);
    }

    public void setEntity(EquipmentEntity entity){
        this.entity = entity;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_equipment_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final EquipmentEntity item = data.get(position);
        viewHolders.add(holder);
        if(entity != null){
            if(item.getMediabox_id().equals(entity.getMediabox_id())){
                holder.setSelected(true);
            }
        }
        //绑定数据
        holder.icon.setImageResource(R.mipmap.mainframe);
        holder.name.setText(item.getName());
        holder.ip.setText(item.getIp());
        holder.layoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.ip)
        TextView ip;
        @BindView(R.id.selected)
        ImageView selected;
        @BindView(R.id.layout_id)
        FrameLayout layoutId;

        boolean aBoolean = false;

        public void setSelected(boolean paramBoolean){
            this.aBoolean = paramBoolean;
            if (this.aBoolean){
                this.aBoolean = true;
                this.selected.setVisibility(View.VISIBLE);
            }else{
                this.aBoolean = false;
                this.selected.setVisibility(View.GONE);
            }
        }


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
