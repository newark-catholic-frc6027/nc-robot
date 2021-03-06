package frc.team6027.robot.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DatahubNetworkTableImpl implements Datahub {
    private final Logger logger = LogManager.getLogger(getClass());

    private String tableName;
    private NetworkTable networkTable;
    private Map<String, NetworkTableEntry> ntEntryCache = new HashMap<>();


    public DatahubNetworkTableImpl(String tableName) {
        this.tableName = tableName;
        this.networkTable = this.getNetworkTable();
    }

    @Override
    public String getString(String key) {
        return this.getString(key, "");
    }


    @Override
    public String getString(String key, String defaultValue) {
        NetworkTableEntry entry = this.getEntry(key);
        String returnValue = defaultValue;
        if (entry != null) {
            returnValue = entry.getString(defaultValue);
        }
        return returnValue;
    }

    @Override
    public Float getFloat(String key) {
        return this.getDouble(key).floatValue();
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return this.getDouble(key, (double) defaultValue).floatValue();
    }

    @Override
    public Double getDouble(String key) {
        return this.getDouble(key, -1.0);
    }
    
    @Override
    public Double getDouble(String key, Double defaultValue) {
        NetworkTableEntry entry = this.getEntry(key);
        Double returnValue = defaultValue;
        if (entry != null) {
            returnValue =  entry.getDouble(defaultValue);
        }
        return returnValue;
    }

    @Override
    public Number getNumber(String key) {
        return this.getNumber(key, -1.0);
    }

    @Override
    public Number getNumber(String key, Number defaultValue) {
        NetworkTableEntry entry = this.getEntry(key);
        Number returnValue = defaultValue;
        if (entry != null) {
            returnValue =  entry.getNumber(defaultValue);
        }
        return returnValue;
    }

    protected NetworkTable getNetworkTable() {
        if (this.networkTable == null) {
            this.networkTable = NetworkTableInstance.getDefault().getTable(this.tableName);
        }

        return this.networkTable;
    }
    
    protected NetworkTableEntry getEntry(String key) {
        NetworkTableEntry entry = null;
        if (! this.ntEntryCache.containsKey(key)) {
            NetworkTable nt = this.getNetworkTable();
            if (nt != null) {
                entry = nt.getEntry(key);
                this.ntEntryCache.put(key, entry);
            } else {
                logger.warn("Failed to get entry '{}' from network table '{}'", key, this.tableName);
            }
        } else {
            entry = this.ntEntryCache.get(key);
        }

        return entry;
    }

    @Override
    public Map<String,Object> getAll() {
        throw new UnsupportedOperationException("getAll not implemented yet");
    }

    @Override
    public void put(String key, String value) {
        NetworkTableEntry entry = this.getEntry(key);
        entry.setString(value);
    }

    @Override
    public void put(String key, Double value) {
        NetworkTableEntry entry = this.getEntry(key);
        entry.setDouble(value);
    }

    @Override
    public void put(String key, Number value) {
        NetworkTableEntry entry = this.getEntry(key);
        entry.setNumber(value);
    }

    @Override
    public void put(Map<String, Object> values, boolean replace) {
        throw new UnsupportedOperationException("put(Map, boolean) not implemented yet");
    }

    @Override
    public String getName() {
        return this.tableName;
    }



}