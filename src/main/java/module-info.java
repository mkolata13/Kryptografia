module com.example.algorytm3des {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;


    opens com.example.algorytm3des to javafx.fxml;
    exports com.example.algorytm3des;
}