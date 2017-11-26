package com.example.kanbi.tcase;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kanbi.tcase.myFavorite.favFragment;
import com.example.kanbi.tcase.totalList.listFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity {



    private NaviPageAdapter navPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    public int[] tabIcons = {
         //   R.drawable.ic_log,
            R.drawable.bag,
            R.drawable.fav,


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    //use viewpager to create navigationbar

    mViewPager = (ViewPager) findViewById(R.id.container);
    tabLayout = (TabLayout) findViewById(R.id.tabs);

    navPageAdapter = new NaviPageAdapter(getSupportFragmentManager());

    setupViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);

    createTabIcons();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            mViewPager.setCurrentItem(tab.getPosition());
            tab.getIcon().setColorFilter(getResources().getColor(R.color.traderaYellow), PorterDuff.Mode.SRC_IN)  ;
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            tab.getIcon().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN)  ;
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

    });

    }

    //viewpager

    private void setupViewPager(ViewPager viewPager){
        NaviPageAdapter adapter=new NaviPageAdapter(getSupportFragmentManager());
       // adapter.addFragment(new logInFragment(),"LogIn");
        adapter.addFragment(new listFragment(),"List");
        adapter.addFragment(new favFragment(),"Favorite");



        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private void createTabIcons() {
       // tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


}
