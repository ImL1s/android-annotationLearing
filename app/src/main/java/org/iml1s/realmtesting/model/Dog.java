package org.iml1s.realmtesting.model;

import com.example.MyAnnotation;

import java.util.Locale;

import io.realm.RealmObject;


/**
 * Created by ImL1s on 2017/5/5.
 * Description:
 */

@MyAnnotation( "1" )
public class Dog extends RealmObject
{
    private String name;
    private int    age;
    private byte   gender;

    public Dog(String name, int age, byte gender)
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Dog() {}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public byte getGender()
    {
        return gender;
    }

    public void setGender(byte gender)
    {
        this.gender = gender;
    }

    @Override
    public String toString()
    {
        return String.format(Locale.CHINESE, "[Dog name:%s age:%d gender:%c]", name, age, gender);
    }
}
