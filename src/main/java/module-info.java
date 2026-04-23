module com.listacompra.interfaces.listacompra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;

    opens com.listacompra.interfaces.listacompra to javafx.fxml;
    opens com.listacompra.interfaces.listacompra.controller to javafx.fxml;

    exports com.listacompra.interfaces.listacompra;
}