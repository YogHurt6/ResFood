package pro2_jdbc;

import java.sql.ResultSet;

/**
 * ORM映射定制化的接口
 */
public interface RowMapper<T> {

    T mapRow(ResultSet rs,int rowNum) throws Exception;
}
