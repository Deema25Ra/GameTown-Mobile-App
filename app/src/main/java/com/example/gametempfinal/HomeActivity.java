package com.example.gametempfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // good 100 %


    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DBHelper myDB;
    ArrayList<String> game_id, game_name, game_type, game_price;
    Button view_btn;

    //***********************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.setTitle("Manage my games");


//        createMenu();

        //************************
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        //************************

        myDB = new DBHelper(HomeActivity.this);
        game_id = new ArrayList<>();
        game_name = new ArrayList<>();
        game_type = new ArrayList<>();
        game_price = new ArrayList<>();

        storeDataInArrays();

        //take the info from data from array to pass it
        CustomAdapter customAdapter = new CustomAdapter(HomeActivity.this, this, game_id, game_name, game_type, game_price);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
    }

    //end of oncreate
    //***********************************************************************************

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, " No data ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                game_id.add(cursor.getString(0));
                game_name.add(cursor.getString(1));
                game_type.add(cursor.getString(2));
                game_price.add(cursor.getString(3));
            }
        }
    }

    void createMenu(){
        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.logout) {
                    Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else   if (menuItem.getItemId() == R.id.viewAll) {
                    Intent intent = new Intent(HomeActivity.this, viewActivity.class);
                    startActivity(intent);
                    return true;
                }
                else   if (menuItem.getItemId() == R.id.rentedAll) {
                    Intent intent = new Intent(HomeActivity.this, AllRentedActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }

        }, this , Lifecycle.State.RESUMED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        View menuView = findViewById(R.id.logout);
        PopupMenu popupMenu = new PopupMenu(this, menuView);

        menuView = findViewById(R.id.viewAll);
        popupMenu = new PopupMenu(this, menuView);

        menuView = findViewById(R.id.rentedAll);
        popupMenu = new PopupMenu(this, menuView);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.viewAll) {

            Intent intent = new Intent(HomeActivity.this, viewActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.rentedAll) {

            Intent intent = new Intent(HomeActivity.this, AllRentedActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.manageMyGames) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

//Done ***********************************************************************************
