import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class parsejsx {
	File f;
	String[] loopData = new String[50];
	int loopcount;
	int loopline;
	/**
	 * @param f
	 */
	public parsejsx(File f) {
		this.f = f;
	}
	public parsejsx() {
	}
	public void parser(PrintStream p,File f)
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

			while (true) {
				String st;
				String data;
				try {
					st = br.readLine();
					if (st.equals(null))
					{
						break;
					}
					if (st.indexOf("<#info#>") >= 0)
					{
						System.out.println("owner info asked");
						data = "Webserver Created By <br>";
						data += "Anant Shrivastava<br>";
						data +="Vikas Singh<br>";
						System.out.println(st + data);
						st = st.replaceAll("<#info#>", data);
						System.out.println(st);
					}
					if(st.indexOf("<#dir_view#>") >= 0)
					{
						String[] dat = st.split("<#dir_view#>");
						DirectoryView d = new DirectoryView();
						System.out.println(f.getParent());
						System.out.flush();
						String y;
						if (f.getParent().indexOf("\\") >= 0) {
							y = f.getParent().substring(f.getParent().indexOf("\\"));
							System.out.println(y);
						}
						else
						{
							y = ".";
						}
						if (dat.length > 0)
							p.println(dat[0]);
						p.flush();
						d.Viewlist(p, y);
						p.flush();
						if (dat.length > 1)
							p.println(dat[1]);						
						p.flush();
					}
					if (st.indexOf("<#loop#>")>=0)
					{
						loopcount = Integer.parseInt(st.split("<#loop#>")[1]);
						int i = 0;
						while (true) {
							String x;
							x=br.readLine();
							if (x.indexOf("<#endloop#>") < 0)
							{
								loopData[i] = x;
								i++;	
							}
							else
							{
								for (int k = 0; k < loopcount; k++) {
									for (int j = 0; j < i; j++) {
										p.println(loopData[j]);
										p.flush();
									}
								}
								break;
							}
						}
					}
					else
					{
						//	System.out.println(st);
						p.println(st);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(NullPointerException e)
				{
					break;
				}

			}			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		p.println("Hello Parser code comes here");
		p.flush();

	}
}
