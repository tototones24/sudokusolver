package com.example.antoniocontreras.sudokusolver;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

public class GridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint black = new Paint();
    private Paint gray = new Paint();
    private Paint blue = new Paint();
    private boolean[][] cellChecked;

    public GridView(Context context) {
        this(context, null);
        black.setColor(Color.BLACK);
        black.setStrokeWidth(8);
        gray.setColor(Color.DKGRAY);
        gray.setStrokeWidth(2);
        blue.setColor(Color.BLUE);
        blue.setTextSize(40);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }

    // See https://stackoverflow.com/questions/42280172/having-trouble-drawing-a-square-grid-for-an-android-game
    @Override
    protected void onDraw(Canvas canvas) {
        float startX;
        float stopX;
        float startY;
        float stopY;

        int width = getWidth();
        int height = getHeight();

        int gridSize = 9;
        int gridSpacing = Math.min(width, height) / gridSize;
        int boardSize = gridSize * gridSpacing;

        int xOffset = (width - boardSize) / 2;
        int yOffset = (height - boardSize) / 2;

        String s = "1";
        String t = "2";

        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset + i*gridSpacing;
            startY = yOffset;

            stopX = startX;
            stopY = startY + boardSize;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black);
                //canvas.drawText(s, startX+10, startY+40, blue);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray);
                //canvas.drawText(t, startX+10, startY+40, blue);
            }
        }

        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset;
            startY = yOffset + i*gridSpacing;

            stopX = startX + boardSize;
            stopY = startY;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black);
                //if (i != 9)
                //    canvas.drawText(s, startX+10, startY+40, blue);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray);
                //canvas.drawText(t, startX+10, startY+40, blue);
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cellChecked[row][col]){
                    canvas.drawText(s, xOffset+row*gridSpacing, yOffset+col*gridSpacing, blue);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            Log.i("CellSelected", "onTouchEvent: " + column + ", " + row);

            cellChecked[column][row] = !cellChecked[column][row];
            invalidate();
        }

        return true;
    }
}
