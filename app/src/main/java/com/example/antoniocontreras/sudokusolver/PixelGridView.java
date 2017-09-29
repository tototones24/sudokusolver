package com.example.antoniocontreras.sudokusolver;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
/**
 * Created by antoniocontreras on 9/29/17.
 */
public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint black = new Paint();
    private Paint gray = new Paint();
    private boolean[][] cellChecked;

    public PixelGridView(Context context) {
        this(context, null);
        black.setColor(Color.BLACK);
        black.setStrokeWidth(8);
        gray.setColor(Color.DKGRAY);
        gray.setStrokeWidth(2);
    }

    public PixelGridView(Context context, AttributeSet attrs) {
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

        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset + i*gridSpacing;
            startY = yOffset;

            stopX = startX;
            stopY = startY + boardSize;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray);
            }
        }

        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset;
            startY = yOffset + i*gridSpacing;

            stopX = startX + boardSize;
            stopY = startY;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray);
            }
        }

//        canvas.drawColor(Color.WHITE);
//
//        if (numColumns == 0 || numRows == 0) {
//            return;
//        }
//
//        int width = getWidth();
//        int height = getHeight();
//
//        for (int i = 0; i < numColumns; i++) {
//            for (int j = 0; j < numRows; j++) {
//                if (cellChecked[i][j]) {
//
//                    canvas.drawRect(i * cellWidth, j * cellHeight,
//                            (i + 1) * cellWidth, (j + 1) * cellHeight,
//                            blackPaint);
//                }
//            }
//        }
//
//        for (int i = 1; i < numColumns; i++) {
//            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
//        }
//
//        for (int i = 1; i < numRows; i++) {
//            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            cellChecked[column][row] = !cellChecked[column][row];
            invalidate();
        }

        return true;
    }

}
