package com.vikination.imagemachine.repository;

import android.app.Application;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vikination.imagemachine.dao.MachineDao;
import com.vikination.imagemachine.helper.AppDatabase;
import com.vikination.imagemachine.model.Machine;

import java.util.Collections;
import java.util.List;

public class MachineRepository {

    MachineDao machineDao;
    private List<Machine> machines;
    private final MutableLiveData<List<Machine>> machineLiveData = new MutableLiveData<>();
    private Boolean isSortByName = true;

    public MachineRepository(Application application){
        machineDao = AppDatabase.getInstance(application).machineDao();
    }

    public void addMachineData(Machine machine){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineDao.addMachine(machine);
            machines = machineDao.getAllMachine();
            sort();
        });
    }

    public void getAllMachineData(){
        AppDatabase.databaseWriterExecutor.execute(() ->{
            machines = machineDao.getAllMachine();
            sort();
        });
    }

    public void getMachineById(int uid){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineLiveData.postValue(machineDao.getMachineById(uid));
        });
    }

    public void getMachineByQRNumber(String qrNumber){
        AppDatabase.databaseWriterExecutor.execute(() -> machineLiveData.postValue(machineDao.getMachineByQrNumber(qrNumber)));
    }

    public void updateMachine(Machine machine){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineDao.updateMachine(machine);
            machineLiveData.postValue(machineDao.getMachineById(machine.uid));
        });
    }

    public void updateEditMachine(Machine machine){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineDao.updateMachine(machine);
            machines = machineDao.getAllMachine();
            sort();
        });
    }

    public void deleteMachineData(Machine machine){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            machineDao.deleteMachine(machine);
            machines = machineDao.getAllMachine();
            sort();
        });
    }

    public LiveData<List<Machine>> getMachineLiveData() {
        return machineLiveData;
    }

    private void sort(){
        if (isSortByName) sortByName(); else sortByType();
    }

    public void sort(Boolean isSortByName){
        this.isSortByName = isSortByName;
        sort();
    }

    private void sortByName(){
        Collections.sort(machines, (machine, t1) -> machine.name.compareToIgnoreCase(t1.name));
        machineLiveData.postValue(machines);
    }

    private void sortByType(){
        Collections.sort(machines, (machine, t1) -> machine.type.compareToIgnoreCase(t1.type));
        machineLiveData.postValue(machines);
    }

}
