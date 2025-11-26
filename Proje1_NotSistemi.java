/**
 * Ad Soyad: Abdullah Gümüş
 * Öğrenci No: 250542016
 * Proje: Not Sistemi
 * Tarih: 26.11.2025
 */

import java.util.Scanner;

public class Proje1_NotSistemi {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("----Öğrenci Not Değerlendirme Sistemi---");

        System.out.print("Vize Notunu Giriniz: ");
        double vize = scan.nextDouble();

        System.out.print("Final Notunu Giriniz: ");
        double finalNotu = scan.nextDouble();

        System.out.print("Ödev Notunu Giriniz: ");
        double odev = scan.nextDouble();

        // ort hesaplama
        double ortalama = calculateAverage(vize, finalNotu, odev);

        // durumlari belirleme
        boolean gecti = isPassingGrade(ortalama);
        char harfNotu = gettingLetterGrade(ortalama);
        boolean onurListesi = isHonorList(ortalama, vize, finalNotu, odev);
        boolean butunlemehakki = hasRetakeRight(ortalama);

        // ciktilar
        System.out.println("---\nSonuç Raporu---");
        System.out.printf("Ortalama: %.2f\n", ortalama);
        System.out.println("Harf Notu: " + harfNotu);

        if (gecti) {
            System.out.println("DURUM: GEÇTİ");
            if (onurListesi) {
                System.out.println("Tebrikler Onur Listesindesiniz!");
            }
        } else {
            System.out.println("DURUM: KALDI");
            if (butunlemehakki) {
                System.out.println("Bütünleme Sınavına Hakkınız var.");
            } else {
                System.out.println("Bütünleme için Ortalamanız yetersiz, dersi tekrar almalısınız.");
            }
        }
        scan.close();
    } // Main metodu bitişi

    // --- DİĞER METOTLAR BURADAN BAŞLIYOR (SINIFIN İÇİNDE OLMALI) ---

    // Ort Hesaplama Metodu
    public static double calculateAverage(double vize, double finalNotu, double odev) {
        return (vize * 0.30) + (finalNotu * 0.40) + (odev * 0.30);
    }

    // Geçti-Kaldı Metodu
    public static boolean isPassingGrade(double ortalama) {
        return ortalama >= 50;
    }

    // Harf Notu
    public static char gettingLetterGrade(double ortalama) {
        if (ortalama >= 90) return 'A';
        else if (ortalama >= 80) return 'B';
        else if (ortalama >= 70) return 'C';
        else if (ortalama >= 60) return 'D';
        else if (ortalama >= 50) return 'E';
        else return 'F';
    }

    // Onur Listesi
    public static boolean isHonorList(double ortalama, double vize, double finalNotu, double odev) {
        return (ortalama >= 85) && (vize >= 70) && (finalNotu >= 70) && (odev >= 70);
    }

    // Bütünleme Hakkı
    public static boolean hasRetakeRight(double ortalama) {
        // 40 ile 50 arasındakiler
        return (ortalama >= 40) && (ortalama < 50);
    }

} // <--- SINIF EN SON BURADA KAPATILMALI