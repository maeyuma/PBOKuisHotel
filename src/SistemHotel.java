import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SistemHotel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String namaHotel;
    private HashMap<String, Tamu> daftarTamu = new HashMap<>();
    private HashMap<String, Kamar> daftarKamar = new HashMap<>();
    private ArrayList<Transaksi> riwayat = new ArrayList<>();

    public SistemHotel(String namaHotel) {
        this.namaHotel = namaHotel;
    }

    public void tambahTamu(String id, String nama) {
        daftarTamu.put(id, new Tamu(id, nama));
    }

    public void tambahKamar(String kode, String nama, int kuota) {
        Kamar k;
        if (nama.contains("Suite")) {
            k = new KamarSuite(kode, nama, kuota);
        } else if (nama.contains("Reguler")) {
            k = new KamarReguler(kode, nama, kuota);
        } else {
            k = new KamarStandard(kode, nama, kuota);
        }
        daftarKamar.put(kode, k);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws IOException {
        StringBuilder isi = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(namaFile))) {
            String baris;
            while ((baris = br.readLine()) != null) {
                isi.append(baris.trim());
            }
        }

        String[] daftarRekord = isi.toString().split("\\|");
        int nomor = 0;
        for (String rekord : daftarRekord) {
            rekord = rekord.trim();
            if (rekord.isEmpty()) continue;
            nomor++;
            try {
                String idTamu = null, kodeKamar = null;
                int jumlah = 0;
                for (String bagian : rekord.split(",")) {
                    if (bagian.trim().isEmpty()) continue;
                    String[] kv = bagian.split("=", 2);
                    String key = kv[0].trim();
                    String value = kv[1].trim();
                    if (key.equals("ID")) idTamu = value;
                    else if (key.equals("KD")) kodeKamar = value;
                    else if (key.equals("JML")) jumlah = Integer.parseInt(value);
                }
                prosesTransaksi(idTamu, kodeKamar, jumlah);
                System.out.println("[OK] Transaksi " + nomor + ": " + idTamu + " -> " + kodeKamar + " x" + jumlah);
            } catch (DataTidakDitemukanException | KuotaHabisException e) {
                System.out.println("[GAGAL] Transaksi " + nomor + ": " + e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("[FORMAT SALAH] Transaksi " + nomor + ": " + rekord);
            }
        }
    }

    private void prosesTransaksi(String idTamu, String kodeKamar, int jumlah)
            throws DataTidakDitemukanException, KuotaHabisException {
        Tamu tamu = daftarTamu.get(idTamu);
        if (tamu == null) {
            throw new DataTidakDitemukanException("Tamu dengan ID " + idTamu + " tidak ditemukan");
        }
        Kamar kamar = daftarKamar.get(kodeKamar);
        if (kamar == null) {
            throw new DataTidakDitemukanException("Kamar dengan kode " + kodeKamar + " tidak ditemukan");
        }
        kamar.kurangiKuota(jumlah);
        riwayat.add(new Transaksi(tamu, kamar, jumlah));
    }

    public void simpanLaporanAkhir() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("backup_data.dat"))) {
            oos.writeObject(this);
            System.out.println("[INFO] Serialisasi berhasil: backup_data.dat");
        } catch (IOException e) {
            System.out.println("[ERROR] Serialisasi gagal: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter("laporan.txt")) {
            fw.write("=== STRUK RIWAYAT TRANSAKSI - " + namaHotel + " ===\n");
            for (int i = 0; i < riwayat.size(); i++) {
                Transaksi t = riwayat.get(i);
                fw.write((i + 1) + ". " + t.getTamu().getId() + " (" + t.getTamu().getNama() + ")"
                        + " | " + t.getKamar().getKode() + " (" + t.getKamar().getNama() + ")"
                        + " | Jumlah: " + t.getJumlah()
                        + " | Total: Rp" + String.format("%,.0f", t.getTotalBiaya()) + "\n");
            }
            System.out.println("[INFO] Laporan berhasil ditulis: laporan.txt");
        } catch (IOException e) {
            System.out.println("[ERROR] Laporan gagal ditulis: " + e.getMessage());
        }
    }
}
