package com.blueto0th.whereismyfood;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameController extends SurfaceView implements SurfaceHolder.Callback {

    private Board board;
    private Renderer renderer;

    public GameController(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        board = new Board();
        board.initialize(17, 31);
        renderer = new Renderer(board);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        final Canvas canvas = holder.lockCanvas();
        renderer.initialize(canvas.getWidth(), canvas.getHeight());
        holder.unlockCanvasAndPost(canvas);
        draw();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void update(int x, int y) {

    }

    public void draw() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            synchronized (canvas)  {
                renderer.draw(canvas);
            }
        } catch (Exception e)  {
            e.printStackTrace();
        } finally {
            if(canvas!= null)  {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}
