// Custom Exception: kuota kamar tidak mencukupi
public class KuotaHabisException extends Exception {
    public KuotaHabisException(String pesan) { super(pesan); }
}
