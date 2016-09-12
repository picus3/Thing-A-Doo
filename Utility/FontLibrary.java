package utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FontLibrary {
	
	public static Font getTrueTypeFont(int size) {
		InputStream is = null;
		Font font = null;
		try {
			is = new FileInputStream("Pacifico.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, size);
		}catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("could not find font");
			
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return font;		
	}

}
