public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private String SkinType;
    private String Merk;

    public Product(String id, String nama, double harga, String kategori, String SkinType, String Merk) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.SkinType = SkinType;
        this.Merk = Merk;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setSkinType(String SkinType) { this.SkinType = SkinType; }

    public void setMerk(String Merk) {this.Merk = Merk; }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public String getSkinType() { return this.SkinType; }

    public String getMerk() { return  this.Merk; }
}