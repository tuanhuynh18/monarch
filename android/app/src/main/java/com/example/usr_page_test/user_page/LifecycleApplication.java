package com.example.usr_page_test.user_page;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class LifecycleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was Created"+"activity==null   "
                        +(activity==null)+"     activity.isFinishing()  "+(activity.isFinishing())+"    activity.isDestroyed()  "+activity.isDestroyed());
                Long a = System.currentTimeMillis();
                Log.e("Lifecycle",a.toString());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was Started"+"activity==null   "
                        +(activity==null)+"     activity.isFinishing()   "+(activity.isFinishing())+"   activity.isDestroyed()  "+activity.isDestroyed());Long a = System.currentTimeMillis();
                Log.e("Lifecycle",a.toString());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was oResumed"+"activity==null   "
                        +(activity==null)+"activity.isFinishing()   "+(activity.isFinishing())+"activity.isDestroyed() "+activity.isDestroyed());
                Long a = System.currentTimeMillis();
                Log.e("Lifecycle",a.toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was Pauseed"+"activity==null   "
                        +(activity==null)+"activity.isFinishing()   "+(activity.isFinishing())+"activity.isDestroyed()  "+activity.isDestroyed());
                Long a = System.currentTimeMillis();
                Log.e("Lifecycle",a.toString());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was Stoped"+"activity==null    "
                        +(activity==null)+"activity.isFinishing()   "+(activity.isFinishing())+"activity.isDestroyed() "+activity.isDestroyed());
                Long a = System.currentTimeMillis();
                Log.e("Lifecycle",a.toString());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was SaveInstanceState"+"activity==null "
                        +(activity==null)+"activity.isFinishing()   "+(activity.isFinishing())+"activity.isDestroyed()  "+activity.isDestroyed());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e("Lifecycle",activity.getLocalClassName()+" was Destroyed"+"activity==null"
                        +(activity==null)+"  activity.isFinishing()  "+(activity.isFinishing())+"  activity.isDestroyed()"+activity.isDestroyed());
            }
        });
    }
}

