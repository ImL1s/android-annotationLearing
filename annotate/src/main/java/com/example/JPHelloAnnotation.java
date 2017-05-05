package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ImL1s on 2017/5/5.
 * Description:
 */

@Retention( RetentionPolicy.CLASS )
@Target( ElementType.TYPE )
public @interface JPHelloAnnotation
{

}
