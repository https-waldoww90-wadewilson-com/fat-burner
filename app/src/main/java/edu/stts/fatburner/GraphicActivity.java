package edu.stts.fatburner;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GraphicActivity extends AppCompatActivity {
    BarGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        /*int y,x;
        x = 0;
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        series = new BarGraphSeries<DataPoint>();
        series.setSpacing(50);
        for(int i = 0; i<10;i++){
            y = 10 - x;
            series.appendData(new DataPoint(x,y),true,20);
            x = x + 1;
        }
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint dataPoint) {
                if(dataPoint.getX() % 2 == 0){
                    return Color.rgb(175, 255, 120);
                }else {
                    return Color.rgb(255, 121, 0);
                }
            }
        });
        graph.addSeries(series);*/

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 2),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });
        series.setSpacing(10);
        graph.addSeries(series);
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GraphicActivity.this, new SimpleDateFormat("yyyy-MM-dd")));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getViewport().setMinY(0);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(false);

    }
}
