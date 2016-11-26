package moneyminder.suncha.com.moneyminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.receivable));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.payable));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.reminders));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final myPagerAdapter adapter = new myPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabSpeedDial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with your menu items, or return false if you don't want to show them

                return true;
            }
        });

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                switch (menuItem.getItemId()) {
                    case R.id.action_receivable:
                        startReceivableDetailsActivity();
                        break;
                    case R.id.action_payable:
                        Toast.makeText(getApplicationContext(), "Payables", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void startReceivableDetailsActivity() {
        Intent receivableIntent = new Intent(this, ReceivableDetails.class);
        startActivity(receivableIntent);
    }
}
