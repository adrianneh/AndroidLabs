package android.labs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    Button loginButton;
    EditText inputEmail;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        loginButton = findViewById(R.id.buttonAddress);
        inputEmail = findViewById(R.id.input_email);

        preferences = getSharedPreferences("savedEmail", MODE_PRIVATE);
        inputEmail.setText(preferences.getString("savedEmail", "email@domain.com"));


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("savedEmail", inputEmail.getText().toString());
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }



    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }


    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
