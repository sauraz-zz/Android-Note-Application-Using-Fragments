package beer.com.mynotes;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import beer.com.mynotes.Constants.Constants;
import beer.com.mynotes.Fragments.LoginFragment;
import beer.com.mynotes.Fragments.NotesFragment;
import beer.com.mynotes.database_helper.sqlite.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    NotesFragment notesFragment;
    SharedPreferenceManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spm = new SharedPreferenceManager(this);
        // Check if User logged in .
        if (!spm.isLoggedIn()) {
            //  If user not logged in show Login Fragment
            loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, loginFragment, Constants.LOGIN_FRAGMENT).commit();
        } else {
            //  If user logged in show Note Fragment
            notesFragment = new NotesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, notesFragment, Constants.NOTES_FRAGMENT).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            spm.logoutUser();
            loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(Constants.LOGIN_FRAGMENT);
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
