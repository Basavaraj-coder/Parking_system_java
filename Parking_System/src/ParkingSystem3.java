import java.sql.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ParkingSystem3 {

	private JFrame frame;
	private JTextField txtuser;
	private JPasswordField txtpass;
	private Connection connection=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParkingSystem3 window = new ParkingSystem3();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public ParkingSystem3() {
		initialize();
		connection=sqliteconnection.dbconnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ParkingSystem3.class.getResource("/images/login img.png")));
		frame.getContentPane().setBackground(new Color(0, 102, 204));
		frame.setFont(new Font("Arial", Font.BOLD, 12));
		frame.setTitle("Parking System");
		frame.setBounds(100, 100, 796, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setForeground(new Color(255, 99, 71));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(21, 81, 119, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBackground(new Color(105, 105, 105));
		lblNewLabel_1.setForeground(new Color(255, 99, 71));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(21, 194, 146, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtuser = new JTextField();
		txtuser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtuser.setBounds(20, 123, 206, 31);
		frame.getContentPane().add(txtuser);
		txtuser.setColumns(9);
		
		txtpass = new JPasswordField();
		txtpass.setToolTipText("*");
		txtpass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpass.setColumns(9);
		txtpass.setBounds(20, 236, 206, 31);
		frame.getContentPane().add(txtpass);
		
		JButton btnlogin = new JButton("Login");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent argO) {
				//JOptionPane.showMessageDialog(null,"HELLO");
				try {
					String query="SELECT * FROM admin where username = ? and password = ?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, txtuser.getText());
					pst.setString(2, txtpass.getText());
					ResultSet rs=pst.executeQuery();
					int count=0;
					while(rs.next())
					{
						count ++;
					}
					if(count==1)
					{
						frame.dispose();
						EntryInfo ei=new EntryInfo();
						ei.setVisible(true);
						ei.setLocationRelativeTo(null);
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Sorry Wrong Credentials Try again...");
						txtuser.setText("");
						txtpass.setText("");
					}
					rs.close();
					pst.close();
				}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
			}
		});
		btnlogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnlogin.setForeground(new Color(255, 99, 71));
		btnlogin.setBackground(new Color(0, 0, 139));
		btnlogin.setBounds(47, 310, 141, 43);
		frame.getContentPane().add(btnlogin);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ParkingSystem3.class.getResource("/images/carblue.jpg")));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(236, 11, 546, 465);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
