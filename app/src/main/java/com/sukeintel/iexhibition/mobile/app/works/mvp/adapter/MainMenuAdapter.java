package com.sukeintel.iexhibition.mobile.app.works.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by czx on 2017/1/13.
 */

public class MainMenuAdapter extends SimpleRecAdapter<MenuEntity, MainMenuAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public MainMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_menu_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final MenuEntity item = data.get(position);

        //绑定数据
        holder.icon.setImageResource(item.getIcon());
        //ILFactory.getLoader().loadNet(holder.icon, AppConstant.API_BASE_URL+item.getICON(),-1, null);
        holder.name.setText(item.getName());
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
        @BindView(R.id.layout_id)
        LinearLayout layoutId;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
