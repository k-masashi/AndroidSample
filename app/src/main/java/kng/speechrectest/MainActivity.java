package kng.speechrectest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnMike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMike = (Button)findViewById(R.id.button);
        btnMike.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == btnMike){
            //音声認識の呼び出し
        }
    }
}
