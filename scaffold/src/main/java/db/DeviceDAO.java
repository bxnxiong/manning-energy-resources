package db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface DeviceDAO {
    @SqlQuery("SELECT charging FROM events WHERE device_id = :device_id")
    public Integer getDevice(@Bind("device_id") String device_id);

    @SqlUpdate("INSERT INTO events (device_id, charging) VALUES (:device_id, :charging)")
    void addDevice(@Bind("device_id") String device_id, @Bind("charging") Integer charging);
}