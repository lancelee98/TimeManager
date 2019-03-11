package com.lance.timemanager.pictureExamle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.lance.timemanager.R;
import com.lance.timemanager.pictureManagerClass.barchartPictureManager;

import java.util.ArrayList;
import java.util.List;

public class barchartPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart_picture);
        initView();
    }
    private BarChart barChart1, barChart2;
    private void initView() {
        barChart1 = (BarChart) findViewById(R.id.Bar_chat1);
        barChart2 = (BarChart) findViewById(R.id.Bar_chat2);
        showBarChartAlong();

        showBarChartMore();


    }
    //显示2条柱状图
    private void showBarChartMore() {
        barchartPictureManager barChartManager = new barchartPictureManager(barChart2);


        List<Float> xAxisValues = new ArrayList<>();
        List<List<Float>> yAxisValues = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();
        List<Float> x1 = new ArrayList<>();
        List<Float> x2 = new ArrayList<>();
        xAxisValues.add(1.0f);
        xAxisValues.add(2.0f);
        xAxisValues.add(3.0f);
        xAxisValues.add(4.0f);
        xAxisValues.add(5.0f);

        x1.add(10f);
        x1.add(20f);
        x1.add(30f);
        x1.add(40f);
        x1.add(50f);

        x2.add(50f);
        x2.add(40f);
        x2.add(30f);
        x2.add(20f);
        x2.add(10f);
        yAxisValues.add(x1);
        yAxisValues.add(x2);
        labels.add("");
        labels.add("");
        colours.add(Color.parseColor("#123456"));
        colours.add(Color.parseColor("#987654"));
        barChartManager.showMoreBarChart(xAxisValues, yAxisValues, labels, colours);
        barChartManager.setXAxis(5, 0, 5);
    }

    /**
     * 显示单条柱状图
     */
    private void showBarChartAlong() {
        barchartPictureManager barChartManager = new barchartPictureManager(barChart1);

        List<BarEntry> yVals = new ArrayList<>();
        yVals.add(new BarEntry(1f, 80f));
        yVals.add(new BarEntry(2f, 50f));
        yVals.add(new BarEntry(3f, 60f));
        yVals.add(new BarEntry(4f, 60f));
        yVals.add(new BarEntry(5f, 70f));
        yVals.add(new BarEntry(6f, 80f));
        String label = "";
        barChartManager.showBarChart(yVals, label, Color.parseColor("#233454"));
    }

}
