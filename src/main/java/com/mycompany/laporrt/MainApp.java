/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage stage;
    private final ManajemenUser manajemenUser = new ManajemenUser();
    private final ManajemenLaporan manajemenLaporan = new ManajemenLaporan();

    private static final String[] KATEGORI = {
        "Jalan Rusak", "Lampu Jalan Mati", "Saluran Air Tersumbat", "Sampah Menumpuk", "Lainnya"
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        // Instansiasi object awal (akun contoh)
        manajemenUser.tambah(new Pengurus(manajemenUser.buatIdBaru(), "Pak Budi", "pengurus", "rt123", "Ketua RT 05"));
        manajemenUser.tambah(new Warga(manajemenUser.buatIdBaru(), "Siti Aminah", "siti", "123", "Jl. Melati No. 3"));

        stage.setTitle("LaporRT - Smart Governance");
        tampilkanLogin();
        stage.show();
    }

    // ===================== UTILITAS =====================

    private void tampilAlert(Alert.AlertType tipe, String judul, String pesan) {
        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    private void gantiScene(Scene scene) {
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private Label buatJudul(String teks) {
        Label label = new Label(teks);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return label;
    }

    private boolean kosong(String teks) {
        return teks == null || teks.trim().isEmpty();
    }

    // ===================== HALAMAN LOGIN =====================

    private void tampilkanLogin() {
        Label lblJudul = buatJudul("LaporRT - Smart Governance");
        Label lblSub = new Label("Pelaporan Fasilitas Publik & Aspirasi Warga");

        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.add(new Label("Username"), 0, 0);
        form.add(tfUsername, 1, 0);
        form.add(new Label("Password"), 0, 1);
        form.add(pfPassword, 1, 1);

        Button btnLogin = new Button("Login");
        Button btnRegistrasi = new Button("Registrasi Warga");
        HBox barisTombol = new HBox(10, btnLogin, btnRegistrasi);
        barisTombol.setAlignment(Pos.CENTER);

        // Event handling: tombol Login
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText().trim();
            String password = pfPassword.getText();

            if (kosong(username) || kosong(password)) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan password wajib diisi.");
                return;
            }

            User user = manajemenUser.login(username, password);
            if (user == null) {
                tampilAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau password salah.");
            } else if (user instanceof Warga) {
                tampilkanDashboardWarga((Warga) user);
            } else if (user instanceof Pengurus) {
                tampilkanDashboardPengurus((Pengurus) user);
            }
        });

        // Event handling: tombol Registrasi
        btnRegistrasi.setOnAction(e -> tampilkanRegistrasi());

        VBox root = new VBox(15, lblJudul, lblSub, form, barisTombol);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        gantiScene(new Scene(root, 440, 330));
    }

    // ===================== HALAMAN REGISTRASI =====================

    private void tampilkanRegistrasi() {
        Label lblJudul = buatJudul("Registrasi Warga");

        TextField tfNama = new TextField();
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        TextField tfAlamat = new TextField();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.add(new Label("Nama lengkap"), 0, 0);
        form.add(tfNama, 1, 0);
        form.add(new Label("Username"), 0, 1);
        form.add(tfUsername, 1, 1);
        form.add(new Label("Password"), 0, 2);
        form.add(pfPassword, 1, 2);
        form.add(new Label("Alamat"), 0, 3);
        form.add(tfAlamat, 1, 3);

        Button btnDaftar = new Button("Daftar");
        Button btnKembali = new Button("Kembali");
        HBox barisTombol = new HBox(10, btnDaftar, btnKembali);
        barisTombol.setAlignment(Pos.CENTER);

        btnDaftar.setOnAction(e -> {
            if (kosong(tfNama.getText()) || kosong(tfUsername.getText())
                    || kosong(pfPassword.getText()) || kosong(tfAlamat.getText())) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Semua kolom wajib diisi.");
                return;
            }
            if (manajemenUser.cari(tfUsername.getText().trim()) != null) {
                tampilAlert(Alert.AlertType.ERROR, "Registrasi Gagal", "Username sudah dipakai.");
                return;
            }
            try {
                // Validasi tambahan (misalnya panjang password) dilakukan oleh setter di class User
                Warga baru = new Warga(manajemenUser.buatIdBaru(), tfNama.getText(),
                        tfUsername.getText(), pfPassword.getText(), tfAlamat.getText());
                manajemenUser.tambah(baru);
                tampilAlert(Alert.AlertType.INFORMATION, "Berhasil", "Registrasi berhasil! Silakan login.");
                tampilkanLogin();
            } catch (IllegalArgumentException ex) {
                tampilAlert(Alert.AlertType.ERROR, "Registrasi Gagal", ex.getMessage());
            }
        });

        btnKembali.setOnAction(e -> tampilkanLogin());

        VBox root = new VBox(15, lblJudul, form, barisTombol);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        gantiScene(new Scene(root, 440, 380));
    }

    // ===================== TABEL LAPORAN (dipakai warga & pengurus) =====================

    private TableView<Laporan> buatTabel(ObservableList<Laporan> data) {
        TableView<Laporan> tabel = new TableView<>(data);

        TableColumn<Laporan, String> kolId = new TableColumn<>("ID");
        kolId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdLaporan())));
        kolId.setPrefWidth(45);

        TableColumn<Laporan, String> kolJenis = new TableColumn<>("Jenis");
        kolJenis.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getJenis()));
        kolJenis.setPrefWidth(75);

        TableColumn<Laporan, String> kolJudul = new TableColumn<>("Judul");
        kolJudul.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getJudul()));
        kolJudul.setPrefWidth(190);

        TableColumn<Laporan, String> kolKategori = new TableColumn<>("Kategori");
        kolKategori.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getKategori()));
        kolKategori.setPrefWidth(140);

        TableColumn<Laporan, String> kolStatus = new TableColumn<>("Status");
        kolStatus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus()));
        kolStatus.setPrefWidth(80);

        tabel.getColumns().add(kolId);
        tabel.getColumns().add(kolJenis);
        tabel.getColumns().add(kolJudul);
        tabel.getColumns().add(kolKategori);
        tabel.getColumns().add(kolStatus);
        tabel.setPlaceholder(new Label("Belum ada laporan."));
        VBox.setVgrow(tabel, Priority.ALWAYS);
        return tabel;
    }

    /** Mencari laporan berdasarkan ID; try-catch menangani input yang bukan angka. */
    private void cariLaporan(TableView<Laporan> tabel, String teksId) {
        if (kosong(teksId)) {
            tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Masukkan ID laporan yang ingin dicari.");
            return;
        }
        try {
            int id = Integer.parseInt(teksId.trim());
            for (Laporan l : tabel.getItems()) {
                if (l.getIdLaporan() == id) {
                    tabel.getSelectionModel().select(l);
                    tabel.scrollTo(l);
                    return;
                }
            }
            tampilAlert(Alert.AlertType.INFORMATION, "Tidak Ditemukan", "Laporan dengan ID " + id + " tidak ditemukan.");
        } catch (NumberFormatException ex) {
            tampilAlert(Alert.AlertType.ERROR, "Format Salah", "ID laporan harus berupa angka.");
        }
    }

    private TextArea buatAreaDetail() {
        TextArea area = new TextArea("Pilih laporan pada tabel untuk melihat detail.");
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(10);
        return area;
    }

    private HBox buatBarisCari(TableView<Laporan> tabel) {
        TextField tfCari = new TextField();
        tfCari.setPromptText("Cari ID laporan");
        tfCari.setPrefWidth(140);
        Button btnCari = new Button("Cari");
        btnCari.setOnAction(e -> cariLaporan(tabel, tfCari.getText()));
        HBox baris = new HBox(8, new Label("ID:"), tfCari, btnCari);
        baris.setAlignment(Pos.CENTER_LEFT);
        return baris;
    }

    private HBox buatHeader(String teks, Runnable aksiLogout) {
        Label lbl = buatJudul(teks);
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> aksiLogout.run());
        HBox.setHgrow(lbl, Priority.ALWAYS);
        lbl.setMaxWidth(Double.MAX_VALUE);
        HBox header = new HBox(10, lbl, btnLogout);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    // ===================== DASHBOARD WARGA =====================

    private void tampilkanDashboardWarga(Warga warga) {
        HBox header = buatHeader("Dashboard Warga - " + warga.getNama(), this::tampilkanLogin);

        // --- Form pengajuan (kiri) ---
        ComboBox<String> cbJenis = new ComboBox<>(FXCollections.observableArrayList("Laporan", "Aspirasi"));
        cbJenis.setPromptText("Pilih jenis");
        TextField tfJudul = new TextField();
        tfJudul.setPromptText("Judul singkat");
        ComboBox<String> cbKategori = new ComboBox<>(FXCollections.observableArrayList(KATEGORI));
        cbKategori.setPromptText("Pilih kategori");
        TextArea taDeskripsi = new TextArea();
        taDeskripsi.setPromptText("Jelaskan masalah / aspirasi Anda");
        taDeskripsi.setWrapText(true);
        taDeskripsi.setPrefRowCount(4);
        TextField tfLokasi = new TextField();
        tfLokasi.setPromptText("Contoh: Jl. Melati No. 3");
        Button btnKirim = new Button("Kirim");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Jenis"), 0, 0);
        form.add(cbJenis, 1, 0);
        form.add(new Label("Judul"), 0, 1);
        form.add(tfJudul, 1, 1);
        form.add(new Label("Kategori"), 0, 2);
        form.add(cbKategori, 1, 2);
        form.add(new Label("Deskripsi"), 0, 3);
        form.add(taDeskripsi, 1, 3);
        form.add(new Label("Lokasi"), 0, 4);
        form.add(tfLokasi, 1, 4);
        form.add(btnKirim, 1, 5);

        VBox kiri = new VBox(10, buatJudul("Buat Laporan / Aspirasi"), form);
        kiri.setPrefWidth(380);

        // --- Riwayat & detail (kanan) ---
        ObservableList<Laporan> data = FXCollections.observableArrayList(manajemenLaporan.getByWarga(warga));
        TableView<Laporan> tabel = buatTabel(data);
        TextArea areaDetail = buatAreaDetail();

        tabel.getSelectionModel().selectedItemProperty().addListener((obs, lama, baru) -> {
            if (baru != null) {
                areaDetail.setText(baru.getDetailText());
            }
        });

        VBox kanan = new VBox(10, buatJudul("Riwayat Laporan Saya"), buatBarisCari(tabel), tabel, areaDetail);
        HBox.setHgrow(kanan, Priority.ALWAYS);

        // Event handling: tombol Kirim
        btnKirim.setOnAction(e -> {
            String jenis = cbJenis.getValue();
            String kategori = cbKategori.getValue();

            if (jenis == null || kategori == null || kosong(tfJudul.getText())
                    || kosong(taDeskripsi.getText()) || kosong(tfLokasi.getText())) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Semua kolom wajib diisi sebelum mengirim.");
                return;
            }

            try {
                Laporan baru = new Laporan(jenis, tfJudul.getText(), kategori,
                        taDeskripsi.getText(), tfLokasi.getText(), warga);
                manajemenLaporan.tambah(baru);
                data.setAll(manajemenLaporan.getByWarga(warga));
                tabel.getSelectionModel().select(baru);

                cbJenis.setValue(null);
                cbKategori.setValue(null);
                tfJudul.clear();
                taDeskripsi.clear();
                tfLokasi.clear();

                tampilAlert(Alert.AlertType.INFORMATION, "Berhasil",
                        jenis + " berhasil dikirim dengan ID " + baru.getIdLaporan() + ".");
            } catch (IllegalArgumentException ex) {
                tampilAlert(Alert.AlertType.ERROR, "Gagal Mengirim", ex.getMessage());
            }
        });

        HBox isi = new HBox(25, kiri, kanan);
        VBox root = new VBox(15, header, isi);
        root.setPadding(new Insets(20));

        gantiScene(new Scene(root, 1050, 600));
    }

    // ===================== DASHBOARD PENGURUS =====================

    private void tampilkanDashboardPengurus(Pengurus pengurus) {
        HBox header = buatHeader("Dashboard Pengurus - " + pengurus.getNama()
                + " (" + pengurus.getJabatan() + ")", this::tampilkanLogin);

        ObservableList<Laporan> data = FXCollections.observableArrayList(manajemenLaporan.getSemua());
        TableView<Laporan> tabel = buatTabel(data);
        TextArea areaDetail = buatAreaDetail();

        VBox kiri = new VBox(10, buatJudul("Semua Laporan & Aspirasi"), buatBarisCari(tabel), tabel);
        kiri.setPrefWidth(560);

        // --- Panel update status (kanan) ---
        ComboBox<String> cbStatus = new ComboBox<>(FXCollections.observableArrayList(
                Laporan.MENUNGGU, Laporan.DIPROSES, Laporan.SELESAI));
        cbStatus.setPromptText("Pilih status baru");
        TextField tfCatatan = new TextField();
        tfCatatan.setPromptText("Catatan tindak lanjut");
        Button btnSimpan = new Button("Simpan Perubahan");
        Button btnMuat = new Button("Muat Ulang");

        GridPane formStatus = new GridPane();
        formStatus.setHgap(10);
        formStatus.setVgap(10);
        formStatus.add(new Label("Status"), 0, 0);
        formStatus.add(cbStatus, 1, 0);
        formStatus.add(new Label("Catatan"), 0, 1);
        formStatus.add(tfCatatan, 1, 1);
        formStatus.add(new HBox(10, btnSimpan, btnMuat), 1, 2);

        tabel.getSelectionModel().selectedItemProperty().addListener((obs, lama, baru) -> {
            if (baru != null) {
                areaDetail.setText(baru.getDetailText());
                cbStatus.setValue(baru.getStatus());
                tfCatatan.setText("-".equals(baru.getCatatan()) ? "" : baru.getCatatan());
            }
        });

        // Event handling: tombol Simpan Perubahan
        btnSimpan.setOnAction(e -> {
            Laporan terpilih = tabel.getSelectionModel().getSelectedItem();
            if (terpilih == null) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih laporan pada tabel terlebih dahulu.");
                return;
            }
            if (cbStatus.getValue() == null) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih status baru.");
                return;
            }
            try {
                manajemenLaporan.ubahStatus(terpilih.getIdLaporan(), cbStatus.getValue(), tfCatatan.getText());
                tabel.refresh();
                areaDetail.setText(terpilih.getDetailText());
                tampilAlert(Alert.AlertType.INFORMATION, "Berhasil", "Status laporan berhasil diperbarui.");
            } catch (IllegalArgumentException ex) {
                tampilAlert(Alert.AlertType.ERROR, "Gagal", ex.getMessage());
            }
        });

        // Event handling: tombol Muat Ulang (laporan baru dari warga)
        btnMuat.setOnAction(e -> {
            data.setAll(manajemenLaporan.getSemua());
            tabel.refresh();
        });

        VBox kanan = new VBox(10, buatJudul("Detail & Tindak Lanjut"), areaDetail, formStatus);
        HBox.setHgrow(kanan, Priority.ALWAYS);

        HBox isi = new HBox(25, kiri, kanan);
        VBox root = new VBox(15, header, isi);
        root.setPadding(new Insets(20));

        gantiScene(new Scene(root, 1050, 600));
    }
}
