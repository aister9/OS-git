package application;

import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
	
	private ObservableList<process> processData;
	
	public void setProcessData(ObservableList<process> _processData) {
		this.processData = _processData;
	}
	
	@FXML
	private void initialize() {
		xAxis.setLabel("Time(sec)");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Progress");
	}
	
	
	public void setProgressData(Queue<ProcessProgress> pq, String mode, colorMap cMap) {
		double totalTime = 0;
		int offset = 0, processNum = 0;
		XYChart.Series<Number, String> series[] = new XYChart.Series[pq.size()];
		colorMap colormap = cMap;
		while(!pq.isEmpty()) {
			series[offset] = new XYChart.Series<>();
			series[offset].getData().add(new XYChart.Data<>(pq.peek().getworkTime(),"process" ) );
			series[offset].setName("P " + Integer.toString(pq.peek().getId()));
			
			sbc.getData().add(series[offset]);
			
			for(Node n : sbc.lookupAll(".series"+offset)) {
				Tooltip.install(n,
						new Tooltip("P" + Integer.toString(pq.peek().getId()) + "\n"
								+"ArrivalTime : " + Integer.toString(processData.get(pq.peek().getId()-1).getArrTime())+ "\n"
								+"BurstTime : " + Integer.toString(processData.get(pq.peek().getId()-1).getBustTime())+ "\n"
								+"EndTime : " + Integer.toString(processData.get(pq.peek().getId()-1).getArrTime()+processData.get(pq.peek().getId()-1).getTaTime())+ "\n"));
				n.setStyle("-fx-bar-fill: "+colormap.getColorOfI(pq.peek().getId()-1)+";");
				if(pq.peek().getId() > processNum) processNum = pq.peek().getId();
			}
			totalTime += pq.peek().getworkTime();
			pq.remove();
			offset++;
		}
		for(int i = 1 ; i<= cMap.getSize() ; i++) createRectangle(hbox, i, colormap);
		sbc.setTitle("Process Simulator : " + mode);
		xAxis.setUpperBound(totalTime);
		
	}
	
	public void createRectangle(HBox box, int pnum, colorMap colormap) {
		Rectangle r = new Rectangle(0, 0, 10, 10);
		try{r.setFill(Color.web(colormap.getColorOfI(pnum-1)));
		}catch(IllegalArgumentException e) {
			System.out.println(colormap.getColorOfI(pnum-1));
		}
		Label la = new Label(" P"+pnum+" ");
		la.setTranslateX(r.getTranslateX()+10);
		la.setTranslateY(r.getTranslateY());
		Group g = new Group(r, la);
		/*
		g.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2) {
				Label tmp = (Label)g.getChildren().get(1);
				Rectangle tmpr = (Rectangle)g.getChildren().get(0);
				System.out.println(tmp.getText() + tmpr.getFill().toString());
			}
		});
		*/
		box.getChildren().add(g);
	}
}
