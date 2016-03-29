import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class WebServe implements Runnable {
	Socket soc;
	OutputStream os;
	BufferedReader is;
	
	String resource;
	
	/*public static void main (String[] args) {
		try {
			ServerSocket s = new ServerSocket(80);
			//for (;;) {
				WebServe w = new WebServe(s.accept());
				Thread thr = new Thread (w);
				thr.start();
			//}
		} catch (IOException e) {
			System.err.println("IOException in server: " + e);
		}
	}*/
	
	WebServe (Socket s) throws IOException {
		soc = s;
		os = soc.getOutputStream();
		is = new BufferedReader(new InputStreamReader(soc.getInputStream()));
	}
	
	void getRequest () {
		try {
			String message;
			
			while ((message = is.readLine()) != null) {
				if (message.equals(""))
					break;
				
				System.err.println(message);
				StringTokenizer t = new StringTokenizer(message);
				String token = t.nextToken();
				
				if (token.equals("GET"))
					resource = t.nextToken();
			}
		} catch (IOException e) {
			System.err.println("Error receiving web request");
		}
	}
	
	void returnResponse () {
		int c;
		
		try {
			FileInputStream f = new FileInputStream("." + resource);
			
			while ((c = f.read()) != -1) {
				os.write(c);
			}
			
			f.close();
		} catch (IOException e) {
			System.err.println ("IOException in reading in web server");
		}
	}
	
	public void close () {
		try {
			is.close(); os.close(); soc.close();
		} catch (IOException e) {
			System.err.println("IOException while closing connection");
		}
	}

	@Override
	public void run() {
		getRequest ();
		returnResponse ();
		close ();
	}
}
