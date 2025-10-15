# TP5DPBO2425C2
Saya Fauzia Rahma Nisa mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berdasarkan Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

**1. Desain Program**

   Program ini dibuat pakai Java Swing buat ngatur data produk kecantikan. Semua data disimpan di database MySQL, jadi waktu kita add, update, atau delete produk, datanya langsung ke-update di database. Ada tiga file utama: 
   - Database.java buat ngatur koneksi ke MySQL
   - Product.java buat nyimpen struktur data produk
   - ProductMenu.java yang jadi tampilan utama tempat semua fitur CRUD dijalankan.

**Desain Database**

   Database-nya namanya db_productKu, isinya satu tabel product yang punya kolom id, nama, harga, kategori, SkinType, dan Merk. Kolom id jadi kunci utama
(primary key) biar tiap produk nggak ada yang sama. Struktur ini cukup buat nyimpen info produk skincare, make up, atau alat make up lengkap sama jenis kulit dan merknya.

**2. Penjelasan Alur**

   Waktu program dijalankan, sistem langsung nyambung ke database dan nampilin semua data produk di tabel. Pengguna bisa nambah produk baru lewat form input terus klik tombol Add. Sebelum disimpan, program ngecek dulu apakah semua kolom udah diisi dan ID-nya belum pernah dipakai/belum ada di tabel. Tombol Update dipakai buat ubah data yang udah ada, Delete buat hapus produk (tapi muncul konfirmasi dulu biar nggak salah hapus), dan Cancel buat ngapus isi form biar balik kayak semula.

**3. Dokumentasi**
   
   **A. ADD**
   
   Prompt error jika masih ada kolom input yang kosong saat add data.
   ![add 5](Dokumentasi/add5.png)

   Data berhasil ditambahkan.
   ![add 1](Dokumentasi/add1.png)
   ![add 2](Dokumentasi/add2.png)

   Prompt error jika sudah ada ID yang sama saat add.
   ![add 3](Dokumentasi/add3.png)

   Data yang ditambahkan masuk ke Database
   ![add 4](Dokumentasi/add4.png)
   

   **B. Update**
   
   Prompt error jika masih ada kolom input yang kosong saat update data.
   ![update 1](Dokumentasi/update1.png)

   
   **C. Delete**
   
   Data pada Database akan ikut terhapus
   ![delete 1](Dokumentasi/delete1.png)
   ![delete 2](Dokumentasi/delete2.png)
   ![delete 3](Dokumentasi/delete3.png)
