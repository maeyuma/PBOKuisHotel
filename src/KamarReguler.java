public class KamarReguler extends Kamar {
    private static final long serialVersionUID = 1L;
    public KamarReguler(String kode, String nama, int kuota) { super(kode, nama, kuota); }
    @Override
    public double getTarif() { return 500000; }
}
