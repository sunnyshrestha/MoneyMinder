package moneyminder.suncha.com.moneyminder;


import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.date.BottomSheetDatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;


import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.view.View.GONE;

public class ReceivableDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, BottomSheetTimePickerDialog.OnTimeSetListener {
    @BindView(R.id.receivableToolbar)
    Toolbar receivableToolbar;
    @BindView(R.id.lentDateButton)
    Button datePicker;
    @BindView(R.id.lentDate)
    EditText lentdate;
    @BindView(R.id.lentDateWrapper)
    TextInputLayout lentDateWrapper; //required to set validation
    @BindView(R.id.reminderSwitch)
    Switch reminderSwitch;
    @BindView(R.id.reminderDetailsWrapper)
    RelativeLayout reminderDetailsWrapper;
    @BindView(R.id.reminderDetails)
    TextView reminderDetails;

    int datePickerID = 0; //If this is 1, then it refers to lend date picker and if it is 2, it refers to reminder date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable_details);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(receivableToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        receivableToolbar.setTitle(R.string.receivable_details);
        receivableToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });

    }

    @OnCheckedChanged(R.id.reminderSwitch)
    public void reminderDate() {
        if(reminderSwitch.isChecked()){
            Calendar now = Calendar.getInstance();
            BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                    ReceivableDetails.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
            datePickerID = 2;
            dialog.show(getSupportFragmentManager(), "reminderDate");
        }else{
            reminderDetails.setText(null);
            reminderDetailsWrapper.setVisibility(GONE);
        }

    }


    @OnClick(R.id.lentDateButton)
    public void pickDate() {
        Calendar now = Calendar.getInstance();
        BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                ReceivableDetails.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerID = 1;
        dialog.show(getSupportFragmentManager(), "lentdate");
    }


    private void createAlertDialog() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        goBackWithoutSavingFragment goBackDialog = new goBackWithoutSavingFragment();
        goBackDialog.setRetainInstance(true);
        goBackDialog.show(fragmentManager, "backwithoutsaving");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            createAlertDialog();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receivablesmenu, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        switch (datePickerID) {
            case 1:
                lentdate.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
                break;
            case 2:
                reminderDetailsWrapper.setVisibility(View.VISIBLE);
                reminderDetails.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
                break;
            default:
                break;
        }


    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }
}

//TODO set switch to inflate date and time picker and set reminder
