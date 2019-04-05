package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import app.MyApp;

public class JSON_Validator implements Validator{

	@Override
	public void validate(Object shem, Object opi,boolean koZove) { //true znaci zove parser
		// TODO Auto-generated method stub
		JSONObject shema=(JSONObject) shem;
		JSONArray opis=(JSONArray) opi;
		
Schema schema = SchemaLoader.load(shema);
		
		ValidatorTest validator = new ValidatorTest(schema,opis);
		//preuzmi sve pakete i onda gledaj njihove parente ne sme da bude sam sebi
		//roditelj iz segmenta paketi mora postojati
		//unikatno ime
		//roditelj iz tabela mora postojati
		//ne sme imati i parent i paket u tabelama	
		//ne smeju biti jedan drugom roditelj niti na visim hijerah c-b,b-a,a-c
		if(validator.validateAll().equals("") && validator.validateFailEarly().equals("") && validator.validateJsonDetails().equals("")) {
		
			String semaPaketi=opis.toString();
			//System.out.println(semaPaketi);
			int pocPaketa=semaPaketi.indexOf("\"imePaketa\"");
			String paketici="";
			String sema="";
			ArrayList<String> ro=new ArrayList<>();
			ArrayList<String> naziviObelezja=new ArrayList<String>();
			boolean sviKsuValid=true;
			HashMap<String, ArrayList<String>> tabeleIObelezja=new HashMap<String,ArrayList<String>>(); //da bi se proverilo da li destination key postoji u destination table
			HashMap<String, ArrayList<String>> tabelaObProvera=new HashMap<String,ArrayList<String>>();
			HashMap<String, String> parenti=new HashMap<String,String>(); //jer ne moze biti sam sebi parent pa je kljuc titl a vrednost parent
			HashMap<String, String> paketiSvi=new HashMap<String,String>();
			HashMap<String,ArrayList<String>> tabelaImeRoditelj=new HashMap<String,ArrayList<String>>();
			ArrayList<String> paketiIzTabela=new ArrayList<String>();
			ArrayList<String> roditelji=new ArrayList<String>();

			if(pocPaketa!=-1) {
			
			String znak=semaPaketi.substring(pocPaketa-1,pocPaketa);
			for(int i=1;i<50;i++) { //ako je tabele pa paketi
				if(znak.equals("[")) {
					sema=semaPaketi.substring(0,pocPaketa-i-1);
					sema=sema+"]";
					paketici=semaPaketi.substring(pocPaketa-i,semaPaketi.length());
					paketici=paketici.substring(0,paketici.length()-1);

					break;
				}else {
					znak=semaPaketi.substring(pocPaketa-1-i,pocPaketa-i);
				}
			}
			
			if(sema.equals("")) { //STA AKO IDE PAKETI PA TABELE	
				/*System.out.println(semaPaketi);
				String traziKraj=semaPaketi.substring(pocPaketa+8);
				System.out.println(traziKraj);
				int krajPP=traziKraj.indexOf("]");
				znak=traziKraj.substring(krajPP, krajPP+1);
				for(int i=0;i<10;i++) {
					if(znak.equals("{")) {
						sema=traziKraj.substring(i,traziKraj.length());
						System.out.println(sema);
						//sema=opis.toString().substring(0,pocPaketa-i+15);
						//paketici=semaPaketi.substring(pocPaketa-i,semaPaketi.length());
						//paketici=paketici.substring(0,paketici.length()-1);
						break;
					}else {
						znak=traziKraj.substring(krajPP+i, krajPP+i+1);
					}
				}*/
				return;
			}

	
			//System.out.println(sema);
			//System.out.println(paketici);

			String imeP="";
			String imeR="";
			String podIme="";
			String podRod="";
		while(paketici.indexOf("{")!=-1) {

			int pp=paketici.indexOf("}");
			
			int p1=paketici.indexOf("\"imePaketa\"");
			podIme=paketici.substring(p1+8);
			int dvotacka=podIme.indexOf(":");
			podIme=podIme.substring(dvotacka+2);
			int p11=podIme.indexOf('"');
			imeP=podIme.substring(0,p11);
			//System.out.println(imeP);
			for(String ime:paketiSvi.keySet()) {
				if(ime.equals(imeP)) {
					JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("duplicatePaket"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
					return;
				}
			}
			
			int p2=paketici.indexOf("\"roditelj\"");
			podRod=paketici.substring(p2+7);
			int dvotacka2=podRod.indexOf(":");
			podRod=podRod.substring(dvotacka2+2);
			int p22=podRod.indexOf('"');
			imeR=podRod.substring(0,p22);
			//System.out.println(imeR);
			paketiSvi.put(imeP, imeR);
			paketici=paketici.substring(pp+1);
		}
		boolean naslaRod=false;
		for(String ime:paketiSvi.keySet()) {
			if(ime.equals(paketiSvi.get(ime))) {
				JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("duplicateParent"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
				return;
			}
		}
		for(String rod:paketiSvi.values()) {
			if(!rod.equals("")) {///////////////////////////////////////////////////
			for(String ime:paketiSvi.keySet()) {
				if(ime.equals(rod)) {
					naslaRod=true;
				}
			}
			if(naslaRod==true) {naslaRod=false;}
			else {
				JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("parentExist"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
				return;
			}
			}
		}
		
		for(String ime:paketiSvi.keySet()) { 
			String rod=paketiSvi.get(ime);//roditelj trazenog
			if(paketiSvi.containsKey(rod)) { //ovde je sad nadjen paket koji je roditelj trazenog
				if(paketiSvi.get(rod).equals(ime)) {
					JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("paketParent"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
					return;
				}else { //ako dodje ovde znaci da mu ime nije roditelj nego mu trazim roditelja
					String praviRoditelj=paketiSvi.get(rod);
					//System.out.println(praviRoditelj);
					while(!praviRoditelj.equals("")) {
					if(paketiSvi.containsKey(praviRoditelj)) {
						if(paketiSvi.get(praviRoditelj).equals(ime)) {
							JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("paketDete"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
							return;
						}else {
							praviRoditelj=paketiSvi.get(praviRoditelj);
						}
					}
					}
					
				}
			}
		}
	
			}else {
				sema=opis.toString();
			}
			//ne moze sema da se parsira ne tabele po title jer title moze biti i na kraju tab ne mora na pocetku
			while(true) {
				int pocNaziva=sema.indexOf("\"title\"");
				String nazivDoKraja=sema.substring(pocNaziva+7);
			
				int nazP=nazivDoKraja.indexOf('"');
				String titl=nazivDoKraja.substring(nazP+1);
				int krajP=titl.indexOf('"');
				titl=titl.substring(0, krajP);
				
				for(String ime:parenti.keySet()) {
					if(ime.equals(titl)) {
						JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("duplicateTable"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
						return;
					}
				}
				
				int pocNaziva2=sema.indexOf("\"paket\"");
				String nazivDoKraja2=sema.substring(pocNaziva2+7);
			
				int nazP2=nazivDoKraja2.indexOf('"');
				String titl2=nazivDoKraja2.substring(nazP2+1);
				int krajP2=titl2.indexOf('"');
				titl2=titl2.substring(0, krajP2);
				paketiIzTabela.add(titl2);
				
				String roditelj="";
				int pocPar=sema.indexOf("\"parent\"");
				String podPar=sema.substring(pocPar);
				int pocPa=podPar.indexOf("[");
				int krajPa=podPar.indexOf("]");
				String parent=podPar.substring(pocPa+1, krajPa);
				ro=new ArrayList<>();
				int parPraviKraj=0;
				int parPravi=0;
				while(parent.indexOf('"')!=-1) {
				if(parent.indexOf('"')!=-1) {
					//System.out.println(parent+" LLLLLLLLLLLLLLLLLLLLLLLLLLL");
					parPravi=parent.indexOf('"');
					String pod=parent.substring(parPravi+1);
					parPraviKraj=pod.indexOf('"');
					roditelj=parent.substring(parPravi+1,parPraviKraj+parPravi+1);
					ro.add(roditelj);
					roditelji.add(roditelj);
					if(!titl2.equals("") && !roditelj.equals("")) {
						JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("tablePP"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
						sviKsuValid=false;
						return;
					}
					}
				parent=parent.substring(parPraviKraj+parPravi+2);
				//System.out.println(parent+" LLLLLLLLLLLLLLLLLLLLLLLLLLL");
				}
				
				
				tabelaImeRoditelj.put(titl, ro);
				parenti.put(titl, roditelj);	
				int pocetak=sema.indexOf("\"attributes\"");
				String skraceno=sema.substring(pocetak);
				int krajA=skraceno.indexOf(']'); 
				String podsema=sema.substring(pocetak,krajA+pocetak);
		
				naziviObelezja=new ArrayList<String>();
				while(podsema.indexOf("\"name\"")!=-1) {
					int poc=podsema.indexOf("\"name\"");
					String xx=podsema.substring(poc+6);
					int poc2=xx.indexOf('"');
					String yy=xx.substring(poc2+1);
					int kraj=yy.indexOf('"');
					String obelezje=yy.substring(0,kraj);
		
					naziviObelezja.add(obelezje);
					podsema=yy;
				}
				tabeleIObelezja.put(titl, naziviObelezja);
			
				int pocK=sema.indexOf("\"key\"");
				String podK=sema.substring(pocK,sema.length());
				int pocZagrada=podK.indexOf("[");
				int zatZagrada=podK.indexOf("]");
				String kljucevi=podK.substring(pocZagrada+1, zatZagrada);
				boolean nasao1=false;
				/*while(kljucevi.indexOf('"')!=-1) {
			
					int prvi=kljucevi.indexOf('"');
					String kljucevi2=kljucevi.substring(prvi+1);
					int kraj2=kljucevi2.indexOf('"');
					String kljuc=kljucevi2.substring(0,kraj2);
					
					for(String ob:naziviObelezja) {
						if(kljuc.equals(ob)) {
							nasao1=true;
						}
					}
					if(nasao1==false) {
						JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("keyExist"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
						sviKsuValid=false;
						return;
					}else {
						nasao1=false;
					}
			
					kljucevi=kljucevi2.substring(kraj2+1);

				}*/
				if(sviKsuValid==true) { //source key
					/*int relcijeP=sema.indexOf("\"relations\"");
					//AKO NEMA RELACIJE
					String relacije=sema.substring(relcijeP);
			
				
					String jednaRel=relacije.substring(pocR,krajR+1);
			//System.out.println(jednaRel);
					String krajRelacija="";
					String krajRelacija2="";
					boolean sledeciSTOP=false;
					String jednaRel2="";*/
				/*	while(sledeciSTOP!=true) {
						if(krajRelacija.equals("]")) {sledeciSTOP=true;}
							int pocDestT=jednaRel.indexOf("\"destinationTable\"");
							String destT=jednaRel.substring(pocDestT+18);
							int destTP=destT.indexOf('"');
							String destTable=destT.substring(destTP+1);
							int krajdestTP=destTable.indexOf('"');
							destTable=destTable.substring(0, krajdestTP);*/
	
							/*int pocSource2=jednaRel.indexOf("\"destinationKey\"");
							String podS2=jednaRel.substring(pocSource2,jednaRel.length()); //od source key do kraja
							int poZagrada2=podS2.indexOf("[");
							int zaZagrada2=podS2.indexOf("]");
			
							String kljucev2=podS2.substring(poZagrada2+1, zaZagrada2); 
							ArrayList<String> d=new ArrayList<String>();
							while(kljucev2.indexOf('"')!=-1) {
			
								int prvi2=kljucev2.indexOf('"');
								String kljucevi22=kljucev2.substring(prvi2+1);
								int kraj22=kljucevi22.indexOf('"');
								String kljuc2=kljucevi22.substring(0,kraj22);
								d.add(kljuc2);

								kljucev2=kljucevi22.substring(kraj22+1);

							}
							if(tabelaObProvera.containsKey(destTable)) {
								for(String b:d) {
									tabelaObProvera.get(destTable).add(b);
								}
							}else {
								tabelaObProvera.put(destTable, d);
							}*/
			
						/*	int pocSource=jednaRel.indexOf("\"sourceKey\"");
							String podS=jednaRel.substring(pocSource,jednaRel.length()); //od source key do kraja
							int poZagrada=podS.indexOf("[");
							int zaZagrada=podS.indexOf("]");
			
							String kljucev=podS.substring(poZagrada+1, zaZagrada); 
							while(kljucev.indexOf('"')!=-1) {
								int prvi=kljucev.indexOf('"');
								String kljucevi2=kljucev.substring(prvi+1);
								int kraj2=kljucevi2.indexOf('"');
								String kljuc=kljucevi2.substring(0,kraj2);
			
								boolean nasao=false;
								for(String ob:naziviObelezja) {
									if(kljuc.equals(ob)) {
										nasao=true;
									}
								}	
		
								if(nasao==false) {
									JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("sourceKey"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
									sviKsuValid=false;
									return;
								}else {
									nasao=false;
								}
								kljucev=kljucevi2.substring(kraj2+1);

							}*/
						//	odmakao+=jednaRel.length();
						/*	krajRelacija=relacije.substring(odmakao, odmakao+1);
							krajRelacija2=relacije.substring(odmakao+1, odmakao+2);
							if(krajRelacija.equals("]") || krajRelacija2.equals("]") )break;
							jednaRel2=relacije.substring(odmakao,relacije.length());
							int pocR2=jednaRel2.indexOf("{");
							int krajR2=jednaRel2.indexOf("}");
							jednaRel=jednaRel2.substring(pocR2,krajR2+2);

					}*/

					int noviPocTabele=0;
		
					if(krajA>(zatZagrada+pocK)) {
						noviPocTabele=krajA;
					}else {
						noviPocTabele=zatZagrada+pocK;
					}
					//if(odmakao>noviPocTabele) { noviPocTabele=odmakao+1;}
					if((nazP+pocNaziva+7)>noviPocTabele) {noviPocTabele=(nazP+1+pocNaziva+7);}
					sema=sema.substring(noviPocTabele+2, sema.length());
					int provera=sema.indexOf("\"attributes\"");
					if(provera==-1) {break;}				
					if(sviKsuValid==true) { //dest key
		
					}else {
						
					}
		
				}else {}
			}
			
			for(String s:roditelji) {
				//System.out.println(s);
				if(!tabelaImeRoditelj.containsKey(s)) {
					JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("parentExist"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
					sviKsuValid=false;
					return;
				}
			}
			
			if(sviKsuValid==true) {
				
				
				boolean sadrzi=true;
				boolean nemaOb2=false;
		
	
		for(String pak: paketiIzTabela) {
			if(!pak.equals("")) {
			if(!paketiSvi.containsKey(pak)) {
				JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("paketExist"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
				sviKsuValid=false;
				return;
			}
			}
		}
		
		/*for(String tab : tabelaObProvera.keySet() ) {
			//System.out.println("KLJUC "+tab);
			sadrzi=tabeleIObelezja.containsKey(tab);
			if(sadrzi==false) {
				JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("destinationExist"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
				sviKsuValid=false;
				return;
			}else {
				//System.out.println(tabelaObProvera.get(tab));
				boolean pronadjenOb=false;
				for(String o: tabelaObProvera.get(tab)) {
					//System.out.println("OB PROVERA "+o);
					ArrayList<String> obb=tabeleIObelezja.get(tab);
					for(String ob :obb) {
						//System.out.println(ob);
						if(o.equals(ob)) {
							pronadjenOb=true;
						
					}
					}
					if(pronadjenOb==false){
						JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("destinationKey"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
						nemaOb2=true;
						sviKsuValid=false;
						return;
					}else { pronadjenOb=false;}
				
				}
				if(nemaOb2==true) break;
			}
		}*/
		if(sviKsuValid==true) {
			boolean postoji=false;
			for(String par: parenti.values()) {
				if(!par.equals("")) {
				for(String naslov:tabeleIObelezja.keySet()) {
					if(naslov.equals(par)) {
						postoji=true;
					}
				}
				if(postoji==true) {postoji=false;}
				else {
					sviKsuValid=false; 
					JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("parentTT"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
					return;
				}
				}
			}
			if(sviKsuValid==true) {
				for(String par: parenti.keySet()) {
					if(par.equals(parenti.get(par))) {
						sviKsuValid=false;
						JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("parentTable"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
						return;
					}
				}
			}
			if(sviKsuValid==true) {
				
				if(koZove==true) {
					JSON_Parser jp=new JSON_Parser();
					jp.parse(opis.toString());

				}else {
					JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("successfulValid"), MyApp.getInstance().getResourceBundle().getString("error"), 1);
				}
			}
			
		}
		
		
	}
			
		}
		else {
			String err[] = validator.validateAll().split("\n");
			JOptionPane.showMessageDialog(null, MyApp.getInstance().getResourceBundle().getString("unsuccessfulValid")+err[0], MyApp.getInstance().getResourceBundle().getString("error"), 1);
				System.out.println("nece");
		}
		
	
	}

}
