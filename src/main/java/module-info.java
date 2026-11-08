module com.mycompany.laporrt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.laporrt to javafx.fxml;
    exports com.mycompany.laporrt;
}
