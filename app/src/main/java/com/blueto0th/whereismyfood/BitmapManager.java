package com.blueto0th.whereismyfood;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {

    private static BitmapManager manager = new BitmapManager();

    static Bitmap TOP_LEFT;
    static Bitmap TOP_RIGHT;
    static Bitmap BOTTOM_RIGHT;
    static Bitmap BOTTOM_LEFT;
    static Bitmap LEFT;
    static Bitmap RIGHT;
    static Bitmap MIDDLE;
    static Bitmap BLOCK;
    static Bitmap TOP;
    static Bitmap BOTTOM;

    static Bitmap[] PLAYER_UP;
    static Bitmap[] PLAYER_LEFT;
    static Bitmap[] PLAYER_DOWN;
    static Bitmap[] PLAYER_RIGHT;

    static Bitmap FOOD;

    private BitmapManager() {

    }

    public static void initialize(Application application) {
        Resources resources = application.getResources();

        TOP_LEFT = BitmapFactory.decodeResource(resources, R.drawable.top_left);
        TOP_RIGHT = BitmapFactory.decodeResource(resources, R.drawable.top_right);
        BOTTOM_RIGHT = BitmapFactory.decodeResource(resources, R.drawable.bottom_right);
        BOTTOM_LEFT = BitmapFactory.decodeResource(resources, R.drawable.bottom_left);
        LEFT = BitmapFactory.decodeResource(resources, R.drawable.left);
        RIGHT = BitmapFactory.decodeResource(resources, R.drawable.right);
        MIDDLE = BitmapFactory.decodeResource(resources, R.drawable.middle);
        BLOCK = BitmapFactory.decodeResource(resources, R.drawable.block);
        TOP = BitmapFactory.decodeResource(resources, R.drawable.top);
        BOTTOM = BitmapFactory.decodeResource(resources, R.drawable.bottom);
        PLAYER_UP = new Bitmap[3];
        PLAYER_LEFT = new Bitmap[3];
        PLAYER_DOWN = new Bitmap[3];
        PLAYER_RIGHT = new Bitmap[3];
        PLAYER_UP[0] = BitmapFactory.decodeResource(resources, R.drawable.player_up_1);
        PLAYER_LEFT[0] = BitmapFactory.decodeResource(resources, R.drawable.player_left_1);
        PLAYER_DOWN[0] = BitmapFactory.decodeResource(resources, R.drawable.player_down_1);
        PLAYER_RIGHT[0] = BitmapFactory.decodeResource(resources, R.drawable.player_right_1);
        PLAYER_UP[1] = BitmapFactory.decodeResource(resources, R.drawable.player_up);
        PLAYER_LEFT[1] = BitmapFactory.decodeResource(resources, R.drawable.player_left);
        PLAYER_DOWN[1] = BitmapFactory.decodeResource(resources, R.drawable.player_down);
        PLAYER_RIGHT[1] = BitmapFactory.decodeResource(resources, R.drawable.player_right);
        PLAYER_UP[2] = BitmapFactory.decodeResource(resources, R.drawable.player_up_2);
        PLAYER_LEFT[2] = BitmapFactory.decodeResource(resources, R.drawable.player_left_2);
        PLAYER_DOWN[2] = BitmapFactory.decodeResource(resources, R.drawable.player_down_2);
        PLAYER_RIGHT[2] = BitmapFactory.decodeResource(resources, R.drawable.player_right_2);

        FOOD = BitmapFactory.decodeResource(resources, R.drawable.food);
    }
}
