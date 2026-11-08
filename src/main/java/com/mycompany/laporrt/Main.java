/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<User> daftarUser = new ArrayList<>();
    private static final ManajemenLaporan manajemen = new ManajemenLaporan();
    private static int nomorUser = 0;

    public static void main(String[] args) {
        // Instansiasi object awal (data contoh)
        daftarUser.add(new Pengurus(buatIdUser(), "Pak Budi", "pengurus", "rt123", "Ketua RT 05"));
        daftarUser.add(new Warga(buatIdUser(), "Siti Aminah", "siti", "123", "Jl. Melati No. 3"));

        System.out.println("========================================");
        System.out.println("   LaporRT - Smart Governance");
        System.out.println("   Pelaporan Fasilitas Publik & Aspirasi");
        System.out.println("========================================");

        boolean jalan = true;
        while (jalan) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Login");
            System.out.println("2. Registrasi Warga");
            System.out.println("0. Keluar");
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    prosesLogin();
                    break;
                case 2:
                    prosesRegistrasi();
                    break;
                case 0:
                    jalan = false;
                    System.out.println("Terima kasih telah menggunakan LaporRT.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // ===================== AUTENTIKASI =====================

    private static String buatIdUser() {
        nomorUser++;
        return String.format("U%03d", nomorUser);
    }

    private static void prosesRegistrasi() {
        System.out.println("\n--- Registrasi Warga ---");
        try {
            System.out.print("Nama lengkap : ");
            String nama = input.nextLine();
            System.out.print("Username     : ");
            String username = input.nextLine();
            if (cariUser(username) != null) {
                System.out.println("Username sudah dipakai.");
                return;
            }
            System.out.print("Password     : ");
            String password = input.nextLine();
            System.out.print("Alamat       : ");
            String alamat = input.nextLine();

            Warga baru = new Warga(buatIdUser(), nama, username, password, alamat);
            daftarUser.add(baru);
            System.out.println("Registrasi berhasil! Silakan login.");
        } catch (IllegalArgumentException e) {
            nomorUser--; // batalkan nomor karena gagal
            System.out.println("Registrasi gagal: " + e.getMessage());
        }
    }

    private static User cariUser(String username) {
        for (User u : daftarUser) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    private static void prosesLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Username : ");
        String username = input.nextLine();
        System.out.print("Password : ");
        String password = input.nextLine();

        User user = cariUser(username.trim());
        if (user != null && user.login(username.trim(), password)) {
            System.out.println("\nLogin berhasil. Selamat datang, " + user.getNama() + "!");
            if (user instanceof Warga) {
                menuWarga((Warga) user);
            } else if (user instanceof Pengurus) {
                menuPengurus((Pengurus) user);
            }
        } else {
            System.out.println("Username atau password salah.");
        }
    }

    // ===================== MENU WARGA =====================

    private static void menuWarga(Warga warga) {
        boolean aktif = true;
        while (aktif) {
            System.out.println("\n=== DASHBOARD WARGA ===");
            System.out.println("1. Buat Laporan / Aspirasi");
            System.out.println("2. Riwayat Laporan Saya");
            System.out.println("3. Lihat Detail & Status Laporan");
            System.out.println("4. Profil Saya");
            System.out.println("0. Logout");
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    buatLaporan(warga);
                    break;
                case 2:
                    tampilkanRiwayat(warga);
                    break;
                case 3:
                    lihatDetailWarga(warga);
                    break;
                case 4:
                    warga.tampilkanProfil();
                    break;
                case 0:
                    aktif = false;
                    System.out.println("Anda telah logout.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void buatLaporan(Warga warga) {
        System.out.println("\n--- Form Pengajuan ---");
        System.out.println("1. Laporan Kerusakan Fasilitas Publik");
        System.out.println("2. Aspirasi Warga");
        int jenisPilihan = bacaInt("Pilih jenis: ");

        String jenis;
        if (jenisPilihan == 1) {
            jenis = "Laporan";
        } else if (jenisPilihan == 2) {
            jenis = "Aspirasi";
        } else {
            System.out.println("Jenis tidak valid.");
            return;
        }

        System.out.print("Judul       : ");
        String judul = input.nextLine();

        System.out.println("Kategori:");
        System.out.println("  1. Jalan Rusak");
        System.out.println("  2. Lampu Jalan Mati");
        System.out.println("  3. Saluran Air Tersumbat");
        System.out.println("  4. Sampah Menumpuk");
        System.out.println("  5. Lainnya");
        int k = bacaInt("Pilih kategori: ");
        String kategori;
        switch (k) {
            case 1: kategori = "Jalan Rusak"; break;
            case 2: kategori = "Lampu Jalan Mati"; break;
            case 3: kategori = "Saluran Air Tersumbat"; break;
            case 4: kategori = "Sampah Menumpuk"; break;
            case 5: kategori = "Lainnya"; break;
            default:
                System.out.println("Kategori tidak valid.");
                return;
        }

        System.out.print("Deskripsi   : ");
        String deskripsi = input.nextLine();
        System.out.print("Lokasi      : ");
        String lokasi = input.nextLine();

        try {
            Laporan baru = new Laporan(jenis, judul, kategori, deskripsi, lokasi, warga);
            manajemen.tambah(baru);
            System.out.println("\n" + jenis + " berhasil dikirim! ID: " + baru.getIdLaporan());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal mengirim: " + e.getMessage());
        }
    }

    private static void tampilkanRiwayat(Warga warga) {
        ArrayList<Laporan> riwayat = manajemen.getByWarga(warga);
        System.out.println("\n--- Riwayat Laporan & Aspirasi ---");
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada laporan.");
            return;
        }
        for (Laporan l : riwayat) {
            l.tampilkanRingkas();
        }
    }

    private static void lihatDetailWarga(Warga warga) {
        int id = bacaInt("Masukkan ID laporan: ");
        Laporan l = manajemen.cariById(id);
        if (l == null || l.getPelapor() != warga) {
            System.out.println("Laporan tidak ditemukan.");
        } else {
            l.tampilkanDetail();
        }
    }

    // ===================== MENU PENGURUS =====================

    private static void menuPengurus(Pengurus pengurus) {
        boolean aktif = true;
        while (aktif) {
            System.out.println("\n=== DASHBOARD PENGURUS (" + pengurus.getJabatan() + ") ===");
            System.out.println("1. Lihat Semua Laporan & Aspirasi");
            System.out.println("2. Lihat Detail Laporan");
            System.out.println("3. Update Status & Catatan");
            System.out.println("4. Profil Saya");
            System.out.println("0. Logout");
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilkanSemua();
                    break;
                case 2:
                    int id = bacaInt("Masukkan ID laporan: ");
                    Laporan l = manajemen.cariById(id);
                    if (l == null) {
                        System.out.println("Laporan tidak ditemukan.");
                    } else {
                        l.tampilkanDetail();
                    }
                    break;
                case 3:
                    updateStatus();
                    break;
                case 4:
                    pengurus.tampilkanProfil();
                    break;
                case 0:
                    aktif = false;
                    System.out.println("Anda telah logout.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tampilkanSemua() {
        System.out.println("\n--- Semua Laporan & Aspirasi ---");
        if (manajemen.getSemua().isEmpty()) {
            System.out.println("Belum ada laporan masuk.");
            return;
        }
        for (Laporan l : manajemen.getSemua()) {
            l.tampilkanRingkas();
        }
    }

    private static void updateStatus() {
        int id = bacaInt("Masukkan ID laporan: ");
        Laporan l = manajemen.cariById(id);
        if (l == null) {
            System.out.println("Laporan tidak ditemukan.");
            return;
        }
        System.out.println("Status saat ini: " + l.getStatus());
        System.out.println("1. Menunggu");
        System.out.println("2. Diproses");
        System.out.println("3. Selesai");
        int s = bacaInt("Pilih status baru: ");

        String statusBaru;
        switch (s) {
            case 1: statusBaru = Laporan.MENUNGGU; break;
            case 2: statusBaru = Laporan.DIPROSES; break;
            case 3: statusBaru = Laporan.SELESAI; break;
            default:
                System.out.println("Status tidak valid.");
                return;
        }
        System.out.print("Catatan tindak lanjut: ");
        String catatan = input.nextLine();

        manajemen.ubahStatus(id, statusBaru, catatan);
        System.out.println("Status laporan berhasil diperbarui.");
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }
}