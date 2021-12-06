package com.example.usr_page_test.user_page;

import android.content.Context;
import android.os.FileUtils;
import android.util.Log;

import com.google.gson.Gson;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class JsonUtils {

    public static allActivity analysisJsonFile(Context context,String fileName){
        String content = fileName;
        Gson gson = new Gson();
        allActivity entity = gson.fromJson(content, allActivity.class);
        Log.e("enti",entity.allTagsList.toString());
        return  entity;

    }
}