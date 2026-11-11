public class KamarSuite extends Kamar {
    private static final long serialVersionUID = 1L;
    public KamarSuite(String kode, String nama, int kuota) { super(kode, nama, kuota); }
    @Override
    public double getTarif() { return 1500000; }
}
