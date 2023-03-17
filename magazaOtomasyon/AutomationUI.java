package magazaOtomasyon;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import magazaOtomasyon.Business.BrandDebtManager;
import magazaOtomasyon.Business.BrandManager;
import magazaOtomasyon.Business.ProductManager;
import magazaOtomasyon.Business.SalesManager;
import magazaOtomasyon.DataAccess.BrandDal;
import magazaOtomasyon.DataAccess.BrandDebtDal;
import magazaOtomasyon.DataAccess.LogDal;
import magazaOtomasyon.DataAccess.ProductDal;
import magazaOtomasyon.DataAccess.SalesDal;
import magazaOtomasyon.Entities.Brand;
import magazaOtomasyon.Entities.BrandDebt;
import magazaOtomasyon.Entities.Log;
import magazaOtomasyon.Entities.Product;
import magazaOtomasyon.Entities.Sales;
import magazaOtomasyon.Business.LogManager;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.RowFilter;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSpinner;

public class AutomationUI extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel productsModel;
	private DefaultTableModel brandsModel;
	private DefaultTableModel logsModel;
	private DefaultTableModel brandDebtModel;
	private DefaultTableModel salesModel;
	private JTabbedPane tabbedPane;
	private JPanel productsPanel;
	private JTable productsTable;
	private JScrollPane productsScrollPane;
	private JPanel brandsPanel;
	private JTable brandsTable;
	private JScrollPane brandsScrollPane;
	private JTextField tbxProductCode;
	private JTextField tbxProductName;
	private JTextField tbxProductPrice;
	private JTextField tbxProductAmount;
	JButton btnAddProduct = new JButton("Ürünü Ekle");
	private JLabel lblUpdateProductId;
	private JTextField tbxBrandCode;
	private JTextField tbxBrandName;
	JButton btnAddBrand = new JButton("Markayı ekle");
	JComboBox<String> cbxBrands = new JComboBox<String>();
	DefaultComboBoxModel<String> boxModel;
	DefaultComboBoxModel<String> updateBoxModel;
	private JTable logsTable;
	private JCheckBox checkBoxBuys;
	private JCheckBox checkBoxUpdates;
	private JTextField tbxSearchProduct;
	private JTextField tbxUpdateProductCode;
	JPanel panelUpdate = new JPanel();
	JPanel logPanel = new JPanel();
	JButton btnUpdatePanel = new JButton("Güncelleme");
	private JTextField tbxUpdateProductName;
	private JTextField tbxUpdateProductPrice;
	private JTextField tbxUpdateProductAmount;
	JButton btnUpdateProduct = new JButton("Güncelle");
	JComboBox<String> cbxUpdateProductProvider = new JComboBox<String>();
	private JTable brandDebtTable;
	JButton btnDeleteProduct = new JButton("Kaldır");
	private JTextField tbxSearchBrand;
	private JTextField tbxSearchDebt;
	private JPanel updateBrandPanel;
	private JTextField tbxUpdateBrandCode;
	private JTextField tbxUpdateBrandName;
	JButton btnShowUpdateBrandPanel = new JButton("Güncelleme");
	private JLabel lblUpdateBrandId;
	JButton btnUpdateBrand = new JButton("Markayı Güncelle");
	JButton btnDeleteBrand = new JButton("Kaldır");
	private JLabel lblSellProductId;
	private JTextField tbxSellAmount;
	private JTextField tbxSellPrice;
	JButton btnOpenSell = new JButton("Satış");
	JPanel sellProductPanel = new JPanel();
	JLabel lblSellProductAmount = new JLabel("Miktar :");
	JLabel lblSellProductPrice = new JLabel("Fiyat :");
	JLabel lblSellProductCode = new JLabel("");
	JLabel lblSellProductName = new JLabel("");
	JButton btnSellProduct = new JButton("SAT");
	private JTable salesTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutomationUI frame = new AutomationUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AutomationUI() {
		setResizable(false);
		initializeTable();
		fulfillProductsTable();
		fulfillBrandsTable();
		fulfillLogsTable();
		fulfillBrandDebtTable();
		fulfillSalesTable();
		setActions();
	}

	public void setActions() {
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ADD NEW PRODUCT
				ProductManager productManager = new ProductManager(new ProductDal());

				if (tbxProductCode.getText().isEmpty() || tbxProductAmount.getText().isEmpty()
						|| tbxProductName.getText().isEmpty() || tbxProductPrice.getText().isEmpty()
						|| cbxBrands.getSelectedItem().toString().isEmpty()) {
					JOptionPane.showMessageDialog(productsPanel, "BOŞ ALAN BIRAKMAYINIZ");
				} else {
					try {
						Product newProduct = new Product(Integer.valueOf(tbxProductCode.getText()),
								tbxProductName.getText(), (String) cbxBrands.getSelectedItem(),
								Float.valueOf(tbxProductPrice.getText()), Integer.valueOf(tbxProductAmount.getText()));

						boolean isAdded = productManager.add(newProduct);
						if (isAdded) {
							// FULFILL TABLES AND LOG IT
							fulfillProductsTable();
							logToDataBase("ALIM",
									newProduct.getProductProvider() + " satıcısından " + newProduct.getProductCode()
											+ " koduna sahip '" + newProduct.getProductName() + "' ürününden, "
											+ newProduct.getProductPrice() + " TL fiyatına "
											+ newProduct.getProductAmount() + " adet satın alındı");
							fulfillLogsTable();

							// ADD DEBT
							BrandDebtManager brandDebtManager = new BrandDebtManager(null);
							// diğer fonksiyonları kullanmadığımdan null kalabilir

							brandDebtManager.addDebt(newProduct.getProductProvider(),
									(Double.valueOf(tbxProductPrice.getText())
											* Integer.valueOf(tbxProductAmount.getText())));
							fulfillBrandDebtTable();
						}

					} catch (Exception exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(productsPanel,
								"Lütfen kod,fiyat ve miktarı sayı olarak girdiğinize emin olunuz");
					}

				}

			}
		});

		productsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = productsTable.getSelectedRow();
				int selectedCbxIndex = 0;

				// Update olacak item için comboBox doldurmak adına
				BrandManager brandManager = new BrandManager(new BrandDal());
				ArrayList<String> brandNames = new ArrayList<String>();

				for (Brand brand : brandManager.getAll()) {
					brandNames.add(brand.getName());
				}

				for (int i = 0; i < brandNames.size(); i++) {
					if (productsTable.getValueAt(row, 3).toString().equals(brandNames.get(i))) {
						selectedCbxIndex = i;
					}
				}
				//

				// Set Update Panel
				lblUpdateProductId.setText(productsTable.getValueAt(row, 0).toString());
				tbxUpdateProductCode.setText(productsTable.getValueAt(row, 1).toString());
				tbxUpdateProductName.setText(productsTable.getValueAt(row, 2).toString());
				cbxUpdateProductProvider.setSelectedIndex(selectedCbxIndex);
				tbxUpdateProductPrice.setText(productsTable.getValueAt(row, 4).toString());
				tbxUpdateProductAmount.setText(productsTable.getValueAt(row, 5).toString());
				//

				// Set Sell Panel
				lblSellProductId.setText(productsTable.getValueAt(row, 0).toString());
				lblSellProductCode.setText(productsTable.getValueAt(row, 1).toString());
				lblSellProductName.setText(productsTable.getValueAt(row, 2).toString());

			}
		});
		btnAddBrand.setBounds(94, 389, 109, 23);

		btnAddBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!tbxBrandCode.getText().isEmpty() || !tbxBrandName.getText().isEmpty()) {

					boolean isUsed = false;

					for (int i = 0; i < brandsTable.getRowCount(); i++) {

						if (brandsTable.getValueAt(i, 2).toString().equals(tbxBrandName.getText())) {
							isUsed = true;
							break;
						}
						if (Integer.valueOf(brandsTable.getValueAt(i, 1).toString())
								.equals(Integer.valueOf(tbxBrandCode.getText()))) {
							isUsed = true;
							break;
						}
					}

					if (!isUsed) {
						BrandManager brandManager = new BrandManager(new BrandDal());
						BrandDebtManager brandDebtManager = new BrandDebtManager(new BrandDebtDal());
						try {
							Brand newBrand = new Brand(Integer.valueOf(tbxBrandCode.getText()), tbxBrandName.getText());
							BrandDebt brandDebt = new BrandDebt(newBrand.getBrandCode(), newBrand.getName(), 0.0);
							// initialize 0 debt for new brand

							brandDebtManager.add(brandDebt);
							brandManager.add(newBrand);

							logToDataBase("GÜNCELLEME", "'" + newBrand.getName() + "' markası , database ' e eklendi!");
							fulfillBrandsTable();
							fulfillLogsTable();
							fulfillBrandDebtTable();

							tbxBrandName.setText("");
							tbxBrandCode.setText("");
						} catch (Exception exception) {
							exception.printStackTrace();
							JOptionPane.showMessageDialog(brandsPanel, "Lütfen alanları doğru doldurunuz");
						}
					} else {
						JOptionPane.showMessageDialog(brandsPanel,
								"Girdiğiniz değerler daha önce kullanılmış\nLütfen başka bir değer giriniz");
					}
				} else {
					JOptionPane.showMessageDialog(brandsPanel, "Lütfen Boş Alanları Doldurunuz");
				}
			}
		});

		tbxSearchProduct.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchString = tbxSearchProduct.getText();
				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(productsModel);
				productsTable.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchString));
			}
		});

		btnUpdatePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelUpdate.setVisible(!panelUpdate.isVisible());
			}
		});

		btnUpdateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(productsPanel, "Güncellemek istediğinize emin misiniz ?",
						"Onaylama", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (tbxUpdateProductAmount.getText().isEmpty() || tbxUpdateProductCode.getText().isEmpty()
						|| tbxUpdateProductName.getText().isEmpty() || tbxUpdateProductPrice.getText().isEmpty()
						|| cbxUpdateProductProvider.getSelectedItem().toString().isEmpty()) {
					JOptionPane.showMessageDialog(productsPanel, "BOŞ ALAN BIRAKMAYINIZ");
				} else {
					try {
						Product oldProduct = new Product(
								Integer.valueOf(productsTable.getValueAt(productsTable.getSelectedRow(), 0).toString()),
								Integer.valueOf(productsTable.getValueAt(productsTable.getSelectedRow(), 1).toString()),
								productsTable.getValueAt(productsTable.getSelectedRow(), 2).toString(),
								productsTable.getValueAt(productsTable.getSelectedRow(), 3).toString(),
								Double.valueOf(productsTable.getValueAt(productsTable.getSelectedRow(), 4).toString()),
								Integer.valueOf(
										productsTable.getValueAt(productsTable.getSelectedRow(), 5).toString()));

						if (choice == 0) { // yes
							// UPDATE CODES

							Product productToUpdate = new Product(Integer.valueOf(lblUpdateProductId.getText()),
									Integer.valueOf(tbxUpdateProductCode.getText()), tbxUpdateProductName.getText(),
									(String) cbxUpdateProductProvider.getSelectedItem(),
									Double.valueOf(tbxUpdateProductPrice.getText()),
									Integer.valueOf(tbxUpdateProductAmount.getText()));
							ProductManager productManager = new ProductManager(new ProductDal());
							boolean isUpdated = productManager.update(productToUpdate);

							if (isUpdated) {
								logToDataBase("GÜNCELLEME", oldProduct.toString() + " özelliklerine sahip ürün , "
										+ productToUpdate.toString() + " özellikleri ile değiştirildi");

								BrandDebtManager brandDebtManager = new BrandDebtManager(null);
								// diğer fonksiyonları kullanmadığımdan null kalabilir

								// önce halihazırdaki fiyatını azaltıyorum // sağlayıcı ismini de tablodan
								// alalım ki yeni satıcı girilmiş olabilir
								brandDebtManager.addDebt(
										productsTable.getValueAt(productsTable.getSelectedRow(), 3).toString(),
										Double.valueOf(
												productsTable.getValueAt(productsTable.getSelectedRow(), 6).toString())
												* (-1));
								// sonra yeni fiyatı ekliyorum
								brandDebtManager.addDebt(productToUpdate.getProductProvider(),
										(Double.valueOf(tbxUpdateProductPrice.getText())
												* Integer.valueOf(tbxUpdateProductAmount.getText())));

								fulfillBrandDebtTable();
								fulfillLogsTable();
								fulfillProductsTable();

								// set null to tbxs
								setProductTextBarsNull();
							}
						} else if (choice == 1) { // no
							// REFUSES UPDATE
							System.out.println("Güncelleme iptal edildi");
						}
					} catch (Exception exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(productsPanel,
								"Lütfen kod,fiyat ve miktarı sayı olarak girdiğinize emin olunuz");
					}
				}
			}
		});

		btnDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (lblUpdateProductId.getText().isBlank()) {
					JOptionPane.showMessageDialog(productsPanel, "Lütfen bir ürün seçiniz");
				} else {
					int choice = JOptionPane.showConfirmDialog(productsPanel,
							"Ürünü Kaldırmak istediğinize emin misiniz?", "Kaldırma", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (choice == 0) {
						int choice2 = JOptionPane.showConfirmDialog(productsPanel,
								"Bu işlemi geri alamazsınız, onaylıyor musunuz?\n (işlem sonucunda borcunuz da değişecektir)",
								"Onaylama", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (choice2 == 0) {

							ProductManager productManager = new ProductManager(new ProductDal());
							productManager.delete(Integer.valueOf(lblUpdateProductId.getText()));

							BrandDebtManager brandDebtManager = new BrandDebtManager(new BrandDebtDal());
							brandDebtManager.addDebt(cbxUpdateProductProvider.getSelectedItem().toString(),
									(Integer.valueOf(tbxUpdateProductAmount.getText())
											* Double.valueOf(tbxUpdateProductPrice.getText()) * (-1)));

							logToDataBase("GÜNCELLEME",
									tbxUpdateProductCode.getText() + " koduna sahip , '"
											+ tbxUpdateProductName.getText() + "' isimli ürün ve "
											+ cbxUpdateProductProvider.getSelectedItem().toString()
											+ " markasına olan borçları kaldırıldı");
							fulfillLogsTable();
							fulfillProductsTable();
							fulfillBrandDebtTable();

							// set null to tbxs
							setProductTextBarsNull();
						}
					}
				}
			}
		});

		tbxSearchBrand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchString = tbxSearchBrand.getText();
				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(brandsModel);
				brandsTable.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchString));
			}
		});

		tbxSearchDebt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchString = tbxSearchDebt.getText();
				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(
						brandDebtModel);
				brandDebtTable.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchString));
			}
		});

		btnShowUpdateBrandPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBrandPanel.setVisible(!updateBrandPanel.isVisible()); // toggle effect
			}
		});

		brandsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = brandsTable.getSelectedRow();

				lblUpdateBrandId.setText(brandsTable.getValueAt(row, 0).toString());
				tbxUpdateBrandCode.setText(brandsTable.getValueAt(row, 1).toString());
				tbxUpdateBrandName.setText(brandsTable.getValueAt(row, 2).toString());
			}
		});

		btnUpdateBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// borç kısmından , alınan ürün kısmından ve kendisinden verilerin değişmesi
				// gerekiyor
				BrandManager brandManager = new BrandManager(new BrandDal());
				Brand brandToUpdate = new Brand(Integer.valueOf(lblUpdateBrandId.getText()),
						Integer.valueOf(tbxUpdateBrandCode.getText()), tbxUpdateBrandName.getText());
				brandManager.update(brandToUpdate);

				// borç isim numara güncellemesi
				BrandDebtManager brandDebtManager = new BrandDebtManager(null);
				brandDebtManager.updateForBrand(
						Integer.valueOf(brandsTable.getValueAt(brandsTable.getSelectedRow(), 1).toString()),
						tbxUpdateBrandName.getText(), Integer.valueOf(tbxUpdateBrandCode.getText()));

				// Ürün sağlayıcı güncellemesi
				ProductManager productManager = new ProductManager(null);
				productManager.updateForBrand(tbxUpdateBrandName.getText(),
						brandsTable.getValueAt(brandsTable.getSelectedRow(), 2).toString());

				// loglama
				logToDataBase("GÜNCELLEME", brandsTable.getValueAt(brandsTable.getSelectedRow(), 2).toString()
						+ " satıcısının ismi , " + tbxUpdateBrandName.getText() + " olarak değiştirildi");

				// reflesh tables
				fulfillBrandsTable();
				fulfillBrandDebtTable();
				fulfillProductsTable();
				fulfillLogsTable();
			}
		});

		btnDeleteBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(lblUpdateBrandId.getText());
				if (!lblUpdateBrandId.getText().isBlank()) {

					int choice = JOptionPane.showConfirmDialog(productsPanel,
							"Markayı Kaldırmak istediğinize emin misiniz?", "Kaldırma", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (choice == 0) {
						int brandId = Integer.valueOf(lblUpdateBrandId.getText());
						BrandManager brandManager = new BrandManager(new BrandDal());
						brandManager.delete(brandId);

						// LOG
						logToDataBase("GÜNCELLEME", tbxUpdateBrandCode.getText() + " koduna sahip '"
								+ tbxUpdateBrandName.getText() + "' markası database'den kaldırıldı!");

						// set boxes null
						setBrandTextBarsNull();

						// DELETE FROM BRAND-DEBTS TABLE
						BrandDebtManager brandDebtManager = new BrandDebtManager(new BrandDebtDal());
						brandDebtManager.delete( // id yerine brand code girelim
								Integer.valueOf(brandsTable.getValueAt(brandsTable.getSelectedRow(), 1).toString()));

						// fulfill tables
						fulfillBrandsTable();
						fulfillLogsTable();
						fulfillBrandDebtTable();
					}

				} else {
					JOptionPane.showMessageDialog(brandsPanel, "Lütfen bir marka seçiniz");
				}

			}
		});

		checkBoxBuys.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String sortString = "";

				// set other checkboxes not selected
				checkBoxUpdates.setSelected(false);

				if (checkBoxBuys.isSelected()) {
					sortString = "ALIM";
				}

				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(logsModel);
				logsTable.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(sortString));

			}
		});

		checkBoxUpdates.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String sortString = "";

				// set other checkboxes not selected
				checkBoxBuys.setSelected(false);

				if (checkBoxUpdates.isSelected()) {
					sortString = "GÜNCELLEME";
				}

				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(logsModel);
				logsTable.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(sortString));

			}
		});

		btnOpenSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sellProductPanel.setVisible(!sellProductPanel.isVisible());
			}
		});

		btnSellProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int totalPrice = Integer.valueOf(tbxSellAmount.getText()) * Integer.valueOf(tbxSellPrice.getText());
				SalesManager salesManager = new SalesManager(new SalesDal());
				salesManager.add(new Sales(lblSellProductName.getText(), Integer.valueOf(lblSellProductCode.getText()),
						Integer.valueOf(tbxSellAmount.getText()), Integer.valueOf(tbxSellPrice.getText()), totalPrice));

				fulfillSalesTable();

				// Loglama
				logToDataBase("SATIM", lblSellProductName.getText() + " ürününden " + tbxSellPrice.getText()
						+ " fiyatına " + tbxSellAmount.getText() + " adet satıldı");

				// Alınan ürün miktarını productslardan düşürme işlemi yapılacak

				// !

				// !

				// !

			}
		});

	}

	public void setBrandTextBarsNull() {
		tbxUpdateBrandCode.setText("");
		tbxUpdateBrandName.setText("");
		lblUpdateBrandId.setText("");
		tbxBrandName.setText("");
		tbxBrandCode.setText("");
	}

	public void setProductTextBarsNull() {
		tbxProductCode.setText("");
		tbxProductName.setText("");
		tbxProductPrice.setText("");
		tbxProductAmount.setText("");
		tbxUpdateProductAmount.setText("");
		tbxUpdateProductCode.setText("");
		tbxUpdateProductName.setText("");
		tbxUpdateProductPrice.setText("");
		lblUpdateProductId.setText("");

	}

	public void fulfillProductsTable() {
		productsModel = (DefaultTableModel) productsTable.getModel();
		productsModel.setRowCount(0);
		ProductManager productManager = new ProductManager(new ProductDal());

		for (Product product : productManager.getAll()) {
			Object[] row = { product.getProductId(), product.getProductCode(), product.getProductName(),
					product.getProductProvider(), product.getProductPrice(), product.getProductAmount(),
					(product.getProductAmount() * product.getProductPrice()) };
			productsModel.addRow(row);
		}

	}

	public void fulfillBrandBox(ArrayList<String> brandNames) {
		boxModel = (DefaultComboBoxModel<String>) cbxBrands.getModel();
		boxModel.removeAllElements();
		boxModel.addAll(brandNames);

		updateBoxModel = (DefaultComboBoxModel<String>) cbxUpdateProductProvider.getModel();
		updateBoxModel.removeAllElements();
		updateBoxModel.addAll(brandNames);
	}

	public void fulfillBrandsTable() {
		brandsModel = (DefaultTableModel) brandsTable.getModel();
		brandsModel.setRowCount(0);
		BrandManager brandManager = new BrandManager(new BrandDal());
		ArrayList<String> brandNames = new ArrayList<String>();

		for (Brand brand : brandManager.getAll()) {
			Object[] row = { brand.getId(), brand.getBrandCode(), brand.getName() };
			brandNames.add(brand.getName());
			brandsModel.addRow(row);
		}
		fulfillBrandBox(brandNames);

	}

	public void fulfillLogsTable() {
		logsModel = (DefaultTableModel) logsTable.getModel();
		logsModel.setRowCount(0);
		LogManager logManager = new LogManager(new LogDal());

		for (Log log : logManager.getAll()) {
			Object[] row = { log.getId(), log.getKind(), log.getContent(), log.getDate() };
			logsModel.addRow(row);
		}

	}

	public void fulfillBrandDebtTable() {
		brandDebtModel = (DefaultTableModel) brandDebtTable.getModel();
		brandDebtModel.setRowCount(0);
		BrandDebtManager brandDebtManager = new BrandDebtManager(new BrandDebtDal());

		for (BrandDebt brandDebt : brandDebtManager.getAll()) {
			Object[] row = { brandDebt.getBrandId(), brandDebt.getBrandCode(), brandDebt.getBrandName(),
					brandDebt.getBrandDebt() };

			brandDebtModel.addRow(row);
		}
	}

	public void fulfillSalesTable() {
		salesModel = (DefaultTableModel) salesTable.getModel();
		salesModel.setRowCount(0);
		SalesManager salesManager = new SalesManager(new SalesDal());

		for (Sales sales : salesManager.getAll()) {
			Object[] row = { sales.getId(), sales.getProductName(), sales.getProductCode(), sales.getProductAmount(),
					sales.getProductSinglePrice(), sales.getProductTotalGain() };

			salesModel.addRow(row);
		}

	}

	public void logToDataBase(String kind, String content) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dateNow = dtf.format(now);

		Log log = new Log();
		log.setId(1);
		log.setKind(kind);
		log.setContent(content);
		log.setDate(dateNow);

		LogManager logManager = new LogManager(new LogDal());
		logManager.add(log);
	}

	public void initializeTable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 969, 739);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 934, 678);
		contentPane.add(tabbedPane);

		productsPanel = new JPanel();
		tabbedPane.addTab("Ürünler", null, productsPanel, null);
		productsPanel.setLayout(null);

		productsScrollPane = new JScrollPane();
		productsScrollPane.setBounds(10, 11, 909, 279);
		productsPanel.add(productsScrollPane);

		productsTable = new JTable();

		productsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Ürün kodu", "Ürün Adı",
				"Ürün Sağlayıcısı", "Ürün Fiyatı", "Miktar", "Toplam Ücret" }) {
			public boolean isCellEditable(int row, int column) { // table not editable yapmak için
				return false;
			}
		});

		productsScrollPane.setViewportView(productsTable);

		tbxProductCode = new JTextField();
		tbxProductCode.setBounds(121, 323, 203, 20);
		productsPanel.add(tbxProductCode);
		tbxProductCode.setColumns(10);

		tbxProductName = new JTextField();
		tbxProductName.setBounds(121, 352, 203, 20);
		productsPanel.add(tbxProductName);
		tbxProductName.setColumns(10);

		tbxProductPrice = new JTextField();
		tbxProductPrice.setBounds(121, 414, 203, 20);
		productsPanel.add(tbxProductPrice);
		tbxProductPrice.setColumns(10);

		tbxProductAmount = new JTextField();
		tbxProductAmount.setBounds(121, 445, 203, 20);
		productsPanel.add(tbxProductAmount);
		tbxProductAmount.setColumns(10);

		JLabel lblProductProductId = new JLabel("Ürün Kodu");
		lblProductProductId.setBounds(20, 321, 91, 14);
		productsPanel.add(lblProductProductId);

		JLabel lblProductName = new JLabel("Ürün Adı");
		lblProductName.setBounds(20, 352, 91, 14);
		productsPanel.add(lblProductName);

		JLabel lblProductProvider = new JLabel("Ürün Sağlayıcısı");
		lblProductProvider.setBounds(20, 383, 91, 14);
		productsPanel.add(lblProductProvider);

		JLabel lblProductPrice = new JLabel("Ürün Fiyatı");
		lblProductPrice.setBounds(20, 414, 91, 14);
		productsPanel.add(lblProductPrice);

		JLabel lblProductAmount = new JLabel("Miktar");
		lblProductAmount.setBounds(20, 445, 91, 14);
		productsPanel.add(lblProductAmount);

		btnAddProduct.setBounds(131, 476, 181, 23);
		productsPanel.add(btnAddProduct);
		cbxBrands.setToolTipText("");

		cbxBrands.setBounds(121, 383, 203, 22);
		productsPanel.add(cbxBrands);

		JLabel lblPerKg = new JLabel("TL  / Kilo");
		lblPerKg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPerKg.setBounds(333, 417, 70, 14);
		productsPanel.add(lblPerKg);

		JLabel lblKilo = new JLabel("Kilo");
		lblKilo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKilo.setBounds(333, 448, 46, 14);
		productsPanel.add(lblKilo);

		tbxSearchProduct = new JTextField();
		tbxSearchProduct.setBounds(812, 301, 107, 20);
		productsPanel.add(tbxSearchProduct);
		tbxSearchProduct.setColumns(10);

		JLabel lblSearch = new JLabel("Arama :");
		lblSearch.setBounds(767, 304, 46, 14);
		productsPanel.add(lblSearch);
		panelUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelUpdate.setVisible(false);

		panelUpdate.setBounds(625, 360, 294, 279);
		productsPanel.add(panelUpdate);
		panelUpdate.setLayout(null);

		tbxUpdateProductCode = new JTextField();
		tbxUpdateProductCode.setBounds(84, 24, 200, 20);
		panelUpdate.add(tbxUpdateProductCode);
		tbxUpdateProductCode.setColumns(10);

		tbxUpdateProductName = new JTextField();
		tbxUpdateProductName.setBounds(84, 55, 200, 20);
		panelUpdate.add(tbxUpdateProductName);
		tbxUpdateProductName.setColumns(10);

		tbxUpdateProductPrice = new JTextField();
		tbxUpdateProductPrice.setBounds(84, 119, 200, 20);
		panelUpdate.add(tbxUpdateProductPrice);
		tbxUpdateProductPrice.setColumns(10);

		tbxUpdateProductAmount = new JTextField();
		tbxUpdateProductAmount.setBounds(84, 151, 200, 20);
		panelUpdate.add(tbxUpdateProductAmount);
		tbxUpdateProductAmount.setColumns(10);

		cbxUpdateProductProvider.setBounds(84, 86, 200, 22);
		panelUpdate.add(cbxUpdateProductProvider);

		JLabel lblUpdateProductCode = new JLabel("Ürün Kodu");
		lblUpdateProductCode.setBounds(10, 27, 64, 14);
		panelUpdate.add(lblUpdateProductCode);

		JLabel lblUpdateProductName = new JLabel("Ürün Adı");
		lblUpdateProductName.setBounds(10, 58, 64, 14);
		panelUpdate.add(lblUpdateProductName);

		JLabel lblUpdateProductProvider = new JLabel("Sağlayıcı");
		lblUpdateProductProvider.setBounds(10, 89, 64, 14);
		panelUpdate.add(lblUpdateProductProvider);

		JLabel lblUpdateProductPrice = new JLabel("Ürün Fiyatı");
		lblUpdateProductPrice.setBounds(10, 121, 64, 14);
		panelUpdate.add(lblUpdateProductPrice);

		JLabel lblUpdateProductAmount = new JLabel("Miktar");
		lblUpdateProductAmount.setBounds(10, 154, 64, 14);
		panelUpdate.add(lblUpdateProductAmount);

		btnUpdateProduct.setBounds(153, 182, 131, 23);
		panelUpdate.add(btnUpdateProduct);

		lblUpdateProductId = new JLabel("");
		lblUpdateProductId.setBounds(0, 0, 45, 14);
		panelUpdate.add(lblUpdateProductId);

		btnDeleteProduct.setFont(new Font("Arial", Font.BOLD, 12));
		btnDeleteProduct.setForeground(new Color(255, 255, 255));
		btnDeleteProduct.setBackground(new Color(165, 42, 42));
		btnDeleteProduct.setBounds(195, 229, 89, 23);
		panelUpdate.add(btnDeleteProduct);

		brandsPanel = new JPanel();
		tabbedPane.addTab("Markalar", null, brandsPanel, null);
		brandsPanel.setLayout(null);

		brandsScrollPane = new JScrollPane();
		brandsScrollPane.setBounds(10, 11, 909, 278);
		brandsPanel.add(brandsScrollPane);

		brandsTable = new JTable();
		brandsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Kod", "İsim" }) {
			public boolean isCellEditable(int row, int column) { // table not editable yapmak için
				return false;
			}
		});
		brandsScrollPane.setViewportView(brandsTable);

		tbxBrandCode = new JTextField();
		tbxBrandCode.setBounds(105, 327, 86, 20);
		brandsPanel.add(tbxBrandCode);
		tbxBrandCode.setColumns(10);

		tbxBrandName = new JTextField();
		tbxBrandName.setBounds(105, 358, 86, 20);
		brandsPanel.add(tbxBrandName);
		tbxBrandName.setColumns(10);

		JLabel lblBrandId = new JLabel("Kod");
		lblBrandId.setBounds(24, 330, 71, 14);
		brandsPanel.add(lblBrandId);

		JLabel lblBrandName = new JLabel("İsim");
		lblBrandName.setBounds(24, 361, 71, 14);
		brandsPanel.add(lblBrandName);
		brandsPanel.add(btnAddBrand);

		JLabel lblSearchBrand = new JLabel("Arama :");
		lblSearchBrand.setBounds(785, 311, 46, 14);
		brandsPanel.add(lblSearchBrand);

		tbxSearchBrand = new JTextField();
		tbxSearchBrand.setBounds(833, 308, 86, 20);
		brandsPanel.add(tbxSearchBrand);
		tbxSearchBrand.setColumns(10);

		btnShowUpdateBrandPanel.setBounds(785, 357, 123, 23);
		brandsPanel.add(btnShowUpdateBrandPanel);

		updateBrandPanel = new JPanel();
		updateBrandPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		updateBrandPanel.setBounds(679, 389, 240, 220);
		brandsPanel.add(updateBrandPanel);
		updateBrandPanel.setLayout(null);

		tbxUpdateBrandCode = new JTextField();
		tbxUpdateBrandCode.setBounds(83, 25, 128, 20);
		updateBrandPanel.add(tbxUpdateBrandCode);
		tbxUpdateBrandCode.setColumns(10);

		tbxUpdateBrandName = new JTextField();
		tbxUpdateBrandName.setBounds(83, 63, 128, 20);
		updateBrandPanel.add(tbxUpdateBrandName);
		tbxUpdateBrandName.setColumns(10);

		JLabel lblUpdateBrandCode = new JLabel("Kod :");
		lblUpdateBrandCode.setBounds(10, 28, 46, 14);
		updateBrandPanel.add(lblUpdateBrandCode);

		JLabel lblUpdateBrandName = new JLabel("İsim :");
		lblUpdateBrandName.setBounds(10, 66, 46, 14);
		updateBrandPanel.add(lblUpdateBrandName);

		btnUpdateBrand.setBounds(20, 98, 180, 23);
		updateBrandPanel.add(btnUpdateBrand);

		lblUpdateBrandId = new JLabel("");
		lblUpdateBrandId.setBounds(0, 0, 46, 14);
		updateBrandPanel.add(lblUpdateBrandId);

		btnDeleteBrand.setForeground(new Color(255, 255, 255));
		btnDeleteBrand.setBackground(new Color(128, 0, 0));
		btnDeleteBrand.setBounds(141, 186, 89, 23);
		updateBrandPanel.add(btnDeleteBrand);
		updateBrandPanel.setVisible(false);

		JPanel debtPanel = new JPanel();
		tabbedPane.addTab("Borç", null, debtPanel, null);
		debtPanel.setLayout(null);

		JScrollPane debtScrollPane = new JScrollPane();
		debtScrollPane.setBounds(10, 11, 909, 269);
		debtPanel.add(debtScrollPane);

		brandDebtTable = new JTable();
		brandDebtTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "id", "Kod", "İsim", "Borç" }) {
			public boolean isCellEditable(int row, int column) { // table not editable yapmak için
				return false;
			}
		});
		debtScrollPane.setViewportView(brandDebtTable);

		JLabel lblSearchDebt = new JLabel("Arama :");
		lblSearchDebt.setBounds(785, 294, 46, 14);
		debtPanel.add(lblSearchDebt);

		tbxSearchDebt = new JTextField();

		tbxSearchDebt.setColumns(10);
		tbxSearchDebt.setBounds(833, 291, 86, 20);
		debtPanel.add(tbxSearchDebt);

		tabbedPane.addTab("Kayıtlar", null, logPanel, null);
		logPanel.setLayout(null);

		JScrollPane logsScrollPane = new JScrollPane();
		logsScrollPane.setBounds(10, 11, 909, 306);
		logPanel.add(logsScrollPane);

		logsTable = new JTable();
		logsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "id", "Tür", "İçerik", "Tarih" }) {
			public boolean isCellEditable(int row, int column) { // table not editable yapmak için
				return false;
			}
		});
		logsScrollPane.setViewportView(logsTable);

		checkBoxBuys = new JCheckBox("Satın alımlar");
		checkBoxBuys.setBounds(783, 324, 136, 23);
		logPanel.add(checkBoxBuys);

		checkBoxUpdates = new JCheckBox("Güncellemeler");
		checkBoxUpdates.setBounds(783, 350, 136, 23);
		logPanel.add(checkBoxUpdates);

		btnUpdatePanel.setBounds(777, 334, 139, 23);
		productsPanel.add(btnUpdatePanel);

		sellProductPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sellProductPanel.setBounds(10, 542, 428, 97);
		productsPanel.add(sellProductPanel);
		sellProductPanel.setLayout(null);

		JLabel lblSellProduct = new JLabel("ÜRÜN SATIŞ");
		lblSellProduct.setForeground(new Color(0, 128, 128));
		lblSellProduct.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSellProduct.setBounds(348, 11, 70, 14);
		sellProductPanel.add(lblSellProduct);

		lblSellProductId = new JLabel("");
		lblSellProductId.setBounds(90, 11, 124, 14);
		sellProductPanel.add(lblSellProductId);

		tbxSellAmount = new JTextField();
		tbxSellAmount.setBounds(68, 66, 112, 20);
		sellProductPanel.add(tbxSellAmount);
		tbxSellAmount.setColumns(10);

		tbxSellPrice = new JTextField();
		tbxSellPrice.setBounds(239, 66, 86, 20);
		sellProductPanel.add(tbxSellPrice);
		tbxSellPrice.setColumns(10);

		lblSellProductAmount.setBounds(10, 69, 46, 14);
		sellProductPanel.add(lblSellProductAmount);

		lblSellProductPrice.setBounds(190, 69, 46, 14);
		sellProductPanel.add(lblSellProductPrice);

		lblSellProductCode.setBounds(90, 28, 124, 14);
		sellProductPanel.add(lblSellProductCode);

		lblSellProductName.setBounds(90, 44, 124, 14);
		sellProductPanel.add(lblSellProductName);

		btnSellProduct.setForeground(new Color(255, 255, 255));
		btnSellProduct.setBackground(new Color(0, 128, 128));
		btnSellProduct.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnSellProduct.setBounds(338, 65, 79, 23);
		sellProductPanel.add(btnSellProduct);

		JLabel lblSellProductNone1 = new JLabel("Ürün Id :");
		lblSellProductNone1.setBounds(10, 11, 70, 14);
		sellProductPanel.add(lblSellProductNone1);

		JLabel lblSellProductNone2 = new JLabel("Ürün Kodu :");
		lblSellProductNone2.setBounds(10, 28, 70, 14);
		sellProductPanel.add(lblSellProductNone2);

		JLabel lblSellProductNone3 = new JLabel("Ürün Adı :");
		lblSellProductNone3.setBounds(10, 44, 70, 14);
		sellProductPanel.add(lblSellProductNone3);
		sellProductPanel.setVisible(false);

		btnOpenSell.setBounds(10, 518, 89, 23);
		productsPanel.add(btnOpenSell);

		JPanel salesPanel = new JPanel();
		tabbedPane.addTab("Sales", null, salesPanel, null);
		salesPanel.setLayout(null);

		JScrollPane salesScrollPane = new JScrollPane();
		salesScrollPane.setBounds(10, 11, 909, 359);
		salesPanel.add(salesScrollPane);

		salesTable = new JTable();
		salesTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "id", "productName", "productCode",
				"productAmount", "productSinglePrice", "productTotalGain" }));
		salesScrollPane.setViewportView(salesTable);

		logsTable.getTableHeader().setReorderingAllowed(false);
		brandsTable.getTableHeader().setReorderingAllowed(false);
		productsTable.getTableHeader().setReorderingAllowed(false);
		brandDebtTable.getTableHeader().setReorderingAllowed(false);
		salesTable.getTableHeader().setReorderingAllowed(false);

	}
}
