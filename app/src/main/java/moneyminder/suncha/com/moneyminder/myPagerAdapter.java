package moneyminder.suncha.com.moneyminder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MSI on 11/15/2016.
 */

public class myPagerAdapter extends FragmentStatePagerAdapter{
    int mNumofTabs;

    public myPagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumofTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Receivables_Fragment receivable_tab = new Receivables_Fragment();
                return receivable_tab;
            case 1:
                Payables_Fragment payable_tab = new Payables_Fragment();
                return payable_tab;
            case 2:
                Reminders_Fragment reminders_tab = new Reminders_Fragment();
                return reminders_tab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}
