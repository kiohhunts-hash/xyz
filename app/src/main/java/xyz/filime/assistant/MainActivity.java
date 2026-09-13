package xyz.filime.assistant;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final int REQ_AUDIO = 100;
    private static final int REQ_VOICE = 101;
    private TextView status, result;
    private boolean flashlightOn = false;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        result = findViewById(R.id.result);

        Button voice = findViewById(R.id.voiceButton);
        Button flash = findViewById(R.id.flashButton);
        Button owner = findViewById(R.id.lockButton);

        voice.setOnClickListener(v -> startVoice());
        flash.setOnClickListener(v -> toggleFlashlight());
        owner.setOnClickListener(v -> authenticateOwner());

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
    }

    private void startVoice() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak a command");
        try { startActivityForResult(i, REQ_VOICE); }
        catch (Exception e) { Toast.makeText(this, "Speech recognition is unavailable", Toast.LENGTH_LONG).show(); }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_VOICE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (list != null && !list.isEmpty()) handleCommand(list.get(0));
        }
    }

    private void handleCommand(String spoken) {
        String cmd = spoken.toLowerCase(Locale.ROOT).trim();
        result.setText("Heard: " + spoken);

        if (cmd.contains("flashlight") || cmd.contains("torch")) {
            toggleFlashlight();
        } else if (cmd.contains("settings")) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        } else if (cmd.contains("open youtube")) {
            Intent launch = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
            if (launch != null) startActivity(launch);
            else Toast.makeText(this, "YouTube is not installed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Command not configured yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleFlashlight() {
        try {
            CameraManager cm = (CameraManager)getSystemService(CAMERA_SERVICE);
            String id = cm.getCameraIdList()[0];
            flashlightOn = !flashlightOn;
            cm.setTorchMode(id, flashlightOn);
            status.setText(flashlightOn ? "Flashlight ON" : "Flashlight OFF");
        } catch (Exception e) {
            Toast.makeText(this, "Flashlight unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private void authenticateOwner() {
        // First version uses Android's secure settings/biometric direction.
        // A custom secret phrase/PIN will be added in the next stage.
        startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
    }
}
