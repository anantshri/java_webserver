import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;

public class httpdata 
{
	String get;
	String accept;
	String host;
	String connectionType;
	String acceptLang;
	String acceptEnc;
	String userAgent;
	String url;
	String version;
	String ext;
	String dataget;
	public void extract(String getdata)
	{
		if (getdata.startsWith("GET"))
		{
			get = getdata;	
			url = (getdata.substring(getdata.indexOf("/"))).split(" ")[0];
			try {
				url = URLDecoder.decode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (url.indexOf("?") >= 0)
			{
				dataget = url.split("\\?")[1];
				url = url.split("\\?")[0];
			}
			else
			{
				dataget="ZERO";
			}
			ext = url.split("\\.")[((url.split("\\.")).length)-1];
			if (url.length() == 0)
			{
				url="index.html";
			}
		}
		else if(getdata.startsWith("Accept:"))
		{
			accept = getdata.substring(getdata.indexOf(":")+1);
		}
		else if(getdata.startsWith("Accept-Language:"))
		{
			acceptLang = getdata.substring(getdata.indexOf(":")+1);
		}
		else if(getdata.startsWith("Accept-Encoding:"))
		{
			acceptEnc = getdata.substring(getdata.indexOf(":")+1);
		}
		else if(getdata.startsWith("User-Agent:"))
		{
			userAgent = getdata.substring(getdata.indexOf(":")+1);
		}
		else if(getdata.startsWith("Host:"))
		{
			host = getdata.substring(getdata.indexOf(":")+1);
		}
	}
	public String getAccept() {
		return accept;
	}
	public String getExt() {
		return ext;
	}
	public String getAcceptEnc() {
		return acceptEnc;
	}
	public void setAcceptEnc(String acceptEnc) {
		this.acceptEnc = acceptEnc;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public String getGet() {
		return get;
	}
	public String getHost() {
		return host;
	}
	public String getUrl() {
		return url;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String getVersion() {
		return version;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public void setUrl(String url) {
		this.url = url;

		if (url.indexOf("\\.")>=0) {
			ext = url.split("\\.")[((url.split("\\.")).length)];
		}		
		else
		{
			ext = "";
		}
	}
	public String getDataget() {
		return dataget;
	}
}

class HTTP_FILE
{
//	HashMap file_list;
	ArrayList startOrder = new ArrayList();
	public HTTP_FILE() {
		// index listing of priority files
		startOrder.add("index.html");
		startOrder.add("index.htm");
		startOrder.add("home.html");
		startOrder.add("home.htm");
		startOrder.add("default.html");
		startOrder.add("default.htm");
		startOrder.trimToSize();

	}
	public String defPageOrder(String url)
	{
		File f;
		for (int i = 0; i < startOrder.size(); i++) 
		{
			f = new File("website" + url + (String)startOrder.get(i));
			if (f.exists())
				return f.getName();
		}		
		return "NO";
	}
	public String newfiletype(String url)
	{
		String ext,type;
		ext = ((url.split("\\.")[1]).toLowerCase());
		File f = new File("mime");
		Properties p = new Properties();
		try {
			if (f.exists()) {
				p.load(new FileInputStream(f));
				type = p.getProperty(ext);
				if (type == null) 
				{
					return("NO");
				}
				return(ext + ":" + type);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("NO");				
	}
}

interface HTTP_STATUS
{
	int HTTP_CONTINUE = 100;
	int HTTP_SWITCH = 101;
	int HTTP_OK = 200;
	int HTTP_CREATED = 201;
	int HTTP_ACCPETED = 202;
	int HTTP_UNAUTHORIZED = 203;
	int HTTP_MOVED_PERMANENTLY = 301;
	int HTTP_TEMP_REDIRECT = 307;
	int HTTP_BAD_REQ = 400;
	int HTTP_NONAUTHORIZED = 401;
	int HTTP_FORBIDDEN = 403;
	int HTTP_NOTFOUND = 404;
	int HTTP_SERVER_ERROR = 500;
	int HTTP_BAD_GATEWAY = 502;
}
