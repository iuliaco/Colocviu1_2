package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText numberText;
    TextView sumText;
    Button computeButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberText = (EditText)findViewById(R.id.next_term);
        sumText = (TextView)findViewById(R.id.sum_text);
        computeButton = (Button)findViewById(R.id.compute);
        addButton = (Button)findViewById(R.id.add);

    }
}