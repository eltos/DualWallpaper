package de.dlyt.yanndroid.dualwallpaper;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class WallpaperService extends Service {

    private WallpaperUtil wallpaperUtil;
    private int uiMode;

    @Override
    public void onCreate() {
        uiMode = getResources().getConfiguration().uiMode;
        wallpaperUtil = new WallpaperUtil(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("WallpaperService", "onStartCommand");
        startForeground(3000, new NotificationCompat.Builder(this, "4000")
                .setSmallIcon(R.drawable.ic_oui_wallpaper_outline)
                .setContentTitle(getString(R.string.wallpaper_service))
                .setContentText(getString(R.string.noti_desc))
                .setContentIntent(PendingIntent.getActivity(
                        WallpaperService.this,
                        0,
                        new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName()).putExtra(Settings.EXTRA_CHANNEL_ID, "4000"),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE))
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build());
        return START_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("WallpaperService", "onConfigurationChanged");
        if (uiMode != newConfig.uiMode) {
            if (newConfig.uiMode == 17) {
                wallpaperUtil.loadWallpaper(true, true);
                wallpaperUtil.loadWallpaper(false, true);
            } else if (newConfig.uiMode == 33) {
                wallpaperUtil.loadWallpaper(true, false);
                wallpaperUtil.loadWallpaper(false, false);
            }
            uiMode = newConfig.uiMode;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}