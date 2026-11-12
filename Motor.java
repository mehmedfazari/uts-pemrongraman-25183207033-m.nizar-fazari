public class Motor extends Kendaraan {
    private String jenisMotor;

    public Motor(String namaKendaraan, String nomorPlat,
                 double hargaSewa, int lamaSewa, String jenisMotor) {
        super(namaKendaraan, nomorPlat, hargaSewa, lamaSewa);
        setJenisMotor(jenisMotor);
    }

    public String getJenisMotor() {
        return jenisMotor;
    }

    public void setJenisMotor(String jenisMotor) {
        if (jenisMotor == null ||
            !(jenisMotor.equalsIgnoreCase("Matic")
              || jenisMotor.equalsIgnoreCase("Manual")
              || jenisMotor.equalsIgnoreCase("Sport"))) {
            throw new IllegalArgumentException(
                "Jenis motor harus Matic, Manual, atau Sport."
            );
        }

        if (jenisMotor.equalsIgnoreCase("Matic")) {
            this.jenisMotor = "Matic";
        } else if (jenisMotor.equalsIgnoreCase("Manual")) {
            this.jenisMotor = "Manual";
        } else {
            this.jenisMotor = "Sport";
        }
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis Kendaraan  : Motor");
        super.tampilkanInfo();
        System.out.println("Jenis Motor      : " + jenisMotor);
        System.out.printf("Total Biaya      : Rp%,.0f%n", hitungBiaya());
    }
}
