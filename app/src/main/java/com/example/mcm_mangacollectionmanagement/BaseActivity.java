package com.example.mcm_mangacollectionmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import MCMClass.AlertClass;
import MCMClass.User;
import database.UserDatabase;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton imageButton;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout drawerBackgorund;
    ImageView imgDrawerUser;
    TextView txtDrawerUsername, txtDrawerGmail;
    UserDatabase database;
    AlertClass alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base);
        drawerLayout = findViewById(R.id.drawerLayout);
        imageButton = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        drawerBackgorund = headerView.findViewById(R.id.drawerBackground);
        CardView cardView = headerView.findViewById(R.id.cardView);
        imgDrawerUser = cardView.findViewById(R.id.imgDrawerUser);
        txtDrawerUsername = headerView.findViewById(R.id.txtDrawerUsername);
        txtDrawerGmail = headerView.findViewById(R.id.txtDrawerGmail);
        database = new UserDatabase(this);
        alert = new AlertClass(this);
        showUserInformation();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.Home){
                    navigateToAcitivity(home.class);
                }
                if (itemId == R.id.Collection){
                    navigateToAcitivity(detailcollection.class);
                }
                if (itemId == R.id.Authors){
                    navigateToAcitivity(detailauthor.class);
                }
                if (itemId == R.id.Publishers){
                    navigateToAcitivity(detailpublisher.class);
                }
                if (itemId == R.id.Statistics){
                    navigateToAcitivity(statistic.class);
                }
                if (itemId == R.id.Setting){
                    navigateToAcitivity(Setting.class);
                }
                if (itemId == R.id.Logout){
                    alert.showAlertDialog("Thông báo", "Thoát ứng dụng ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                        @Override
                        public void onResult(boolean isDone) {
                            if (isDone){
                                System.exit(0);
                            }
                        }
                    });
                }
                drawerLayout.close();
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");
        EditText searchEditText =  searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (searchClose != null){
            searchClose.setColorFilter(ContextCompat.getColor(this, android.R.color.white));
        }
        if (searchEditText != null) {
            searchEditText.setHintTextColor(ContextCompat.getColor(this, android.R.color.white));
            searchEditText.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    handleSearch(newText);
                    return true;
                }
            });
        }
        return true;
    }

    protected void handleSearch(String newText) {

    }
    protected void navigateToAcitivity(Class<?> target){
        if (!this.getClass().getSimpleName().equals(target.getSimpleName())){
            Intent intent = new Intent(BaseActivity.this,target);
            startActivity(intent);
        }
    }
    protected void showUserInformation(){
            User user = database.getAccount();
            txtDrawerUsername.setText(user.getName());
            txtDrawerGmail.setText(user.getGmail());
            if (user.getAvatar()!= null){
                byte[] image = user.getAvatar();
                Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgDrawerUser.setImageBitmap(img);
            }
            if (user.getBackground() != null){
                byte[] image = user.getBackground();
                Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
                Drawable drawable = new BitmapDrawable(getResources(),img);
                drawerBackgorund.setBackground(drawable);
            }


    }
    protected void disableEditText(EditText edt){
        edt.setFocusable(false);
        edt.setEnabled(false);
        edt.setCursorVisible(false);
        edt.setKeyListener(null);
        edt.setBackgroundColor(Color.TRANSPARENT);
    }
    @Override
    protected void onResume() {
        showUserInformation();
        super.onResume();
    }
}