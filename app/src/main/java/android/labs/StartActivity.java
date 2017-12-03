package android.labs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button startChatBtn = findViewById(R.id.startChatButton);

        startChatBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if(responseCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(StartActivity.this, messagePassed, Toast.LENGTH_LONG); //ListActivity
            toast.show();//display message box
        }
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
