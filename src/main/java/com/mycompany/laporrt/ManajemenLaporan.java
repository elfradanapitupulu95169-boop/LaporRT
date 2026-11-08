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

public class ManajemenLaporan {
    private ArrayList<Laporan> daftarLaporan = new ArrayList<>();

    public void tambah(Laporan laporan) {
        daftarLaporan.add(laporan);
    }

    public Laporan cariById(int id) {
        for (Laporan l : daftarLaporan) {
            if (l.getIdLaporan() == id) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Laporan> getSemua() {
        return daftarLaporan;
    }

    public ArrayList<Laporan> getByWarga(Warga warga) {
        ArrayList<Laporan> hasil = new ArrayList<>();
        for (Laporan l : daftarLaporan) {
            if (l.getPelapor() == warga) {
                hasil.add(l);
            }
        }
        return hasil;
    }

    public boolean ubahStatus(int id, String statusBaru, String catatan) {
        Laporan l = cariById(id);
        if (l == null) {
            return false;
        }
        l.setStatus(statusBaru);
        l.setCatatan(catatan);
        return true;
    }
}
