package com.vikination.imagemachine.ui.addmachine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.repository.MachineRepository;

import java.util.List;

public class AddMachineViewModel extends AndroidViewModel {

    MachineRepository machineRepository;
    public LiveData<List<Machine>> machineLiveData;

    public AddMachineViewModel(@NonNull Application application) {
        super(application);
        machineRepository = new MachineRepository(application);
        machineLiveData = machineRepository.getMachineLiveData();
    }

    public void addMachine(Machine machine){
        machineRepository.addMachineData(machine);
    }

    public void fetchAllMachineData(){
        machineRepository.getAllMachineData();
    }

    public void deleteMachineData(Machine machine){
        machineRepository.deleteMachineData(machine);
    }

    public void sortData(Boolean isSortByName){
        machineRepository.sort(isSortByName);
    }

}
