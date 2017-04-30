package app.freelancer.syafiqq.courierselection.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 6:40 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public abstract class DatabaseModel
{
    protected final Context        context;
    protected       DatabaseHelper dbHelper;
    protected       SQLiteDatabase database;

    public DatabaseModel(final Context context)
    {
        Timber.d("Constructor");

        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public void openWrite() throws SQLException
    {
        Timber.d("openWrite");

        if(!this.isDatabaseReady())
        {
            this.database = this.dbHelper.getWritableDatabase();
        }
    }

    public void openRead() throws SQLException
    {
        Timber.d("openRead");

        if(!this.isDatabaseReady())
        {
            this.database = this.dbHelper.getReadableDatabase();
        }
    }

    public void close()
    {
        Timber.d("close");

        this.dbHelper.close();
    }

    public boolean isDatabaseReady()
    {
        Timber.d("isDatabaseReady");

        return this.database != null && this.database.isOpen();
    }
}
