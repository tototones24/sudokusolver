package com.example.antoniocontreras.sudokusolver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class GridView extends View {
    private int numColumns = 9, numRows = 9;
    private int[] focusedCell = {-1, -1};
    private int cellWidth, cellHeight;
    private Paint black = new Paint();
    private Paint gray = new Paint();
    private Paint green = new Paint();

    public GridView(Context context) {
        this(context, null);
        black.setColor(Color.BLACK);
        black.setStrokeWidth(8);
        gray.setColor(Color.DKGRAY);
        gray.setStrokeWidth(2);
        green.setColor(Color.GREEN);
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

        if (cellWidth > cellHeight) {
            cellWidth = cellHeight;
        } else {
            cellHeight = cellWidth;
        }

        invalidate();
    }

    // See https://stackoverflow.com/questions/42280172/having-trouble-drawing-a-square-grid-for-an-android-game
    @Override
    protected void onDraw(Canvas canvas) {
        black.setColor(Color.BLACK);
        black.setStrokeWidth(8);
        gray.setColor(Color.DKGRAY);
        gray.setStrokeWidth(2);
        green.setColor(Color.GREEN);
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
        int yOffset = 0;

        // Handle selected cell
        if (focusedCell[0] != -1 && focusedCell[1] != -1) {
            int c = focusedCell[0];
            int r = focusedCell[1];
            canvas.drawRect(c*cellWidth, r*cellHeight, (c+1)*cellWidth, (r+1)*cellHeight, green);
        }

        // Draw vertical lines
        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset + i*gridSpacing;
            startY = yOffset;

            stopX = startX;
            stopY = startY + boardSize;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black); // major axis
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray); // minor axis
            }
        }

        // Draw horizontal lines
        for (int i = 0; i <= gridSize; i++) {
            startX = xOffset;
            startY = yOffset + i*gridSpacing;

            stopX = startX + boardSize;
            stopY = startY;

            if (i % 3 == 0){
                canvas.drawLine(startX, startY, stopX, stopY, black); // major axis
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, gray); // minor axis
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            if (column < 9 && row < 9 && column >= 0 && row >= 0){
                Toast.makeText(getContext(), "[" + column + ", " + row + "]", Toast.LENGTH_SHORT).show();
                focusedCell[0] = column;
                focusedCell[1] = row;
            } else {
                Toast.makeText(getContext(), "Out of bounds", Toast.LENGTH_SHORT).show();
            }

            invalidate();
        }

        return true;
    }
}
