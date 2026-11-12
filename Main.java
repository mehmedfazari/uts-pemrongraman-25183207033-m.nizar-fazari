import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();

    public static void main(String[] args) {
        int pilihan;

        do {
            tampilkanMenu();
            pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tambahKendaraan();
                    break;
                case 2:
                    tampilkanKendaraan();
                    break;
                case 3:
                    ubahData();
                    break;
                case 4:
                    hitungBiaya();
                    break;
                case 5:
                    detailKendaraan();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan aplikasi Rental Kendaraan.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
            System.out.println();
        } while (pilihan != 0);
    }

    private static void tampilkanMenu() {
        System.out.println("==================================");
        System.out.println("       RENTAL KENDARAAN");
        System.out.println("==================================");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Kendaraan");
        System.out.println("3. Ubah Data");
        System.out.println("4. Hitung Biaya");
        System.out.println("5. Detail Kendaraan");
        System.out.println("0. Keluar");
        System.out.println("----------------------------------");
    }

    private static void tambahKendaraan() {
        System.out.println("\n--- TAMBAH KENDARAAN ---");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        int jenis = bacaInt("Pilih jenis kendaraan: ");

        if (jenis != 1 && jenis != 2) {
            System.out.println("Jenis kendaraan tidak valid.");
            return;
        }

        String nama = bacaString("Nama/merek kendaraan: ");
        String plat = bacaString("Nomor plat: ");
        double harga = bacaDoublePositif("Harga sewa per hari (Rp): ");
        int lama = bacaIntMinimal("Lama sewa (hari): ", 1);

        try {
            if (jenis == 1) {
                int kursi = bacaIntMinimal("Jumlah kursi: ", 1);
                daftarKendaraan.add(new Mobil(nama, plat, harga, lama, kursi));
            } else {
                String tipe = bacaString("Jenis motor (Matic/Manual/Sport): ");
                daftarKendaraan.add(new Motor(nama, plat, harga, lama, tipe));
            }
            System.out.println("Kendaraan berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambahkan kendaraan: " + e.getMessage());
        }
    }

    private static void tampilkanKendaraan() {
        System.out.println("\n--- DAFTAR KENDARAAN ---");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan yang tersimpan.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-10s %-18s%n",
                "No", "Nama", "Plat", "Jenis", "Harga/Hari");
        System.out.println("---------------------------------------------------------------------");

        for (int i = 0; i < daftarKendaraan.size(); i++) {
            Kendaraan k = daftarKendaraan.get(i);
            String jenis = (k instanceof Mobil) ? "Mobil" : "Motor";
            System.out.printf("%-5d %-20s %-15s %-10s Rp%,.0f%n",
                    i + 1, k.getNamaKendaraan(), k.getNomorPlat(),
                    jenis, k.getHargaSewa());
        }
    }

    private static void ubahData() {
        System.out.println("\n--- UBAH DATA KENDARAAN ---");
        Kendaraan k = pilihKendaraan();
        if (k == null) return;

        System.out.println("Kosongkan input jika data tidak ingin diubah.");
        String nama = bacaStringOpsional("Nama baru [" + k.getNamaKendaraan() + "]: ");
        String plat = bacaStringOpsional("Plat baru [" + k.getNomorPlat() + "]: ");

        try {
            if (!nama.isEmpty()) k.setNamaKendaraan(nama);
            if (!plat.isEmpty()) k.setNomorPlat(plat);

            String harga = bacaStringOpsional(
                    "Harga sewa baru [" + k.getHargaSewa() + "]: ");
            if (!harga.isEmpty()) k.setHargaSewa(Double.parseDouble(harga));

            String lama = bacaStringOpsional(
                    "Lama sewa baru [" + k.getLamaSewa() + "]: ");
            if (!lama.isEmpty()) k.setLamaSewa(Integer.parseInt(lama));

            if (k instanceof Mobil) {
                Mobil m = (Mobil) k;
                String kursi = bacaStringOpsional(
                        "Jumlah kursi baru [" + m.getJumlahKursi() + "]: ");
                if (!kursi.isEmpty()) m.setJumlahKursi(Integer.parseInt(kursi));
            } else if (k instanceof Motor) {
                Motor m = (Motor) k;
                String tipe = bacaStringOpsional(
                        "Jenis motor baru [" + m.getJenisMotor() + "]: ");
                if (!tipe.isEmpty()) m.setJenisMotor(tipe);
            }

            System.out.println("Data kendaraan berhasil diperbarui.");
        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid. Perubahan yang sudah dilakukan mungkin tersimpan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Data tidak valid: " + e.getMessage());
        }
    }

    private static void hitungBiaya() {
        System.out.println("\n--- HITUNG BIAYA SEWA ---");
        Kendaraan k = pilihKendaraan();
        if (k == null) return;

        System.out.println("Nama Kendaraan : " + k.getNamaKendaraan());
        System.out.printf("Harga per hari : Rp%,.0f%n", k.getHargaSewa());
        System.out.println("Lama sewa      : " + k.getLamaSewa() + " hari");
        System.out.printf("Total biaya    : Rp%,.0f%n", k.hitungBiaya());
    }

    private static void detailKendaraan() {
        System.out.println("\n--- DETAIL KENDARAAN ---");
        Kendaraan k = pilihKendaraan();
        if (k != null) {
            System.out.println("----------------------------------");
            k.tampilkanInfo();
            System.out.println("----------------------------------");
        }
    }

    private static Kendaraan pilihKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan. Tambahkan kendaraan terlebih dahulu.");
            return null;
        }

        tampilkanKendaraan();
        int nomor = bacaInt("Pilih nomor kendaraan: ");
        if (nomor < 1 || nomor > daftarKendaraan.size()) {
            System.out.println("Nomor kendaraan tidak ditemukan.");
            return null;
        }
        return daftarKendaraan.get(nomor - 1);
    }

    private static String bacaString(String pesan) {
        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine().trim();
            if (!nilai.isEmpty()) return nilai;
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static String bacaStringOpsional(String pesan) {
        System.out.print(pesan);
        return input.nextLine().trim();
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan bilangan bulat yang benar.");
            }
        }
    }

    private static int bacaIntMinimal(String pesan, int minimal) {
        while (true) {
            int nilai = bacaInt(pesan);
            if (nilai >= minimal) return nilai;
            System.out.println("Nilai minimal adalah " + minimal + ".");
        }
    }

    private static double bacaDoublePositif(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                double nilai = Double.parseDouble(input.nextLine().trim());
                if (nilai > 0) return nilai;
                System.out.println("Harga harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang benar.");
            }
        }
    }
}
