package beer.com.mynotes.database_helper.sqlite;

import android.content.Context;
import android.content.SharedPreferences;

import beer.com.mynotes.Constants.Constants;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class SharedPreferenceManager {
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context c) {
        context = c;
        pref = context.getSharedPreferences(Constants.NOTE_PREFERENCE, 0);
        editor = pref.edit();
    }

    public void createLoginSession(String email) {
        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(Constants.USER_EMAIL, email);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(Constants.IS_LOGIN, false);
    }

    public void logoutUser() {
        editor.putBoolean(Constants.IS_LOGIN, false);
        editor.putString(Constants.USER_EMAIL, "");
        editor.commit();
    }
}
