/**
 * Ad Soyad: Abdullah Gümüş
 * Öğrenci No: 250542016
 * Proje: Restoran Sipariş Sistemi
 * Tarih: 26.11.2025
 */


import java.util.Scanner;


public class Proje3_RestoranSiparis {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- AKILLI RESTORAN SİPARİŞ SİSTEMİ ---");

        // 1. MENÜ SEÇİMLERİ
        System.out.println("\n--- ANA YEMEKLER ---");
        System.out.println("1.Izgara Tavuk(85₺) 2.Adana Kebap(120₺) 3.Levrek(110₺) 4.Mantı(65₺) 0.Yok");
        System.out.print("Seçiminiz: ");
        int anaSecim = scan.nextInt();

        System.out.println("\n--- BAŞLANGIÇLAR ---");
        System.out.println("1.Çorba(25₺) 2.Humus(45₺) 3.Sigara Böreği(55₺) 0.Yok");
        System.out.print("Seçiminiz: ");
        int baslangicSecim = scan.nextInt();

        System.out.println("\n--- İÇECEKLER ---");
        System.out.println("1.Kola(15₺) 2.Ayran(12₺) 3.Meyve Suyu(35₺) 4.Limonata(25₺) 0.Yok");
        System.out.print("Seçiminiz: ");
        int icecekSecim = scan.nextInt();

        System.out.println("\n--- TATLILAR ---");
        System.out.println("1.Künefe(65₺) 2.Baklava(55₺) 3.Sütlaç(35₺) 0.Yok");
        System.out.print("Seçiminiz: ");
        int tatliSecim = scan.nextInt();

        // 2. DİĞER BİLGİLER
        System.out.print("\nSaat kaç? (0-23): ");
        int saat = scan.nextInt();

        System.out.print("Öğrenci misiniz? (true/false): ");
        boolean isStudent = scan.nextBoolean();

        System.out.print("Hafta içi mi? (true/false): ");
        boolean isWeekday = scan.nextBoolean();

        // 3. FİYAT VE İSİM ALMA
        double pAna = getMainDishPrice(anaSecim);
        double pBaslangic = getAppetizerPrice(baslangicSecim);
        double pIcecek = getDrinkPrice(icecekSecim);
        double pTatli = getDessertPrice(tatliSecim);

        // 4. HESAPLAMA MANTIĞI

        // Ara Toplam
        double araToplam = pAna + pBaslangic + pIcecek + pTatli;

        // İndirimler
        double happyHourIndirim = 0.0;
        double comboIndirim = 0.0;
        double ogrenciIndirim = 0.0;

        // A. Happy Hour İndirimi (İçecek varsa ve saat uygunsa) -> İçecek fiyatı üzerinden %20
        if (isHappyHour(saat) && pIcecek > 0) {
            happyHourIndirim = pIcecek * 0.20;
        }

        // B. Combo İndirimi (Ana + İçecek + Tatlı varsa) -> Toplam Tutar üzerinden %15
        if (isComboOrder(pAna > 0, pIcecek > 0, pTatli > 0)) {
            comboIndirim = araToplam * 0.15;
        }

        // C. Öğrenci İndirimi (Hafta içi ise) -> KALAN TUTAR üzerinden %10
        // Formül: (AraToplam - HappyHour - Combo) * 0.10
        double kalanTutar = araToplam - happyHourIndirim - comboIndirim;

        if (isStudent && isWeekday) {
            ogrenciIndirim = kalanTutar * 0.10;
        }

        // Son Tutar
        double toplamOdenen = araToplam - happyHourIndirim - comboIndirim - ogrenciIndirim;

        // Bahşiş (Son tutar üzerinden %10)
        double bahsis = calculateServiceTip(toplamOdenen);


        // 5. ÇIKTI FORMATI
        System.out.println("\nTest Sonucu:");

        // Sipariş Özeti String Oluşturma
        String siparisOzeti = "";
        if (pAna > 0) siparisOzeti += getMainDishName(anaSecim) + "(" + (int)pAna + "₺)+";
        if (pBaslangic > 0) siparisOzeti += getAppetizerName(baslangicSecim) + "(" + (int)pBaslangic + "₺)+";
        if (pIcecek > 0) siparisOzeti += getDrinkName(icecekSecim) + "(" + (int)pIcecek + "₺)+";
        if (pTatli > 0) siparisOzeti += getDessertName(tatliSecim) + "(" + (int)pTatli + "₺)";

        // Sondaki fazlalık "+" işaretini temizleme
        if (siparisOzeti.endsWith("+")) {
            siparisOzeti = siparisOzeti.substring(0, siparisOzeti.length() - 1);
        }
        System.out.println(siparisOzeti);

        // Durum Bilgisi
        String gunMetni = isWeekday ? "Çarşamba" : "Hafta Sonu";
        String ogrMetni = isStudent ? "Öğrenci" : "Sivil";
        System.out.printf("        Saat=%d, %s, %s\n", saat, ogrMetni, gunMetni);

        // Hesap Detayları
        System.out.printf("        Ara toplam: %.0f₺\n", araToplam);

        if (comboIndirim > 0) {
            System.out.printf("        Combo -15%%: -%.2f₺\n", comboIndirim);
        }
        if (happyHourIndirim > 0) {
            System.out.printf("        Happy Hour (içecek) -20%%: -%.2f₺\n", happyHourIndirim);
        }
        if (ogrenciIndirim > 0) {
            System.out.printf("        Öğrenci -10%%: -%.2f₺\n", ogrenciIndirim);
        }

        System.out.printf("        Toplam: %.2f₺\n", toplamOdenen);
        System.out.printf("        Bahşiş önerisi: %.2f₺\n", bahsis);

        scan.close();
    }

    // --- ZORUNLU METOTLAR ---

    // Ana Yemek Fiyatı
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0;
            case 2: return 120.0;
            case 3: return 110.0;
            case 4: return 65.0;
            default: return 0.0;
        }
    }
    // Ana Yemek İsmi
    public static String getMainDishName(int secim) {
        switch (secim) {
            case 1: return "Izgara Tavuk";
            case 2: return "Adana";
            case 3: return "Levrek";
            case 4: return "Mantı";
            default: return "";
        }
    }

    // Başlangıç Fiyatı
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0;
            case 2: return 45.0;
            case 3: return 55.0;
            default: return 0.0;
        }
    }
    // Başlangıç İsmi
    public static String getAppetizerName(int secim) {
        switch (secim) {
            case 1: return "Çorba";
            case 2: return "Humus";
            case 3: return "Sigara Böreği";
            default: return "";
        }
    }

    // İçecek Fiyatı
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0;
            case 2: return 12.0;
            case 3: return 35.0;
            case 4: return 25.0;
            default: return 0.0;
        }
    }
    // İçecek İsmi
    public static String getDrinkName(int secim) {
        switch (secim) {
            case 1: return "Kola";
            case 2: return "Ayran";
            case 3: return "Meyve Suyu";
            case 4: return "Limonata";
            default: return "";
        }
    }

    // Tatlı Fiyatı
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0;
            case 2: return 55.0;
            case 3: return 35.0;
            default: return 0.0;
        }
    }
    // Tatlı İsmi
    public static String getDessertName(int secim) {
        switch (secim) {
            case 1: return "Künefe";
            case 2: return "Baklava";
            case 3: return "Sütlaç";
            default: return "";
        }
    }

    // Combo Kontrolü
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    // Happy Hour Kontrolü
    public static boolean isHappyHour(int saat) {
        return (saat >= 14 && saat < 17);
    }

    // Genel İndirim Hesaplama (mainde hesaplandi)
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int saat) {
        if (tutar > 200) return tutar * 0.10;
        return 0.0;
    }

    // Bahşiş Hesaplama
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
}