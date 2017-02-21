package bluepanther.jiljungjuk;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bluepanther.jiljungjuk.Tabs.Audio;
import bluepanther.jiljungjuk.Tabs.Files;
import bluepanther.jiljungjuk.Tabs.Image;
import bluepanther.jiljungjuk.Tabs.Text;
import bluepanther.jiljungjuk.Tabs.Video;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_text,
            R.drawable.ic_action_image,
            R.drawable.ic_action_audio,
            R.drawable.ic_action_video,
            R.drawable.ic_action_files
    };
    public String str[]={"Text","Image","Audio","Video","Files"};
    public static int int_items = 5 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        //viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        FindTab.curTab = "Text";
        /**
         *Set an Apater for the View Pager
         */
        //viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
        setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    System.out.println("Selected tab is"+tab.getText());
                    FindTab.curTab=tab.getText().toString();
                    System.out.println("After tab select shyam"+FindTab.curTab);
                super.onTabSelected(tab);
            }
        });
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                tabLayout.setupWithViewPager(viewPager);
//
//            }
//        });
        return x;

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new Text(), "Text");
        adapter.addFrag(new Image(), "Image");
        adapter.addFrag(new Audio(), "Audio");
        adapter.addFrag(new Video(), "Video");
        adapter.addFrag(new Files(), "Files");
        viewPager.setAdapter(adapter);


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
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}



