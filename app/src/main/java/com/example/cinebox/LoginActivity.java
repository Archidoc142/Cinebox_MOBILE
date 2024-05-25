package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText userInput;
    private EditText pwdInput;
    private TextView inscriptionTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View nav = findViewById(R.id.nav);

        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            //imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
        } else {
            imageUser.setVisibility(View.INVISIBLE);
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);

        userInput = findViewById(R.id.username_input);
        pwdInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        inscriptionTxt = findViewById(R.id.inscriptionTxt);

        loginBtn.setOnClickListener(this);
        inscriptionTxt.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.inscriptionTxt)
        {
            Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
            startActivity(intent);
        }
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
                        if(APIRequests.postLoginUser(username, pwd, LoginActivity.this))
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
                            //Intent intent = new Intent(LoginActivity.this, PanierActivity.class);
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
            } else if (v.getId() == R.id.connexionNav) {
                if (Utilisateur.getInstance() != null) {
                    Utilisateur.logOutUser(this);

                    View nav = findViewById(R.id.nav);
                    TextView connexion = nav.findViewById(R.id.connexionNav);
                    connexion.setText("Se connecter");
                }
            } else {
                Toast.makeText(this, "not nice", Toast.LENGTH_SHORT).show();
            }
        }
    }
}