package com.example.hotfix;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.example.hotfix.utils.FixDexUtils;

/**
 * @ClassName: Application
 * @Description:
 * @CreateDate: 2020/2/15
 */
public class HotApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        try {
            FixDexUtils.loadFixedDex(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
