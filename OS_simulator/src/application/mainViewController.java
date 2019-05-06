package application;

import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class mainViewController {
	@FXML
	private TableView<process> processTable;
	@FXML
	private TableColumn<process, Integer> idColumn;
	@FXML
	private TableColumn<process, Integer> atColumn;
	@FXML
	private TableColumn<process, Integer> btColumn;
	@FXML
	private TableColumn<process, Integer> wtColumn;
	@FXML
	private TableColumn<process, Integer> ttColumn;
	@FXML
	private TableColumn<process, Double> nttColumn;
	@FXML
	private ChoiceBox modes;
	@FXML
	private TextField tqTF;
	@FXML
	private Pane animatePane;
	@FXML
	private Pane cmapPane;
	
	private int mode = 0;
	private int tq = 0;
	private colorMap cMap;
	
	private Main mainApp = null;
	
	public mainViewController() {
		
	}
	
	
	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		atColumn.setCellValueFactory(cellData -> cellData.getValue().arrTimeProperty().asObject());
		btColumn.setCellValueFactory(cellData -> cellData.getValue().burstProperty().asObject());
		wtColumn.setCellValueFactory(cellData -> cellData.getValue().waitProperty().asObject());
		ttColumn.setCellValueFactory(cellData -> cellData.getValue().ttProperty().asObject());
		nttColumn.setCellValueFactory(cellData -> cellData.getValue().nttProperty().asObject());
		modes.setItems(FXCollections.observableArrayList("FCFS", "RR", "SPN", "SRTN", "HRN", "THRN"));
		modes.getSelectionModel().selectFirst();
		modes.setTooltip(new Tooltip("set simulation mode"));
		
		//열 선택 더블 클릭 리스너
		processTable.setRowFactory(tv-> {
			TableRow<process> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (! row.isEmpty())) {
					process rowData = row.getItem();
					if(rowData != null) {
						boolean okClicked = mainApp.showProcessDataEditor(rowData);
						if(okClicked) {
							
						}
					}else {
						
					}
				}
			});
			return row;
		});
		
		
		//초이스 박스 리스너
		modes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				setMode(new_value.intValue());
				System.out.println("mode = " + mode);
			}
		});
		
		
	}
	
	public void setMain(Main main) {
		this.mainApp = main;
		
		processTable.setItems(mainApp.getProcessData());
		
		cMap = new colorMap(mainApp.getProcessData().size());
		createColorPickerPane();
	}
	
	public void createColorPickerPane() {
		cmapPane.getChildren().clear();
		Label title = new Label("Process Color Map");
		cmapPane.getChildren().add(title);
		VBox paneLayoutC = new VBox();
		paneLayoutC.setTranslateY(30);
		for(int j = 0 ; j<=cMap.getSize()/5; j++) {
			HBox paneLayoutR = new HBox();
			for(int i = 0; i<5; i++) {
				if(j*5+i >= cMap.getSize()) break;
				Rectangle colorr = new Rectangle(0, 0, 30, 30);
				Label colorText = new Label("P"+Integer.toString(j*5+i+1));
				
				colorText.setStyle("-fx-text-fill: #ffffff;");
				
				try{colorr.setFill(Color.web(cMap.getColorOfI(j*5+i)));} catch(IllegalArgumentException e) {
				}
				Group g = new Group(colorr, colorText);
				g.setOnMouseClicked(event -> {
					if(event.getClickCount() == 2) {
						Label tmp = (Label)g.getChildren().get(1);
						Rectangle tmpr = (Rectangle)g.getChildren().get(0);
						System.out.println(tmp.getText() +" "+ tmpr.getFill().toString());
						String s = (String) tmp.getText().subSequence(1, tmp.getText().length());
						int cmapPos = Integer.parseInt(s);
						cMap.resetColorPosI(cmapPos-1);
						System.out.println(cMap.getColorOfI(cmapPos-1));
						createColorPickerPane();
					}
				});
				paneLayoutR.getChildren().add(g);
			}
			paneLayoutC.getChildren().add(paneLayoutR);
		}
		cmapPane.getChildren().add(paneLayoutC);
	}
	
	//-버튼 리스너
	@FXML
	private void handleDeleteProcessData() {
		if(processTable.getItems().size() >= 1) {
			processTable.getItems().remove(processTable.getItems().size()-1);
			cMap.removeColorMap();
			createColorPickerPane();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Process Data Exist");
			alert.setContentText("No more have process data in the table");
			
			alert.showAndWait();
		}
	}
	
	//+버튼 리스너
	@FXML
	private void handleAddProcessData() {
		process tempProcess;
		if(processTable.getItems().size() != 0 && processTable.getItems().size() <= 25) {
			process lastProcess = processTable.getItems().get(processTable.getItems().size()-1);
			tempProcess = new process(lastProcess.getId()+1, lastProcess.getArrTime()+1, 0);
		}else if(processTable.getItems().size() == 0){
			tempProcess = new process(1, 0, 0);
		}else {
			return;
		}
		mainApp.getProcessData().add(tempProcess);
		cMap.addColorMap();
		createColorPickerPane();
	}
	
	//Run버튼 리스너
	@FXML
	private void handleSimulatorRun() {
		if(isValidData()) {
			if(tqTF.getText() != null && tqTF.getText().length() > 0) tq=Integer.parseInt(tqTF.getText());
			Simulator s = new Simulator();
			s.setUp(processTable.getItems());
			s.setMain(mainApp);
			animatePane.getChildren().clear();
			switch (mode) {
			case 0:
				Animation(s.runFCFS(), "FCFS");
				break;
			case 1:
				s.setTimeQ(tq);
				Animation(s.runRR(), "RR");
				break;
			case 2:
				Animation(s.runSPN(), "SPN");
				break;
			case 3:
				Animation(s.runSRTN(), "SRTN");
				break;
			case 4:
				Animation(s.runHRN(), "HRN");
				break;
			case 5:
				s.setTimeQ(tq);
				Animation(s.runTHRN(), "THRN");
				break;

			default:
				System.out.println("error");
				break;
			}
		}
		else {
			
		}
	}
	
	private void setMode(int md) {
		mode = md;
	}
	private boolean isValidData() {
		String errmsg = "";
		if((mode == 1 || mode == 5)) {
			if((tqTF.getText() == null || tqTF.getText().length() == 0)) {
				errmsg += "No valid timeQuantum!\n";
				System.out.println(errmsg);
			}
			else{
				try {
					int testInt = Integer.parseInt(tqTF.getText());
					if(testInt <= 0) {
						errmsg += "No vaild tqTF (must be unsigned Integer)\n";
					}
				}catch(NumberFormatException e) {
					errmsg += "No valid tqTF (must be an integer)!\n";
				}
			}
		}
		if(errmsg.length() == 0) {
			return true;
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errmsg);
			
			alert.showAndWait();
			return false;
		}
	}
	
	public void Animation(Queue<ProcessProgress> pq, String mode) {
		Queue<ProcessProgress> animationQ = new LinkedList<>();
		animationQ.addAll(pq);
		
		//StartLine and EndLine Setting
		Line startLine = new Line(animatePane.getLayoutX()+10, animatePane.getLayoutY()+50, animatePane.getLayoutX()+10, animatePane.getLayoutY()+60);
		Label startTime = new Label("0");
		startTime.setTranslateX(startLine.getStartX()-startTime.getWidth()/2);
		startTime.setTranslateY(startLine.getEndY()+10);
		Group startPoint = new Group(startLine, startTime);
		
		Line endLine = new Line(animatePane.getLayoutX()+animatePane.getWidth()-10, animatePane.getLayoutY()+50, animatePane.getLayoutX()+animatePane.getWidth()-10, animatePane.getLayoutY()+60);
		Label endTime = new Label("100");
		endTime.setTranslateX(endLine.getStartX()-15);
		endTime.setTranslateY(endLine.getEndY()+10);
		Group endPoint = new Group(endLine, endTime);

		double lineLength = endLine.getStartX()-startLine.getStartX();
		double xposs[] = new double[animationQ.size()+1];
		int totalTime = 0;
		for(int i = 0; i<animationQ.size(); i++) {
			ProcessProgress tmp =animationQ.remove();
			totalTime+=tmp.getworkTime();
			animationQ.add(tmp);
		}
		endTime.setText(Integer.toString(totalTime));
		for(int i = 0; i<animationQ.size(); i++) {
			ProcessProgress tmp =animationQ.remove();
			if( i == 0) xposs[i]=(double)tmp.getworkTime()/(double)totalTime * lineLength;
			else xposs[i]=xposs[i-1]+(double)tmp.getworkTime()/(double)totalTime * lineLength;
			animationQ.add(tmp);
			System.out.println(xposs[i]);
		}
		
		
		animatePane.getChildren().add(startPoint);
		animatePane.getChildren().add(endPoint);
		
		double nextStartPos = 0;
		
		//Animation Setting
		int index = -1;
		ProgressAnimation pa = new ProgressAnimation(animationQ.remove(), cMap, 10, 50, totalTime, lineLength,index);
		pa.setTimeLine();
		animatePane.getChildren().add(pa.getProcessGroup());
		animatePane.getChildren().add(pa.getEndGroup());
		pa.setFinish(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//End of Animation
				ProgressAnimation pb = new ProgressAnimation(animationQ.remove(), cMap, xposs[pa.getNextIndex()], 50, pa.getTotalTime(), lineLength, pa.getIndex());
				pb.setTimeLine();
				animatePane.getChildren().add(pb.getProcessGroup());
				if(animationQ.isEmpty()) {
					pb.getTimeline().play();
					pb.setFinish(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							mainApp.showProcessProgressChart(pq, mode, cMap);
						}});
				}
				else{
					animatePane.getChildren().add(pb.getEndGroup());
					pb.setFinish(this);
					pb.getTimeline().play();
				}
			}
		});
		pa.getTimeline().play();
		/*
		Label l = new Label("P"+animationQ.peek().getId());
		Rectangle r = new Rectangle(10+nextStartPos, 50, 20, 20);
		r.setFill(Color.web(cMap.getColorOfI(0)));
		l.setTranslateX(10+nextStartPos);
		l.setTranslateY(50);
		l.setTextFill(Color.WHITE);
		Group p1 = new Group(r, l);
		
		
		
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(p1.translateXProperty(), nextPos-r.getWidth());
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//End of Animation
				Line curLine = new Line(p1.getTranslateX()+r.getWidth()+10, animatePane.getLayoutY()+50, p1.getTranslateX()+r.getWidth()+10, animatePane.getLayoutY()+60);
				Label currentTime = new Label(Integer.toString(animationQ.peek().getworkTime()));
				currentTime.setTranslateX(curLine.getStartX());
				currentTime.setTranslateY(curLine.getEndY()+10);
				Group currentGroup = new Group(curLine, currentTime);
				animatePane.getChildren().add(currentGroup);
				mainApp.showProcessProgressChart(pq, mode, cMap);
			}
		},keyValue);
		
		timeline.getKeyFrames().add(keyFrame);
		animatePane.getChildren().add(p1);
		timeline.play();
		*/
		
	}
}
