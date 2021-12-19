import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class javaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtid;
	
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javaCrud window = new javaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public javaCrud() {
		initialize();
		Connect();
		
		
		table_load();
	}
	
	Connection connect;
	PreparedStatement st;
	ResultSet rs;
	
	
	
	public void Connect() {
		
		String url = "jdbc:mysql://localhost:3306/javaCrud";
		String username = "root";
		String password = "root";
		
		try {
//  		Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url,username,password);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void table_load() {
		try {
			
			st = connect.prepareStatement("select * from good");
			rs = st.executeQuery();
			table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"ID", "NAME", "EDITION", "PRICE"
					}
				));
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 255, 255));
		frame.setBounds(100, 100, 765, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setForeground(new Color(0, 51, 0));
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 40));
		lblNewLabel.setBounds(290, 25, 210, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 51, 51)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(26, 85, 341, 204);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setBounds(23, 66, 113, 32);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Edition");
		lblNewLabel_2.setBounds(23, 113, 76, 25);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Price");
		lblNewLabel_3.setBounds(23, 156, 57, 25);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel_3);
		
		txtbname = new JTextField();
		txtbname.setBounds(172, 67, 138, 32);
		txtbname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtbname.setForeground(Color.BLACK);
		txtbname.setBackground(Color.LIGHT_GRAY);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setBounds(172, 110, 138, 32);
		txtedition.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtedition.setForeground(Color.BLACK);
		txtedition.setBackground(Color.LIGHT_GRAY);
		txtedition.setColumns(10);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setBounds(172, 153, 138, 32);
		txtprice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtprice.setForeground(Color.BLACK);
		txtprice.setBackground(Color.LIGHT_GRAY);
		txtprice.setColumns(10);
		panel.add(txtprice);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(23, 23, 113, 32);
		panel.add(lblNewLabel_1_1);
		
		txtid = new JTextField();
		txtid.setForeground(Color.BLACK);
		txtid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtid.setColumns(10);
		txtid.setBackground(Color.LIGHT_GRAY);
		txtid.setBounds(172, 24, 138, 32);
		panel.add(txtid);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price,bid;
				bid = txtid.getText();
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				try {
					st = connect.prepareStatement("insert into good values(?,?,?,?)");
					st.setString(1,bid);
					st.setString(2,bname);
					st.setString(3,edition);
					st.setString(4,price);
					
					st.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					st.close();
					connect.close();
					txtid.setText("");
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtid.requestFocus();
					
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null,"Invalid Entry");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(26, 306, 101, 49);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("CLEAR");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtid.setText("");
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnExit.setBounds(249, 306, 118, 49);
		frame.getContentPane().add(btnExit);
		
		JButton btnExit_1 = new JButton("EXIT");
		btnExit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnExit_1.setBounds(137, 306, 102, 49);
		frame.getContentPane().add(btnExit_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(396, 85, 329, 271);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(27, 366, 340, 66);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("BOOK ID");
		lblNewLabel_2_1.setBounds(26, 23, 93, 25);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblNewLabel_2_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = txtbid.getText();
					st = connect.prepareStatement("select name,edition,price from good where id=?");
					st.setString(1, id);
					txtid.setText(id);
					
					rs = st.executeQuery();
					
					if(rs.next()==true) {
//						
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
//						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					}
					else {
						txtid.setText("");
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				}
				catch(SQLException e1) {
					
				}
			}
		});
		txtbid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtbid.setBounds(154, 14, 161, 41);
		txtbid.setForeground(Color.BLACK);
		txtbid.setColumns(10);
		txtbid.setBackground(Color.LIGHT_GRAY);
		panel_1.add(txtbid);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
				bid = txtid.getText();
				
				
				try {
					st = connect.prepareStatement("delete from good where id=?");
					st.setString(1,bid);
					
					
					st.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
					st.close();
					connect.close();
					txtid.setText("");
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtid.requestFocus();
					
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null,"Invalid Entry");
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDelete.setBounds(576, 383, 118, 49);
		frame.getContentPane().add(btnDelete);
		
		JButton btnExit_1_1 = new JButton("UPDATE");
		btnExit_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price,bid;
				bid = txtid.getText();
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				try {
					st = connect.prepareStatement("update good set name=?,edition=?,price=?where id=?");
					st.setString(1,bname);
					st.setString(2,edition);
					st.setString(3,price);
					st.setString(4,bid);
					
					st.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
					st.close();
					connect.close();
					txtid.setText("");
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtid.requestFocus();
					
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null,"Invalid Entry");
				}
			}
		});
		btnExit_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnExit_1_1.setBounds(434, 383, 118, 49);
		frame.getContentPane().add(btnExit_1_1);
	}
}
