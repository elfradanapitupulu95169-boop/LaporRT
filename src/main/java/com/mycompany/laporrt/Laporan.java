/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

import java.time.LocalDate;


public class Laporan {
    public static final String MENUNGGU = "Menunggu";
    public static final String DIPROSES = "Diproses";
    public static final String SELESAI = "Selesai";

    private static int counter = 0; // untuk membuat ID otomatis

    private int idLaporan;
    private String jenis;       // "Laporan" atau "Aspirasi"
    private String judul;
    private String kategori;
    private String deskripsi;
    private String lokasi;
    private LocalDate tanggal;
    private String status;
    private String catatan;
    private Warga pelapor;

    public Laporan(String jenis, String judul, String kategori, String deskripsi,
                   String lokasi, Warga pelapor) {
        this.jenis = jenis;
        setJudul(judul);
        setKategori(kategori);
        setDeskripsi(deskripsi);
        setLokasi(lokasi);
        this.pelapor = pelapor;
        this.tanggal = LocalDate.now();
        this.status = MENUNGGU;
        this.catatan = "-";
        // ID dibuat paling akhir supaya nomor tidak terpakai jika validasi gagal
        counter++;
        this.idLaporan = counter;
    }

    // ===== Getter =====
    public int getIdLaporan() { return idLaporan; }
    public String getJenis() { return jenis; }
    public String getJudul() { return judul; }
    public String getKategori() { return kategori; }
    public String getDeskripsi() { return deskripsi; }
    public String getLokasi() { return lokasi; }
    public LocalDate getTanggal() { return tanggal; }
    public String getStatus() { return status; }
    public String getCatatan() { return catatan; }
    public Warga getPelapor() { return pelapor; }

    // ===== Setter dengan validasi input =====
    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public void setKategori(String kategori) {
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategori tidak boleh kosong.");
        }
        this.kategori = kategori.trim();
    }

    public void setDeskripsi(String deskripsi) {
        if (deskripsi == null || deskripsi.trim().isEmpty()) {
            throw new IllegalArgumentException("Deskripsi tidak boleh kosong.");
        }
        this.deskripsi = deskripsi.trim();
    }

    public void setLokasi(String lokasi) {
        if (lokasi == null || lokasi.trim().isEmpty()) {
            throw new IllegalArgumentException("Lokasi tidak boleh kosong.");
        }
        this.lokasi = lokasi.trim();
    }

    public void setStatus(String status) {
        if (!MENUNGGU.equals(status) && !DIPROSES.equals(status) && !SELESAI.equals(status)) {
            throw new IllegalArgumentException("Status tidak valid.");
        }
        this.status = status;
    }

    public void setCatatan(String catatan) {
        if (catatan == null || catatan.trim().isEmpty()) {
            this.catatan = "-";
        } else {
            this.catatan = catatan.trim();
        }
    }

    /** Teks detail laporan, dipakai oleh GUI (TextArea). */
    public String getDetailText() {
        return "ID Laporan   : " + idLaporan + "\n"
             + "Jenis        : " + jenis + "\n"
             + "Pelapor      : " + pelapor.getNama() + "\n"
             + "Judul        : " + judul + "\n"
             + "Kategori     : " + kategori + "\n"
             + "Deskripsi    : " + deskripsi + "\n"
             + "Lokasi       : " + lokasi + "\n"
             + "Tanggal      : " + tanggal + "\n"
             + "Status       : " + status + "\n"
             + "Tindak lanjut: " + catatan;
    }

    public void tampilkanRingkas() {
        System.out.printf("[ID %d] %-8s | %-25s | %-18s | %s%n",
                idLaporan, jenis, judul, kategori, status);
    }

    public void tampilkanDetail() {
        System.out.println("----------------------------------------");
        System.out.println(getDetailText());
        System.out.println("----------------------------------------");
    }
}