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
import app.freelancer.syafiqq.courierselection.model.database.model.MWeight;
import app.freelancer.syafiqq.courierselection.model.method.saw.weight.ContinuousWeight;
import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 7:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class DAOWeight extends DatabaseModel
{
    private static DAOWeight mInstance = null;

    private DAOWeight(final Context context)
    {
        super(context);
        Timber.d("Constructor");
    }

    public static DAOWeight getInstance(@NotNull final Context context)
    {
        Timber.d("getInstance");

        /*
          use the application context as suggested by CommonsWare.
          this will ensure that you dont accidentally leak an Activitys
          context (see this article for more information:
          http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if(DAOWeight.mInstance == null)
        {
            DAOWeight.mInstance = new DAOWeight(context);
        }
        return DAOWeight.mInstance;
    }

    private static void insert(SQLiteDatabase database, @NotNull MWeight weight)
    {
        Timber.d("static insert");

        database.execSQL(
                String.format(Locale.getDefault(), "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)",
                        DatabaseContract.Weight.TABLE_NAME,
                        DatabaseContract.Weight.COLUMN_NAME_ID,
                        DatabaseContract.Weight.COLUMN_NAME_FLEET,
                        DatabaseContract.Weight.COLUMN_NAME_COVERAGE,
                        DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE,
                        DatabaseContract.Weight.COLUMN_NAME_COST,
                        DatabaseContract.Weight.COLUMN_NAME_TIME,
                        DatabaseContract.Weight.COLUMN_NAME_PACKAGING,
                        DatabaseContract.Weight.COLUMN_NAME_PROFILE
                ),
                new Object[] {
                        weight.getFleet().getWeight(),
                        weight.getCoverage().getWeight(),
                        weight.getExperience().getWeight(),
                        weight.getCost().getWeight(),
                        weight.getTime().getWeight(),
                        weight.getPackaging().getWeight(),
                        weight.getProfile().getId(),
                });
    }

    public static void update(final SQLiteDatabase database, @NotNull MWeight weight)
    {
        Timber.d("static update");

        database.execSQL(
                String.format(Locale.getDefault(), "UPDATE `%s` SET `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ? WHERE `%s` = ?",
                        DatabaseContract.Weight.TABLE_NAME,

                        DatabaseContract.Weight.COLUMN_NAME_FLEET,
                        DatabaseContract.Weight.COLUMN_NAME_COVERAGE,
                        DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE,
                        DatabaseContract.Weight.COLUMN_NAME_COST,
                        DatabaseContract.Weight.COLUMN_NAME_TIME,
                        DatabaseContract.Weight.COLUMN_NAME_PACKAGING,

                        DatabaseContract.Weight.COLUMN_NAME_ID
                ),
                new Object[] {
                        weight.getFleet().getWeight(),
                        weight.getCoverage().getWeight(),
                        weight.getExperience().getWeight(),
                        weight.getCost().getWeight(),
                        weight.getTime().getWeight(),
                        weight.getPackaging().getWeight(),

                        weight.getProfile().getId(),
                });
    }

    private static MWeight getByProfile(@NotNull SQLiteDatabase database, @NotNull MProfile profile)
    {
        Timber.d("static getByID");

        final Cursor cursor = database.rawQuery(
                String.format(
                        Locale.getDefault(),
                        "SELECT `%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s` FROM `%s` WHERE `%s` = ? LIMIT 1",
                        DatabaseContract.Weight.COLUMN_NAME_ID,
                        DatabaseContract.Weight.COLUMN_NAME_FLEET,
                        DatabaseContract.Weight.COLUMN_NAME_COVERAGE,
                        DatabaseContract.Weight.COLUMN_NAME_EXPERIENCE,
                        DatabaseContract.Weight.COLUMN_NAME_COST,
                        DatabaseContract.Weight.COLUMN_NAME_TIME,
                        DatabaseContract.Weight.COLUMN_NAME_PACKAGING,

                        DatabaseContract.Weight.TABLE_NAME,

                        DatabaseContract.Weight.COLUMN_NAME_PROFILE
                ),
                new String[] {String.valueOf(profile.getId())});

        MWeight records = null;
        if(cursor.moveToFirst())
        {
            do
            {
                records = new MWeight(
                        cursor.getInt(0),
                        new ContinuousWeight(cursor.getDouble(2)),
                        new ContinuousWeight(cursor.getDouble(3)),
                        new ContinuousWeight(cursor.getDouble(4)),
                        new ContinuousWeight(cursor.getDouble(5)),
                        new ContinuousWeight(cursor.getDouble(6)),
                        new ContinuousWeight(cursor.getDouble(7)),
                        profile
                );
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void insert(@NotNull MWeight weight)
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

        DAOWeight.insert(super.database, weight);
    }

    public void update(@NotNull MWeight weight)
    {
        Timber.d("update");

        try
        {
            super.openWrite();
        }
        catch(SQLException ignored)
        {
            Timber.d("SQLException");
        }

        DAOWeight.update(super.database, weight);
    }

    public MWeight getByProfile(@NotNull MProfile profile)
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

        return DAOWeight.getByProfile(super.database, profile);
    }
}
