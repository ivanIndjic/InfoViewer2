package app;

import javax.swing.UIManager;

import view.ChangeLanguage;
import view.ChooseSource;
public class Main {


	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			ChangeLanguage cl = new ChangeLanguage();
		

			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

}
