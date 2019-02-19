package in.bharatrohan.bharatrohan.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.bharatrohan.bharatrohan.Activities.RepoFragments.HistoryFragment;
import in.bharatrohan.bharatrohan.Activities.RepoFragments.SolutionFragment;
import in.bharatrohan.bharatrohan.Activities.RepoFragments.StatusFragment;

public class RepoFragmentAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public RepoFragmentAdapter(FragmentManager fm, int NumOfTabs) {
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
                SolutionFragment sol = new SolutionFragment();
                return sol;
            }

            case 1: {
                StatusFragment status = new StatusFragment();
                return status;
            }

            case 2: {
                HistoryFragment hisory = new HistoryFragment();
                return hisory;
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
