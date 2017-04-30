package app.freelancer.syafiqq.courierselection.model.util;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 29 April 2017, 6:35 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class SystemSetting
{
    private static final SystemSetting ourInstance = new SystemSetting();
    private int profileID;

    private SystemSetting()
    {
        this.profileID = 1;
    }

    public static SystemSetting getInstance()
    {
        return ourInstance;
    }

    public int getProfileID()
    {
        return profileID;
    }
}
