package com.example.mcm_mangacollectionmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.ArrayAdapter_DESCMangaList;
import database.StatisticClass;

public class statistic extends BaseActivity {
    int[] colorArray = new int[] {Color.BLUE, Color.YELLOW,Color.RED, Color.LTGRAY, Color.CYAN, Color.GREEN, Color.MAGENTA};
    Spinner spinnerMoneyFilter, spinnerPublisherFilter;
    Button btnDatePicker_Money, btnDatePicker_Publisher;

    BarChart chartMoney,  chartPublisher;
    PieChart chartManga;

    TextView txtNeed, txtHad, txtSum;
    String[] filterArray = {"Theo ngày", "Theo tháng", "Theo năm"};

    ArrayList<String> publisherTitles;
    ArrayList<Integer> mangaQuant;

    ArrayList<String> mangaStatus;
    ArrayList<Integer> mangaCount;
    StatisticClass statistic;

    BarDataSet publisherBarDataSet;
    BarData publisherBarData;

    PieDataSet mangaPieDataSet;
    PieData mangaPieData;

    BarDataSet moneyBarDataSet;
    BarData moneyBarData;

    TextView txtShowMangaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_statistic, findViewById(R.id.frameContent));
        txtShowMangaList = findViewById(R.id.txtShowMangaList);
        spinnerMoneyFilter = findViewById(R.id.spinnerMoneyFilter);
        spinnerPublisherFilter = findViewById(R.id.spinnerPublisherFilter);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterArray);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoneyFilter.setAdapter(filterAdapter);
        spinnerPublisherFilter.setAdapter(filterAdapter);

        txtNeed = findViewById(R.id.txtNeed);
        txtHad  = findViewById(R.id.txtHad);
        txtSum   = findViewById(R.id.txtSum);

        btnDatePicker_Money = findViewById(R.id.btnDatePicker_Money);
        btnDatePicker_Publisher = findViewById(R.id.btnDatePicker_Publisher);
        btnDatePicker_Money.setText(getTodayDate());
        btnDatePicker_Publisher.setText(getTodayDate());
        initDatePicker(btnDatePicker_Money);
        initDatePicker(btnDatePicker_Publisher);
        statistic = new StatisticClass(this);
        publisherTitles = new ArrayList<>();
        mangaQuant = new ArrayList<>();
        chartPublisher = findViewById(R.id.chartPublisher);
        chartManga = findViewById(R.id.chartManga);
        mangaStatus = new ArrayList<>();
        mangaCount = new ArrayList<>();
        chartMoney = findViewById(R.id.chartMoney);
        spinnerPublisherFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                publisherTitles.clear();
                mangaQuant.clear();
                statistic.getQuantityPublisher(publisherTitles,mangaQuant,i+1,btnDatePicker_Publisher.getText().toString());
                setPublisherChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDatePicker_Publisher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                publisherTitles.clear();
                mangaQuant.clear();
                statistic.getQuantityPublisher(publisherTitles, mangaQuant, 0, "");
                setPublisherChart();
                return true;
            }
        });
        setMangaChart();

        spinnerMoneyFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer[] arr = statistic.getMoney(i,btnDatePicker_Money.getText().toString());
                setMoneyChart(arr);
                txtNeed.setText(String.valueOf(arr[0]));
                txtHad.setText(String.valueOf(arr[1]));
                int sum = arr[0] + arr[1];
                txtSum.setText(String.valueOf(sum));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Integer[] arr = statistic.getMoney(0,btnDatePicker_Money.getText().toString());
                setMoneyChart(arr);
                txtNeed.setText(String.valueOf(arr[0]));
                txtHad.setText(String.valueOf(arr[1]));
                int sum = arr[0] + arr[1];
                txtSum.setText(String.valueOf(sum));
            }

        });
        txtShowMangaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.mcm_mangacollectionmanagement.statistic.this,descmangalist.class);
                startActivity(intent);
            }
        });
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }

    private void initDatePicker(Button dateButton) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light, dateSetListener, year, month, day);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void setPublisherChart(){
        ArrayList<BarEntry> publisherValues = new ArrayList<>();
        for (int i = 0; i < publisherTitles.size();i++){
            publisherValues.add(new BarEntry(i,mangaQuant.get(i)));
        }
        XAxis xAxis = chartPublisher.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(publisherTitles));
        xAxis.setGranularity(1f); // Đảm bảo mỗi cột là một giá trị
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        publisherBarDataSet = new BarDataSet(publisherValues,"Số truyện");
        publisherBarData = new BarData(publisherBarDataSet);
        chartPublisher.setData(publisherBarData);
        chartPublisher.invalidate();
    }
    public void setMangaChart(){
        statistic.getMangaStatus(mangaStatus, mangaCount);
        ArrayList<PieEntry> mangaValues = new ArrayList<>();
        for (int i = 0; i< mangaStatus.size();i++){
            mangaValues.add(new PieEntry(mangaCount.get(i),mangaStatus.get(i)));
        }
        mangaPieDataSet = new PieDataSet(mangaValues,"");
        mangaPieDataSet.setColors(colorArray);
        mangaPieDataSet.setValueTextColor(Color.BLACK);
        mangaPieData = new PieData(mangaPieDataSet);
        mangaPieData.setValueTextColor(Color.BLACK);
        mangaPieData.setValueTextSize(16f);
        chartManga.setHoleRadius(30);
        chartManga.setTransparentCircleRadius(10);
        chartManga.setData(mangaPieData);
        Legend legend = chartManga.getLegend();
        legend.setTextColor(Color.BLACK);
        chartManga.invalidate();
    }
    public void setMoneyChart(Integer[] results){
        ArrayList<BarEntry> moneyValues = new ArrayList<>();
        for (int i = 0; i < results.length;i++){
            moneyValues.add(new BarEntry(i,results[i]));
        }
        XAxis xAxis = chartMoney.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Chưa có", "Đã có"}));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        moneyBarDataSet = new BarDataSet(moneyValues,"VND");
        moneyBarData = new BarData(moneyBarDataSet);
        chartMoney.setData(moneyBarData);
        chartMoney.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMangaChart();
        publisherTitles.clear();
        mangaQuant.clear();
        statistic.getQuantityPublisher(publisherTitles,mangaQuant,1,btnDatePicker_Publisher.getText().toString());
        setPublisherChart();
        Integer[] arr = statistic.getMoney(0,btnDatePicker_Money.getText().toString());
        setMoneyChart(arr);
        txtNeed.setText(String.valueOf(arr[0]));
        txtHad.setText(String.valueOf(arr[1]));
        int sum = arr[0] + arr[1];
        txtSum.setText(String.valueOf(sum));
    }
}