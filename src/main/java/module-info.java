module com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4 to javafx.fxml;
    exports com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4;
    exports com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.GUI;
    exports com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic;
    exports com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Data;
    opens com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic to javafx.fxml;
    opens com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.GUI to javafx.fxml;
}