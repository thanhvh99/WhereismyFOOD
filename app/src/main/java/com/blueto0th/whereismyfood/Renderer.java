package com.blueto0th.whereismyfood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class Renderer {

    private Board board;
    private Player player;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    private int size;
    private Rect rect = new Rect();

    public Renderer(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getSize() {
        return size;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);

        int row = board.getRow();
        int col = board.getCol();
        int x;
        int y;
        for (int i = 1; i < row - 1; i++) {
            y = i * size - yOffset;
            rect.set(-xOffset, y, size - xOffset, y + size);
            canvas.drawBitmap(BitmapManager.LEFT, null, rect, null);
            rect.set((col - 1) * size - xOffset, y, col * size - xOffset, y + size);
            canvas.drawBitmap(BitmapManager.RIGHT, null, rect, null);
            for (int j = 1; j < col - 1; j++) {
                x = j * size - xOffset;
                y = i * size - yOffset;
                rect.set(x, y, x + size, y + size);
                canvas.drawBitmap(BitmapManager.MIDDLE, null, rect, null);
            }
        }
        for (int i = 1; i < col - 1; i++) {
            x = i * size - xOffset;
            rect.set(x, -yOffset, x + size, size - yOffset);
            canvas.drawBitmap(BitmapManager.TOP, null, rect, null);
            rect.set(x, (row - 1) * size - yOffset, x + size, row * size - yOffset);
            canvas.drawBitmap(BitmapManager.BOTTOM, null, rect, null);
        }
        rect.set(-xOffset, -yOffset, size - xOffset, size - yOffset);
        canvas.drawBitmap(BitmapManager.TOP_LEFT, null, rect, null);
        rect.set((col - 1) * size - xOffset, -yOffset, col * size - xOffset, size - yOffset);
        canvas.drawBitmap(BitmapManager.TOP_RIGHT, null, rect, null);
        rect.set(-xOffset, (row - 1) * size - yOffset, size - xOffset, row * size - yOffset);
        canvas.drawBitmap(BitmapManager.BOTTOM_LEFT, null, rect, null);
        rect.set((col - 1) * size - xOffset, (row - 1) * size - yOffset, col * size - xOffset, row * size - yOffset);
        canvas.drawBitmap(BitmapManager.BOTTOM_RIGHT, null, rect, null);


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board.getValue(j, i) != Board.VALUE_EMPTY) {
                    x = j * size - xOffset;
                    y = i * size - yOffset;
                    rect.set(x, y, x + size, y + size);
                    canvas.drawBitmap(board.getValue(j, i) == Board.VALUE_BLOCK ? BitmapManager.BLOCK : BitmapManager.FOOD, null, rect, null);
                }
            }
        }

        x = (int) (player.getX() * size - xOffset);
        y = (int) (player.getY() * size - yOffset);
        rect.set(x, y, x + size, y + size);
        canvas.drawBitmap(player.getImage(), null, rect, null);
    }

    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
        size = (int) Math.min(((float) width) / board.getCol(), ((float) height) / board.getRow());
        xOffset = (board.getCol() * size - width) / 2;
        yOffset = (board.getRow() * size - height) / 2;
    }
}
