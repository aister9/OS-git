package application;

import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProcessCharController {
	@FXML
	private StackedBarChart<Number, String> sbc;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private CategoryAxis yAxis;
	@FXML
	private HBox hbox;
	
	private String colormap[];
	
	@FXML
	private void initialize() {
		xAxis.setLabel("Time(sec)");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Progress");
	}
	
	private void colormapGenerate(int size) {
		colormap = new String[size];
		
		for(int i = 0; i<size; i++) {
			colormap[i]="#";
			Random random = new Random();
			int a = random.nextInt(16777215);
			colormap[i]+=Integer.toHexString(a);
		}
	}
	
	public void setProgressData(Queue<ProcessProgress> pq, String mode) {
		double totalTime = 0;
		int offset = 0, processNum = 0;
		XYChart.Series<Number, String> series[] = new XYChart.Series[pq.size()];
		colormapGenerate(pq.size());
		while(!pq.isEmpty()) {
			series[offset] = new XYChart.Series<>();
			series[offset].getData().add(new XYChart.Data<>(pq.peek().getworkTime(),"process" ) );
			series[offset].setName("P " + Integer.toString(pq.peek().getId()));


			sbc.getData().add(series[offset]);
			for(Node n : sbc.lookupAll(".default-color"+offset+".chart-bar")) {
				n.setStyle("-fx-bar-fill: "+colormap[pq.peek().getId()-1]+";");
				if(pq.peek().getId() > processNum) processNum = pq.peek().getId();
			}
			totalTime += pq.peek().getworkTime();
			pq.remove();
			offset++;
		}
		System.out.println(processNum);
		for(int i = 1 ; i<= processNum ; i++) createRectangle(hbox, i);
		sbc.setLegendSide(Side.BOTTOM);
		sbc.setLegendVisible(true);
		sbc.setTitle("Process Simulator : " + mode);
		xAxis.setUpperBound(totalTime);
		
	}
	
	public void createRectangle(HBox box, int pnum) {
		Rectangle r = new Rectangle(50, 50, 10, 10);
		r.setFill(Color.web(colormap[pnum-1]));
		Label la = new Label("P"+pnum+" ");
		box.getChildren().add(r);
		box.getChildren().add(la);
	}
}
