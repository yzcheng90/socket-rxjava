package com.sukeintel.iexhibition.mobile.app.works.mvp.utils;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czx on 2017/8/25.
 */

public class TypeForObjectUtil {

    public static String typeForName(String type){
        if(type.equals("ppt")){
            return  "PPT文件";
        }else if(type.equals("doc")){
            return "Word文件";
        }else if(type.equals("exe")){
            return "EXE可执行程序";
        }else if(type.equals("xls")){
            return "Excel文件";
        }else if(type.equals("url")){
            return "Web文件";
        }else if(type.equals("img")){
            return "图片";
        }else if(type.equals("video")){
            return "视频";
        }else if(type.equals("dir")){
            return "新目录";
        }else if(type.equals("list")){
            return "预案";
        }else{
            return "未知类型";
        }
    }

    public static int typeForImgResource(String type){
        if(type.equals("ppt") || type.equals("pptx") ) {
            return R.mipmap.ppt_icon;
        }else if(type.equals("doc") || type.equals("docx") ) {
            return R.mipmap.text_icon;
        }else if(type.equals("exe")) {
            return R.mipmap.exe_icon;
        }else if(type.equals("xls") || type.equals("xlsx") ) {
            return R.mipmap.xls_icon;
        }else if(type.equals("url")) {
            return R.mipmap.web_icon;
        }else if(type.equals("png") || type.equals("jpg") || type.equals("gif") || type.equals("bmp") || type.equals("jpeg") || type.equals("image")  || type.equals("img")) {
            return R.mipmap.image_icon;
        }else if(type.equals("mp4") || type.equals("avi") || type.equals("wmv") || type.equals("mov") || type.equals("video")) {
            return R.mipmap.video_icon;
        }else if(type.equals("list")) {
            return R.mipmap.list_file_icon;
        }else if(type.equals("dir")) {
            return R.mipmap.folder_icon;
        }
        return R.mipmap.default_file_icon;
    }

    public static List<TypeEntity> getTypeList(List<TypeEntity> list){
        List<TypeEntity> typeEntities = new ArrayList<>();
        TypeEntity ppt = new TypeEntity();
        TypeEntity doc = new TypeEntity();
        TypeEntity exe = new TypeEntity();
        TypeEntity xls = new TypeEntity();
        TypeEntity url = new TypeEntity();
        TypeEntity img = new TypeEntity();
        TypeEntity video = new TypeEntity();
        TypeEntity lists = new TypeEntity();
        TypeEntity pdf = new TypeEntity();
        for(int i=0;i<list.size();i++){
            String type = list.get(i).getFile_ext();
            if(type.equals("ppt") || type.equals("pptx") ) {
                ppt.getList().add(list.get(i));
            }else if(type.equals("doc") || type.equals("docx") ) {
                doc.getList().add(list.get(i));
            }else if(type.equals("exe")) {
                exe.getList().add(list.get(i));
            }else if(type.equals("xls") || type.equals("xlsx") ) {
                xls.getList().add(list.get(i));
            }else if(type.equals("url")) {
                url.getList().add(list.get(i));
            }else if(type.equals("png") || type.equals("jpg") || type.equals("gif") || type.equals("bmp") || type.equals("jpeg") || type.equals("image")  || type.equals("img")) {
                img.getList().add(list.get(i));
            }else if(type.equals("mp4") || type.equals("avi") || type.equals("wmv") || type.equals("mov") || type.equals("video")) {
                video.getList().add(list.get(i));
            }else if(type.equals("list")) {
                lists.getList().add(list.get(i));
            }else if(type.equals("pdf")) {
                pdf.getList().add(list.get(i));
            }
        }

        ppt.setFile_name(typeForName("ppt"));
        ppt.setType("ppt");
        ppt.setCount(ppt.getList().size());
        typeEntities.add(ppt);

        doc.setFile_name(typeForName("doc"));
        doc.setType("doc");
        doc.setCount(doc.getList().size());
        typeEntities.add(doc);

        exe.setFile_name(typeForName("exe"));
        exe.setType("exe");
        exe.setCount(exe.getList().size());
        typeEntities.add(exe);

        xls.setFile_name(typeForName("xls"));
        xls.setType("xls");
        xls.setCount(xls.getList().size());
        typeEntities.add(xls);

        url.setFile_name(typeForName("url"));
        url.setType("url");
        url.setCount(url.getList().size());
        typeEntities.add(url);

        img.setFile_name(typeForName("img"));
        img.setType("img");
        img.setCount(img.getList().size());
        typeEntities.add(img);

        video.setFile_name(typeForName("video"));
        video.setType("video");
        video.setCount(video.getList().size());
        typeEntities.add(video);

        lists.setFile_name(typeForName("list"));
        lists.setType("list");
        lists.setCount(lists.getList().size());
        typeEntities.add(lists);

        pdf.setFile_name(typeForName("pdf"));
        pdf.setType("pdf");
        pdf.setCount(pdf.getList().size());
        typeEntities.add(pdf);

        return typeEntities;
    }
}
