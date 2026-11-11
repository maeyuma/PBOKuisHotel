public class KamarStandard extends Kamar {
    private static final long serialVersionUID = 1L;
    public KamarStandard(String kode, String nama, int kuota) { super(kode, nama, kuota); }
    @Override
    public double getTarif() { return 250000; }
}
