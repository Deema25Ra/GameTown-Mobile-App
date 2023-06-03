package com.example.gametempfinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ManageRentActivity extends AppCompatActivity {
    Button rent_btn ;
    EditText time , date;
    String id, name, type, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rent);
        rent_btn = findViewById(R.id.rent_button);
        time = (EditText)findViewById(R.id.time_input);
        date = (EditText)findViewById(R.id.date_input);
        setSupportActionBar(findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Rent a game");






        //First we call this
        getIntentData();

        //*****************************

        rent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeR =time.getText().toString();
                String dateR = date.getText().toString();
                if(timeR.equals("")||dateR.equals(""))
                {
                    Toast.makeText(ManageRentActivity.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    confirmInfo(timeR, dateR);
                }
                Intent intent = new Intent(ManageRentActivity.this, AllRentedActivity.class);
                startActivity(intent);
            }
        });


    }   // end of on create good
    //***********************************************************************************

    // take the name of game and info
    void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("type") && getIntent().hasExtra("price")) {
            //Getting Data from Intent the recent data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            type = getIntent().getStringExtra("type");
            price = getIntent().getStringExtra("price");

        } else {
            Toast.makeText(this, "There is no Games rented.", Toast.LENGTH_SHORT).show();
        }
    }

    //***********************************************************************************
    // working good

    //*******************************************

    void confirmInfo(String timeR , String dateR) {
        try {
            DBHelper myDB = new DBHelper(ManageRentActivity.this);

            myDB.addRentedGame(id,name,type,price,timeR,dateR);
        }catch (Exception e){
            Toast.makeText(ManageRentActivity.this, "No game added", Toast.LENGTH_SHORT).show();
        }
        // go to all rented games
        Intent intent = new Intent(ManageRentActivity.this, AllRentedActivity.class);
        startActivity(intent);
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
            Intent intent = new Intent(ManageRentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.viewAll) {

            Intent intent = new Intent(ManageRentActivity.this, viewActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.rentedAll) {

            Intent intent = new Intent(ManageRentActivity.this, AllRentedActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.manageMyGames) {
            Intent intent = new Intent(ManageRentActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}