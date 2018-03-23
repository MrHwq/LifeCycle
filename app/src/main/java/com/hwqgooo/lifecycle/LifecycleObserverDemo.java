package com.hwqgooo.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Created by hpq on 2018/3/22.
 */

public class LifecycleObserverDemo implements LifecycleObserver {
//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    void onAny(LifecycleOwner owner, Lifecycle.Event event) {
//        System.out.println("onAny:" + owner + ",Event:" + event.name());
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        System.out.println("onCreate:" + owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        System.out.println("onDestroy" + owner);
    }
}
