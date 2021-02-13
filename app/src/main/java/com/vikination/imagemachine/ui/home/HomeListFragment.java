package com.vikination.imagemachine.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.FragmentHomeListBinding;
import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.ui.MainActivity;

public class HomeListFragment extends Fragment implements OnClickMachineItemListener{

    FragmentHomeListBinding binding;
    MachineListAdapter adapter;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeListBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setToolbarTitle("Image Machine");
        ((MainActivity)getActivity()).setVisibleMenu(true);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        setupList();
        binding.swipeList.setOnRefreshListener(() -> viewModel.fetchAllMachineData());
        binding.fabMachine.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_homeListFragment_to_nav_dialog_addmachine));
        viewModel.machineLiveData.observe(getViewLifecycleOwner(), machines ->{
                    adapter.updateData(machines);
                    binding.swipeList.setRefreshing(false);
                });
        viewModel.fetchAllMachineData();
    }

    private void setupList(){
        adapter = new MachineListAdapter(this);
        binding.rvMachineImage.setAdapter(adapter);
        binding.rvMachineImage.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvMachineImage.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    public void sorting(Boolean isSortByName){
        viewModel.sortData(isSortByName);
    }

    @Override
    public void onClickMachine(Machine machine) {
        Bundle bundle = new Bundle();
        bundle.putInt("machine_id", machine.uid);
        NavHostFragment.findNavController(this).navigate(R.id.action_homeListFragment_to_detailMachineFragment, bundle);
    }

    @Override
    public void onClickDeleteMachine(Machine machine) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Machine Item")
                .setMessage(String.format("Are you sure want to delete %s ?", machine.name))
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    viewModel.deleteMachineData(machine);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }

    @Override
    public void onClickEditMachine(Machine machine) {
        Bundle bundle = new Bundle();
        bundle.putInt("uid", machine.uid);
        Navigation.findNavController(binding.fabMachine).navigate(R.id.action_homeListFragment_to_updateMachineFragment, bundle);
    }

    public void resultQrCode(String qrcode){
        Bundle bundle = new Bundle();
        bundle.putString("qrnumber", qrcode);
        NavHostFragment.findNavController(this).navigate(R.id.action_homeListFragment_to_detailMachineFragment, bundle);
    }
}