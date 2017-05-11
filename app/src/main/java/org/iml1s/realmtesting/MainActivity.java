package org.iml1s.realmtesting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.JPHelloAnnotation;
import com.example.MyAnnotation;

import org.iml1s.realmtesting.model.Dog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

@JPHelloAnnotation()
@MyAnnotation( "Hello" )
public class MainActivity extends AppCompatActivity
{
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                insert(new Dog("豆豆", 8, (byte) 'f'));
                List<Dog> dogs = select();

                for (Dog dog : dogs)
                {
                    Log.d(MainActivity.class.getSimpleName(), dog.toString());
                }
    }

    void insert(Dog dog)
    {
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();
    }

    List<Dog> select()
    {
        RealmResults<Dog> results = realm.where(Dog.class).findAll();
        return realm.copyFromRealm(results);
    }
}
