package com.blueto0th.whereismyfood;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.PriorityQueue;

public class Player {

    private Board board;
    private PointF position = new PointF();
    private float speed = 0.05f;
    private char direction = 'S';
    private String path = "";
    private boolean move = false;
    private int state = 1;
    private int stateAdd = 1;
    private int tick = 0;

    public Player(Board board) {
        this.board = board;
    }

    public void update() {
        updateAnimation();
        if (move) {
            move();
        }
        if (isRoundPosition()) {
            if (board.getValue((int) getX(), (int) getY()) == Board.VALUE_BLOCK) {
                move = false;
                return;
            }
            findPath();
            if (!path.isEmpty()) {
                move = true;
                direction = path.charAt(0);
                path = path.substring(1);
            } else {
                move = false;
            }
        }
    }

    private boolean isRoundPosition() {
        return position.x == (int) position.x && position.y == (int) position.y;
    }

    private void updateAnimation() {
        if (move) {
            tick++;
            if (tick % (GameController.TICKS_PER_SECOND / 5) == 0) {
                state += stateAdd;
                if (state == 2 || state == 0) {
                    stateAdd *= -1;
                }
            }
        } else {
            state = 1;
            tick = 0;
        }
    }

    private void move() {
        switch (direction) {
            case 'W': position.y -= speed; break;
            case 'D': position.x += speed; break;
            case 'S': position.y += speed; break;
            case 'A': position.x -= speed; break;
        }
        if (Math.abs(Math.round(position.x) - position.x) < speed * 2 / 3) {
            position.x = Math.round(position.x);
        }
        if (Math.abs(Math.round(position.y) - position.y) < speed * 2 / 3) {
            position.y = Math.round(position.y);
        }
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Bitmap getImage() {
        switch (direction) {
            case 'W': return BitmapManager.PLAYER_UP[state];
            case 'D': return BitmapManager.PLAYER_RIGHT[state];
            case 'S': return BitmapManager.PLAYER_DOWN[state];
            case 'A': return BitmapManager.PLAYER_LEFT[state];
        }
        return null;
    }

    public void reset() {
        position.x = 0;
        position.y = 0;
        direction = 'S';
        path = "";
    }

    private void findPath() {
        path = "";
        int row = board.getRow();
        int col = board.getCol();
        int[][] matrix = new int[row][col];
        int minDistance = Integer.MAX_VALUE;
        Tile food = new Tile(-1, -1);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = board.getValue(j, i);
                if (matrix[i][j] == Board.VALUE_FOOD) {
                    int temp = manhattanDistance(j, i, (int) getY(), (int) getX());
                    if (temp < minDistance) {
                        minDistance = temp;
                        food.setPosition(j, i);
                    }
                }
            }
        }
        if (food.getX() == -1 && food.getY() == -1) {
            return;
        }
        pathFinding(matrix, food);
    }

    private void pathFinding(int[][] board, Tile food) {
        PriorityQueue<Tile> queue = new PriorityQueue<>();
        queue.add(new Tile((int) getX(), (int) getY(), -1, food));
        while (!queue.isEmpty()) {
            Tile tile = queue.poll();
            int x = tile.getX();
            int y = tile.getY();
            int value = tile.getValue();
            if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
                continue;
            }
            if (board[y][x] < 0 && value <= board[y][x]) {
                continue;
            }
            if (board[y][x] == Board.VALUE_BLOCK) {
                continue;
            }
            if (board[y][x] == Board.VALUE_FOOD) {
                path = tile.getPath();
                break;
            }
            board[y][x] = value;
            value--;
            queue.add(new Tile(x + 1, y, value, food, tile.getPath() + "D"));
            queue.add(new Tile(x - 1, y, value, food, tile.getPath() + "A"));
            queue.add(new Tile(x, y + 1, value, food, tile.getPath() + "S"));
            queue.add(new Tile(x, y - 1, value, food, tile.getPath() + "W"));
        }
    }

    private class Tile implements Comparable<Tile> {
        private Point position;
        private int distance = Integer.MAX_VALUE;
        private String path = "";
        private int value = 0;

        Tile(int x, int y) {
            position = new Point(x, y);
        }
        Tile(int x, int y, int value, Tile food) {
            position = new Point(x, y);
            this.value = value;
            distance = Math.abs(food.getX() - position.x) + Math.abs(food.getY() - position.y);
        }

        Tile(int x, int y, int value, Tile food, String path) {
            position = new Point(x, y);
            this.value = value;
            this.path = path;
            distance = euclideanDistance(x, y, food.getX(), food.getY());
        }

        void setPosition(int x, int y) {
            position.x = x;
            position.y = y;
        }

        String getPath() {
            return path;
        }

        int getX() {
            return position.x;
        }

        int getY() {
            return position.y;
        }

        int getValue() {
            return value;
        }

        @Override
        public int compareTo(Tile o) {
            if (value == o.value) {
                return distance - o.distance;
            }
            return o.value - value;
        }
    }

    private static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }
}
