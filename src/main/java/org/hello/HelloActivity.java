package org.hello;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.joda.time.LocalTime;

public class HelloActivity extends Activity {
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.hello_layout);
   }
   @Override
   public void onStart(){
   super.onStart();
   LocalTime currentTime = new LocalTime();
   TextView textView = (TextView) findViewById(R.id.text_view);
   textView.setText("Welcome to the \nHitchhiker's Guide to the Galaxy"+"\n\nEarthean Time:"+currentTime);
   }
}
