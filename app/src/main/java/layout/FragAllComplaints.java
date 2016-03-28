package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dhairya.complaintsystem.R;
import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;




public class FragAllComplaints extends android.support.v4.app.Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_all_complaints_layout,container,false);
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.pager);
        final FragIndividualComplaints indi = new FragIndividualComplaints();
        final FragHostelComplaints hostel = new FragHostelComplaints();
        final FragInstituteComplaints insti = new FragInstituteComplaints();

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(indi, "Individual");
        adapter.addFragment(hostel, " Hostel ");
        adapter.addFragment(insti, "Institute");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        return view;

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
