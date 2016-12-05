package moneyminder.suncha.com.moneyminder;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class popupReceivable extends AppCompatActivity {
    String name,amount,lentDate,isReminderActivated,reminderDate,reminderTime,remarks;
    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.amountValue)
    EditText amountTV;
    @BindView(R.id.dateLent)
    EditText lentDateTV;
    @BindView(R.id.reminderInfo)
    TextView reminderInfoTV;
    @BindView(R.id.remarksDetails)
    EditText remarksET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_receivables);
        ButterKnife.bind(this);

        //get data from previous intent and load the views
        loadViews();
    }

    private void loadViews() {

        name=getIntent().getStringExtra("name");
        amount=getIntent().getStringExtra("amount");
        lentDate=getIntent().getStringExtra("date");
        isReminderActivated=getIntent().getStringExtra("reminderCheck");
        reminderDate=getIntent().getStringExtra("reminderDate");
        reminderTime=getIntent().getStringExtra("reminderTime");
        remarks=getIntent().getStringExtra("remarks");

        nameTV.setText(name);
        amountTV.setText(amount);
        lentDateTV.setText(lentDate);
        if(isReminderActivated=="1"){
            reminderInfoTV.setText(getResources().getString(R.string.reminderSetat)+reminderDate+getResources().getString(R.string.at)+reminderTime);
        }else
            reminderInfoTV.setText(getResources().getString(R.string.noReminder));
        remarksET.setText(remarks);

    }


}
