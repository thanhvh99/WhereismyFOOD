package com.blueto0th.whereismyfood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

public class GameController extends SurfaceView implements SurfaceHolder.Callback {

    static final int MODE_NULL = 0;
    static final int MODE_DRAW = 1;
    static final int MODE_ERASE = 2;

    static final int TICKS_PER_SECOND = 60;
    private static final double NS_PER_TICK = 1000000000.0 / TICKS_PER_SECOND;
    private static final int LOOP_DELAY = 10;

    private Player player;
    private Board board;
    private Renderer renderer;
    private int editMode = MODE_NULL;
    private Thread thread;
    private boolean running = false;
    private boolean pause = false;
    private Point food = new Point(0, 0);
    private Random random = new Random();
    private SurfaceHolder surfaceHolder;

    public GameController(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        initialize();
    }

    private void initialize() {
        board = new Board();
        board.initialize(13, 25);
        player = new Player(board);
        renderer = new Renderer(board, player);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                double delta = 0;
                long now = System.nanoTime();

                while (running) {
                    try {
                        Thread.sleep(LOOP_DELAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    board.setValue(food.x, food.y, Board.VALUE_FOOD);
                    draw();

                    if (pause) {
                        now = System.nanoTime();
                        continue;
                    }

                    delta -= (now - (now = System.nanoTime())) / NS_PER_TICK;

                    while (delta > 1) {
                        delta -= 1;
                        update();
                        checkFood(false);
                    }
                }
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editMode != MODE_NULL) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            int x = ((int) event.getX() + renderer.getXOffset()) / renderer.getSize();
                            int y = ((int) event.getY() + renderer.getYOffset()) / renderer.getSize();
                            board.setValue(x, y, editMode == MODE_DRAW ? Board.VALUE_BLOCK : Board.VALUE_EMPTY);
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        final Canvas canvas = holder.lockCanvas();
        renderer.initialize(canvas.getWidth(), canvas.getHeight());
        holder.unlockCanvasAndPost(canvas);
        surfaceHolder = holder;
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void update() {
        player.update();
    }

    private void draw() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (canvas) {
                renderer.draw(canvas);
            }
        } catch (Exception e)  {
            e.printStackTrace();
        } finally {
            if (canvas != null)  {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void checkFood(boolean createNew) {
        board.setValue(food.x, food.y, Board.VALUE_EMPTY);
        if (player.getX() == food.x && player.getY() == food.y || createNew) {
            do {
                food.x = random.nextInt(board.getCol());
                food.y = random.nextInt(board.getRow());
            } while (board.getValue(food.x, food.y) == Board.VALUE_BLOCK);
        }
        board.setValue(food.x, food.y, Board.VALUE_FOOD);
    }

    public void setEditMode(int mode) {
        editMode = mode;
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void restart() {
        player.reset();
        checkFood(true);
        board.reset();
    }
}
