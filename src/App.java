import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class App extends Application {
    TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();

    public static void main(String[] args) {
        launch();
    }
   
    @Override
    public void start(Stage primaryStage) {    

        primaryStage.setTitle("OOP GUI");
        
        TableColumn<Mahasiswa, Integer> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Mahasiswa, String> columnNAMA = new TableColumn<>("NAMA");
        columnNAMA.setCellValueFactory(new PropertyValueFactory<>("nama"));
        
        TableColumn<Mahasiswa, String> columnNIM = new TableColumn<>("NIM");
        columnNIM.setCellValueFactory(new PropertyValueFactory<>("nim"));
        
        TableColumn<Mahasiswa, String> columnJURUSAN = new TableColumn<>("JURUSAN");
        columnJURUSAN.setCellValueFactory(new PropertyValueFactory<>("jurusan"));

        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNAMA);
        tableView.getColumns().add(columnNIM);
        tableView.getColumns().add(columnJURUSAN);
       

        // BUTTON
        ToolBar toolBar = new ToolBar();

        Button buttonADD = new Button("Add");
        toolBar.getItems().add(buttonADD);
        buttonADD.setOnAction(e -> add());
        buttonADD.setStyle("-fx-background-color : #f1faee");

        Button buttonEDIT = new Button("Edit");
        toolBar.getItems().add(buttonEDIT);
        buttonEDIT.setOnAction(e -> edit());
        buttonEDIT.setStyle("-fx-background-color : #a8dadc");

        Button buttonDELETE = new Button("Delete");
        toolBar.getItems().add(buttonDELETE);
        buttonDELETE.setOnAction(e -> delete());
        buttonDELETE.setStyle("-fx-background-color : #e63946");

        Button buttonREFRESH = new Button("Refresh");
        toolBar.getItems().add(buttonREFRESH);
        buttonREFRESH.setOnAction(e -> refresh());
        buttonREFRESH.setStyle("-fx-background-color : #2b9348");
    
        load();
        VBox vbox = new VBox(tableView, toolBar);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show(); 

        Statement stmt;

        
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from mahasiswa");
            tableView.getItems().clear();
            
            while (record.next()){
                tableView.getItems()
                .add(new Mahasiswa(record.getInt("id"), record.getString("nama"), record.getString("nim"), record.    getString("jurusan")));
            }

            stmt.close();
            db.conn.close();
        } 
        catch (SQLException e) {
            System.out.println("koneksi gagal");
        }
    }

    public void load(){
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from mahasiswa");
            
            while (record.next()){
                tableView.getItems()
                .add(new Mahasiswa(record.getInt("id"), record.getString("nama"), record.getString("nim"), record.    getString("jurusan")));
            }

            stmt.close();
            db.conn.close();
        } 
        catch (SQLException e) {
            System.out.println("");
        }
    }

    // BAGIAN ADD DATA
    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Add Data");

        TextField namaField = new TextField();
        TextField nimField = new TextField();
        TextField jurusanField = new TextField();
        Label labelnama = new Label("Nama");
        Label labelnim = new Label("Nim");
        Label labeljurusan = new Label("Jurusan");

        VBox hbox1 = new VBox(5, labelnama, namaField);
        VBox hbox2 = new VBox(5, labelnim, nimField);
        VBox hbox3 = new VBox(5, labeljurusan, jurusanField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into mahasiswa SET nama='%s', nim='%s', jurusan='%s'";
                sql = String.format(sql, namaField.getText(), nimField.getText(), jurusanField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                // System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    // BAGIAN EDIT DATA
    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Data");

        TextField idField = new TextField();
        TextField namaField = new TextField();
        TextField nimField = new TextField();
        TextField jurusanField = new TextField();
        Label labelid = new Label("Pilih id");
        Label labelnama = new Label("Nama");
        Label labelnim = new Label("Nim");
        Label labeljurusan = new Label("Jurusan");

        VBox hbox1 = new VBox(5, labelid, idField);
        VBox hbox2 = new VBox(5, labelnama, namaField);
        VBox hbox3 = new VBox(5, labelnim, nimField);
        VBox hbox4 = new VBox(5, labeljurusan, jurusanField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, hbox4, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "update mahasiswa SET nama= '%s', nim='%s', jurusan='%s' WHERE id='%s'";
                sql = String.format(sql, namaField.getText(), nimField.getText(), jurusanField.getText(), 
                    idField.getText());
                state.execute(sql);
                addStage.close();
                load();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    // BAGIAN DELETE DATA
    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("Delete");

        addStage.setTitle("Hapus Data");

        TextField idField = new TextField();
        Label labelid = new Label("Pilih Id");

        
        VBox hbox1 = new VBox(5, labelid, idField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from mahasiswa WHERE id='%s'";
                sql = String.format(sql, idField.getText());
                state.execute(sql);
                addStage.close();
                load();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void refresh() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE mahasiswa DROP id";
            sql = String.format(sql);
            state.execute(sql);
            refresh2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void refresh2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE mahasiswa ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}
