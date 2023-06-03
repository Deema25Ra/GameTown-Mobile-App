package com.example.gametempfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // good 100 %

    EditText name_input, type_input, price_input;
    Button add_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.setTitle("Add a new game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        name_input = findViewById(R.id.name_input);
        type_input = findViewById(R.id.type_input);
        price_input = findViewById(R.id.price_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(name_input.getText().length() == 0||type_input.getText().length() == 0||price_input.getText().length() == 0 ) {
                        Toast.makeText(AddActivity.this, "Please fill in all of the fields ", Toast.LENGTH_SHORT).show();
                    }else {

                        DBHelper myDB = new DBHelper(AddActivity.this);
                        myDB.addGame(name_input.getText().toString().trim(),
                                type_input.getText().toString().trim(),
                                Integer.valueOf(price_input.getText().toString().trim()));

                        // go to home again
                        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }}catch (Exception e){
                    Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }

            }
        });
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
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.viewAll) {

            Intent intent = new Intent(AddActivity.this, viewActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.rentedAll) {

            Intent intent = new Intent(AddActivity.this, AllRentedActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.manageMyGames) {
            Intent intent = new Intent(AddActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
