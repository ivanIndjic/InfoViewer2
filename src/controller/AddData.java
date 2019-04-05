package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import app.MyApp;
import model.Tabela;
import model.TableModel;
import view.TableView;

public class AddData implements CRUDFactory{
	
	private ArrayList<JTextField> polja = new ArrayList<>();
	public LinkedHashMap<String, Object> glavno = new LinkedHashMap<>();
	private Tabela t = new Tabela();
	String query="";
	boolean dete;
	int w;
	int h;
	boolean search;
	ArrayList<TableModel> listaTabela;
	Tabela roditelj;
	private String ime;
	JTable tT = new JTable();
	private ArrayList<ButtonGroup> dugmad = new ArrayList<>();
	private ArrayList<JRadioButton> but = new ArrayList<>();
	public AddData(ArrayList<JTextField> polja,ArrayList<ButtonGroup> dugmad,ArrayList<JRadioButton>but,Tabela t,LinkedHashMap<String,Object> g,boolean dete,Tabela roditelj,ArrayList<TableModel> listaTabela,int w,int h,boolean search,JTable tT,String ime) {
		this.polja = polja;
		this.tT=tT;
		this.ime=ime;
		this.dugmad=dugmad;
		this.but=but;
		this.t=t;
		this.dete=dete;
		this.w=w;
		this.h=h;
		this.search=search;
		this.roditelj=roditelj;
		this.listaTabela=listaTabela;
		glavno=g;
		addData();
	}

	@Override
	public boolean addData() {
		// TODO Auto-generated method stub
		MyApp.getInstance().getZapamcenoAdd().clear();
			String izmena=ime;
			t.setTitle(ime);
			if(ime.contains(" ")) {
				String str = " ";
				izmena=izmena.replace(str, "_");
			}
			query="insert into "+izmena+" (";
			for(int i=0;i<t.getAttributes().size();i++) {
				if(i+1==t.getAttributes().size())
					query+=t.getAttributes().get(i).getCode()+")";
				else	
				query+=t.getAttributes().get(i).getCode()+",";
			}
			query+= " values (";
			int counter=-1;
			for(String s : glavno.keySet()) {
				++counter;
				
				if(glavno.get(s) instanceof JTextField) {
					JTextField f= (JTextField) glavno.get(s);
					if(t.getAttributes().get(counter).getType().equals("number")) {
					if(f.getText().equals(""))
						query+="null"+",";
					else
						query+=f.getText() +",";
					}
					else {
						
						if(f.getText().equals(""))
							query+="\'\'"+",";
						else
							query+="\'"+f.getText()+"\'"+",";
							
					}
				}
				else if(glavno.get(s) instanceof ButtonGroup) {
					ButtonGroup bg = (ButtonGroup) glavno.get(s);
					 for (Enumeration<AbstractButton> buttons = bg.getElements(); 
							 buttons.hasMoreElements();) {
				            AbstractButton button = buttons.nextElement();

                            if (button.isSelected()) {
                                if(button.getText().equals("True"))
                                    query+="1"+",";
                                if(button.getText().equals("False"))                                
                                    query+="0"+",";
                            }
                        }
                }
                
                
            }
            query+=");";
            
            query = query.replace(",);", ");");
            System.out.println(query);
            Connection conn;
            try {
                conn = DriverManager.getConnection("jdbc:jtds:sqlserver://147.91.175.155/psw-2018-tim2-4","psw-2018-tim2-4","tim2-413090834");
            
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
            
            //ako je tabla dete razlicito se crta u tableView


			JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("success"));
			if(dete) {
				Row rw=new Row(); 
				Object[][] data=rw.konekcijaSaBazom(roditelj);
				ArrayList<TableModel> deca=new ArrayList<>();
				for(TableModel tm:listaTabela) {
					if(tm.getTabela().getTitle().equals(roditelj.getTitle())) {
						deca=tm.getDeca();
					}
				}
				ArrayList<Tabela> decaT=new ArrayList<>();
				for(TableModel tm: deca) {
					decaT.add(tm.getTabela());
				}
			HashMap<Tabela, Object[][]> data2=rw.konekcijaSaBazomDeca(decaT);
			TableView is=new TableView(listaTabela,roditelj.getTitle());
			JPanel pa=is.iscrtavanje(w,h,data,data2);
			MyApp.getInstance().addToCentralPanel2(pa);	 
			}
			else {
				Row rw=new Row(); 
				Object[][] data=rw.konekcijaSaBazom(t);
				ArrayList<TableModel> deca=new ArrayList<>();
				for(TableModel tm:listaTabela) {
					if(tm.getTabela().getTitle().equals(t.getTitle())) {
						deca=tm.getDeca();
					}
				}
				ArrayList<Tabela> decaT=new ArrayList<>();
				for(TableModel tm: deca) {
					decaT.add(tm.getTabela());
				}
			HashMap<Tabela, Object[][]> data2=rw.konekcijaSaBazomDeca(decaT);
			TableView is=new TableView(listaTabela,t.getTitle());
			JPanel pa=is.iscrtavanje(w,h,data,data2);
			MyApp.getInstance().addToCentralPanel2(pa);	 
			}
			if(search) {
				//System.out.println("USAO");
				Row rw=new Row(); 
				Object[][] data=rw.konekcijaSaBazom(t);
				String[] columnNames=rw.uzmiHeadere(t);
				SearchData sd=new SearchData(data, columnNames, ime, t, listaTabela, w, h, dete, roditelj, tT);
				
			}
            }  catch (SQLException ex) {
                if(ex.getErrorCode()==2627)
                    JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("duplicate"));
                else
                    ex.printStackTrace();
                return false;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            
            
        
        return true;
        
    }

    @Override
    public boolean removeData() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateData() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void searchData() {
        // TODO Auto-generated method stub
        
    }
    
    

}