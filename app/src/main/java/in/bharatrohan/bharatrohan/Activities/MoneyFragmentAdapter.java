package in.bharatrohan.bharatrohan.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.bharatrohan.bharatrohan.Activities.MoneyFragments.AddRecord;
import in.bharatrohan.bharatrohan.Activities.MoneyFragments.MyRecords;
import in.bharatrohan.bharatrohan.Activities.RepoFragments.HistoryFragment;
import in.bharatrohan.bharatrohan.Activities.RepoFragments.SolutionFragment;
import in.bharatrohan.bharatrohan.Activities.RepoFragments.StatusFragment;

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
                MyRecords mr = new MyRecords();
                return mr;
            }

            case 1: {
                AddRecord ar = new AddRecord();
                return ar;
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
