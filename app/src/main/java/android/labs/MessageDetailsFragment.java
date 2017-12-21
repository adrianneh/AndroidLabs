package android.labs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetailsFragment extends Fragment {

    private Activity callingActivity;
    final String msg = getArguments().getString("msg");
    final long id = getArguments().getLong("id");
    final int position = getArguments().getInt("position");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_message_details_fragment, container, false);
        TextView msgDetailsTV = (TextView) view.findViewById(R.id.messageDetailsTextView);
        TextView msgIdTV = (TextView)view.findViewById(R.id.showIDTextView);

        msgDetailsTV.setText("Message: " + msg);
        msgIdTV.setText("ID: " + Long.toString(id));

        Button deleteMessageBtn = (Button)view.findViewById(R.id.BtnDeleteMessage);

        switch(callingActivity.getLocalClassName()){
            case "ChatWindow":
                deleteMessageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatWindow)callingActivity).deleteMessage(id, position);
                        callingActivity.getFragmentManager().beginTransaction().
                                remove(MessageDetailsFragment.this).commit();
                    }
                });
                break;

            case "MessageDetails":
                deleteMessageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MessageDetails) callingActivity).deleteMessage(id, position);
                    }
                });
                break;
        }
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.callingActivity = activity;
    }
}
