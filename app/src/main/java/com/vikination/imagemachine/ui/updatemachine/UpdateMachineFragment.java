package com.vikination.imagemachine.ui.updatemachine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.vikination.imagemachine.databinding.FragmentEditMachineBinding;
import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.ui.home.HomeViewModel;

public class UpdateMachineFragment extends DialogFragment {

    public UpdateMachineFragment(){}

    FragmentEditMachineBinding binding;
    Machine machine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditMachineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int id = getArguments().getInt("uid");
        HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.machineLiveData.observe(getViewLifecycleOwner(), machines -> {
            if (machines.size() != 0) {
                this.machine = machines.get(0);
                fillData();
            }
        });
        viewModel.getMachineDataById(id);
        binding.buttonUpdate.setOnClickListener(view1 -> {
            if (isValidInput()) {
                machine.name = binding.inputMachinename.getText().toString();
                machine.type = binding.inputMachinetype.getText().toString();
                machine.qrNumber = binding.inputMachinenumber.getText().toString();
                viewModel.updateMachine(machine);
                Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void fillData(){
        binding.inputMachinename.setText(machine.name);
        binding.inputMachinetype.setText(machine.type);
        binding.inputMachinenumber.setText(machine.qrNumber);
    }

    private Boolean isValidInput(){
        if (binding.inputMachinename.getText().toString().isEmpty()){
            binding.inputMachinename.setError("Machine Name do not empty");
            return false;
        }else if(binding.inputMachinetype.getText().toString().isEmpty()){
            binding.inputMachinetype.setError("Machine Type do not empty");
            return false;
        }else if(binding.inputMachinenumber.getText().toString().isEmpty()){
            binding.inputMachinenumber.setError("Machine Number do not empty");
            return false;
        }else return true;
    }
}
