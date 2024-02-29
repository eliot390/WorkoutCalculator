package com.example.workoutcalculator;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphFragment extends Fragment {

    private static final String ARG_PARAM1 = "graphParam1";
    private static final String ARG_PARAM2 = "graphParam2";

    public GraphFragment() {}

    public static GraphFragment newInstance(String param1, String param2) {
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String graphParam1 = getArguments().getString(ARG_PARAM1);
            String graphParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        LineChart graph = view.findViewById(R.id.graph);

        Bundle args = getArguments();
        if(args != null && args.containsKey("selectedMovement")){
            String selectedMovement = args.getString("selectedMovement");
            filterGraph(selectedMovement, graph);
        }

        return view;
    }

    private void filterGraph(String selectedMovement, LineChart graph) {
        List<WorkoutData> workoutDataList;
        try(WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(getActivity())){
            workoutDataList = dbHelper.getDataByMovement(selectedMovement);
        }catch (Exception e){
            e.printStackTrace();
            workoutDataList = new ArrayList<>();
        }

        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Entry> weights = new ArrayList<>();
        int i = 0;
        for(WorkoutData data : workoutDataList){
            dates.add(data.getDateAdded());
            weights.add(new Entry(i, Float.parseFloat(data.getWeight())));
            i++;
        }

        LineDataSet dataSet = new LineDataSet(weights, "Weight Lifted");
        int circleColor = ContextCompat.getColor(requireContext(), R.color.dark_blue);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(7f);
        dataSet.setCircleColor(circleColor);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(20f);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        XAxis xAxis = graph.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(11f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis yAxis = graph.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(11f);

        Legend legend = graph.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(15f);

        graph.setData(lineData);
        graph.invalidate();
    }
}