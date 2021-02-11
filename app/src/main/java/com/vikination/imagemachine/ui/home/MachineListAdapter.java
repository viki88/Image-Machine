package com.vikination.imagemachine.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vikination.imagemachine.databinding.LayoutRowMachineBinding;
import com.vikination.imagemachine.model.Machine;

public class MachineListAdapter extends ListAdapter<Machine, MachineListAdapter.MachineViewHolder> {

    protected MachineListAdapter() {
        super(new MachineDiff());
    }

    @NonNull
    @Override
    public MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRowMachineBinding binding = LayoutRowMachineBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MachineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class MachineViewHolder extends RecyclerView.ViewHolder {
        LayoutRowMachineBinding binding;

        public MachineViewHolder(@NonNull LayoutRowMachineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Machine machine){
            binding.textIdmachine.setText(machine.machineId);
            binding.textName.setText(machine.name);
            binding.textType.setText(machine.type);
        }
    }

    static class MachineDiff extends DiffUtil.ItemCallback<Machine>{

        @Override
        public boolean areItemsTheSame(@NonNull Machine oldItem, @NonNull Machine newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Machine oldItem, @NonNull Machine newItem) {
            return oldItem.machineId.equals(newItem.machineId);
        }
    }
}
