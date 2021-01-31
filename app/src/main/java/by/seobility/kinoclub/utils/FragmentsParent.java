package by.seobility.kinoclub.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentsParent extends Fragment {
    protected String getBaseUrl(){
        @Nullable ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getContext().getPackageManager()
                    .getApplicationInfo(
                            getContext().getPackageName(),
                            PackageManager.GET_META_DATA
                    );
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        @Nullable String baseUrl = null;
        if (applicationInfo != null){
            baseUrl = applicationInfo.metaData.getString("BASE_URL");
        }

        return baseUrl;
    }
}
