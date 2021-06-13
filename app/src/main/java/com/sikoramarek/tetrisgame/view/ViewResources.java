package com.sikoramarek.tetrisgame.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sikoramarek.tetrisgame.R;

import static com.sikoramarek.tetrisgame.view.BlockColors.BLUE;
import static com.sikoramarek.tetrisgame.view.BlockColors.GREEN;
import static com.sikoramarek.tetrisgame.view.BlockColors.ORANGE;
import static com.sikoramarek.tetrisgame.view.BlockColors.RED;
import static com.sikoramarek.tetrisgame.view.BlockColors.VIOLET;
import static com.sikoramarek.tetrisgame.view.BlockColors.YELLOW;

class ViewResources {

    private final Bitmap[] activeImages;
    private final Bitmap[] activeImagesThumbs;
    private final Bitmap[] inactiveImages;

    ViewResources(Resources resources) {

        activeImages = new Bitmap[BlockColors.values().length];
        activeImagesThumbs = new Bitmap[BlockColors.values().length];
        inactiveImages = new Bitmap[BlockColors.values().length];

        activeImages[RED.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.redblock);
        inactiveImages[RED.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.redblock_i);

        activeImages[BLUE.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.blueblock);
        inactiveImages[BLUE.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.blueblock_i);

        activeImages[GREEN.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.greenblock);
        inactiveImages[GREEN.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.greenblock_i);

        activeImages[ORANGE.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.orangeblock);
        inactiveImages[ORANGE.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.orangeblock_i);

        activeImages[VIOLET.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.violetblock);
        inactiveImages[VIOLET.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.violetblock_i);

        activeImages[YELLOW.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.yellowblock);
        inactiveImages[YELLOW.getValue()] = BitmapFactory.decodeResource(resources, R.drawable.yellowblock_i);
    }

    protected Bitmap getActiveBlock(BlockColors color) {
        switch (color) {
            case BLUE:
                return activeImages[BLUE.getValue()];
            case GREEN:
                return activeImages[GREEN.getValue()];
            case ORANGE:
                return activeImages[ORANGE.getValue()];
            case RED:
                return activeImages[RED.getValue()];
            case VIOLET:
                return activeImages[VIOLET.getValue()];
            case YELLOW:
                return activeImages[YELLOW.getValue()];
        }
        return activeImages[RED.getValue()];
    }

    protected Bitmap getInactiveBlock(BlockColors color) {
        switch (color) {
            case BLUE:
                return inactiveImages[BLUE.getValue()];
            case GREEN:
                return inactiveImages[GREEN.getValue()];
            case ORANGE:
                return inactiveImages[ORANGE.getValue()];
            case RED:
                return inactiveImages[RED.getValue()];
            case VIOLET:
                return inactiveImages[VIOLET.getValue()];
            case YELLOW:
                return inactiveImages[YELLOW.getValue()];
        }
        return inactiveImages[RED.getValue()];
    }

    protected Bitmap getThumbnail(BlockColors color) {
        switch (color) {
            case BLUE:
                return activeImagesThumbs[BLUE.getValue()];
            case GREEN:
                return activeImagesThumbs[GREEN.getValue()];
            case ORANGE:
                return activeImagesThumbs[ORANGE.getValue()];
            case RED:
                return activeImagesThumbs[RED.getValue()];
            case VIOLET:
                return activeImagesThumbs[VIOLET.getValue()];
            case YELLOW:
                return activeImagesThumbs[YELLOW.getValue()];
        }
        return activeImagesThumbs[RED.getValue()];
    }

    protected void resizeImages(float width, float height) {
        float widthToHeightRatio = (width / height);
        float cellHeight = height / 20;
        float cellWidth = width / 10;

        if (widthToHeightRatio > 1) {
            cellWidth = Math.abs((int) (cellWidth * (2 - widthToHeightRatio)));
        }

        for (int i = 0; i < activeImages.length; i++) {
            activeImages[i] = Bitmap.createScaledBitmap(activeImages[i], (int) cellWidth, (int) cellHeight, false);
        }
        for (int i = 0; i < inactiveImages.length; i++) {
            inactiveImages[i] = Bitmap.createScaledBitmap(inactiveImages[i], (int) cellWidth, (int) cellHeight, false);
        }

        for (int i = 0; i < activeImages.length; i++) {
            activeImagesThumbs[i] = Bitmap.createScaledBitmap(activeImages[i], (int) cellWidth / 5, (int) cellHeight / 5, false);
        }
    }
}
