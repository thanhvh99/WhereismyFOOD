package com.blueto0th.whereismyfood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class Renderer {

    private Board board;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    private float size;
    private Rect rect = new Rect();

    public Renderer(Board board) {
        this.board = board;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int row = board.getRow();
        int col = board.getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

            }
        }
    }

    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private int mul(float a, float b) {
        return (int) (a * b);
    }
}
