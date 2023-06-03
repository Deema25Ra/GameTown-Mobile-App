package com.example.gametempfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    EditText username, password, repassword ,email,phone;
    Button signup, signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.number);

//
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);

        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String em = email.getText().toString();
                String pho = phone.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("") ||em.equals("")||pho.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        Boolean checkemail= DB.checkemail(em);
                        Boolean checkphone=DB.checkphone(pho);
                        //email validation
                        String regexPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                        Pattern patternE = Pattern.compile(regexPattern);
                        Matcher matcherE = patternE.matcher(em);
                        //phone number validation
                        String reg = "^(0)?(5)(\\d{8})$";
                        Pattern patternP = Pattern.compile(reg);
                        Matcher matcherP = patternP.matcher(pho);
                        if(checkuser==false & checkemail==false & checkphone==false){
                            if(!matcherE.matches())
                            { Toast.makeText(MainActivity.this, "Please enter a valid mail", Toast.LENGTH_SHORT).show();}
                            else if ((!matcherP.matches()))
                            { Toast.makeText(MainActivity.this, "Please enter a valid number ", Toast.LENGTH_SHORT).show();}
                            else{
                                Boolean insert=false;
                                try{ insert = DB.insertData(user, pass,em,pho);}
                                catch(Exception e){};
                                if(insert==true){
                                    Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),viewActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }}
                        else{
                            Toast.makeText(MainActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}