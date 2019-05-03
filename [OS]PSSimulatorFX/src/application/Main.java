<<<<<<< HEAD
package application;
	
import java.io.IOException;
import java.util.Queue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	private Stage primaryStage;
	private GridPane rootLayout;
	private ObservableList<process> processData = FXCollections.observableArrayList();
	
	public Main() {
		processData.add(new process(1, 0, 3));
		processData.add(new process(2, 1, 7));
		processData.add(new process(3, 3, 2));
		processData.add(new process(4, 5, 5));
		processData.add(new process(5, 6, 3));
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Process Scheduler Simulator");
			
			initRootLayout();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("SimulatorForm.fxml"));
			rootLayout = (GridPane) loader.load();
			
			mainViewController mvC = loader.getController();
			mvC.setMain((Main) this);
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<process> getProcessData(){
		return processData;
	}
	
	public boolean processDataChange(process p) {
		processData.set(p.getId()-1, p);
		return true;
	}
	
	public boolean showProcessDataEditor(process p) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ProcessDataEditer.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Process");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			ProcessDataEditerController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProcess(p);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void showProcessProgressChart(Queue<ProcessProgress> pq) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ProcessProgressChartForm.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Progress Chart Overview");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			ProcessCharController controller = loader.getController();
			System.out.println(pq);
			controller.setProgressData(pq);
			
			dialogStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
=======
package application;
	
import java.io.IOException;
import java.util.Queue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	private Stage primaryStage;
	private GridPane rootLayout;
	private ObservableList<process> processData = FXCollections.observableArrayList();
	
	public Main() {
		processData.add(new process(1, 0, 3));
		processData.add(new process(2, 1, 7));
		processData.add(new process(3, 3, 2));
		processData.add(new process(4, 5, 5));
		processData.add(new process(5, 6, 3));
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Process Scheduler Simulator");
			
			initRootLayout();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("SimulatorForm.fxml"));
			rootLayout = (GridPane) loader.load();
			
			mainViewController mvC = loader.getController();
			mvC.setMain((Main) this);
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<process> getProcessData(){
		return processData;
	}
	
	public boolean processDataChange(process p) {
		processData.set(p.getId()-1, p);
		return true;
	}
	
	public boolean showProcessDataEditor(process p) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ProcessDataEditer.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Process");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			ProcessDataEditerController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProcess(p);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void showProcessProgressChart(Queue<ProcessProgress> pq) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ProcessProgressChartForm.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Progress Chart Overview");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			ProcessCharController controller = loader.getController();
			System.out.println(pq);
			controller.setProgressData(pq);
			
			dialogStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
>>>>>>> f1c07344ca7e846838ad0cb7d04eccc16bcad5a9
