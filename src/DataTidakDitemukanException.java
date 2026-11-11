// Custom Exception: ID tamu atau kode kamar tidak ada
public class DataTidakDitemukanException extends Exception {
    public DataTidakDitemukanException(String pesan) { super(pesan); }
}
