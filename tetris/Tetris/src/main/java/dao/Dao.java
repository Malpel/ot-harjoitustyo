package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao {

    void create(String s, int i) throws SQLException;

    List<String> findAll() throws SQLException;
}
