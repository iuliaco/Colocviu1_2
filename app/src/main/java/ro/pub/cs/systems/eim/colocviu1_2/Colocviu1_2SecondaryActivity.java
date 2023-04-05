package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("sum")) {
            String sumString = intent.getStringExtra("sum");
            String[] sumParts = sumString.split(" \\+ ");
            int sum = 0;
            for (String sumPart : sumParts) {
                sum += Integer.parseInt(sumPart);
            }
            setResult(sum);
            finish();
        }
    }
}