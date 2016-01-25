package test;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Utility {
	
	public static String readFromStdin() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder input = new StringBuilder();
		String temp;
		try {
			while ((temp = br.readLine()) != null) {
				input.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input.toString();
	}
	// http://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
				desktop.browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void openWebpage(String url) {
        try {
			openWebpage(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void launchFile(String path) {
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
        	System.out.println(e);
        	return null;
		}
    }
    
	// Writes over the contents of the file
    public static void writeFile(String path, String stuff) {
        BufferedWriter b;
		try {
			b = new BufferedWriter(new FileWriter(path));
	        b.write(stuff);
	        b.close();
		} catch (IOException e) {
			System.out.println(e);
		}
    }

}
