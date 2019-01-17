package com.mobile.im.dao;

import com.mobile.im.enums.editors.PropertyEditor;
import com.mobile.im.util.ObjectUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class BaseDao<T> {
    static Logger log = Logger.getLogger(BaseDao.class);
    static final ConcurrentMap<Class<?>, BeanPropertyRowMapper> mapperCache = new ConcurrentHashMap(64);
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int insertObj(T t) {
        return conversionToColumnValue(t);
    }
    private int conversionToColumnValue(T t) {
        Map<String, String> map = ObjectUtil.getProperty(t);
        Set<String> set = map.keySet();
        int size = set.size();
        String[] columns = new String[size];
        Object[] values = new Object[size];

        int i = 0;
        for (String key : set) {
            columns[i] = key;
            values[i] = map.get(key);
            i++;
        }
        return insertObj(t, columns, values);
    }


    public int insertObj(T t, String[] columns, Object[] colVals) {


        String tableName = getTableName(t);
        String sql = " insert into " + tableName + " (";
        String filedStr = "";
        for (String key : columns) {
            filedStr += (key + ",");
        }
        filedStr = filedStr.substring(0, filedStr.length() - 1);
        filedStr += " ) ";

        String values = " values ( ";
        for (Object object : colVals) {
            values += ("'" + object + "',");
        }
        values = values.substring(0, values.length() - 1);
        values += " ) ";

        sql += (filedStr + values);

        String sqlFinal = sql;


        //判断是否有主键字段
        Field autoIncrement = ObjectUtil.getAutoIncrement(t.getClass());
        if (autoIncrement != null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                    PreparedStatement ps = conn.prepareStatement(sqlFinal, Statement.RETURN_GENERATED_KEYS);
                    return ps;
                }

            }, keyHolder);
            long auto = keyHolder.getKey().longValue();
            try {
                //设置主键值
                ObjectUtil.invokeSetMethod(t, autoIncrement.getName(), new Object[]{auto});
                return 1;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw  new RuntimeException("insert "+tableName+" error.");
            }
        }
        //
        return jdbcTemplate.update(sql);
    }


    public BeanPropertyRowMapper getMapper(T t) {
        BeanPropertyRowMapper mapper = mapperCache.get(t.getClass());
        if (mapper != null) {
            return mapper;
        }
        mapper = new BeanPropertyRowMapper(t.getClass()) {
            @Override
            protected void initBeanWrapper(BeanWrapper bw) {
                super.initBeanWrapper(bw);

                Map<String, Class<?>> enumFields = ObjectUtil.getEnumFields(t);
                Set<String> keys = enumFields.keySet();
                for (String key : keys) {
                    new PropertyEditor(key, enumFields.get(key)).regist(bw);
                }
                //bw.registerCustomEditor(UserStatus.class, "status", new UserStatusEditor());
            }
        };
        mapperCache.put(t.getClass(), mapper);
        return mapper;
    }

    protected String getTableName(T t) {
        return ObjectUtil.getObjTableName(t.getClass());
    }

}
