package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
	
	private int mode = 0;
	private int tq = 0;
	
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
		modes.setItems(FXCollections.observableArrayList("FCFS", "RR", "SPN", "HRN", "THRN"));
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
	}
	
	//-버튼 리스너
	@FXML
	private void handleDeleteProcessData() {
		if(processTable.getItems().size() >= 1)
			processTable.getItems().remove(processTable.getItems().size()-1);
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
		if(processTable.getItems().size() != 0) {
			process lastProcess = processTable.getItems().get(processTable.getItems().size()-1);
			tempProcess = new process(lastProcess.getId()+1, lastProcess.getArrTime()+1, 0);
		}else {
			tempProcess = new process(1, 0, 0);
		}
		mainApp.getProcessData().add(tempProcess);
	}
	
	//Run버튼 리스너
	@FXML
	private void handleSimulatorRun() {
		if(isValidData()) {
			if(tqTF.getText() != null && tqTF.getText().length() > 0) tq=Integer.parseInt(tqTF.getText());
			Simulator s = new Simulator();
			s.setUp(processTable.getItems());
			s.setMain(mainApp);
			switch (mode) {
			case 0:
				mainApp.showProcessProgressChart(s.runFCFS(), "FCFS");
				break;
			case 1:
				s.setTimeQ(tq);
				mainApp.showProcessProgressChart(s.runRR(), "RR");
				break;
			case 2:
				mainApp.showProcessProgressChart(s.runSPN(), "SPN");
				break;
			case 3:
				mainApp.showProcessProgressChart(s.runHRN(), "HRN");
				break;
			case 4:
				s.setTimeQ(tq);
				mainApp.showProcessProgressChart(s.runTHRN(), "THRN");
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
		if((mode == 1 || mode == 4)) {
			if((tqTF.getText() == null || tqTF.getText().length() == 0)) {
				errmsg += "No valid timeQuantum!\n";
				System.out.println(errmsg);
			}else{
				try {
					Integer.parseInt(tqTF.getText());
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
			return false;
		}
	}
}
