package librarysystem.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import librarysystem.models.Student;
import librarysystem.models.services.StudentDAOImp;

import java.io.IOException;
import java.util.List;

public class SubmitReservationController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> studentListView;

    private final StudentDAOImp studentDAO = new StudentDAOImp();

    // Method to handle switching forms
    public void switchForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to search for students by name
    public void searchStudents(ActionEvent actionEvent) {
        String keyword = searchField.getText().trim();

        // Validate input
        if (keyword.isEmpty()) {
            studentListView.setItems(FXCollections.observableArrayList("Please enter a name to search."));
            return;
        }

        // Search for students
        List<Student> students = studentDAO.searchStudentsByName(keyword);

        // Check if students are found
        if (students.isEmpty()) {
            studentListView.setItems(FXCollections.observableArrayList("No students found."));
        } else {
            // Populate the ListView with student names
            ObservableList<String> studentNames = FXCollections.observableArrayList();
            for (Student student : students) {
                studentNames.add(student.getStudentName());
            }
            studentListView.setItems(studentNames);
        }
    }
}
