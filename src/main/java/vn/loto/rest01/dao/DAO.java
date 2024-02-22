package vn.loto.rest01.dao;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T,TSearch,TId> {
    protected Connection connection;
    public DAO() {
        connection = SQLServletConnect.getInstance();
    }
    public abstract T getByID(TId id);
    public abstract ArrayList<T> getAll();
    public abstract ArrayList<T> getLike(TSearch object);
    public abstract boolean insert(T object);
    public abstract boolean update(T object);
    public abstract boolean delete(T object);
}
