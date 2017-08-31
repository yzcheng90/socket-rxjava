package com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.lecture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.ResourceEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.TypeEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.TypeForObjectUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by czx on 2017/1/13.
 */

public class LectureTypeAdapter extends SimpleRecAdapter<TypeEntity, LectureTypeAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public LectureTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_type_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final TypeEntity item = data.get(position);

        //绑定数据
        holder.icon.setImageResource(TypeForObjectUtil.typeForImgResource(item.getType()));
        if(item.getFile_ext() == null || item.getFile_ext().equals("")){
            holder.number.setText(item.getCount()+"项");
            holder.number.setVisibility(View.VISIBLE);
        }else{
            holder.number.setVisibility(View.GONE);
        }

        holder.name.setText(item.getFile_ext()==null||item.getFile_ext().equals("")?TypeForObjectUtil.typeForName(item.getType()):item.getFile_name()+"."+item.getFile_ext());
        holder.layoutId.setOnClickListener((View v)-> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.layout_id)
        LinearLayout layoutId;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
