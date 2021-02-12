package com.vikination.imagemachine.ui.detail;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.repository.MachineRepository;

import java.util.Calendar;
import java.util.List;

public class DetailMachineViewModel extends AndroidViewModel {

    MachineRepository machineRepository;
    public LiveData<List<Machine>> machineLiveData;
    public MutableLiveData<Calendar> calendarLiveData = new MutableLiveData<>();

    public DetailMachineViewModel(@NonNull Application application) {
        super(application);
        machineRepository = new MachineRepository(application);
        machineLiveData = machineRepository.getMachineLiveData();
    }

    public void getMachineDataById(int uid){
        machineRepository.getMachineById(uid);
    }

    public void updateMachine(Machine machine){
        machineRepository.updateMachine(machine);
    }

    public void datePicked(Calendar calendar){
        calendarLiveData.postValue(calendar);
    }

}
