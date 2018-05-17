/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.githang.rankgithubchina.support;

import rx.Subscriber;
import rx.observers.SafeSubscriber;

/**
 * SafeSubscriber with tag.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-11-29 1.3
 */
public class TagSafeSubscriber<T> extends SafeSubscriber<T> {
    private Object mTag;

    public TagSafeSubscriber(Subscriber<? super T> actual) {
        super(actual);
    }

    public TagSafeSubscriber<T> tag(Object tag) {
        mTag = tag;
        return this;
    }

    public Object getTag() {
        return mTag;
    }

    public TagSafeSubscriber<T> manageOn(SubscriberWatcher watcher) {
        watcher.watchSubscription(this);
        return this;
    }

    @Override
    public void onStart() {
        getActual().onStart();
    }
}
