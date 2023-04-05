package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    EditText numberText;
    TextView sumText;
    Button computeButton;
    Button addButton;

    private IntentFilter intentFilter = new IntentFilter();

    boolean serviceStatus = false;


    int sum = -1;

    boolean isCompute = false;
    boolean firstNumber = true;


    private void startPracticalService() {
        if (sum > 10 && !serviceStatus) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
            intent.putExtra("sum", String.valueOf(sum));
            serviceStatus = true;
            getApplicationContext().startService(intent);
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("primesc");
            Toast.makeText(context, "Received from service " + intent.getStringExtra("ro.pub.cs.systems.eim.colocviu1_2.broadcastreceiverextra"), Toast.LENGTH_LONG).show();
        }
    }
    public View onClick(View view) {
        switch(view.getId()) {
            case R.id.compute:
                Intent intent = new Intent(this, Colocviu1_2SecondaryActivity.class);
                intent.putExtra("sum", sumText.getText().toString());
                startActivityForResult(intent, 1);

                break;
            case R.id.add:
                if(firstNumber) {
                    if(!numberText.getText().toString().equals("")) {
                        sumText.setText(numberText.getText());
                        firstNumber = false;
                    }
                } else {
                    if(!numberText.getText().toString().equals("")) {
                        sumText.setText(String.valueOf(sumText.getText().toString()) + " + " + numberText.getText().toString());
                    }
                isCompute = false;
                }

                break;
        }
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        numberText = (EditText)findViewById(R.id.next_term);
        sumText = (TextView)findViewById(R.id.sum_text);
        computeButton = (Button)findViewById(R.id.compute);
        addButton = (Button)findViewById(R.id.add);
        sumText.setText("");
        intentFilter.addAction("ro.pub.cs.systems.eim.colocviu1_2.sum");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "Rezultatul este " + resultCode, Toast.LENGTH_LONG).show();
            sum = resultCode;
            isCompute = true;
            startPracticalService();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isCompute) {
            outState.putString("isCompute", String.valueOf("true"));
            outState.putString("sum", String.valueOf(sum));
            outState.putString("operation", sumText.getText().toString());
        } else {
            outState.putString("isCompute", String.valueOf("false"));
            outState.putString("sum", sumText.getText().toString());
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
        getApplicationContext().stopService(intent);
        serviceStatus = false;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("isCompute").equals("true")) {
            sumText.setText(savedInstanceState.getString("sum"));
            sum = Integer.parseInt(savedInstanceState.getString("sum"));
            isCompute = true;
        } else {
            sumText.setText(savedInstanceState.getString("sum"));
            isCompute = false;

        }
    }
}