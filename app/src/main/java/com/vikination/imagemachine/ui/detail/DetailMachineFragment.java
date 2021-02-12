package com.vikination.imagemachine.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.FragmentDetailMachineBinding;
import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailMachineFragment extends Fragment {

    public DetailMachineFragment(){}

    FragmentDetailMachineBinding binding;
    DetailMachineViewModel viewModel;
    Machine machine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailMachineBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setToolbarTitle("Machine Detail");
        ((MainActivity)getActivity()).setVisibleMenu(false);
        int id = getArguments().getInt("machine_id");
        viewModel = new ViewModelProvider(requireActivity()).get(DetailMachineViewModel.class);
        viewModel.machineLiveData.observe(getViewLifecycleOwner(), machines -> {
            if (machines.size() != 0) updateData(machines.get(0));
        });
        viewModel.calendarLiveData.observe(getViewLifecycleOwner(), calendar -> {
            String textLastUpdate = String.format("Machine last updated : %s",getDateFormatted(calendar.getTime(), "dd MMM yyyy"));
            machine.lastModified = calendar.getTime();
            viewModel.updateMachine(machine);
            binding.textMachinelastupdate.setText(textLastUpdate);
        });
        viewModel.getMachineDataById(id);
        binding.buttonPickdate.setOnClickListener(view1 -> NavHostFragment.findNavController(DetailMachineFragment.this)
                .navigate(R.id.action_detailMachineFragment_to_datePickerFragment));
    }

    private void updateData(Machine machine){
        this.machine = machine;
        binding.textMachineid.setText(String.format("Machine Id : \n%s", machine.machineId));
        binding.textMachineName.setText(String.format("Machine Name : \n%s", machine.name));
        binding.textMachineType.setText(String.format("Machine Type : \n%s", machine.type));
        Date date = machine.lastModified;
        String lastModifiedText = "Not set";
        if (date != null) {
            lastModifiedText = getDateFormatted(date, "dd MMM yyyy");
        }
        binding.textMachinelastupdate.setText(String.format("Machine Last Updated : \n%s", lastModifiedText));
    }

    private String getDateFormatted(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }
}
