package com.vikination.imagemachine.ui.addmachine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.vikination.imagemachine.databinding.FragmentAddMachineBinding;
import com.vikination.imagemachine.model.Machine;

import java.util.UUID;

public class AddMachineDialogFragment extends DialogFragment {

    public AddMachineDialogFragment(){}

    private FragmentAddMachineBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMachineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddMachineViewModel viewModel = new ViewModelProvider(requireActivity()).get(AddMachineViewModel.class);
        binding.buttonAddmachine.setOnClickListener(view1 -> {
            if (isValidInput()){
                Machine machine = new Machine();
                machine.machineId = UUID.randomUUID().toString();
                machine.name = binding.inputMachinename.getText().toString();
                machine.type = binding.inputMachinetype.getText().toString();
                machine.qrNumber = binding.inputMachinenumber.getText().toString();
                viewModel.addMachine(machine);
            }
            dismiss();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
