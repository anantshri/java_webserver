import java.io.File;
import java.io.PrintStream;


public class DirectoryView {
	synchronized public void Viewlist(PrintStream p, String url)
	{
		File fl,list[];
//		String str1="<HTML><HEAD><TITLE> Directory View</TITLE></HEAD><BODY>";
//		String str2="</BODY></HTML>";
		String beforeicon="<TR><TD>";
		String aftericon="</TD></tr>";
		fl = new File("website" + url);
		list=fl.listFiles();
//		p.println(str1);
		p.flush();
		p.println("<div align=center>Index of "+url+" </div><table>");
		p.flush();
		p.println(beforeicon+"<li><img src='/images/home.jpg'><a href='/'>Home Link</a></li>"+aftericon);
		p.flush();
		p.println(beforeicon+"<li><img src='/images/back.jpg'><a href=../>Parent</a></li>"+aftericon);
		p.flush();
		for(int i=0;i<list.length;i++)
		{
			if(list[i].isDirectory())
			{
				p.println(beforeicon+"<li><img src='/images/fld1.jpg'><a href='"+ list[i].getName() +"/"+ "'>"+ list[i].getName()+"</a></li>"+aftericon);
				p.flush();	
			}
			else
			{
				p.println(beforeicon+"<li><img src='/images/file.jpg'><a href='"+ list[i].getName() + "'>"+ list[i].getName()+"</a></li>"+aftericon);
				p.flush();
			}
		}
		p.println("</table>");
//		p.println(str2);
		
		p.flush();

	}
}
