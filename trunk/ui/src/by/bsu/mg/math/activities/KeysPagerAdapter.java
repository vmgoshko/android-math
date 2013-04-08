package by.bsu.mg.math.activities;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class KeysPagerAdapter extends PagerAdapter {

    List<View> pages = null;

    public KeysPagerAdapter(List<View> pages) {
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(View collection, int position) {
        View v = pages.get(position);
        ((ViewPager) collection).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public List<View> getPages() {
        return pages;
    }
}