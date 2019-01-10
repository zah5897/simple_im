package com.mobile.im.util;

import com.mobile.im.annotation.AutoIncrementColumn;
import com.mobile.im.annotation.IgnoreColumn;
import com.mobile.im.annotation.Table;
import com.mobile.im.annotation.UuidColumn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author cuisuqiang
 * @version 1.0
 * @说明 对象操纵高级方法
 * @since
 */
public class ObjectUtil {
    /**
     * 返回一个对象的属性和属性值
     */
    public static Map<String, String> getProperty(Object entityName) {
        Map<String, String> map = new HashMap<String, String>();
        Class<? extends Object> c = entityName.getClass();
        // 获得对象属性
        List<Field> allFields = new ArrayList<Field>();
        getClassField(c, allFields);
        for (Field f : allFields) {
            f.setAccessible(true);

            //忽略的字段
            IgnoreColumn ignore = f.getAnnotation(IgnoreColumn.class);
            if (ignore != null) {
                continue;
            }

            //主键递增忽略的字段
            AutoIncrementColumn auto = f.getAnnotation(AutoIncrementColumn.class);
            if (auto != null) {
                continue;
            }

            //uuid主键
            UuidColumn uuidColumn = f.getAnnotation(UuidColumn.class);
            if (uuidColumn != null) {
                map.put(f.getName(), UUIDUtil.getUUID());
                continue;
            }
            Object result = null;
            try {
                result = invokeGetMethod(entityName, f.getName(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {
                if (result instanceof Date) {//日期类型
                    map.put(f.getName(), DateTimeUtil.parse((Date) result));
                } else if (result.getClass().isEnum()) { //枚举类型
                    try {
                        map.put(f.getName(), String.valueOf(invokeOrdinalValue(result)));
                    } catch (Exception e) {
                        map.put(f.getName(), String.valueOf(Integer.MAX_VALUE));
                    }
                } else {
                    map.put(f.getName(), result.toString());
                }
            } else {
                map.put(f.getName(), "");
            }
        }
        return map;
    }

    private static List<Field> getClassField(Class<? extends Object> clazz, List<Field> allFields) {
        if (clazz.getName().equals(Object.class.getName())) {
            return allFields;
        }
        Field fields[] = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (!allFields.contains(f)) {
                allFields.add(f);
            }
        }
        return getClassField(clazz.getSuperclass(), allFields);
    }

    /**
     * 获得对象属性的值
     */
    private static Object invokeGetMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        Method method = null;
        try {
            method = ownerClass.getMethod("get" + methodName);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
            return " can't find 'get" + methodName + "' method";
        }
        return method.invoke(owner);
    }

    public static void invokeSetMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        Method method = null;
        method = ownerClass.getMethod("set" + methodName);
        method.invoke(owner, args);
    }

    public static int invokeOrdinalValue(Object owner) throws Exception {
        Method method = owner.getClass().getMethod("ordinal");
        return (int) method.invoke(owner);
    }

    public static String getObjTableName(Class<?> c) {
        Field field[] = c.getDeclaredFields();
        String tableName = null;
        for (Field f : field) {
            f.setAccessible(true);
            Table table = f.getAnnotation(Table.class);
            if (table != null) {
                tableName = table.name();
                break;
            }
        }
        if (TextUtils.isEmpty(tableName)) {
            tableName = "t_" + c.getSimpleName();
        }
        return tableName;
    }


    public static Field getAutoIncrement(Class<?> c) {
        List<Field> allFields = new ArrayList<Field>();
        getClassField(c, allFields);
        for (Field f : allFields) {
            f.setAccessible(true);
            AutoIncrementColumn autoIncrement = f.getAnnotation(AutoIncrementColumn.class);
            if (autoIncrement != null) {
                return f;
            }
        }
        return null;
    }

    //获取枚举字段及枚举对应的class
    public static Map<String, Class<?>> getEnumFields(Object obj) {
        List<Field> allFields = new ArrayList<Field>();
        getClassField(obj.getClass(), allFields);
        Map<String, Class<?>> enumFields = new HashMap<String, Class<?>>();
        for (Field f : allFields) {
            f.setAccessible(true);
            if (f.getClass().isEnum()) {
                enumFields.put(f.getName(), f.getClass());
            }
        }
        return enumFields;
    }
}
