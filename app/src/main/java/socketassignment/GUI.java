package socketassignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
    private SocketClient client;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home Page");
        renderInitForm(primaryStage);
        primaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }

    private void renderInitForm(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label serverPortLabel = new Label("Enter Server Port Number");
        gridPane.add(serverPortLabel, 0, 1);

        TextField serverPortField = new TextField();
        gridPane.add(serverPortField, 0, 2);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.rgb(255, 0, 0));
        gridPane.add(errorLabel, 0, 4);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(connectToServer(primaryStage, serverPortField, errorLabel));
        gridPane.add(submitBtn, 0, 3);

        Scene scene = new Scene(gridPane, 240, 200);
        primaryStage.setScene(scene);

    }

    private EventHandler<ActionEvent> connectToServer(Stage primaryStage, TextField portField, Label errorLabel) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorLabel.setText("");
                try {
                    int portNumber = Integer.parseInt(portField.getText());
                    client = new SocketClient("localhost", portNumber);
                    String status = client.connect();
                    System.out.println(status);
                    if (status == "Connected successfully") {
                        Random random = new Random();
                        buildForm(primaryStage, random.nextInt(100000));
                    } else {
                        errorLabel.setText(status);
                    }
                } catch (NumberFormatException nfe) {
                    errorLabel.setText("Invalid port number");
                } catch (FileNotFoundException e) {
                    System.exit(1);
                }

            }

        };
    }

    private void buildForm(Stage primaryStage, int personalCode) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        File source = new File("./socket.png");
        Image image = new Image(new FileInputStream(source));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        HBox imageContainer = new HBox(10);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.getChildren().add(imageView);
        gridPane.add(imageContainer, 0, 0, 4, 1);

        Label firstNameLabel = new Label("First Name");
        gridPane.add(firstNameLabel, 0, 1);
        Label lastNameLabel = new Label("Last Name");
        gridPane.add(lastNameLabel, 1, 1);

        TextField firstNameField = new TextField();
        gridPane.add(firstNameField, 0, 2);

        TextField lastNameField = new TextField();
        gridPane.add(lastNameField, 1, 2);

        Label admissionNoLabel = new Label("Admission No.");
        gridPane.add(admissionNoLabel, 0, 3);

        TextField admissionNoField = new TextField();
        gridPane.add(admissionNoField, 0, 4);

        Label facultyLabel = new Label("Faculty");
        gridPane.add(facultyLabel, 1, 3);

        ComboBox<String> facultyComboBox = new ComboBox<String>(getFacultiesList());
        facultyComboBox.setPromptText("Select Faculty");
        gridPane.add(facultyComboBox, 1, 4);

        Label degreeLabel = new Label("Degree");
        gridPane.add(degreeLabel, 0, 5);

        ComboBox<String> degreeComboBox = new ComboBox<String>(getDegreesList());
        degreeComboBox.setPromptText("Select Degree");
        gridPane.add(degreeComboBox, 0, 6);

        Label coursesLabel = new Label("Courses");
        gridPane.add(coursesLabel, 1, 5);

        ComboBox<String> courseComboBox = new ComboBox<String>(getCoursesList());
        courseComboBox.setPromptText("Select Course");
        gridPane.add(courseComboBox, 1, 6);

        Label messageLabel = new Label("Personal Code");
        gridPane.add(messageLabel, 0, 7);

        TextField messageField = new TextField();
        gridPane.add(messageField, 0, 8, 2, 1);

        Label personalCodeLabel = new Label("Personal Code is " + personalCode);
        gridPane.add(personalCodeLabel, 0, 9);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.rgb(255, 0, 0));
        gridPane.add(errorLabel, 0, 10);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(submitHandler(firstNameField, lastNameField, admissionNoField, facultyComboBox,
                degreeComboBox, courseComboBox, messageField, errorLabel));
        HBox submitBtnBox = new HBox(10);
        submitBtnBox.setAlignment(Pos.CENTER);
        submitBtnBox.getChildren().add(submitBtn);
        gridPane.add(submitBtnBox, 0, 11, 2, 1);

        Scene scene = new Scene(gridPane, 640, 540);
        primaryStage.setScene(scene);
    }

    private ObservableList<String> getFacultiesList() {
        return FXCollections.observableArrayList("FIT", "SCES", "LLB", "SHM", "CIPIT");
    }

    private ObservableList<String> getDegreesList() {
        return FXCollections.observableArrayList("Diploma", "Bachelor's", "Master's", "PhD");
    }

    private ObservableList<String> getCoursesList() {
        return FXCollections.observableArrayList("Computer Science", "Business IT", "BCOM", "Law", "Hospitality");
    }

    private EventHandler<ActionEvent> submitHandler(TextField firstNameField, TextField lastNameField,
            TextField admissionNoField, ComboBox<String> facultyComboBox, ComboBox<String> degreeComboBox,
            ComboBox<String> courseComboBox, TextField messageField, Label errorLabel) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorLabel.setText("");
                if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
                    errorLabel.setText("First name is required");
                    return;
                } else if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
                    errorLabel.setText("Last name is required");
                    return;
                } else if (admissionNoField.getText() == null || admissionNoField.getText().isEmpty()) {
                    errorLabel.setText("Admission number is required");
                    return;
                } else if (facultyComboBox.getSelectionModel().getSelectedItem() == null) {
                    errorLabel.setText("Faculty is required");
                    return;
                } else if (degreeComboBox.getSelectionModel().getSelectedItem() == null) {
                    errorLabel.setText("Degree is required");
                    return;
                } else if (courseComboBox.getSelectionModel().getSelectedItem() == null) {
                    errorLabel.setText("Course is required");
                    return;
                } else if (messageField.getText() == null || messageField.getText().isEmpty()) {
                    errorLabel.setText("Personal code is required");
                    return;
                }

                Data data = new Data();
                data.name = firstNameField.getText() + " " + lastNameField.getText();
                data.number = Integer.parseInt(admissionNoField.getText());
                data.faculty = facultyComboBox.getSelectionModel().getSelectedItem();
                data.degree = degreeComboBox.getSelectionModel().getSelectedItem();
                data.course = courseComboBox.getSelectionModel().getSelectedItem();
                data.code = Integer.parseInt(messageField.getText());

                int code = client.send(data, errorLabel);
                if (code == 0) {
                    showSuccessDialog();
                } else {
                    showFailureDialog();
                }

            }
        };
    }

    private void showSuccessDialog() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sent successfully");
        alert.setHeaderText(null);
        alert.setContentText("Content was sent successfully");
        alert.showAndWait();
    }

    private void showFailureDialog() {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Unable to send");
        alert.setHeaderText(null);
        alert.setContentText("Content failed to send");

        ButtonType retryButtonType = new ButtonType("Retry");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(retryButtonType, cancelButtonType);
        alert.showAndWait();
    }

}
