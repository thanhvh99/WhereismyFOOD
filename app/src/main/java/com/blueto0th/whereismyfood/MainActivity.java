package com.blueto0th.whereismyfood;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private GameController controller;

    ExpandableConstraintLayout layout;
    ImageButton drawButton;
    ImageButton eraseButton;
    ImageButton restartButton;
    ImageButton pauseButton;
    ImageButton arrowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new GameController(this);
        setContentView(controller);

        View view = View.inflate(this, R.layout.activity_main, null);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mapping(view);
        setup();
    }

    private void mapping(View view) {
        layout = view.findViewById(R.id.layout);
        drawButton = view.findViewById(R.id.drawButton);
        eraseButton = view.findViewById(R.id.eraseButton);
        restartButton = view.findViewById(R.id.restartButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        arrowButton = view.findViewById(R.id.arrowButton);
    }

    private void setup() {
        BitmapManager.initialize(getApplication());

        layout.setExpandHandler(ExpandableConstraintLayout.WIPE_HORIZONTAL);

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.toggle();
                arrowButton.setImageResource(layout.isExpanded() ? R.drawable.right_arrow : R.drawable.left_arrow);
            }
        });

        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawButton.setAlpha(drawButton.getAlpha() < 0.5f ? 1f : 0.4f);
                controller.setEditMode(drawButton.getAlpha() > 0.5f ? GameController.MODE_DRAW : GameController.MODE_NULL);
                eraseButton.setAlpha(0.4f);
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraseButton.setAlpha(eraseButton.getAlpha() < 0.5f ? 1f : 0.4f);
                controller.setEditMode(eraseButton.getAlpha() > 0.5f ? GameController.MODE_ERASE : GameController.MODE_NULL);
                drawButton.setAlpha(0.4f);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseButton.setAlpha(pauseButton.getAlpha() < 0.5f ? 1f : 0.4f);
                controller.setPause(pauseButton.getAlpha() > 0.5f);
            }
        });

        restartButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                controller.restart();
                return true;
            }
        });
    }
}
