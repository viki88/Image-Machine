package com.vikination.imagemachine.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vikination.imagemachine.dao.MachineDao;
import com.vikination.imagemachine.helper.AppDatabase;
import com.vikination.imagemachine.model.Machine;

import java.util.List;

public class MachineRepository {

    MachineDao machineDao;
    private MutableLiveData<List<Machine>> machineLiveData = new MutableLiveData<>();

    public MachineRepository(Application application){
        machineDao = AppDatabase.getInstance(application).machineDao();
    }

    public void addMachineData(Machine machine){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineDao.addMachine(machine);
            machineLiveData.postValue(machineDao.getAllMachine());
        });
    }

    public void getAllMachineData(){
        AppDatabase.databaseWriterExecutor.execute(() ->{
            machineLiveData.postValue(machineDao.getAllMachine());
        });
    }

    public LiveData<List<Machine>> getMachineLiveData() {
        return machineLiveData;
    }

}
