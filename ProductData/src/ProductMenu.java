import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;


public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.mainPanel.setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private Database database;

    public JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTextField merkField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JLabel merkLabel;
    private JComboBox<String> SkinTypeBox;
    private JComboBox<String> finishProdukBox;



    // constructor
    public ProductMenu() {
        // inisialisasi listProduct

        database = new Database();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box kategori
        String[] kategoriData = { "Pilih Kategori", "Skincare", "Make Up", "Tools Make Up" };
        kategoriBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // atur isi combo box skin type (pakai yang dari desain, bukan add manual!)
        SkinTypeBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Oily", "Dry", "Combination", "All Skin Types"
        }));


        // tombol delete saat awal
        deleteButton.setVisible(true);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData(); // tambah data baru
                } else {
                    updateData(); // update data yang dipilih
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = productTable.getSelectedRow();

                // ambil data dari tabel
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curSkinType = productTable.getModel().getValueAt(selectedIndex, 5).toString();
                String curMerk = productTable.getModel().getValueAt(selectedIndex, 6).toString();


                // tampilkan ke field
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriBox.setSelectedItem(curKategori);
                SkinTypeBox.setSelectedItem(curSkinType);
                merkField.setText(curMerk);

                // ubah tombol jadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan tombol delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel (sekarang ada 7 kolom)
        Object[] cols = { "No", "ID Produk", "Nama", "Harga", "Kategori", "Skin Type", "Merk", };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        // isi tabel dengan listProduct
        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");
            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[7];
                row[0] = i+1;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("SkinType");
                row[6] = resultSet.getString("Merk");


                tmp.addRow(row);
                i++;

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return tmp;
    }

    public void insertData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            String hargaText = hargaField.getText();
            String kategori = kategoriBox.getSelectedItem().toString();
            String SkinType = SkinTypeBox.getSelectedItem().toString();
            String Merk = merkField.getText();

            // Cek input kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() ||
                    kategori.equals("Pilih Kategori") || Merk.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Semua kolom harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return; // stop proses insert
            }

            // konversi harga
            double harga = Double.parseDouble(hargaText);

            // Cek ID duplikat
            ResultSet rs = database.selectQuery("SELECT * FROM product WHERE id='" + id + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "ID sudah digunakan, silakan gunakan ID lain!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return; // stop proses insert
            }

            // kalau lolos semua pengecekan → lanjut insert
            String sqlQuery = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + SkinType + "', '" + Merk + "')";
            database.insertUpdateDeleteQuery(sqlQuery);

            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
            System.out.println("Insert Berhasil");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            String hargaText = hargaField.getText();
            String kategori = kategoriBox.getSelectedItem().toString();
            String SkinType = SkinTypeBox.getSelectedItem().toString();
            String Merk = merkField.getText();

            // Cek input kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() ||
                    kategori.equals("Pilih Kategori") || Merk.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Semua kolom harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaText);

            String sqlQuery = "UPDATE product SET nama='" + nama + "', harga=" + harga +
                    ", kategori='" + kategori + "', SkinType='" + SkinType + "', Merk='" + Merk + "' WHERE id='" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery);

            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
            System.out.println("Update Berhasil");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // Tambahkan konfirmasi dulu
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Apakah kamu yakin ingin menghapus produk ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        // Hanya hapus data kalau user menekan "Yes"
        if (confirm == JOptionPane.YES_OPTION) {

            String id = idField.getText();

            // hapus data dari list
            String sqlQuery = "DELETE FROM product WHERE id='" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } else {
            // kalau user klik "No", tampilkan pesan batal (opsional)
            JOptionPane.showMessageDialog(null, "Penghapusan dibatalkan.");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriBox.setSelectedIndex(0);
        SkinTypeBox.setSelectedIndex(0);
        merkField.setText("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}