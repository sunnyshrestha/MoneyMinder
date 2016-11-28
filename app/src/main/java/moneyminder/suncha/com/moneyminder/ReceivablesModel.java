package moneyminder.suncha.com.moneyminder;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by MSI on 11/27/2016.
 */

@Table
public class ReceivablesModel extends SugarRecord{
    private Long id;


    public ReceivablesModel() {

    }

    public String name;
    public String lentDate;
    public String lentAmount;
    public String isReminderActivated;
    public String reminderDate;
    public String reminderTime;
    public String remarks;

    //Default constructor
    public ReceivablesModel(String name, String lentDate, String lentAmount, String isReminderActivated, String reminderDate, String reminderTime, String remarks) {
        this.name = name;
        this.lentDate = lentDate;
        this.lentAmount = lentAmount;
        this.isReminderActivated = isReminderActivated;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.remarks = remarks;
    }

    public Long getId(){
        return id;
    }
}
