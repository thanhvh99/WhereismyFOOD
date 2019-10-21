package com.blueto0th.whereismyfood;

public class Board {

    static int VALUE_EMPTY = 0;
    static int VALUE_BLOCK = 1;
    static int VALUE_FOOD = 2;

    private int row;
    private int col;
    private int[][] matrix;

    public Board() {

    }

    public void reset() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = VALUE_EMPTY;
            }
        }
    }

    public int getValue(int x, int y) {
        if (isInRange(x, y)) {
            return matrix[y][x];
        }
        return VALUE_EMPTY;
    }

    public void setValue(int x, int y, int value) {
        if (isInRange(x, y)) {
            matrix[y][x] = value;
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void initialize(int row, int col) {
        this.row = row;
        this.col = col;
        matrix = new int[row][col];
        reset();
    }

    public boolean isInRange(int x, int y) {
        return x >= 0 && x < col && y >= 0 && y < row;
    }


}
