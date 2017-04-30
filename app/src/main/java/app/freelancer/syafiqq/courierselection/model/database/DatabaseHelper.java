package app.freelancer.syafiqq.courierselection.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import timber.log.Timber;


/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 6:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int    DATABASE_VERSION = 2;
    private static final String DATABASE_NAME    = "courier.mcrypt";
    private static final char   COMMA_SEPARATOR  = ',';
    private static final char   WHITESPACE       = ' ';

    private static final String TYPE_TEXT    = "TEXT";
    private static final String TYPE_INTEGER = "INTEGER";
    private static final String TYPE_DOUBLE  = "DOUBLE";

    private static final String CONSTRAINT_NOT_NULL       = "NOT NULL";
    private static final String CONSTRAINT_PRIMARY_KEY    = "PRIMARY KEY";
    private static final String CONSTRAINT_AUTO_INCREMENT = "AUTOINCREMENT";

    private static final String SQL_CREATE_ALTERNATIVE_ENTRIES = "" +
            "CREATE TABLE IF NOT EXISTS" + WHITESPACE + DatabaseContract.Alternative.TABLE_NAME + WHITESPACE +
            "( " +
            DatabaseContract.Alternative.COLUMN_NAME_ID + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_PRIMARY_KEY + WHITESPACE + CONSTRAINT_AUTO_INCREMENT + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_NAME + WHITESPACE + TYPE_TEXT + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_FLEET + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_COVERAGE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_EXPERIENCE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_COST + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_TIME + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_PACKAGING + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_PROFILE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Alternative.COLUMN_NAME_ACTIVE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + WHITESPACE +
            " );";

    private static final String SQL_CREATE_WEIGHT_ENTRIES = "" +
            "CREATE TABLE IF NOT EXISTS" + WHITESPACE + DatabaseContract.Weight.TABLE_NAME + WHITESPACE +
            "( " +
            DatabaseContract.Weight.COLUMN_NAME_ID + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_PRIMARY_KEY + WHITESPACE + CONSTRAINT_AUTO_INCREMENT + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_FLEET + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_COVERAGE + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_COST + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_TIME + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_PACKAGING + WHITESPACE + TYPE_DOUBLE + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Weight.COLUMN_NAME_PROFILE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + WHITESPACE +
            " );";

    private static final String SQL_CREATE_PROFILE_ENTRIES = "" +
            "CREATE TABLE IF NOT EXISTS" + WHITESPACE + DatabaseContract.Profile.TABLE_NAME + WHITESPACE +
            "( " +
            DatabaseContract.Profile.COLUMN_NAME_ID + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_PRIMARY_KEY + WHITESPACE + CONSTRAINT_AUTO_INCREMENT + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            DatabaseContract.Profile.COLUMN_NAME_NAME + WHITESPACE + TYPE_TEXT + WHITESPACE + CONSTRAINT_NOT_NULL + WHITESPACE +
            " );";

    private static final String SQL_DROP_ALTERNATIVE_ENTRIES = "" +
            "DROP TABLE IF EXISTS " + DatabaseContract.Alternative.TABLE_NAME;

    private static final String SQL_DROP_WEIGHT_ENTRIES = "" +
            "DROP TABLE IF EXISTS " + DatabaseContract.Weight.TABLE_NAME;

    private static final String SQL_DROP_PROFILE_ENTRIES = "" +
            "DROP TABLE IF EXISTS " + DatabaseContract.Profile.TABLE_NAME;

    @Nullable private static DatabaseHelper mInstance = null;
    private final Context context;

    private DatabaseHelper(final Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Timber.d("Constructor");

        this.context = context;
    }

    public static DatabaseHelper getInstance(final Context context)
    {
        Timber.d("getInstance");

        /*
          use the application context as suggested by CommonsWare.
          this will ensure that you dont accidentally leak an Activitys
          context (see this article for more information:
          http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if(DatabaseHelper.mInstance == null)
        {
            DatabaseHelper.mInstance = new DatabaseHelper(context);
        }
        return DatabaseHelper.mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Timber.d("onCreate");

        sqLiteDatabase.execSQL(SQL_CREATE_ALTERNATIVE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_WEIGHT_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_PROFILE_ENTRIES);
        this.prePopulateDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        Timber.d("onUpgrade");

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DROP_ALTERNATIVE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DROP_WEIGHT_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DROP_PROFILE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        super.onDowngrade(db, oldVersion, newVersion);
        Timber.d("onDowngrade");
    }

    private void prePopulateDatabase(final SQLiteDatabase sqLiteDatabase)
    {
        Timber.d("prePopulateDatabase");

        final String json = this.readDataFromAssets("stream.json");
        try
        {
            final JSONObject jsonObject  = new JSONObject(json);
            final JSONArray  profile     = jsonObject.getJSONObject("data").getJSONArray("profile");
            final JSONArray  weight      = jsonObject.getJSONObject("data").getJSONArray("weight");
            final JSONArray  alternative = jsonObject.getJSONObject("data").getJSONArray("alternative");
            this.populateProfile(sqLiteDatabase, profile);
            this.populateWeight(sqLiteDatabase, weight);
            this.populateAlternative(sqLiteDatabase, alternative);
        }
        catch(JSONException e)
        {
            Timber.d("JSONException");
        }
    }

    @NonNull
    private String readDataFromAssets(String path)
    {
        Timber.d("readDataFromAssets");

        final StringBuilder sb     = new StringBuilder();
        BufferedReader      reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(this.context.getAssets().open(path)));

            // do reading, usually loop until end of file reading
            String mLine;
            while((mLine = reader.readLine()) != null)
            {
                sb.append(mLine);
            }
        }
        catch(IOException ignored)
        {
            Timber.d("IOException");
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch(IOException ignored)
                {
                    Timber.d("IOException");
                }
            }
        }

        return sb.toString();
    }

    private void populateProfile(final SQLiteDatabase database, final JSONArray data)
    {
        Timber.d("populateProfile");

        for(int i = -1, is = data.length(); ++i < is; )
        {
            try
            {
                final JSONObject entry = data.getJSONObject(i);
                database.execSQL(
                        String.format(Locale.getDefault(), "" +
                                        "INSERT OR REPLACE INTO %s(%s, %s) VALUES (?, COALESCE((SELECT %s FROM %s WHERE %s = ?), ?))",
                                DatabaseContract.Profile.TABLE_NAME,

                                DatabaseContract.Profile.COLUMN_NAME_ID,
                                DatabaseContract.Profile.COLUMN_NAME_NAME,

                                DatabaseContract.Profile.COLUMN_NAME_NAME,

                                DatabaseContract.Profile.TABLE_NAME,

                                DatabaseContract.Profile.COLUMN_NAME_ID
                        ),
                        new Object[] {
                                entry.getInt("id"), entry.getInt("id"), entry.getString("name")
                        });
            }
            catch(JSONException ignored)
            {
                Timber.d("JSONException");
            }
        }
    }

    private void populateWeight(SQLiteDatabase database, JSONArray weight)
    {
        Timber.d("populateWeight");

        for(int i = -1, is = weight.length(); ++i < is; )
        {
            try
            {
                final JSONObject entry = weight.getJSONObject(i);
                database.execSQL(
                        String.format(Locale.getDefault(), "" +
                                        "INSERT OR REPLACE INTO %s(%s, %s, %s, %s, %s, %s, %s, %s) VALUES " +
                                        "(?, " +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)" +
                                        ")",
                                DatabaseContract.Weight.TABLE_NAME,

                                DatabaseContract.Weight.COLUMN_NAME_ID,
                                DatabaseContract.Weight.COLUMN_NAME_FLEET,
                                DatabaseContract.Weight.COLUMN_NAME_COVERAGE,
                                DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE,
                                DatabaseContract.Weight.COLUMN_NAME_COST,
                                DatabaseContract.Weight.COLUMN_NAME_TIME,
                                DatabaseContract.Weight.COLUMN_NAME_PACKAGING,
                                DatabaseContract.Weight.COLUMN_NAME_PROFILE,

                                DatabaseContract.Weight.COLUMN_NAME_FLEET,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_COVERAGE,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_COST,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_TIME,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_PACKAGING,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID,

                                DatabaseContract.Weight.COLUMN_NAME_PROFILE,
                                DatabaseContract.Weight.TABLE_NAME,
                                DatabaseContract.Weight.COLUMN_NAME_ID
                        ),
                        new Object[] {
                                entry.getInt("id"),
                                entry.getInt("id"), entry.getDouble("fleet"),
                                entry.getInt("id"), entry.getDouble("coverage"),
                                entry.getInt("id"), entry.getDouble("experience"),
                                entry.getInt("id"), entry.getDouble("cost"),
                                entry.getInt("id"), entry.getDouble("time"),
                                entry.getInt("id"), entry.getDouble("packaging"),
                                entry.getInt("id"), entry.getInt("profile")
                        });
            }
            catch(JSONException ignored)
            {
                Timber.d("JSONException");
            }
        }
    }

    private void populateAlternative(SQLiteDatabase database, JSONArray alternative)
    {
        Timber.d("populateAlternative");

        for(int i = -1, is = alternative.length(); ++i < is; )
        {
            try
            {
                final JSONObject entry = alternative.getJSONObject(i);
                database.execSQL(
                        String.format(Locale.getDefault(), "" +
                                        "INSERT OR REPLACE INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES " +
                                        "(?, " +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)," +
                                        "COALESCE((SELECT %s FROM %s WHERE %s = ?), ?)" +
                                        ")",
                                DatabaseContract.Alternative.TABLE_NAME,

                                DatabaseContract.Alternative.COLUMN_NAME_ID,
                                DatabaseContract.Alternative.COLUMN_NAME_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_FLEET,
                                DatabaseContract.Alternative.COLUMN_NAME_COVERAGE,
                                DatabaseContract.Alternative.COLUMN_NAME_EXPERIENCE,
                                DatabaseContract.Alternative.COLUMN_NAME_COST,
                                DatabaseContract.Alternative.COLUMN_NAME_TIME,
                                DatabaseContract.Alternative.COLUMN_NAME_PACKAGING,
                                DatabaseContract.Alternative.COLUMN_NAME_PROFILE,
                                DatabaseContract.Alternative.COLUMN_NAME_ACTIVE,

                                DatabaseContract.Alternative.COLUMN_NAME_NAME,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_FLEET,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_COVERAGE,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_EXPERIENCE,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_COST,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_TIME,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_PACKAGING,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_PROFILE,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID,

                                DatabaseContract.Alternative.COLUMN_NAME_ACTIVE,
                                DatabaseContract.Alternative.TABLE_NAME,
                                DatabaseContract.Alternative.COLUMN_NAME_ID
                        ),
                        new Object[] {
                                entry.getInt("id"),
                                entry.getInt("id"), entry.getString("name"),
                                entry.getInt("id"), entry.getInt("fleet"),
                                entry.getInt("id"), entry.getInt("coverage"),
                                entry.getInt("id"), entry.getInt("experience"),
                                entry.getInt("id"), entry.getInt("cost"),
                                entry.getInt("id"), entry.getInt("time"),
                                entry.getInt("id"), entry.getInt("packaging"),
                                entry.getInt("id"), entry.getInt("profile"),
                                entry.getInt("id"), entry.getInt("active")
                        });
            }
            catch(JSONException ignored)
            {
                Timber.d("JSONException");
            }
        }
    }
}
