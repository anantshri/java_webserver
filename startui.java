import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.plaf.PanelUI;


public class startui extends JPanel implements ActionListener {
//	Thread t;
	webserver t;
	JButton b1,b2,b3,b4;
	JLabel l1,l2,l3;
	JList li1,li2;
	public startui() {
		b1 = new JButton("Start");
		b2 = new JButton("Stop");
		b3 = new JButton("Setting");
		b4 = new JButton("Info");
		b1.setMnemonic('S');
		b2.setMnemonic('T');
		b3.setMnemonic('E');
		b4.setMnemonic('I');
		b1.setVisible(true);
		b2.setVisible(false);
		b1.setBounds(10, 10, 100, 25);
		b2.setBounds(10, 10, 100, 25);
		b3.setBounds(10, 45, 100, 25);
		b4.setBounds(10, 75, 100, 25);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b1.setToolTipText("Starts the server");
		b2.setToolTipText("Stops the server");
		b3.setToolTipText("Configure the server");
		b4.setToolTipText("Version Information");
		b4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLayout(null);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
	}
	public static void main(String args[])
	{
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(new startui());
		jf.setTitle("Webserver CP");
		jf.setSize(120, 150);
		jf.setVisible(true);

	}


	public void actionPerformed(ActionEvent ae) {
		String act = ae.getActionCommand();
		this.repaint();
		if (act.compareTo("Start") == 0)
		{
			b2.setBackground(Color.red);
			b1.setVisible(false);
			b2.setVisible(true);
			b1.setDisplayedMnemonicIndex(0);
			t = new webserver();
			if (t.isRuning()) {
				t.setRuning(false);
			}	
			t.setRuning(true);
			t.setDaemon(true);
			t.start();
			System.out.println("Starting up the server");
			this.repaint();
		}
		if (act.compareTo("Stop") == 0)
		{
			b1.setVisible(true);
			b2.setVisible(false);
			b2.setBackground(Color.LIGHT_GRAY);
			b1.setBackground(Color.LIGHT_GRAY);
			t.setRuning(false);
		//	t.destroy();
//			t.interrupt();
			try {
				Socket s = new Socket("localhost",80);
				s.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.println("Server stopped");
			this.repaint();
		}
		if (act.compareTo("Setting") == 0)
		{
			this.repaint();	
		}
		if (act.compareTo("Info") == 0 )
		{
			System.out.println("Reached the points");
			JFrame j = new JFrame(); 
			j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			j.setContentPane(new info());
			j.setTitle("WebServer");
			j.setSize(200,200);
			j.setVisible(true);
			this.repaint();
		}
		this.repaint();
	}
}
class setting extends JPanel
{
	JButton b1;
	
}

class info extends JPanel
{
	JLabel l1;
	public info()
	{
		File f;
		f = new File("version.info");
		if (f.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				int i=0;
				while (true) {
					String txt = br.readLine();
					if( txt == null)
					{
						break;
					}
					l1 = new JLabel();
					l1.setText(txt);
					l1.setBounds(10, i + 10, 200, 20);
					setLayout(null);
					add(l1);
					i = i+20;
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}