package com.vikination.imagemachine.ui.fullscreenimage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.FragmentFullscreenBinding;
import com.vikination.imagemachine.ui.MainActivity;

import java.io.File;

public class FullScreenImageFragment extends Fragment {

    FragmentFullscreenBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFullscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setToolbarTitle("Fullscreen Image");
        String uri = getArguments().getString("uri");
        Glide.with(requireActivity())
                .load(new File(uri))
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageviewFull);
    }
}
