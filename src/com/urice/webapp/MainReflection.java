package com.urice.webapp;

import com.urice.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Name_1");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));

        field.set(r, "new_uuid");
        System.out.println(r);

        Resume r2 = new Resume("uuid1_test");
        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(r2));
    }
}
