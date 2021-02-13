package com.vikination.imagemachine.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vikination.imagemachine.databinding.LayoutRowMachineBinding;
import com.vikination.imagemachine.model.Machine;

import java.util.Collections;
import java.util.List;

public class MachineListAdapter extends RecyclerView.Adapter<MachineListAdapter.MachineViewHolder> {

    private final OnClickMachineItemListener onClickMachineItemListener;

    public MachineListAdapter(OnClickMachineItemListener onClickMachineItemListener){
        this.onClickMachineItemListener = onClickMachineItemListener;
    }

    private List<Machine> machines = Collections.emptyList();

    @NonNull
    @Override
    public MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRowMachineBinding binding = LayoutRowMachineBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MachineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineViewHolder holder, int position) {
        holder.bind(machines.get(position), onClickMachineItemListener);
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    public void updateData(List<Machine> machines){
        this.machines = machines;
        notifyDataSetChanged();
    }

    static class MachineViewHolder extends RecyclerView.ViewHolder {
        LayoutRowMachineBinding binding;

        public MachineViewHolder(@NonNull LayoutRowMachineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Machine machine, OnClickMachineItemListener onClickMachineItemListener){
            binding.layoutRow.setOnClickListener(view -> onClickMachineItemListener.onClickMachine(machine));
            binding.buttonDelete.setOnClickListener(view -> onClickMachineItemListener.onClickDeleteMachine(machine));
            binding.textName.setText(machine.name);
            binding.textType.setText(machine.type);
        }
    }
}
