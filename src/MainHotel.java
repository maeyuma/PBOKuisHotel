public class MainHotel {
    public static void main(String[] args) {
        SistemHotel hotel = new SistemHotel("Grand Aston");
        
        hotel.tambahTamu("ACT-01", "Gilang");
        hotel.tambahTamu("ACT-02", "Hani");
        
        hotel.tambahKamar("OBJ-A", "Suite Room", 10);
        hotel.tambahKamar("OBJ-B", "Reguler Room", 2);
        hotel.tambahKamar("OBJ-C", "Standard Room", 50);
        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");
        
        try {
            hotel.jalankanTransaksiDariFile("input_transaksi.txt");
        } catch (Exception e) {
            System.out.println("[SISTEM FATAL ERROR] File gagal dibaca/diproses: " + e.getMessage());
        }
        hotel.simpanLaporanAkhir();
    }
}
