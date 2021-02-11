package com.vikination.imagemachine.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
    }

    void setToolbar(){
        binding.toolbarMain.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(binding.toolbarMain);
        getSupportActionBar().setTitle("Image Machine");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.scan_menu){
            Toast.makeText(this, "scan menu clicked", Toast.LENGTH_SHORT).show();
            return true;
        }else if (menuId == R.id.sort_menu){
            showSortMenu();
            return true;
        }else return super.onOptionsItemSelected(item);
    }

    private void showSortMenu(){
        View anchor = findViewById(R.id.sort_menu);
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int menuId = menuItem.getItemId();
            if (menuId == R.id.sort_by_name){
                Toast.makeText(MainActivity.this, "sort by name", Toast.LENGTH_SHORT).show();
                return true;
            }else if (menuId == R.id.sort_by_type){
                Toast.makeText(MainActivity.this, "sort by type", Toast.LENGTH_SHORT).show();
                return true;
            }else return false;
        });
        popupMenu.show();
    }
}