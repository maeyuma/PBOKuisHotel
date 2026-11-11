import java.io.Serializable;

// Superclass abstrak untuk semua tipe kamar
public abstract class Kamar implements Serializable {
    private static final long serialVersionUID = 1L;
    private String kode;
    private String nama;
    private int kuota;

    public Kamar(String kode, String nama, int kuota) {
        this.kode = kode;
        this.nama = nama;
        this.kuota = kuota;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public int getKuota() { return kuota; }

    public void kurangiKuota(int jumlah) throws KuotaHabisException {
        if (jumlah > kuota) {
            throw new KuotaHabisException("Kuota " + nama + " (" + kode + ") tidak cukup. Sisa: "
                    + kuota + ", diminta: " + jumlah);
        }
        kuota -= jumlah;
    }

    // Polymorphism: tiap tipe kamar punya tarif berbeda
    public abstract double getTarif();

    public double hitungBiaya(int jumlah) {
        return getTarif() * jumlah;
    }
}
