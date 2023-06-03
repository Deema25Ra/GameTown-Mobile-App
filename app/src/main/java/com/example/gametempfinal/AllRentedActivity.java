package com.example.gametempfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRentedActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    DBHelper myDB;
    ArrayList<String> game_id, game_name, game_type, game_price, game_time,game_date;


    //***********************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rented);

        this.setTitle("Rented games");


                recyclerView = findViewById(R.id.recyclerView2);

        setSupportActionBar(findViewById(R.id.toolbar2));
//        createMenu();

        //************************

        //************************

        myDB = new DBHelper(AllRentedActivity.this);
        game_id = new ArrayList<>();
        game_name = new ArrayList<>();
        game_type = new ArrayList<>();
        game_price = new ArrayList<>();
        game_time = new ArrayList<>();
        game_date = new ArrayList<>();



        storeDataInArrays();

        //take the info from data from array to pass it
        Custom custom = new Custom(AllRentedActivity.this, this, game_id, game_name, game_type, game_price , "r");

        recyclerView.setAdapter(custom);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllRentedActivity.this));
    }

    //end of oncreate
    //***********************************************************************************

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllRentedGames();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, " No data ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                game_id.add(cursor.getString(0));
                game_name.add(cursor.getString(1));
                game_type.add(cursor.getString(2));
                game_price.add(cursor.getString(3));
                game_time.add(cursor.getString(5));
                game_date.add(cursor.getString(6));
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
                    Toast.makeText(AllRentedActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AllRentedActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AllRentedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.viewAll) {

            Intent intent = new Intent(AllRentedActivity.this, viewActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.rentedAll) {

            Intent intent = new Intent(AllRentedActivity.this, AllRentedActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.manageMyGames) {
            Intent intent = new Intent(AllRentedActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

//Done ***********************************************************************************

