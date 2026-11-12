public class Mobil extends Kendaraan {
    private int jumlahKursi;

    public Mobil(String namaKendaraan, String nomorPlat,
                 double hargaSewa, int lamaSewa, int jumlahKursi) {
        super(namaKendaraan, nomorPlat, hargaSewa, lamaSewa);
        setJumlahKursi(jumlahKursi);
    }

    public int getJumlahKursi() {
        return jumlahKursi;
    }

    public void setJumlahKursi(int jumlahKursi) {
        if (jumlahKursi <= 0) {
            throw new IllegalArgumentException("Jumlah kursi harus lebih dari 0.");
        }
        this.jumlahKursi = jumlahKursi;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis Kendaraan  : Mobil");
        super.tampilkanInfo();
        System.out.println("Jumlah Kursi     : " + jumlahKursi);
        System.out.printf("Total Biaya      : Rp%,.0f%n", hitungBiaya());
    }
}
