package config;

import org.hibernate.EmptyInterceptor;

/**
 * Hibernate 拦截器
 * Created by huangxiao on 2017/5/16.
 */
public class TableInterceptor extends EmptyInterceptor {

    private String targetTableName;     // 母表名
    private String tempTableName;       // 子表名

    public TableInterceptor() {
    }

    public TableInterceptor(String targetTableName, String tempTableName) {
        this.targetTableName = targetTableName;
        this.tempTableName = tempTableName;
    }

    @Override
    public String onPrepareStatement(String sql) {
        sql = sql.replaceAll(targetTableName, tempTableName);
        return sql;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTempTableName() {
        return tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

}
