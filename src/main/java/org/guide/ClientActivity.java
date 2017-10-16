package org.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import org.joda.time.LocalTime;

public class ClientActivity extends Activity {

    public static final String EXTRA_MESSAGE = "org.guide.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.client_layout);
    }

/*  @Override
    public void onStart(){
        super.onStart();
        LocalTime currentTime = new LocalTime();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("Welcome to the \nHitchhiker's Guide to the Galaxy"+"\n\nEarthean Time:"+currentTime);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("text preset");
        Button button = (Button) findViewById(R.id.button);
        button.setText("Send");
    }
*/
    public void sendMessage(View view){
/*      Intent intent = new Intent(this, DisplayMessageActivity.class);
*/      EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView stage = (TextView) findViewById(R.id.text_view);
        stage.setText(message);
/*      intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
*/  }

}
