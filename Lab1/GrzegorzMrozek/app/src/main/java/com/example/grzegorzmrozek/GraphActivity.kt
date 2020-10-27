package com.example.grzegorzmrozek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlin.math.abs

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        setSupportActionBar(findViewById(R.id.toolbar))

        val real = intent.getDoubleExtra(REAL, 0.0)
        val imaginary = intent.getDoubleExtra(IMAGINARY, 0.0)

        val graph = findViewById<GraphView>(R.id.graph)
        val series = PointsGraphSeries<DataPoint>()

        series.appendData(DataPoint(real, imaginary), true, 1)

        graph.addSeries(series)

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true

        val aspect = 1.4

        graph.viewport.setMinY(-abs(imaginary) * aspect)
        graph.viewport.setMaxY(abs(imaginary) * aspect)
        graph.viewport.setMinX(-abs(real) * aspect)
        graph.viewport.setMaxX(abs(real) * aspect)

        if (real == 0.0) {
            graph.viewport.setMinX(-1.0)
            graph.viewport.setMaxX(1.0)
        }

        if (imaginary == 0.0) {
            graph.viewport.setMinY(-1.0)
            graph.viewport.setMaxY(1.0)
        }
    }
}