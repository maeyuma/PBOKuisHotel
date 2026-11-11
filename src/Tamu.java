// Inheritance: Tamu mewarisi Aktor
public class Tamu extends Aktor {
    private static final long serialVersionUID = 1L;

    public Tamu(String id, String nama) {
        super(id, nama);
    }

    @Override
    public String getPeran() {
        return "Tamu Hotel";
    }
}
