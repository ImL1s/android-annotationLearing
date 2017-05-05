package org.iml1s.realmtesting;

import android.app.Application;

import com.example.JPHelloAnnotation;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ImL1s on 2017/5/5.
 * Description:
 */

@JPHelloAnnotation
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
