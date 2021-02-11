package com.vikination.imagemachine.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.FragmentHomeListBinding;
import com.vikination.imagemachine.ui.addmachine.AddMachineViewModel;

public class HomeListFragment extends Fragment {

    FragmentHomeListBinding binding;
    MachineListAdapter adapter;
    AddMachineViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeListBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddMachineViewModel.class);
        setupList();
        binding.fabMachine.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_homeListFragment_to_nav_dialog_addmachine));
        viewModel.machineLiveData.observe(getViewLifecycleOwner(), machines -> adapter.submitList(machines));
        viewModel.fetchAllMachineData();
    }

    private void setupList(){
        adapter = new MachineListAdapter();
        binding.rvMachineImage.setAdapter(adapter);
        binding.rvMachineImage.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvMachineImage.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

}