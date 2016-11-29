package moneyminder.suncha.com.moneyminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartApplication extends AppCompatActivity {
    Button newReceivableButton;
    Button viewReceivableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_application);

        newReceivableButton = (Button)findViewById(R.id.newReceivableEntry);
        viewReceivableDatabase= (Button)findViewById(R.id.viewReceivableDatabase);

        newReceivableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receivableIntent = new Intent(getApplicationContext(),ReceivableDetails.class);
                startActivity(receivableIntent);
            }
        });

        viewReceivableDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receivableDatabase = new Intent(getApplicationContext(),ViewAllReceivables.class);
                startActivity(receivableDatabase);
            }
        });

    }
}
