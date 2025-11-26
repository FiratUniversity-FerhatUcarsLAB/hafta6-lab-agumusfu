/**
 * Ad Soyad: Abdullah Gümüş
 * Öğrenci No: 250542016
 * Proje: Sinema Bileti
 * Tarih: 26.11.2025
 */
import java.util.Scanner;

public class Proje2_SinemaBileti {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- SİNEMA BİLETİ HESAPLAMA ---");

        // Girdiler
        System.out.print("Günü seçiniz (1=Pzt, 2=Sal, 3=Çar, 4=Per, 5=Cum, 6=Cmt, 7=Paz): ");
        int gun = scan.nextInt();

        System.out.print("Saati giriniz (0-23): ");
        int saat = scan.nextInt();

        System.out.print("Yaşınızı giriniz: ");
        int yas = scan.nextInt();

        System.out.println("Meslek Seçiniz (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
        int meslek = scan.nextInt();

        System.out.println("Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
        int filmTuru = scan.nextInt();

        // 1. Temel Fiyatı Bul
        double temelFiyat = calculateBasePrice(gun, saat);

        // 2. İndirim Oranını Bul
        double indirimOrani = calculateDiscount(yas, meslek, gun);

        // 3. İndirimli Ara Tutarı Hesapla (Temel - İndirim)
        double indirimliTutar = temelFiyat - (temelFiyat * indirimOrani);

        // 4. Format Ücretini Bul
        double formatEkstra = getFormatExtra(filmTuru);

        // 5. Son Fiyat (İndirimli Tutar + Format Farkı)
        double sonFiyat = calculateFinalPrice(indirimliTutar, formatEkstra);

        // Çıktı Üretme (İstediğin formata uygun)
        generateTicketInfo(temelFiyat, indirimOrani, indirimliTutar, formatEkstra, sonFiyat, filmTuru);

        scan.close();
    }

    // --- METOTLAR ---

    // Temel Fiyat Hesaplama
    public static double calculateBasePrice(int gun, int saat) {
        boolean isWeekend = (gun == 6 || gun == 7);
        boolean isMatinee = (saat < 12);

        if (isWeekend) {
            return isMatinee ? 55.0 : 85.0;
        } else {
            return isMatinee ? 45.0 : 65.0;
        }
    }

    // İndirim Oranı Hesaplama
    public static double calculateDiscount(int yas, int meslek, int gun) {
        // Yaş indirimleri
        if (yas >= 65) return 0.30; // %30
        if (yas < 12) return 0.25;  // %25

        // Meslek İndirimleri
        double oran = 0.0;
        switch (meslek) {
            case 1: // Öğrenci
                // Pzt(1)-Prş(4) arası %20, diğer günler %15
                if (gun >= 1 && gun <= 4) oran = 0.20;
                else oran = 0.15;
                break;
            case 2: // Öğretmen
                // Sadece Çarşamba(3) %35
                if (gun == 3) oran = 0.35;
                break;
            case 3: // Diğer
                oran = 0.0;
                break;
            default:
                oran = 0.0;
        }
        return oran;
    }

    // Format Ekstra Ücreti
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 1: return 0.0;  // 2D
            case 2: return 25.0; // 3D
            case 3: return 35.0; // IMAX
            case 4: return 50.0; // 4DX
            default: return 0.0;
        }
    }

    // Format ismini döndüren yardımcı metot
    public static String getFormatName(int filmTuru) {
        switch (filmTuru) {
            case 2: return "3D";
            case 3: return "IMAX";
            case 4: return "4DX";
            default: return "2D";
        }
    }

    // Final Fiyat Hesaplama
    public static double calculateFinalPrice(double indirimliTutar, double formatEkstra) {
        return indirimliTutar + formatEkstra;
    }

    // Çıktı Formatı
    public static void generateTicketInfo(double temel, double oran, double indirimliHal, double ekstra, double toplam, int filmTuru) {
        String formatAdi = getFormatName(filmTuru);

        System.out.println("\n--- HESAP DETAYI ---");

        System.out.printf("Temel=%.0fTL, İnd=%%%.0f -> %.2fTL", temel, (oran * 100), indirimliHal);

        if (ekstra > 0) {
            System.out.printf(", %s+%.0fTL", formatAdi, ekstra);
        }

        System.out.printf(" = %.2f TL\n", toplam);
    }
}
