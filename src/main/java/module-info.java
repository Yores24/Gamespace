module com.home.gamespace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.home.gamespace to javafx.fxml;
    exports com.home.gamespace;
    exports com.home.gamespace.Admin;
    opens com.home.gamespace.Admin to javafx.fxml;
    exports com.home.gamespace.User;
    opens com.home.gamespace.User to javafx.fxml;
    exports com.home.gamespace.Game;
    opens com.home.gamespace.Game to javafx.fxml;
}