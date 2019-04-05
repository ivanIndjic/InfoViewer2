package tree;

import java.util.ResourceBundle;

import javax.swing.tree.DefaultMutableTreeNode;

import app.MyApp;
import model.PaketModel;
import model.TableModel;
/**
 * Klasa koja iscrtava stablo
 * @author Ivan
 * @version %I%, %G%
 * @see ResourceBundle
 * 
 * 
 * */
public class DrawTree {
	/**
	 * Metoda prolazi kroz kroz pakete i tabele i ubacuje ih po hijerarhiji u stablo
	 * Ulazni parametar je cvor u koji se ubacuje trenutni objekat koji je drugi ulazni parametar ove metode
	 * */
	   public void iscrtajStablo(DefaultMutableTreeNode cvor,Object trenutn) {

		  if(trenutn instanceof PaketModel) {
			  PaketModel trenutni=(PaketModel) trenutn;
			  
		   if(trenutni.getDeca().size()!=0) {
			   for(Object pm:trenutni.getDeca()) {
				   if(pm instanceof PaketModel) {
					   PaketModel pmm=(PaketModel)pm;
					   DefaultMutableTreeNode node=new DefaultMutableTreeNode(pmm);
					   cvor.add(node);
					   iscrtajStablo(node, pmm);
				   }
				   else if(pm instanceof TableModel){
					   TableModel pmm=(TableModel)pm;
					   DefaultMutableTreeNode node=null;
					   DefaultMutableTreeNode nodee=null;

					   
					   if(pmm.getTabela().getTitle().equals("Drzava"))
					   {   
					   TableModel object = new TableModel(pmm.getTabela(),pmm.getDeca());
					   object.getTabela().setTitle(MyApp.getInstance().getResourceBundle().getString("drzava"));
					   node=new DefaultMutableTreeNode(object);
					   }else if(pmm.getTabela().getTitle().equals("Naseljeno mesto") || pmm.getTabela().getTitle().equals("Naseljeno_mesto")|| pmm.getTabela().getTitle().equals("Naseljenomesto"))
					   {
						   TableModel object = new TableModel(pmm.getTabela(),pmm.getDeca());
						   object.getTabela().setTitle(MyApp.getInstance().getResourceBundle().getString("naseljenoMesto"));
						   node=new DefaultMutableTreeNode(object);
					   }
					   else					   
					   node=new DefaultMutableTreeNode(pmm);
					   
					   //ew DefaultMutableTreeNode(pmm);
					   cvor.add(node);
					   iscrtajStablo(node, pmm);
				   }
			   }
		   }
		  }else {
			  TableModel pmm=(TableModel) trenutn;
			 	  
			  DefaultMutableTreeNode node = null;
			   if(pmm.getDeca().size()!=0) {
				   
				  
				   
				   for(TableModel tm:pmm.getDeca()) {
					   
					     if(tm.getTabela().getTitle().equals("Drzava"))
					   {   
					   TableModel object = new TableModel(tm.getTabela(),tm.getDeca());
					   object.getTabela().setTitle(MyApp.getInstance().getResourceBundle().getString("drzava"));
					   node=new DefaultMutableTreeNode(object);
					   }else if(tm.getTabela().getTitle().equals("Naseljeno mesto") || tm.getTabela().getTitle().equals("Naseljeno_mesto")|| pmm.getTabela().getTitle().equals("Naseljenomesto"))
					   {
						   TableModel object = new TableModel(pmm.getTabela(),pmm.getDeca());
						   object.getTabela().setTitle(MyApp.getInstance().getResourceBundle().getString("naseljenoMesto"));
						   node=new DefaultMutableTreeNode(object);
					   }
						  
					   
					   else					   
					   node=new DefaultMutableTreeNode(tm);
					   
					   cvor.add(node);
					   iscrtajStablo(node, tm);
				   }
			   }
		  }
	   }
	   


}
