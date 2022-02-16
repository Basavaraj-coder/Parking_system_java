import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*; //database
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dialog.ModalExclusionType;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
// sms Apis library
import java.io.*;
import java.net.*;
import java.awt.SystemColor;
public class EntryInfo extends JFrame{

	//JProgressBar p=new JProgressBar(0,20);
	//private Jframe frame;
	private Connection connection=null;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private JPanel panel_1;
	private JPanel panel_3;
	private JTextField txtpn;
	private JTextField txtvn;
	private JComboBox cbrate;
	private int num;
	private String str;
	private Date d1,d2,d3,d4;
	private long diff;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntryInfo frame = new EntryInfo();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public EntryInfo() {
		connection=sqliteconnection.dbconnector();	
		GUI();
		jtable();
		}
	public void switchScreen(JPanel p)
	{
		layeredPane.removeAll();
		layeredPane.add(p);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	public void GUI()
	{
		setTitle("Input");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1255, 688);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBounds(0, 0, 201, 657);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnenterinfo = new JButton("Entry");
		btnenterinfo.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnenterinfo.setForeground(new Color(220, 20, 60));
		btnenterinfo.setBackground(SystemColor.controlHighlight);
		btnenterinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchScreen(panel_1);
			}
		});
		btnenterinfo.setBounds(10, 11, 181, 82);
		panel.add(btnenterinfo);
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchScreen(panel_3);
			}
		});
		btnAbout.setForeground(new Color(220, 20, 60));
		btnAbout.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnAbout.setBackground(SystemColor.controlHighlight);
		btnAbout.setBounds(10, 509, 181, 99);
		panel.add(btnAbout);
		
		 layeredPane = new JLayeredPane();
		layeredPane.setBounds(200, 0, 1041, 657);
		contentPane.add(layeredPane);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(0, 0, 1041, 646);
	    layeredPane.add(scrollPane);
		
	    panel_1 = new JPanel();
	    scrollPane.setViewportView(panel_1);
	    panel_1.setBackground(new Color(0, 102, 204));
		panel_1.setLayout(null);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setForeground(SystemColor.text);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhoneNumber.setBounds(384, 21, 138, 30);
		panel_1.add(lblPhoneNumber);
		
		txtpn = new JTextField();
		txtpn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				TableRowSorter<DefaultTableModel>tr=new TableRowSorter<DefaultTableModel>(model);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(txtpn.getText().trim()));
			}
		});
		txtpn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtpn.setColumns(10);
		txtpn.setBounds(526, 23, 161, 30);
		panel_1.add(txtpn);
		
		txtvn = new JTextField();
		txtvn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				TableRowSorter<DefaultTableModel>tr=new TableRowSorter<DefaultTableModel>(model);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(txtvn.getText().trim()));
			}
		});
		txtvn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtvn.setColumns(10);
		txtvn.setBounds(173, 23, 161, 30);
		panel_1.add(txtvn);
		
		JLabel lblVechicleNumber = new JLabel("Vechicle Number");
		lblVechicleNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblVechicleNumber.setForeground(SystemColor.text);
		lblVechicleNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVechicleNumber.setBounds(0, 20, 161, 32);
		panel_1.add(lblVechicleNumber);
		
		
		JButton btnparkin = new JButton("Park In");
		btnparkin.setForeground(new Color(0, 0, 0));
		btnparkin.setBackground(SystemColor.controlHighlight);
		btnparkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int num = Integer.parseInt((String) comboBox.getSelectedItem());
				String mob=txtpn.getText(); 
				String str;                     //regex for phone number
				int count=0,error=0,error2=0;
				Pattern p=Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
				Matcher m=p.matcher(mob);
				if(m.matches())                                                   
				{
					//JOptionPane.showMessageDialog(null, "correct Mobile Number");
					error=error+1;
				}
				else
				{
					txtpn.setBackground(Color.red);
					JOptionPane.showMessageDialog(null, "Incorrect Mobile Number");
					count=0;
					txtpn.setText("");
					txtpn.setBackground(Color.white);
				}
				//JOptionPane.showMessageDialog(null, "proper plate number");
				String vn=txtvn.getText();                            // regex for vechicle number
				if(vn.matches("^[a-z]{2}\\s[0-9]{2}\\s[a-z]{2}\\s[0-9]{4}$") && (vn.length()==13))
				{
					//assert true;
					//JOptionPane.showMessageDialog(null, "proper plate number");
					error=error+1;
				}
				else
				{
					txtvn.setBackground(Color.RED);
					JOptionPane.showMessageDialog(null, "wrong fromat of Vechicle number.. try again");
					count=0;
					txtvn.setText("");
					txtvn.setBackground(Color.white);
				}
				//JOptionPane.showMessageDialog(null, "ready to insert");
				if(error==2) {
					//JOptionPane.showMessageDialog(null, "proper plate number");
					String strquery="SELECT * FROM vehicle WHERE bike_number2=? ";
					PreparedStatement pst = null;
					try {
						str=date();
						pst = connection.prepareStatement(strquery);
						pst.setString(1, txtvn.getText());
						ResultSet rs=pst.executeQuery();
						if(rs.next())
						{
							JOptionPane.showMessageDialog(null, "Saved");
							String query3="UPDATE vehicle set timeIn=? WHERE bike_number2='"+txtvn.getText()+"' ";
							try {
								PreparedStatement pst3=connection.prepareStatement(query3);                                   //  UPDATE customer set phone_number=? WHERE bike_number='"+txtvn.getText()+"'
								pst3.setString(1,str.toString());
								pst3.execute();
								pst3.close();
								jtable();
							}
							catch(SQLException e3 )
							{
								e3.printStackTrace();
							}
							update();
							//JOptionPane.showMessageDialog(null, "before sms send in update");
							//sendSms();
						}
						else {
							str=date();
								try {
							//JOptionPane.showMessageDialog(null,"begin insert");																								//inserting data into DB
									String query1="insert into vehicle (bike_number2,Charges,timeIn) values(?,?,?)";
									PreparedStatement ps=connection.prepareStatement(query1);
									ps.setString(1, txtvn.getText());
									ps.setString(2, cbrate.getSelectedItem().toString());				 	
									ps.setString(3, str);
									ps.execute();
									jtable();
							//JOptionPane.showMessageDialog(null,"insert into vehicle");			
									ps.close();
								}
								catch(Exception Ae)
								{
									JOptionPane.showMessageDialog(null,Ae);
								}
								try {          
						
								String query2="insert into customer (phone_number,bike_number) values(?,?)";
								PreparedStatement ps2=connection.prepareStatement(query2);
								ps2.setString(1, mob);
								ps2.setString(2, txtvn.getText().toString());
					 			ps2.execute();
					 			jtable();
					 			ps2.close();
					 			//JOptionPane.showMessageDialog(null, "before sms send in insert");
					 			//sendSms();
					 			
					 			JOptionPane.showMessageDialog(null, "Details Saved..");
								}catch(Exception ae)
								{
									JOptionPane.showMessageDialog(null,ae);
									clear();
								}
					//radio button fetching date time jrparkin; rdbtnParkOut
								clear();
					
								try {
									//sendSms(str);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					clear();
				}
				//pst.setString(2, txtpass.getText());
					//jtable();
				}// if close error==0
				else
				{
					JOptionPane.showMessageDialog(null, "Sorry please provide proper input");
				}
			}
		});
		btnparkin.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnparkin.setBounds(125, 151, 131, 38);
		panel_1.add(btnparkin);
		
		cbrate = new JComboBox();
		cbrate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String str=cbrate.getSelectedItem().toString();
				num=Integer.parseInt(str);
			}
		});
		cbrate.addItem("5");
		cbrate.addItem("6");
		cbrate.addItem("7");
		cbrate.addItem("8");
		cbrate.addItem("9");
		cbrate.addItem("10");
		
		cbrate.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cbrate.setBounds(895, 18, 72, 38);
		panel_1.add(cbrate);
		
		JLabel lblChargeAmount = new JLabel("Charge Amount");
		lblChargeAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblChargeAmount.setForeground(SystemColor.text);
		lblChargeAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChargeAmount.setBounds(731, 21, 154, 30);
		panel_1.add(lblChargeAmount);
		
		JButton btnparkout = new JButton("Park Out");
		btnparkout.setBackground(SystemColor.controlHighlight);
		btnparkout.setForeground(new Color(0, 0, 0));
		btnparkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long charge;
				String strperHr = null,strin,strdate,substr1 = null,substr2 = null,strdate2,strtimein=null,strtimeout=null;
				strdate=date();
				if(!(txtpn.getText().isBlank() && txtvn.getText().isBlank()))
				{
					JOptionPane.showMessageDialog(null,"before update timeout");
					String query="UPDATE vehicle SET timeOut=? WHERE bike_number2='"+txtvn.getText()+"' ";
					try {
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1,strdate.toString());
						ps.execute();
						ps.close();
						jtable();
					} catch (SQLException ez) {
					// TODO Auto-generated catch block
						ez.printStackTrace();
					}

					try
					{
						String query2="SELECT Charges, timeIn, timeOut FROM vehicle WHERE bike_number2= '"+txtvn.getText()+"' ";
						PreparedStatement ps2=connection.prepareStatement(query2);
						ResultSet rs2=ps2.executeQuery();
						strperHr=rs2.getString("Charges");
						strin=rs2.getString("timeIn");
						strdate2=rs2.getString("timeOut");
						substr1=strin.substring(6, 16);
						substr2=strdate2.substring(6, 16);
						strtimein=strin.substring(0, 5);
						strtimeout=strdate2.substring(0, 5);
						rs2.close();
						ps2.close();
					}
					catch(SQLException e2)
					{
						e2.printStackTrace();
					}
					//JOptionPane.showMessageDialog(null,substr1);
					//JOptionPane.showMessageDialog(null,substr2);
					//charge=calTimeDiff( substr1, substr2,strperHr);
					charge=calTimeDiff(substr1,substr2,strperHr,strtimein,strtimeout);
					//JOptionPane.showMessageDialog(null,charge);
					String query3="UPDATE vehicle SET Pay=? WHERE bike_number2='"+txtvn.getText()+"' ";
					try {
						PreparedStatement ps3=connection.prepareStatement(query3);
						ps3.setString(1,String.valueOf(charge));
						ps3.execute();
						ps3.close();
						jtable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					clear();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "sorry please select record which you wanted to parkout");
				}
				
			}
		});
		btnparkout.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnparkout.setBounds(304, 151, 138, 38);
		panel_1.add(btnparkout);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(new Color(0, 0, 0));
		btnDelete.setBackground(SystemColor.controlHighlight);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(txtvn.getText().isEmpty() && (txtpn.getText().isEmpty())))
				{
					String pswd=JOptionPane.showInputDialog("Re-Enter Password !!");
					String query="SELECT password FROM admin WHERE password=? ";
				try {
					PreparedStatement pst3=connection.prepareStatement(query);
					pst3.setString(1,pswd);
					ResultSet rs0=pst3.executeQuery();
					while(rs0.next())
					{
						int count=0;
						String q="DELETE FROM vehicle WHERE bike_number2='"+txtvn.getText()+"'  ";
						try {
							PreparedStatement pst=connection.prepareStatement(q);
							pst.execute();
							count+=1;
							pst.close();
						} catch (SQLException e1) {
						
							e1.printStackTrace();
						}
						if(count==1)
						{
							String q1="DELETE FROM customer WHERE bike_number='"+txtvn.getText()+"'  ";
							try {
								PreparedStatement pst=connection.prepareStatement(q1);
								pst.execute();
								JOptionPane.showMessageDialog(null,"Data Deleted");
								jtable();
								clear();
								pst.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
								}
						} // if closing
						}// while close
					}// try
					catch(SQLException e0)
					{
						e0.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Empty Fields !! Select record to Delete");
				}
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnDelete.setBounds(491, 151, 131, 38);
		panel_1.add(btnDelete);
		
		JButton btnPrintReport = new JButton("Print");
		btnPrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Report Sheet");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try {
					table.print(JTable.PrintMode.NORMAL, header, footer);
				}
				catch(Exception p)
				{
					JOptionPane.showMessageDialog(null, p);
				}
			}
		});
		btnPrintReport.setBounds(668, 151, 149, 38);
		panel_1.add(btnPrintReport);
		btnPrintReport.setForeground(new Color(0, 0, 0));
		btnPrintReport.setBackground(SystemColor.controlHighlight);
		btnPrintReport.setFont(new Font("Times New Roman", Font.BOLD, 24));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(212, 217, 491, 394);
		panel_1.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				int i=table.getSelectedRow();
				//TableModel model=table.getModel();
				txtpn.setText(model.getValueAt(i, 0).toString());
				txtvn.setText(model.getValueAt(i, 1).toString());
				
				String rate=model.getValueAt(i, 2).toString();
				for(int count=0;count< 	cbrate.getItemCount();count++)
				{
					if(cbrate.getItemAt(count).toString().equalsIgnoreCase(rate)){
						cbrate.setSelectedIndex(count);
					}
				}
			}
		});
		scrollPane_1.setViewportView(table);
		
		panel_3 = new JPanel();
		panel_3.setForeground(new Color(255, 51, 51));
		panel_3.setBackground(new Color(0, 0, 153));
		panel_3.setBounds(0, 0, 1041, 646);
		layeredPane.add(panel_3);
		
		JLabel aboutme = new JLabel("About Us :- DEVELOPED by Basavaraj Hiremath, EMAIl -ID : basavcoder@gmail.com");
		//aboutme.setHorizontalAlignment(SwingConstants.CENTER);
		aboutme.setVerticalAlignment(SwingConstants.CENTER);
		aboutme.setForeground(new Color(240, 248, 255));
		aboutme.setFont(new Font("Tahoma", Font.PLAIN, 25));
		aboutme.setBounds(500, 500, 150, 30);
		panel_3.add(aboutme);

	}
	public String date()
	{
		String str;
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("kk:mm");
		Date d2=new Date();
		SimpleDateFormat sdf2=new SimpleDateFormat("dd:MM:yyyy");
		str=sdf.format(d).toString()+"-"+sdf2.format(d2).toString();
		
		return str;	
	}
	public void jtable()
	{
		try {                                                                                              // SELECT a1, a2, b1, b2
			//FROM A
			//INNER JOIN B on B.f = A.f;
				String q="SELECT phone_number, bike_number, timeIn, timeOut, Charges,Pay FROM customer LEFT JOIN vehicle ON customer.bike_number=vehicle.bike_number2 ORDER BY timeIn";
				PreparedStatement ps3=connection.prepareStatement(q);
				ResultSet rs3=ps3.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs3));
				table.getColumnModel().getColumn(0).setPreferredWidth(95);
				table.getColumnModel().getColumn(0).setMinWidth(54);
				table.getColumnModel().getColumn(0).setMaxWidth(96);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				table.getColumnModel().getColumn(1).setMinWidth(85);
				table.getColumnModel().getColumn(1).setMaxWidth(100);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				table.getColumnModel().getColumn(2).setMinWidth(85);
				table.getColumnModel().getColumn(2).setMaxWidth(100);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				table.getColumnModel().getColumn(3).setMinWidth(85);
				table.getColumnModel().getColumn(3).setMaxWidth(100);
				table.getColumnModel().getColumn(4).setPreferredWidth(56);
				table.getColumnModel().getColumn(4).setMinWidth(51);
				table.getColumnModel().getColumn(4).setMaxWidth(56);
				table.getColumnModel().getColumn(5).setPreferredWidth(32);
				table.getColumnModel().getColumn(5).setMinWidth(31);
				table.getColumnModel().getColumn(5).setMaxWidth(32);
			}catch(Exception ae)
			{
				JOptionPane.showMessageDialog(null, ae);
			}
	}
	public long calTimeDiff(String substr1,String substr2,String strperHr,String strtimein,String strtimeout)
	{
		long num=Integer.parseInt(strperHr);
//		}
		//JOptionPane.showMessageDialog(null, "good4");
		DateTimeFormatter format= DateTimeFormatter.ofPattern("dd:MM:uuuu");
		//JOptionPane.showMessageDialog(null, "good5");
		LocalDate dateIn=LocalDate.parse(substr1,format);
		//JOptionPane.showMessageDialog(null, "good6");
		LocalDate dateOut=LocalDate.parse(substr2, format);
		//JOptionPane.showMessageDialog(null, "good7");
		long Gaps=ChronoUnit.DAYS.between(dateIn, dateOut);
		//JOptionPane.showMessageDialog(null, "good8");
		long num1=Math.abs(Gaps);		
		//JOptionPane.showMessageDialog(null, "good9");//Gaps*-1 return possitive integer
		if(num1 > 1)
		{
			//JOptionPane.showMessageDialog(null, "good1");
			return num*num1;
		}
		else
		{	
			//JOptionPane.showMessageDialog(null, "good2");
			return num;
		}
		//JOptionPane.showMessageDialog(null, "good3");
		//return num;
	}
	public void clear()
	{
		txtpn.setText("");
		txtvn.setText("");
		jtable();
	}
	public void update()
	{
		String str=date();
		if(!(txtvn.getText().isEmpty()))
		{
		String query="UPDATE customer set phone_number=? WHERE bike_number='"+txtvn.getText()+"' ";
		try {
			//JOptionPane.showMessageDialog(null, "3");
			PreparedStatement pst=connection.prepareStatement(query);                                   //  UPDATE customer set phone_number=? WHERE bike_number='"+txtvn.getText()+"'
			pst.setString(1,txtpn.getText());
			pst.execute();
			//JOptionPane.showMessageDialog(null, "4");
			String query2="UPDATE vehicle set Charges=? WHERE bike_number2='"+txtvn.getText()+"' ";
			try {
				PreparedStatement pst2=connection.prepareStatement(query2);
				//JOptionPane.showMessageDialog(null, "5");//  UPDATE customer set phone_number=? WHERE bike_number='"+txtvn.getText()+"'
				pst2.setString(1,cbrate.getSelectedItem().toString());
				pst2.execute();
				pst2.close();
			} catch (SQLException e1) {
		// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Updated Record successfully");
			jtable();
			pst.close();
		} catch (SQLException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			jtable();
			clear();
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Cannot Update Due To Empty Fields");
		}
	}// close fun
//	public void sendSms(String str) throws Exception
//	{
//		String message = "your vehicle is parked at "+str;      
//		String phone = "8956651900";
//		String username = "abcd";
//		String password = "1234";
//		String address = "http://192.168.1.101";
//		String port = "8090";
//
//		URL url = new URL(
//		        address+":"+port+"/SendSMS?username="+username+"&password="+password+
//		        "&phone="+phone+"&message="+URLEncoder.encode(message,"UTF-8"));
//
//		URLConnection connection = url.openConnection();
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//		String inputLine;
//		while((inputLine = bufferedReader.readLine()) !=null){
//		    System.out.println(inputLine);
//		}
//		bufferedReader.close();
//	}
}
