package com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.power;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.CameraEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.ResourceEntity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by czx on 2017/1/13.
 */

public class LampAdapter extends SimpleRecAdapter<ResourceEntity, LampAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public LampAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_monitoring_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ResourceEntity item = data.get(position);

        //绑定数据
        holder.icon.setImageResource(R.mipmap.lamp_icon);
        holder.name.setText(item.getName());
        holder.address.setText(item.getAddress());
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
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.layout_id)
        LinearLayout layoutId;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
