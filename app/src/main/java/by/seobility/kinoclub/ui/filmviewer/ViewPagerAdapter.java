package by.seobility.kinoclub.ui.filmviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> fragmentsTitle;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments = new ArrayList<>();
        fragmentsTitle = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String fragmentTitle){
        fragments.add(fragment);
        fragmentsTitle.add(fragmentTitle);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
