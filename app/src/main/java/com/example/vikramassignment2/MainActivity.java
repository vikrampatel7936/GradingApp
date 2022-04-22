package com.example.vikramassignment2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //variable declaration
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawer_layout;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    NavigationView nav_view;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        //properties of drawer
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);

        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerItem(menuItem);
                return true;
            }
        });

        if (savedInstanceState == null) {
            drawerItem(nav_view.getMenu().getItem(0));
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    //changing fragment as per the item selected in drawer
    private void drawerItem(MenuItem menuItem) {
        boolean specialToolbarBehaviour = false;
        Class fragmentClass;
        if (menuItem.getItemId() == R.id.gAdd) {
            fragmentClass = GradeEntry.class;
        } else if (menuItem.getItemId() == R.id.search_id) {
            fragmentClass = Search.class;
        } else if (menuItem.getItemId() == R.id.gView) {
            fragmentClass = ViewGrades.class;
            specialToolbarBehaviour = true;
        } else {
            fragmentClass = ViewGrades.class;
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer_layout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}