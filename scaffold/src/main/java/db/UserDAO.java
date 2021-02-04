package db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDAO {

    @SqlQuery("SELECT email FROM <table> WHERE ID = :id")
    public String getEmail(@Define("table") String table, @Bind("id") String id);

    @SqlUpdate("INSERT INTO <table> (id, email) VALUES (:id, :email)")
    void addUser(@Define("table") String table, @Bind("id") String id, @Bind("email") String email);
}