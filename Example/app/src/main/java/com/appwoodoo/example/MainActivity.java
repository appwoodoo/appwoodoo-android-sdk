package com.appwoodoo.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.appwoodoo.sdk.Woodoo;

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

        ExamplePagerAdapter examplePagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(examplePagerAdapter);
    }

	@Override
	public void onPause() {
		super.onPause();
	}

	public class ExamplePagerAdapter extends FragmentPagerAdapter {

		public ExamplePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new SettingsFragment();
                default:
                    return Woodoo.stories().getFragment();
            }
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Appwoodoo";
                default:
					String title = Woodoo.stories().getStoryWall(getApplicationContext()).getTitle();
					if (title != null) {
						return title;
					}
                    return "Stories";
            }
		}
	}
}
