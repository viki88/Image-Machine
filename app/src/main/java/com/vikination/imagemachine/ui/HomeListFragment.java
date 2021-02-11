package com.vikination.imagemachine.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vikination.imagemachine.databinding.FragmentHomeListBinding;

public class HomeListFragment extends Fragment {

    FragmentHomeListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeListBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }
}