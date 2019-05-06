package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ProcessDataEditerController {
	@FXML
	private Label pidLabel;
	@FXML
	private TextField arrTimeTextField;
	@FXML
	private TextField brTimeTextField;
	
	private Stage dialogStage;
	private process prc;
	private process preProcess;
	private process nxtProcess;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
		
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setProcess(process p, process pre, process nxt) {
		this.prc = p;
		this.preProcess = pre;
		this.nxtProcess = nxt;
		
		pidLabel.setText(Integer.toString(prc.getId()));
		arrTimeTextField.setText(Integer.toString(prc.getArrTime()));
		brTimeTextField.setText(Integer.toString(prc.getBustTime()));
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	@FXML
	private void handleOK() {
		if(isInputValid()) {
			prc.setArrTime(Integer.parseInt(arrTimeTextField.getText()));
			prc.setBustTime(Integer.parseInt(brTimeTextField.getText()));
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errmsg = "";
		if(arrTimeTextField.getText() == null || arrTimeTextField.getText().length() == 0) {
			errmsg += "No valid arrTime!\n";
		}else{
			try {
				Integer.parseInt(arrTimeTextField.getText());
				if(Integer.parseInt(arrTimeTextField.getText()) < 0) errmsg+="arr Time must be >= 0";
				if(prc.getId()!= preProcess.getId() && (Integer.parseInt(arrTimeTextField.getText()) <= preProcess.getArrTime())) errmsg+="arr Time must be > previous process`s arrival time";
				//도착시간은 무조건 이전값보다 커야함
				if(prc.getId()!= nxtProcess.getId() && (Integer.parseInt(arrTimeTextField.getText()) >= nxtProcess.getArrTime())) errmsg+="arr Time must be < next process`s arrival time";
				//도착시간은 무조건 다음값보다 작아야함
			}catch(NumberFormatException e) {
				errmsg += "No valid arrTime (must be an integer)!\n";
			}
		}
		if(brTimeTextField.getText() == null || brTimeTextField.getText().length() == 0) {
			errmsg += "No valid brTime!\n";
		}else{
			try {
				Integer.parseInt(brTimeTextField.getText());
				if(Integer.parseInt(brTimeTextField.getText()) <= 0) errmsg+="br Time must be > 0";
			}catch(NumberFormatException e) {
				errmsg += "No valid brTime (must be an integer)!\n";
			}
		}
		if(errmsg.length() == 0) {
			return true;
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errmsg);
			
			alert.showAndWait();
			return false;
		}
	}
}
