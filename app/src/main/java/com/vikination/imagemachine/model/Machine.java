package com.vikination.imagemachine.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Machine{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "machine_id")
    public String machineId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "qrnumber")
    public String qrNumber;

    @ColumnInfo(name = "lastmodified")
    public String lastModified = "";

    @ColumnInfo(name = "imagePaths")
    public String imagePaths = "";
}
