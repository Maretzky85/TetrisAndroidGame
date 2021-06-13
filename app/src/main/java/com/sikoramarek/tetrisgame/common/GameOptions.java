package com.sikoramarek.tetrisgame.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import com.sikoramarek.tetrisgame.MainActivity;
import com.sikoramarek.tetrisgame.R;

public class GameOptions {

    public final static String GAME_OPTIONS_ID = "TETRISINO_GAME_OPTIONS";
    public final static int BLOCK_ROTATION_DEFAULT = 0;
    public final static int BLOCK_ROTATION_RIGHT = 0;
    public final static int BLOCK_ROTATION_LEFT = 1;
    public final static int GAME_ORIENTATION_HORIZONTAL = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public final static int GAME_ORIENTATION_VERTICAL = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public final static int GAME_ORIENTATION_FREE = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    public final static int GAME_ORIENTATION_DEFAULT = GAME_ORIENTATION_FREE;
    public final static String SHARED_PREFERENCES_GAME_ORIENTATION_ID = "SP_GAME_ORIENTATION_ID";
    public final static String SHARED_PREFERENCES_BLOCK_ROTATION_ID = "SP_GAME_BLOCK_ROTATION_ID";
    private final Context context;
    private final SharedPreferences gameOptions;

    public GameOptions() {
        this.context = MainActivity.getContextOfApplication();
        gameOptions = context.getSharedPreferences(GAME_OPTIONS_ID, 0);
    }

    public int getCurrentBlockRotation() {
        return gameOptions.getInt(SHARED_PREFERENCES_BLOCK_ROTATION_ID, BLOCK_ROTATION_DEFAULT);
    }

    public String getCurrentBlockRotationLabel() {
        int blockRotation = gameOptions.getInt(
                SHARED_PREFERENCES_BLOCK_ROTATION_ID,
                BLOCK_ROTATION_RIGHT);

        if (blockRotation == BLOCK_ROTATION_RIGHT) {
            return context.getString(R.string.right);
        } else {
            return context.getString(R.string.left);
        }
    }

    public void changeBlockRotation() {
        int rotation = gameOptions.getInt(
                SHARED_PREFERENCES_BLOCK_ROTATION_ID,
                BLOCK_ROTATION_DEFAULT);

        if (rotation == BLOCK_ROTATION_RIGHT) {
            gameOptions.edit()
                    .putInt(SHARED_PREFERENCES_BLOCK_ROTATION_ID, BLOCK_ROTATION_LEFT).apply();
        } else {
            gameOptions.edit()
                    .putInt(SHARED_PREFERENCES_BLOCK_ROTATION_ID, BLOCK_ROTATION_RIGHT).apply();
        }
    }

    public int getCurrentGameOrientation() {
        return gameOptions.getInt(SHARED_PREFERENCES_GAME_ORIENTATION_ID, GAME_ORIENTATION_DEFAULT);
    }

    public String getCurrentGameOrientationLabel() {
        int gameOrientation = getCurrentGameOrientation();
        switch (gameOrientation) {
            case GAME_ORIENTATION_FREE:
                return context.getString(R.string.gameOrientationFree);
            case GAME_ORIENTATION_VERTICAL:
                return context.getString(R.string.gameOrientationVertical);
            case GAME_ORIENTATION_HORIZONTAL:
            default:
                return context.getString(R.string.gameOrientationHorizontal);
        }
    }

    public void changeGameOrientation() {
        int gameOrientation = gameOptions.getInt(
                SHARED_PREFERENCES_GAME_ORIENTATION_ID,
                GAME_ORIENTATION_DEFAULT);

        switch (gameOrientation) {
            case GAME_ORIENTATION_FREE:
                gameOptions.edit().putInt(
                        SHARED_PREFERENCES_GAME_ORIENTATION_ID,
                        GAME_ORIENTATION_HORIZONTAL).apply();
                break;
            case GAME_ORIENTATION_VERTICAL:
                gameOptions.edit().putInt(
                        SHARED_PREFERENCES_GAME_ORIENTATION_ID,
                        GAME_ORIENTATION_FREE).apply();
                break;
            case GAME_ORIENTATION_HORIZONTAL:
            default:
                gameOptions.edit().putInt(
                        SHARED_PREFERENCES_GAME_ORIENTATION_ID,
                        GAME_ORIENTATION_VERTICAL).apply();
                break;
        }
    }
}
