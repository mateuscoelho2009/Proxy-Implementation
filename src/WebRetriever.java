import java.io.*;
import java.net.*;

public class WebRetriever {
	Socket soc;
	OutputStream os;
	InputStream is;
	
	WebRetriever (String server, int port)
				throws IOException, UnknownHostException {
		soc = new Socket (server, port);
		os = soc.getOutputStream();
		is = soc.getInputStream();
	}
	
	void request (String path) {
		try {
			String message = "GET" + path + "\n\n";
			os.write(message.getBytes());
			os.flush();
		} catch (IOException e) {
			System.err.println("Error in HTTP request");
		}
	}
	
	void getResponse () {
		int c;
		
		try {
			while ((c = is.read()) != -1)
				System.out.print((char) c);
		} catch (IOException e) {
			System.err.println("IOException in reading from Web Server");
		}
	}
	
	public void close () {
		try {
			is.close(); os.close(); soc.close();
		} catch (IOException e) {
			System.err.println ("IOException in closing the connection");
		}
	}
}
