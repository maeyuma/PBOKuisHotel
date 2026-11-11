import java.io.Serializable;

// Association: Transaksi menghubungkan Tamu dan Kamar
public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;
    private Tamu tamu;
    private Kamar kamar;
    private int jumlah;
    private double totalBiaya;

    public Transaksi(Tamu tamu, Kamar kamar, int jumlah) {
        this.tamu = tamu;
        this.kamar = kamar;
        this.jumlah = jumlah;
        this.totalBiaya = kamar.hitungBiaya(jumlah);
    }

    public Tamu getTamu() { return tamu; }
    public Kamar getKamar() { return kamar; }
    public int getJumlah() { return jumlah; }
    public double getTotalBiaya() { return totalBiaya; }
}
