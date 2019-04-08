package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

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
		
		processTable.setRowFactory(tv-> {
			TableRow<process> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (! row.isEmpty())) {
					process rowData = row.getItem();
					System.out.println(rowData);
				}
			});
			return row;
		});
	}
	
	public void setMain(Main main) {
		this.mainApp = main;
		
		processTable.setItems(mainApp.getProcessData());
	}
	
	@FXML
	private void handleDeleteProcessData() {
		int selectedIndex = processTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
			processTable.getItems().remove(selectedIndex);
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Process Data Selected");
			alert.setContentText("Please select a process in the table");
			
			alert.showAndWait();
		}
	}
	@FXML
	private void handelAddProcessData() {
		process tempProcess;
		if(processTable.getItems().size() != 0) {
			process lastProcess = processTable.getItems().get(processTable.getItems().size()-1);
			tempProcess = new process(lastProcess.getId()+1, lastProcess.getArrTime()+1, 0);
		}else {
			tempProcess = new process(1, 0, 0);
		}
		mainApp.getProcessData().add(tempProcess);
	}
}
