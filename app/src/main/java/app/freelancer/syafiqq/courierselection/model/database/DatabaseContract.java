package app.freelancer.syafiqq.courierselection.model.database;

import android.provider.BaseColumns;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 6:00 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public final class DatabaseContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract()
    {
    }

    /* Inner class that defines the table contents */
    public static class Alternative implements BaseColumns
    {
        public static final String TABLE_NAME             = "alternative";
        public static final String COLUMN_NAME_ID         = "id";
        public static final String COLUMN_NAME_NAME       = "name";
        public static final String COLUMN_NAME_FLEET      = "fleet";
        public static final String COLUMN_NAME_COVERAGE   = "coverage";
        public static final String COLUMN_NAME_EXPERIENCE = "experience";
        public static final String COLUMN_NAME_COST       = "cost";
        public static final String COLUMN_NAME_TIME       = "time";
        public static final String COLUMN_NAME_PACKAGING  = "packaging";
        public static final String COLUMN_NAME_PROFILE    = "profile";
        public static final String COLUMN_NAME_ACTIVE     = "active";
    }

    /* Inner class that defines the table contents */
    public static class Weight implements BaseColumns
    {
        public static final String TABLE_NAME             = "weight";
        public static final String COLUMN_NAME_ID         = "id";
        public static final String COLUMN_NAME_FLEET      = "fleet";
        public static final String COLUMN_NAME_COVERAGE   = "coverage";
        public static final String COLUMN_NAME_EXPERIENCE = "experience";
        public static final String COLUMN_NAME_COST       = "cost";
        public static final String COLUMN_NAME_TIME       = "time";
        public static final String COLUMN_NAME_PACKAGING  = "packaging";
        public static final String COLUMN_NAME_PROFILE    = "profile";
    }

    /* Inner class that defines the table contents */
    public static class Profile implements BaseColumns
    {
        public static final String TABLE_NAME       = "profile";
        public static final String COLUMN_NAME_ID   = "id";
        public static final String COLUMN_NAME_NAME = "name";
    }
}
