package android.labs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "CHAT_WINDOW";

    final ArrayList<String> arrayList = new ArrayList<>();
    private ChatAdapter messageAdapter;
    EditText chatText;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        ListView listView = findViewById(R.id.chatView);
        chatText = findViewById(R.id.editText);
        Button sendButton = findViewById(R.id.sendButton);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * from MESSAGES", null);
        int colInd = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        while(cursor.moveToNext()){
            String message = cursor.getString(colInd);
            arrayList.add(message); //add messages to array
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message);

        }
        Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());

        for (int i= 0; i< cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(chatText.getText().toString());
                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.KEY_MESSAGE, chatText.getText().toString());
                db.insert("MESSAGES", "Null replacement value", cValues);
                chatText.setText("");
                messageAdapter.notifyDataSetChanged();

            }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return arrayList.size();
        }

        public String getItem(int position){
            return arrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position % 2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
