package model;

import java.util.ArrayList;

import model.Atributi;
import model.Relacija;

/**
 * Klasa <code>Paket</code> predstavlja podatke
 * o paketima koji su potrebni prilikom mapiranja
 * JSON �eme na objekat Paket.
 * 
 * @author Luka
 * @version %I%, %G%
 */
public class Tabela {
	
	private String title;
	private String titleCode;
	private ArrayList<String> parent;
	private String paket;
	private ArrayList<Atributi> attributes;
	private ArrayList<String> key;
	private ArrayList<Relacija> relations;
	
	public Tabela()
	{
	attributes = new ArrayList<>();
	
	key = new ArrayList<>();
	relations = new ArrayList<>();
	parent=new ArrayList<>();
	
	}
	
	public Tabela(String title, ArrayList<String> parent, String paket, ArrayList<Atributi> attributes,
			 ArrayList<String> key, ArrayList<Relacija> relations) {
		super();
		this.title = title;
		this.parent = parent;
		this.paket = paket;
		this.attributes = attributes;
		this.key = key;
		this.relations = relations;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Atributi> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<Atributi> attributes) {
		this.attributes = attributes;
	}
	public ArrayList<String> getKey() {
		return key;
	}
	public void setKey(ArrayList<String> key) {
		this.key = key;
	}
	public ArrayList<Relacija> getRelations() {
		return relations;
	}
	public void setRelations(ArrayList<Relacija> relations) {
		this.relations = relations;
	}
	public ArrayList<String> getParent() {
		return parent;
	}
	public void setParent(ArrayList<String> parent) {
		this.parent = parent;
	}
	
	public String getPaket() {
		return paket;
	}
	public void setPaket(String paket) {
		this.paket = paket;
	}
	@Override
	public String toString() {
		return "Tabela [title=" + title + ", parent=" + parent + ", paket=" + paket + ", attributes=" + attributes
				+ ", key=" + key + ", relations=" + relations + "]";
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	
	
	

}
