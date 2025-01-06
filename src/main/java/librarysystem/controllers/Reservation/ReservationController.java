package librarysystem.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import librarysystem.models.ReservationReq;
import librarysystem.models.Student;
import librarysystem.models.Book;
import librarysystem.models.services.ReservationReqDAOImp;
import librarysystem.models.services.StudentDAOImp;
import librarysystem.models.services.BookDAOImp;
import librarysystem.models.interfaces.ReservationReqDAO;
import librarysystem.utils.StageUtil;

import java.util.Objects;

public class ReservationController
{
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentNameField;
    private int bookId;
    @FXML
    private Button reserveButton;
    @FXML
    private Button reserveBook_Back;

    private ReservationReqDAO reservationReqDAO;
    private StudentDAOImp studentDAOImp;
    private BookDAOImp bookDAOImp;

    public ReservationController()
    {
        this.reservationReqDAO = new ReservationReqDAOImp();
        this.studentDAOImp = new StudentDAOImp();
        this.bookDAOImp = new BookDAOImp();
    }
    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }
    @FXML
    public void handleReservation()
    {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();

        if (!studentId.isEmpty() && !studentName.isEmpty())
        {
            saveReservation(studentId, studentName);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            System.out.println("All fields are required!");
        }
    }
    private void saveReservation(String studentId, String studentName)
    {
        ReservationReq reservationReq = new ReservationReq();
        Student student = studentDAOImp.getStudentById(Integer.parseInt(studentId));
        if (student != null)
        {
            reservationReq.setStudent(student);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Student not found!");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            clearFieldsUpdateBook();
            return;
        }
        Book book = bookDAOImp.getBookById(bookId);
        if (book != null)
        {
            reservationReq.setBook(book);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book not found!");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            clearFieldsUpdateBook();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Reservation successful!");
        alert.setContentText("Your reservation has been saved successfully.");
        alert.showAndWait();

        reservationReqDAO.saveReservation(reservationReq);
        clearFieldsUpdateBook();
    }
    private void clearFieldsUpdateBook()
    {
        studentIdField.clear();
        studentNameField.clear();
        studentIdField.requestFocus();
    }
    @FXML
    public void switchForm(ActionEvent event)
    {
        if (event.getSource() == reserveBook_Back)
        {
            try
            {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Client/BookDetailsClient.fxml")));
                Stage currentStage = (Stage) reserveBook_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                StageUtil.setAppIcon(currentStage);
                currentStage.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
