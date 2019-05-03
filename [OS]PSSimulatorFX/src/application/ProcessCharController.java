<<<<<<< HEAD
package application;

import java.util.Arrays;
import java.util.Queue;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class ProcessCharController {
	@FXML
	private StackedBarChart<Number, String> sbc;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private CategoryAxis yAxis;
	
	
	@FXML
	private void initialize() {
		xAxis.setLabel("Time(sec)");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Progress");
	}
	
	public void setProgressData(Queue<ProcessProgress> pq) {
		double totalTime = 0;
		while(!pq.isEmpty()) {
			XYChart.Series<Number, String> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>(pq.peek().getworkTime(),"process" ) );
			series.setName("P " + Integer.toString(pq.peek().getId()));
			
			sbc.getData().add(series);
			totalTime += pq.peek().getworkTime();
			pq.remove();
		}
		sbc.setLegendVisible(true);
		xAxis.setUpperBound(totalTime);
		
	}
}
=======
package application;

import java.util.Arrays;
import java.util.Queue;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class ProcessCharController {
	@FXML
	private StackedBarChart<Number, String> sbc;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private CategoryAxis yAxis;
	
	
	@FXML
	private void initialize() {
		xAxis.setLabel("Time(sec)");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Progress");
	}
	
	public void setProgressData(Queue<ProcessProgress> pq) {
		double totalTime = 0;
		while(!pq.isEmpty()) {
			XYChart.Series<Number, String> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>(pq.peek().getworkTime(),"process" ) );
			series.setName("P " + Integer.toString(pq.peek().getId()));
			
			sbc.getData().add(series);
			totalTime += pq.peek().getworkTime();
			pq.remove();
		}
		sbc.setLegendVisible(true);
		xAxis.setUpperBound(totalTime);
		
	}
}
>>>>>>> f1c07344ca7e846838ad0cb7d04eccc16bcad5a9
