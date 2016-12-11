package moneyminder.suncha.com.moneyminder;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
    TextView lentDateTV;
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
    @BindView(R.id.popupparentlayout)
    RelativeLayout popupparentlayout;

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                startEditing();

            }
        });

        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                confirmCancelEditDialog();
            }
        });

        doneEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                saveUpdates();
                disableEditTexts();
                doneEditing.setVisibility(View.INVISIBLE);
                cancelEdit.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
            }
        });
    }


    private void confirmCancelEditDialog() {
        //Create a dialog to confirm that the edit is to be canceled
        //If yes, set visibility of self to invisible, done to invisible and edit to visible
        //if no, return as was
        //disable editing functionality
    }

    private void saveUpdates() {
        //save updates and refresh views with updated data
        String newName = nameTV.getText().toString();
        String newAmount = amountTV.getText().toString();
        //TODO GET NEW DATE FROM DATEPICKER
        //// TODO: 12/11/2016 GET REMINDER FORM DATEPICKER AND DATEPICKER
        String newRemarks = remarksET.getText().toString();

        if (newName.trim().length() == 0 || newAmount.trim().length() == 0)
            Toast.makeText(getApplicationContext(), R.string.saveError, Toast.LENGTH_SHORT).show();
        if (newRemarks.trim().length() == 0)
            newRemarks = getResources().getString(R.string.noRemarksSet);

        List<ReceivablesModel> receivables = ReceivablesModel.find(ReceivablesModel.class, "name=?", name);
        if (receivables.size() > 0) {
            ReceivablesModel editedReceivable = receivables.get(0);
            editedReceivable.name = newName;
            editedReceivable.lentAmount = newAmount;
            editedReceivable.remarks = newRemarks;
            //TODO ADD EDITED DATES AS WELL
            editedReceivable.save();
            Toast.makeText(getApplicationContext(), R.string.changesSaved, Toast.LENGTH_SHORT).show();
        }


    }

    private void startEditing() {
        Snackbar.make(popupparentlayout, R.string.editingmodeon, Snackbar.LENGTH_SHORT).show();
        enableEditTexts();
        changeReminderButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.INVISIBLE);
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
            reminderInfoTV.setText(reminderDate + " " + getResources().getString(R.string.at) + " " + reminderTime);
        } else if (isReminderActivated == 0) {
            reminderInfoTV.setText(getResources().getString(R.string.noReminder));
        } else {
            reminderInfoTV.setText("Error is happens");
        }
        remarksET.setText(remarks);

    }


}
