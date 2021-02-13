package com.vikination.imagemachine.ui.updatemachine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.repository.MachineRepository;

import java.util.List;

public class UpdateMachineViewModel extends AndroidViewModel {

    MachineRepository machineRepository;
    public LiveData<List<Machine>> machineLiveData;

    public UpdateMachineViewModel(@NonNull Application application) {
        super(application);
        machineRepository = new MachineRepository(application);
        machineLiveData = machineRepository.getMachineLiveData();
    }

    public void getMachineDataById(int uid){
        machineRepository.getMachineById(uid);
    }

    public void updateMachine(Machine machine){
        machineRepository.updateEditMachine(machine);
    }

}
