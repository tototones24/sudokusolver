package com.example.antoniocontreras.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test
        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(9);
        pixelGrid.setNumRows(9);

        setContentView(pixelGrid);

//        int[][] grid = new int[9][9];
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                grid[i][j] = 9;
//            }
//        }
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(grid[i][j] + "|");
//            }
//        }


    }



}
