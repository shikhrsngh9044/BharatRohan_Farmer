package in.bharatrohan.bharatrohan.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.bharatrohan.bharatrohan.Activities.MoneyFragments.AddRecord;
import in.bharatrohan.bharatrohan.Activities.MoneyFragments.MyRecords;

public class MoneyFragmentAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public MoneyFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
//        Bundle b = new Bundle();
//        b.putInt("position", position);
//        Fragment frag = LandFragment.newInstance();
//        frag.setArguments(b);
//        return frag;

        switch (position) {
            case 0: {
                AddRecord ar = new AddRecord();
                return ar;
            }

            case 1: {
                MyRecords mr = new MyRecords();
                return mr;
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
