package com.example.charm.borrowbook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    MaterialSearchView searchView;
    private DrawerLayout drawer;

    public static final int BORROWED_EDIT = 1;
    public static final int BORROWED_ADD = -1;
    public static final int LIBRARY_EDIT = 1;
    public static final int LIBRARY_ADD = -1;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_library));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_borrowed));

        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.libraryViewPager);

        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pa);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbarSearch = (Toolbar)findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setTitle("BorrowBook");
        toolbarSearch.setTitleTextColor(Color.parseColor("#FFFFFF"));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbarSearch,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        /*if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LibraryFragment()).commit();
            navigationView.setCheckedItem(R.id.menu_library);
        }*/
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }

        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_library:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new LibraryFragment()).commit();
                break;

           case R.id.menu_account:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new AccountFragment()).commit();
               break;


           case R.id.menu_filter:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                     new FilterBooksFragment()).commit();
               break;

           case R.id.menu_logout:
               //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
               //new LogoutFragment()).commit();
               break;

       }

       drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
