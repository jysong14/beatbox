package com.beatboxmetronome;

import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, LoadListFragment.OnTemplateSelectedListener
{
	/**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    
    /*
     * Receives the position (maybe irrelevant) and template 
     * that was selected from load list.
     */
    public void onTemplateSelected(int position, Template t) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
    	System.out.println("Just loaded template: "+t.getTemplateName());
    	    	
    	MetronomeFragment metronome = (MetronomeFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
    	if(metronome != null)
    	{
    		metronome.load(t);
    	}
    	this.getActionBar().setSelectedNavigationItem(1);
    }


    /**
     * Creates the UI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                invalidateOptionsMenu();
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        this.getActionBar().setSelectedNavigationItem(1);
    }
    
    private String downloadItemText = "Download";
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item  = menu.findItem(R.id.action_download);
        item.setTitle(downloadItemText);
        int tab = this.getActionBar().getSelectedNavigationIndex();
        if (tab == 2) item.setVisible(true);
        else item.setVisible(false);
        return true;
    }
    
    public void sendTemplateToEdit(Template t)
    {
    	// Do similar commands like when template sent to metronome fragment
    	System.out.println("Send template to edit");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_download) {
        	//TODO send a callback to the loadlistfragment, tell it to switch the list to downloads view.
        	// Also change the download text to say local
        	if (item.getTitle().equals("Download"))
        		{
        			item.setTitle("Local");
        			downloadItemText = "Local";
        			//
        		}
        	else {
        		item.setTitle("Download");
        		downloadItemText = "Download";
        	}
        	//need to redraw actionbar here too.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }
    

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
    

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
    
    
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            switch(position)
            {
    	        case 0: return new EditFragment();
    	        case 1: return new MetronomeFragment();
    	        case 2: return new LoadListFragment();
    	        default: break;
            }
            return null;
        }

        
        /**
         * Returns the number of tabs
         */
        @Override
        public int getCount()
        {
            return 3;
        }

        
        /**
         * Returns the title of the tab at a given position
         */
        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();
            switch (position)
            {
                case 0: return getString(R.string.title_edit).toUpperCase(l);
                case 1: return getString(R.string.title_metronome).toUpperCase(l);
                case 2: return getString(R.string.title_load).toUpperCase(l);
            }
            return null;
        }
    }
}
