package in.bharatrohan.bharatrohan.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class MyFarm extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private FragmentAdapter mFragmentAdapter;
    private ImageView headImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_farm);
        new CheckInternet(this).checkConnection();
        new PrefManager(this).saveFarmNo(0);

        int farmCount = new PrefManager(this).getFarmerFarmCount();

        initViews();

        setDynamicFragmentToTabLayout(farmCount);

        if (mFragmentAdapter != null) {
            mFragmentAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        headImage = findViewById(R.id.head_img);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);

        mTabLayout = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(0);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                new PrefManager(MyFarm.this).saveFarmNo(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setDynamicFragmentToTabLayout(int count) {
        if (count > 0) {
            for (int i = 1; i <= count; i++) {

                mTabLayout.addTab(mTabLayout.newTab().setText("Land: " + i));
            }
            mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
            viewPager.setAdapter(mFragmentAdapter);
            viewPager.setCurrentItem(0);
        } else if ((new PrefManager(this).getFarmerFarmCount()) == 1) {
            new PrefManager(MyFarm.this).saveFarmNo(0);
        } else {
            Toast.makeText(MyFarm.this, "No Farms Registered yet!!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.removeOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*if (mFragmentAdapter != null) {
            mFragmentAdapter.notifyDataSetChanged();
        }*/
    }

}
