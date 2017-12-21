package android.labs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MessageDetails extends Activity {


    private Bundle messageDetails;
    private MessageDetailsFragment loadedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            messageDetails = bundle.getBundle("messageDetails");
            loadedFragment = new MessageDetailsFragment();
            loadedFragment.setArguments(messageDetails);
            getFragmentManager().beginTransaction()
                    .add(R.id.frameChatDetailsPhone, loadedFragment).commit();

        }

    }

    public void deleteMessage(long id, int position){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}

