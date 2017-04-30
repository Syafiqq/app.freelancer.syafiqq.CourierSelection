package app.freelancer.syafiqq.courierselection.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Locale;

import app.freelancer.syafiqq.courierselection.model.database.DatabaseContract;
import app.freelancer.syafiqq.courierselection.model.database.DatabaseModel;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 8:01 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class DAOProfile extends DatabaseModel
{
    private static DAOProfile mInstance = null;

    private DAOProfile(final Context context)
    {
        super(context);
        Timber.d("Constructor");
    }

    public static DAOProfile getInstance(@NotNull final Context context)
    {
        Timber.d("getInstance");

        /*
          use the application context as suggested by CommonsWare.
          this will ensure that you dont accidentally leak an Activitys
          context (see this article for more information:
          http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if(DAOProfile.mInstance == null)
        {
            DAOProfile.mInstance = new DAOProfile(context);
        }
        return DAOProfile.mInstance;
    }

    public static void insert(SQLiteDatabase database, @NotNull MProfile profile)
    {
        Timber.d("static insert");

        database.execSQL(
                String.format(Locale.getDefault(), "INSERT INTO %s(%s, %s) VALUES (NULL, ?)",
                        DatabaseContract.Profile.TABLE_NAME,
                        DatabaseContract.Profile.COLUMN_NAME_ID,
                        DatabaseContract.Profile.COLUMN_NAME_NAME
                ),
                new Object[] {
                        profile.getName()
                });
    }

    private static MProfile getByID(@NotNull SQLiteDatabase database, int id)
    {
        Timber.d("static getByID");

        final Cursor cursor = database.rawQuery(
                String.format(
                        Locale.getDefault(),
                        "SELECT `%s`, `%s` FROM `%s` WHERE `%s` = ? LIMIT 1",
                        DatabaseContract.Profile.COLUMN_NAME_ID,
                        DatabaseContract.Profile.COLUMN_NAME_NAME,

                        DatabaseContract.Profile.TABLE_NAME,

                        DatabaseContract.Profile.COLUMN_NAME_ID
                ),
                new String[] {String.valueOf(id)});

        MProfile records = null;
        if(cursor.moveToFirst())
        {
            do
            {
                records = new MProfile(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void insert(@NotNull MProfile profile)
    {
        Timber.d("insert");

        try
        {
            super.openWrite();
        }
        catch(SQLException ignored)
        {
            Timber.d("SQLException");
        }

        DAOProfile.insert(super.database, profile);
    }

    public MProfile getByID(int id)
    {
        Timber.d("getByID");
        try
        {
            super.openRead();
        }
        catch(SQLException ignored)
        {
            Timber.d("SQLException");
        }

        return DAOProfile.getByID(super.database, id);
    }
}
