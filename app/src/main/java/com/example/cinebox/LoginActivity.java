package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText userInput;
    private EditText pwdInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userInput = findViewById(R.id.username_input);
        pwdInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.login_btn)
        {
            String username = userInput.getText().toString();
            String pwd = pwdInput.getText().toString();

            if(!username.isEmpty() && !pwd.isEmpty())
            {
                Toast.makeText(this, "nice", Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(APIRequests.postLoginUser(username, pwd))
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(LoginActivity.this, "Working!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        else
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(LoginActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
            else
            {
                Toast.makeText(this, "not nice", Toast.LENGTH_SHORT).show();
            }
        }
    }
}