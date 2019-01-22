package id.web.skytacco.cataloguemovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import id.web.skytacco.cataloguemovie.Database.MovieContract;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(NavigationActivity.this, query, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.EXTRAS_MOVIE, query);

                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String title = "";
        if (id == R.id.nav_home) {
            title = "Home";
            fragment = new HomeFragment();
            bundle.putString(NowPlayingFragment.EXTRAS, title);
            fragment.setArguments(bundle);
       /* } else if (id == R.id.nav_search) {
            title = "Search";
            fragment = new UpComingFragment();
            bundle.putString(NowPlayingFragment.EXTRAS, title);
            fragment.setArguments(bundle);*/
        } else if (id == R.id.nav_favorite) {
            ArrayList<MovieItem> movieFavoriteArrayList = new ArrayList<>();
            Cursor cursor = null;
                cursor = getContentResolver().query(MovieContract.CONTENT_URI, null,
                        null, null, null, null);
                cursor.moveToFirst();
            MovieItem favorite;

                if (cursor.getCount() > 0) {
                    do {
                        favorite = new MovieItem(cursor.getString(cursor.getColumnIndexOrThrow(
                                MovieContract.MovieColumns.ID_MOVIE)));
                        movieFavoriteArrayList.add(favorite);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
            title = getResources().getString(R.string.favorite);
            fragment = new FavoriteFragment();
            bundle.putString(NowPlayingFragment.EXTRAS, title);
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_share) {
            title = "Catalogue Movie";
            Intent si = new Intent(android.content.Intent.ACTION_SEND);
            si.setType("text/plain");
            si.putExtra(android.content.Intent.EXTRA_SUBJECT, "Handoyo Oficial");
            si.putExtra(android.content.Intent.EXTRA_TEXT, "Dapatkan Informasi tentang Aplikasi Lainnya. Kunjungi https://skytacco.web.id/ \n atau hubungi email REALTH99@GMAIL.COM");
            startActivity(Intent.createChooser(si, "Share via"));
        } else if (id == R.id.nav_about) {
            title = "About Us";
            fragment = new AboutFragment();
            bundle.putString(AboutFragment.EXTRAS, title);
            fragment.setArguments(bundle);
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
