module com.listacompra.interfaces.listacompra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;

    opens com.listacompra.interfaces.listacompra to javafx.fxml;
    opens com.listacompra.interfaces.listacompra.controller to javafx.fxml;
    opens com.listacompra.interfaces.listacompra.model to javafx.base;

    exports com.listacompra.interfaces.listacompra;
    exports com.listacompra.interfaces.listacompra.view;
    opens com.listacompra.interfaces.listacompra.view to javafx.fxml;
}