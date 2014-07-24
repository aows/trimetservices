package ws.otero.adrian.portlandbus.trimetservices;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class TrimetServices {

    private static final String TAG = TrimetServices.class.getName();

    private static final TrimetServices mInstance = new TrimetServices();
    private String APP_ID = null;

    public static TrimetServices get() {
        if (mInstance.APP_ID == null || mInstance.APP_ID.trim().length() == 0) {
            throw new IllegalStateException("APP_ID not set");
        }
        return mInstance;
    }

    public static TrimetServices init(String apiKey) {
        mInstance.APP_ID = apiKey;
        return mInstance;
    }

    public static TrimetServices init(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            mInstance.APP_ID = bundle.getString("TRIMET_APP_ID");
            return mInstance;
        } catch (Exception e) {
            Log.e(TAG, "Error getting APP_ID from metadata: " + e.getMessage());
            return null;
        }
    }

    public String getAppId() {
        return APP_ID;
    }
}
