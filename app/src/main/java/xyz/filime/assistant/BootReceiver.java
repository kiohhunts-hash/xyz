package xyz.filime.assistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Android 12 restricts background starts. We deliberately do not
            // secretly launch a UI here. A foreground service can be added
            // after the user explicitly enables it.
        }
    }
}
