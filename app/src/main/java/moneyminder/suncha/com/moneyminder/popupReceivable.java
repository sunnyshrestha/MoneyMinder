package moneyminder.suncha.com.moneyminder;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class popupReceivable extends AppCompatActivity {
    String name, amount, lentDate, reminderDate, reminderTime, remarks;
    int isReminderActivated;
    @BindView(R.id.name)
    EditText nameTV;
    @BindView(R.id.amountValue)
    EditText amountTV;
    @BindView(R.id.dateLent)
    EditText lentDateTV;
    @BindView(R.id.reminderInfo)
    TextView reminderInfoTV;
    @BindView(R.id.remarksDetails)
    EditText remarksET;
    @BindView(R.id.changeReminder)
    Button changeReminderButton;
    @BindView(R.id.doneEditing)
    ImageView doneEditing;
    @BindView(R.id.cancelEdit)
    ImageView cancelEdit;

    ImageView editButton;

    KeyListener nameKeylistener;
    KeyListener amountKeyListener;
    KeyListener lentDateKeyListener;
    KeyListener remarksDateKeyListener;

    private AlphaAnimation buttonAnimation = new AlphaAnimation(1F, 0.7F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_receivables);
        ButterKnife.bind(this);

        editButton = (ImageView) findViewById(R.id.editButton);

        nameKeylistener = nameTV.getKeyListener();
        amountKeyListener = amountTV.getKeyListener();
        lentDateKeyListener = lentDateTV.getKeyListener();
        remarksDateKeyListener = remarksET.getKeyListener();

        //lock all the edittexts
        disableEditTexts();

        //get data from previous intent and load the views
        loadViews();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                startEditing();
            }
        });

    }

    private void startEditing() {
        enableEditTexts();
        changeReminderButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        cancelEdit.setVisibility(View.VISIBLE);
        doneEditing.setVisibility(View.VISIBLE);

    }

    private void disableEditTexts() {
        nameTV.setKeyListener(null);
        amountTV.setKeyListener(null);
        lentDateTV.setKeyListener(null);
        remarksET.setKeyListener(null);

    }

    private void enableEditTexts() {
        nameTV.setKeyListener(nameKeylistener);
        amountTV.setKeyListener(amountKeyListener);
        lentDateTV.setKeyListener(lentDateKeyListener);
        remarksET.setKeyListener(remarksDateKeyListener);
    }


    private void loadViews() {

        name = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("amount");
        lentDate = getIntent().getStringExtra("date");
        isReminderActivated = getIntent().getIntExtra("reminderCheck", -1);
        reminderDate = getIntent().getStringExtra("reminderDate");
        reminderTime = getIntent().getStringExtra("reminderTime");
        remarks = getIntent().getStringExtra("remarks");

        nameTV.setText(name);
        amountTV.setText(amount);
        lentDateTV.setText(lentDate);
        if (isReminderActivated == 1) {
            reminderInfoTV.setText(getResources().getString(R.string.reminderSetat) + " " + reminderDate + " " + getResources().getString(R.string.at) + " " + reminderTime);
        } else if (isReminderActivated == 0) {
            reminderInfoTV.setText(getResources().getString(R.string.noReminder));
        } else {
            reminderInfoTV.setText("Error is happens");
        }
        remarksET.setText(remarks);

    }


}
