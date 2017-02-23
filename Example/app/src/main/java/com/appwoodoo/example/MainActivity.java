package com.appwoodoo.example;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.appwoodoo.example.app.SettingsFragment;
import com.appwoodoo.example.demo.RemoteSettingsDemoFragment;
import com.appwoodoo.example.demo.StoriesDemoFragment;
import com.appwoodoo.example.demo.WoodooDownloadDemoFragment;
import com.appwoodoo.example.demo.DialogDemoFragment;
import com.appwoodoo.sdk.Woodoo;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * An example app that uses the Appwoodoo SDK.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View pager setup
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mainPagerAdapter);

        // Action bar customisation
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Tab strip customisation
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setNonPrimaryAlpha(1);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "roboto/RobotoBlack.ttf");
        for (int counter = 0; counter < pagerTitleStrip.getChildCount(); counter++) {
            if (pagerTitleStrip.getChildAt(counter) instanceof TextView) {
                ((TextView) pagerTitleStrip.getChildAt(counter)).setTypeface(typeFace);
            }
        }
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WoodooDownloadDemoFragment();
                case 1:
                    return new StoriesDemoFragment();
                case 2:
                    return new DialogDemoFragment();
                case 3:
                    return new RemoteSettingsDemoFragment();
                default:
                    return new SettingsFragment();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String text = "";
            Drawable drawable;

            switch (position) {
                case 0:
                    text = getString(R.string.tabs_home);
                    drawable = getResources().getDrawable(R.drawable.ic_tab_woodoo);
                    break;
                case 1:
                    text = Woodoo.stories().getStoryWall(getApplicationContext()).getTitle();
                    if (text == null) {
                        text = getString(R.string.tabs_home);
                    }
                    drawable = getResources().getDrawable(R.drawable.ic_tab_stories);
                    break;
                case 2:
                    text = getString(R.string.tabs_dialogs);
                    drawable = getResources().getDrawable(R.drawable.ic_tab_dialogs);
                    break;
                case 3:
                    text = getString(R.string.tabs_remoteconfig);
                    drawable = getResources().getDrawable(R.drawable.ic_tab_remoteconfig);
                    break;
                case 4:
                    text = getString(R.string.tabs_yoursettings);
                    drawable = getResources().getDrawable(R.drawable.ic_tab_settings);
                    break;
                default:
                    drawable = getResources().getDrawable(R.drawable.ic_tab_woodoo);
                    text = "";
            }

            SpannableStringBuilder sb = new SpannableStringBuilder("  " + text);

            drawable.setBounds(32, 32, 82, 82);
            ImageSpan span = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;

        }
    }
}
