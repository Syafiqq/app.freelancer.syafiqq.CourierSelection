package app.freelancer.syafiqq.courierselection.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import app.freelancer.syafiqq.courierselection.model.database.DatabaseContract;
import app.freelancer.syafiqq.courierselection.model.database.DatabaseModel;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.saw.property.Identity;
import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 6:42 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class DAOAlternative extends DatabaseModel
{
    private static DAOAlternative mInstance = null;

    private DAOAlternative(final Context context)
    {
        super(context);
        Timber.d("Constructor");
    }

    public static DAOAlternative getInstance(final Context ctx)
    {
        Timber.d("getInstance");

        /*
          use the application context as suggested by CommonsWare.
          this will ensure that you dont accidentally leak an Activitys
          context (see this article for more information:
          http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if(DAOAlternative.mInstance == null)
        {
            DAOAlternative.mInstance = new DAOAlternative(ctx);
        }
        return DAOAlternative.mInstance;
    }

    public static void insert(final SQLiteDatabase database, @NotNull MAlternative alternative)
    {
        Timber.d("static insert");

        database.execSQL(
                String.format(Locale.getDefault(), "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
                        DatabaseContract.Alternative.COLUMN_NAME_ACTIVE
                ),
                new Object[] {
                        alternative.getIdentity().getName(),
                        alternative.getFleet().getValue(),
                        alternative.getCoverage().getValue(),
                        alternative.getExperience().getValue(),
                        alternative.getCost().getValue(),
                        alternative.getTime().getValue(),
                        alternative.getPackaging().getValue(),
                        alternative.getProfile().getId(),
                        alternative.getActive(),
                });
    }

    public static void deleteByID(SQLiteDatabase database, @NotNull MAlternative alternative)
    {
        Timber.d("static deleteByID");

        database.execSQL(String.format(Locale.getDefault(), "DELETE FROM `%s` WHERE `%s`=?", DatabaseContract.Alternative.TABLE_NAME, DatabaseContract.Alternative.COLUMN_NAME_ID), new Object[] {alternative.getId()});
    }

    private static List<MAlternative> getByProfile(@NotNull SQLiteDatabase database, @NotNull MProfile profile)
    {
        Timber.d("static getByID");

        final Cursor cursor = database.rawQuery(
                String.format(
                        Locale.getDefault(),
                        "SELECT `%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s` FROM `%s` WHERE `%s` = ? ORDER BY `%s` ASC",
                        DatabaseContract.Alternative.COLUMN_NAME_ID,
                        DatabaseContract.Alternative.COLUMN_NAME_NAME,
                        DatabaseContract.Alternative.COLUMN_NAME_FLEET,
                        DatabaseContract.Alternative.COLUMN_NAME_COVERAGE,
                        DatabaseContract.Alternative.COLUMN_NAME_EXPERIENCE,
                        DatabaseContract.Alternative.COLUMN_NAME_COST,
                        DatabaseContract.Alternative.COLUMN_NAME_TIME,
                        DatabaseContract.Alternative.COLUMN_NAME_PACKAGING,
                        DatabaseContract.Alternative.COLUMN_NAME_ACTIVE,

                        DatabaseContract.Alternative.TABLE_NAME,

                        DatabaseContract.Alternative.COLUMN_NAME_PROFILE,

                        DatabaseContract.Alternative.COLUMN_NAME_ID
                ),
                new String[] {String.valueOf(profile.getId())});

        final List<MAlternative> records = new LinkedList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                records.add(new MAlternative(
                        cursor.getInt(0),
                        new Identity(cursor.getString(1)),
                        new Fleet(cursor.getInt(2)),
                        new Coverage(cursor.getInt(3)),
                        new Experience(cursor.getInt(4)),
                        new Cost(cursor.getInt(5)),
                        new Time(cursor.getInt(6)),
                        new Packaging(cursor.getInt(7)),
                        profile,
                        cursor.getInt(8)
                ));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void insert(@NotNull MAlternative alternative)
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

        DAOAlternative.insert(super.database, alternative);
    }

    public void deleteByID(@NotNull MAlternative alternative)
    {
        Timber.d("deleteByID");
        try
        {
            super.openWrite();
        }
        catch(SQLException ignored)
        {
            Timber.d("SQLException");
        }

        DAOAlternative.deleteByID(super.database, alternative);
    }

    public List<MAlternative> getByProfile(@NotNull MProfile profile)
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

        return DAOAlternative.getByProfile(super.database, profile);
    }
}
