# TKTPL Mobile Programming

Nama    : Siti Aulia Rahmatussyifa

NPM     : 1706022073

Kelas   : TKTPL - A

* * *

## water-time
[Water Time](https://github.com/sarsyifa/water-time) helps you to records your water intake every day. This app reminds you to drink water daily using notification

## Requirements

1.  Menerapkan seluruh stack Android Framework standard

    a.  Menerapkan activity

        Aplikasi ini menerapkan lebih dari satu activity. Activity untuk Profile (User), Activity untuk Log (WaterConsumption), dan Activity untuk Reminder (Event). Masing-masing activity mengimplementasikan sebuah interface untuk keperluan komunikasi dan transfer data antar activity.

    b.  Menggunakan Service dan pemanggilan Remote Method (di luar aplikasi)

        Aplikasi ini menggunakan AlarmManager untuk men-trigger kode pada waktu yang ditentukan pada saat membuat reminder. AlarmManager menggunakan Android SDK’s alarm service yang berjalan secara independen dari application’s lifecycle. AlarmManager menyediakan akses terhadap service Alarm pada android. Dengan service ini, pengguna dapat menjadwalkan fitur notifikasi reminder untuk dijalankan pada saat waktunya,

    c.  Memanfaatkan Content Provider

        Menggunakan kalender yang terhubung dengan kalender pengguna untuk membuat reminder untuk konsumsi air putih yang dibutuhkan setiap harinya.
        Menyediakan menu untuk shortcut ke Locale Settings untuk mengganti bahasa.

    d.  Menerapkan BroadcastReceiver

        Aplikasi ini menerapkan BroadcastReceiver untuk mengambil informasi tentang Notifikasi dengan menggunakan NotificationManager untuk mengirimkan notifikasi kepada pengguna.
        Aplikasi memberikan notifikasi reminder jika suatu alarm sudah pada waktunya.
        Aplikasi ini memanfaatkan BroadcastReceiver dengan memunculkan notification ketika ketika pengguna sudah berada pada tanggal dan waktu dari reminder yang telah dibuat sebelumnya. Saya menerapkan BroadcastReceiver pada class NotifierAlarm dan BootUpReceiver, dimana NotifierAlam memiliki method onReceive yang akan dipanggil ketika BroadcastReceiver menerima boradcast Intent. Setelah Alarm dijalankan, method BroadcastReceiver ini akan membroadcast notifikasi pada aplikasi.


    e.  Menerapkan Background Process (cth: AsyncTask) yang tidak “mati” ketika activity tidak aktif.

        Query database dijalankan pada background process dengan menggunakan AsyncTask. Repositori akan me-manage query threads untuk digunakan. Pada aplikasi ini WaterConsumptionRepository dan UserRepository mengimplementasikan AsyncTask.
        Pada saat pemanggilan addLog, method ini akan meng-execute class InsertLogAsyncTask. Kemudian akan meng-override doInBackground. Pada method ini proses pengiriman/pengambilan data terjadi. Prosesnya berjalan pada background process (dipanggil pada Background Thread setelah onPreExecute() selesai dijalankan).

2.  Menerapkan multi environment

    a.  Multi Layout

        Dalam aplikasi dilakukan penyesuaian tampilan untuk mobile phone. Tampilan aplikasi akan menyesuaikan untuk posisi landscape atau portrait.
        Resource file untuk tampilan mode portrait berada di layout dan untuk tampilan mode landscape berada di layout-land

    b.  Multi Language (i18n)

        Aplikasi disediakan dalam 2 bahasa, yaitu Bahasa Indonesia dan Bahasa Inggris. Tampilan aplikasi akan menyesuaikan bahasa yang digunakan pada device tersebut.
        Aplikasi ini menggunakan string resources untuk menyimpan objek-objek string yang sering muncul dan sering digunakan, mulai dari judul aplikasi, tombol, dan toast. String resources disimpan pada file res/values/strings.xml dan res/values-in-rID/strings.xml

3.  Menerapkan Design Pattern MVVM & Background Task

    a.  Setiap komponen (cth: Tombol, TextBox, Menu) yang tampil, memiliki ViewModel.

        Setiap button memiliki ViewModel yang merujuk ke salah satu Model (WaterConsumptiom maupun User).
        MVVM dikolaborasikan dengan datapersistence. ViewModel digunakan pada activity yang menampilkan informasi atau data dari object, dengan menerapkan method ViewModelProviders.



    b.  Melakukan Binding antara ViewModel dengan Model.
    
        WaterConsumptionViewModel ↔ WaterConsumption
        UserViewModel ↔ User
        The adapter caches data and populates the RecyclerView with it. The inner class WaterConsumptionViewHolder holds and manages a view for one list item.

4.  Menerapkan Assets dengan benar
    a.  String resource dipergunakan untuk menyimpan label untuk segala teks statik yang dipergunakan di dalam aplikasi dan harus mendukung i18n (minimal 2 bahasa).

        String yang ada pada tampilan ditaruh di res/values/strings.xml versi Bahasa Inggris dan Bahasa Indonesia di res/values-in-rID/strings.xml.

    b.  Mempersiapkan drawable resource untuk 2 (dua) screen size (& beda resolusi).

        Penggunaan resource seperti image atau icon, resolusi atau sizenya akan disesuaikan dengan ukuran layar.
        Aplikasi ini mempersiapkan drawable resources untuk berbagai resolusi. Drawable resources yang dipersiapkan adalah icon aplikasi. Icon aplikasi dipersiapkan dalam berbagai resolusi, diantaranya hdpi, mdpi, xhdpi, xxhdpi, dan xxxhdpi.

    c.  Memanfaatkan ContentProvider

        Menyediakan menu untuk shortcut ke Locale Settings untuk mengganti bahasa.


5.  Menerapkan Data Persistence

    Aplikasi ini menggunakan Room Persistence Library. Adapun data yang disimpan terdiri dari 3 Entity, yaitu:
    -   WaterConsumption: id, water_drunk (mL), date, time

        Class WaterConsumption akan berisi informasi mengenai jumlah air yang diminum dalam mL, beserta waktu kapan air tersebut diminum.
    -   User: id, name, age, weight, height

        Class User akan berisi tentang data diri pengguna mengenai nama, umur, berat badan, dan tinggi badan.

    -   Event: id, message, date, time

        Class Event akan berisi reminder yang sudah ditentukan kapan harusnya alarm dinyalakan.

    Dalam room terdapat tiga komponen utama: Database, DAO (Data Access Object), dan Entity. Setiap komponen memiliki tanggung jawabnya, dan ketiganya harus diimplementasikan agar sistem berfungsi. Entity berperan sebagai Model kelas ini yang akan disimpan di Database. Tabel database eksklusif dibuat untuk setiap kelas yang diberi anotasi @Entity. DAO adalah interface yang dianotasikan dengan @Dao yang memediasi akses ke objek dalam database dan tabelnya. Ada empat anotasi khusus untuk operasi dasar DAO: @Insert, @Update, @Delete, dan @Query. Komponen Database adalah kelas abstrak dengan anotasi @Database, yang memperluas RoomDatabase. Kelas mendefinisikan daftar Entity dan DAO-nya.

6.  Menerapkan Runtime Permission

    Pada saat menekan tombol Share, aplikasi akan mengakses status konektivitas yang digunakan pada
    saat itu dengan mengakses ACCESS_NETWORK_STATE dan ACCESS_WIFI_STATE, untuk mengetahui tipe
    jaringan yang digunakan diperlukan membaca READ_PHONE_STATE. User akan mendapatkan runtime p
    ermission pada saat aplikasi akan mengakses TelephonyMagager. Apabila dilakukan allow permission,
    maka aplikasi dapat melakukan getDataNetworkType() dari koneksi yang digunakan pada saat itu.
    Sehingga aplikasi dapat mendeteksi status konektivitas beserta network type yang digunakan.

7.  Memanfaatkan JNI (Java Native Interface)

    Terdapat tambahan menu baru Water Calculator untuk menghitung daily water intake berdasarkan
    berat badan. Menu ini akan memanggil CalculatorActivity. Perhitungannya memanfaatkan fungsi
    library native C sederhana

    Kemudian pada CalculatorActivity dilakukan pemanggilaan library watercalc-jni

8.  Menampilkan animasi dengan OpenGL

    Pada saat pindah Activity dari MainActivity ke CalculatorActivity akan terdapat loading screen
    yang dibuat menggunakan OpenGL. Loading screen berupa sebuah Cube yang di-rotate untuk
    menggambarkan bahwa terdapat loading selama 1.5 seconds.

9.  Memanfaatkan ConnectivityManager untuk mengetahui status konektifitas yang dihubungkan dengan fitur tertentu.

    User dapat membagikan aktivitasnya mengenai penggunaan aplikasi Water Time di Twitter dengan
    cara memilih menu Share. Karena untuk melakukan share diperlukan koneksi internet, pada saat
    menekan tombol Share, aplikasi akan mengakses status konektivitas yang digunakan pada saat itu.

10.  Menerapkan service background yang berhubungan dengan tampilan / aplikasi utama. Cth: Service pemutar lagu tetap berjalan walaupun tampilan/aplikasi utama sedang lost focus.

11.  Menerapkan Notifikasi atas event-event penting yang terjadi saat aplikasi dalam keadaan lost focus.

     Pada aplikasi ini terdapat notifikasi menggunakan NotificationManager untuk mengirimkan
     notifikasi kepada pengguna. Aplikasi memberikan notifikasi reminder jika suatu alarm sudah
     pada waktunya.