import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//implement has table for indexing of types data,image,scripting and others
public class webserver extends Thread {
	public boolean runing;
	/**
	 * @param args
	 */
	public void run() {
		ServerSocket ss;
		// TODO Auto-generated method stub
		try {
			ss = new ServerSocket(8008);
			System.out.println("Server Started");
			System.out.println(runing);
			while (runing) 
			{
				Thread t = new mainthread(ss.accept());
				t.start();
			}
			System.out.println("Moved  out of loop");
			System.out.println(runing);			
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public boolean isRuning() {
		return runing;
	}
	public void setRuning(boolean runing) {
		this.runing = runing;
	}
}
/*public class webserver {

	
	 * @param args
	 *//*
	public static void main(String[] args) {
		ServerSocket ss;
		// TODO Auto-generated method stub
		try {
			ss = new ServerSocket(80);
			while (true) {
				Thread t = new mainthread(ss.accept());
				t.start();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
*/class mainthread extends Thread implements HTTP_STATUS
{
	Socket sc;
	httpdata h = new httpdata();
	parsejsx pjx = new parsejsx();
	HTTP_FILE hf = new HTTP_FILE();
	DirectoryView d=new DirectoryView();
	byte[] EOL = {(byte)'\r',(byte)'\n'};

	/**
	 * @param sc
	 */
	public mainthread(Socket sc) 
	{
		this.sc = sc;
	}

	public void run() 
	{
		BufferedReader br;
		PrintStream ps;
		String data = null;
		try {
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			ps = new PrintStream(sc.getOutputStream());

			while (true) 
			{
				data=br.readLine();
				if (data == null)
				{
					break;
				}
				if (data.compareTo("")==0)
				{
					break;
				}
				h.extract(data);
//				System.out.println(data);

				//			ps.println(data);
			}
			if (data != null) {
				System.out.println(h.getDataget());
				showpage(h.getUrl(), br, ps);
			}			
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
	void showpage(String url,BufferedReader b,PrintStream p)
	{
		File f;
		try {
			f = new File( "website/" + url);
			System.out.println(f.getAbsolutePath());
			String m = hf.defPageOrder(url);
			if (m.compareTo("NO") != 0)
			{
				url = m;
				h.setUrl(url);
			}
			if (h.getExt().compareTo("jsx")==0)
			{
				f = new File("website"+ url);
				printHeader(p, "text/html" , HTTP_OK, "OK");				
				pjx.parser(p, f);
			}
			else if (url.charAt(url.length()-1) == '/')
			{
				printHeader(p, "text/html" , HTTP_OK, "OK");
				d.Viewlist(p,url);				
			}
			else if (f.exists())
			{
				try {
					String type=hf.newfiletype(url);
					String[] help;
					if (type.compareTo("NO")!=0)
					{	
						help = type.split(":");
						printHeader(p, help[1], HTTP_OK, "OK");
						printimage(f,p);
					}
					else
					{
						printHeader(p, "text/html", HTTP_OK, "OK");
						p.println("Sorry the file type is not supported yet");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else
			{
				p.print("HTTP/1.0 " + HTTP_NOTFOUND +  " File Not Found ");
				p.flush();
				p.write(EOL);
				p.flush();
				p.write(EOL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * @param p
	 * @throws IOException
	 */
	private void printHeader(PrintStream p, String fileType, int stat, String statMsg) throws IOException {
		p.print("HTTP/1.0 " + stat +  " " + statMsg + " ");
		p.flush();
		p.print("Content-type: " + fileType);
		p.write(EOL);
		p.flush();
		p.write(EOL);
		p.flush();
	}

	private void printimage(File f,PrintStream p)
	{
		//	byte[] tmp=new byte[1000];
		int t;
		try {
			BufferedInputStream bos = new BufferedInputStream(new FileInputStream(f));
			while (true) {
				t=bos.read();
				if (t == -1)
					break;
				p.write(t);
				p.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param p
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
}