package Avira_VendingMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;

import javax.swing.*;

public class GUI {

	private JFrame frame;
	private JPanel gridPanel;
	private JButton[] Products;
	private JButton settings;
	private JLabel label1;
	private ImageIcon[] icons;
	private String[] strings= {"Avira Prime - 75$","Antivirus PRO - 35$","Phantom VPN- 50$","Password Manager - 20$","Optimizer - 10$","System Speedup - 25$"};
	private int[] prices = {75,35,50,20,10,25};
	private static int max = 10;
	private AviraPrime[] list;
	
	public static void main(String[] args) {
		GUI window = new GUI();
		window.frame.setVisible(true);
	}
	
	public GUI() {
		
		list = new AviraPrime[6];
		list[0] = new AviraPrime(0);
		list[1] = new AntivirusPro(0);
		list[2] = new PhantomVPN(0);
		list[3] = new PasswordManager(0);
		list[4] = new Optimizer(0);
		list[5] = new SystemSpeedup(0);
		
		initialize();
		Listeners();
	}
	
	public void initialize() {
		icons = new ImageIcon[6];
		for(int i=0;i<6;i++) {
			icons[i]= new ImageIcon("resources/img"+i+".png");
		}
		
		frame = new JFrame("Avira Vending Machine");
		frame.setBounds(1000, 150, 500, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel(null);
		gridPanel = new JPanel(new GridLayout(3,2));
		gridPanel.setBounds(0,200,500,580);
		
		Products = new JButton[6];
		for(int i=0;i<6;i++) {
			Products[i] = new JButton("",icons[i]);
			Products[i].setHorizontalTextPosition(SwingConstants.RIGHT);
			Products[i].setText(strings[i]);
			Products[i].setForeground(Color.BLACK);
			Products[i].setBackground(Color.WHITE);
			Products[i].setFont(new Font("Times New Roman",Font.BOLD,15));
			gridPanel.add(Products[i]);
		}
		contentPane.add(gridPanel);
		
		label1 = new JLabel("Alegeti produsul dorit:",SwingConstants.CENTER);
		label1.setFont(new Font("Times New Roman",Font.BOLD,40));
		label1.setBounds(0,50, 500, 50);
		contentPane.add(label1);		
		
		settings = new JButton("S");
		settings.setBounds(0, 0, 0,0);
		settings.setMnemonic(KeyEvent.VK_F1);
		contentPane.add(settings);
		
		frame.add(contentPane);
	}
	
	public void showStock() {
		for(int i=0;i<6;i++)
			list[i].show();
		 System.out.print("\n");
	}
	
	public void Listeners() {

		for(int i=0;i<6;i++) {
			int j=i;
			Products[i].addActionListener(new ActionListener() {

				String[] options= {"Cash","Card Bancar","Anulare"};
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(list[j].getQuantity()>0) {
					int n = JOptionPane.showOptionDialog(frame, "Alegeti modalitatea de plata:\nSuma de platit: "+prices[j]+"$","Plata",
							JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
							null,options,options[2]);
					if(n==0) {
						int s=0;
						while(s<prices[j]) {
							s+=Integer.parseInt(JOptionPane.showInputDialog(frame,
									"Introduceti bancnote (1$,5$,10$,20$,50$):\nSuma introdusa: "+s+"$ din "+prices[j]+"$","Plata",1));
						}
						JOptionPane.showMessageDialog(frame, "Tranzactie reusita !\nRest: "+(s-prices[j])+"$\nColectati produsul !");
						list[j].takeOne();
						showStock();
					}
					if(n==1) {
						JPanel panel = new JPanel();
						JLabel label = new JLabel("Introduceti codul PIN:");
						JPasswordField pin = new JPasswordField(10);
						panel.add(label);
						panel.add(pin);
						String[] options = new String[]{"OK", "Anulare"};
						int option = JOptionPane.showOptionDialog(null, panel, "Tranzactie Bancara",
						                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						                         null, options, options[0]);
						if(option == 0)
						{
							char[] a = pin.getPassword();
							System.out.print(a);
							System.out.print("\n");
						    	JOptionPane.showMessageDialog(frame, "Tranzactie reusita !\nColectati produsul !");
						    	list[j].takeOne();
						    	showStock();
						}
					}
					}
					else
						JOptionPane.showMessageDialog(frame, "Produsul selectat nu mai este in stock !\nVa rugam alegeti alt produs");
				}
			}
				
			);
		}
		settings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] options= {"Adaugare Produs","Umplere stock","Anulare"};
				int n = JOptionPane.showOptionDialog(frame, "Setari", "Settings", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[2]);
				if(n==0) {
					String[] possibilities = {"Avira Prime","Antivirus PRO","Phantom VPN","Password Manager","Optimizer","System Speedup"};
					String s= (String)JOptionPane.showInputDialog(frame, "Alegeti produsul", "Settings", JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
					int ammount = Integer.parseInt(JOptionPane.showInputDialog(frame,
							"Introduceti numarul de produse adaugate:","Settings",1));
					switch(s) {
					case "Avira Prime": list[0].add(ammount); break;
					case "Antivirus PRO": list[1].add(ammount); break;
					case "Phantom VPN": list[2].add(ammount); break;
					case "Password Manager": list[3].add(ammount); break;
					case "Optimizer": list[4].add(ammount); break;
					case "System Speedup": list[5].add(ammount); break;
					}
					showStock();
					
				}
				if(n==1) {
					for(int i=0;i<6;i++) {
						list[i].add(max);
					}
					showStock();
				}
			}
		});
		
	}
	
}
