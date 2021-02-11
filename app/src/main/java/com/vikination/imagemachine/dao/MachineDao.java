package com.vikination.imagemachine.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.vikination.imagemachine.model.Machine;

import java.util.List;

@Dao
public interface MachineDao {

    @Insert
    public void addMachine(Machine machine);

    @Query("SELECT * FROM Machine")
    List<Machine> getAllMachine();
}
