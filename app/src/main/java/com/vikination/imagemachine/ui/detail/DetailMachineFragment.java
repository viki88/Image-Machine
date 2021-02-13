package com.vikination.imagemachine.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.FragmentDetailMachineBinding;
import com.vikination.imagemachine.model.Machine;
import com.vikination.imagemachine.ui.MainActivity;
import com.vikination.imagemachine.ui.detail.adapter.ImageThumbAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailMachineFragment extends Fragment implements OnThumbnailClickListener{

    public DetailMachineFragment(){}

    FragmentDetailMachineBinding binding;
    DetailMachineViewModel viewModel;
    Machine machine;
    ImageThumbAdapter imageThumbAdapter;
    ArrayList<String> uris = new ArrayList<>();

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
        setupImageGallery();
        int id = getArguments().getInt("machine_id", 0);
        String qrnumber = getArguments().getString("qrnumber");
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
        if (id == 0)viewModel.getMachineDataByQrNumber(qrnumber); else viewModel.getMachineDataById(id);
        binding.buttonPickdate.setOnClickListener(view1 -> NavHostFragment.findNavController(DetailMachineFragment.this)
                .navigate(R.id.action_detailMachineFragment_to_datePickerFragment));
        binding.machineimageButton.setOnClickListener(view12 -> ImagePicker.create(this).limit(10).start());
    }

    private void updateData(Machine machine){
        this.machine = machine;
        binding.textMachineid.setText(String.format("Machine Id : \n%s", machine.machineId));
        binding.textMachineName.setText(String.format("Machine Name : \n%s", machine.name));
        binding.textMachineType.setText(String.format("Machine Type : \n%s", machine.type));
        binding.textMachineQrNumber.setText(String.format("Machine QR Number : \n%s", machine.qrNumber));
        uris.clear();
        if (!machine.imagePaths.isEmpty()){
            uris = getListUri(machine.imagePaths);
            imageThumbAdapter.updateData(uris);
        }
        Date date = machine.lastModified;
        String lastModifiedText = "Not set";
        if (date != null) {
            lastModifiedText = getDateFormatted(date, "dd MMM yyyy");
        }
        binding.textMachinelastupdate.setText(String.format("Machine Last Updated : \n%s", lastModifiedText));
    }

    private void setupImageGallery(){
        imageThumbAdapter = new ImageThumbAdapter(requireContext(),this);
        binding.rvMachineThumb.setAdapter(imageThumbAdapter);
        binding.rvMachineThumb.setLayoutManager(new GridLayoutManager(requireContext(),3));
    }

    private String getDateFormatted(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)){
            List<Image> images = ImagePicker.getImages(data);
            Log.i("TAG", "onActivityResult: "+ new Gson().toJson(images));
            for (int i = 0; i < images.size(); i++) {
                if (!uris.contains(images.get(i).getPath())) uris.add(images.get(i).getPath());
            }
        }
        imageThumbAdapter.updateData(uris);
        machine.imagePaths = new Gson().toJson(uris);
        viewModel.updateMachine(machine);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClickThumbnail(String uri) {
        Bundle bundle = new Bundle();
        bundle.putString("uri",uri);
        NavHostFragment.findNavController(this).navigate(R.id.action_detailMachineFragment_to_fullScreenImageFragment,bundle);
    }

    private ArrayList<String> getListUri(String string){
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(string, type);
    }
}
