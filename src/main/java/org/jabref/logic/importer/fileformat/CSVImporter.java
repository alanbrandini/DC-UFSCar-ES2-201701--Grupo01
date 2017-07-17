package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.FileExtensions;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexEntryTypes;

public class CSVImporter extends Importer {

    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public FileExtensions getExtensions() {
        return FileExtensions.CSV;
    }

    @Override
    public String getDescription() {
        return "Imports CSV files, where every field is separated by a semicolon.";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) throws IOException {
    	String str;
    	if(!reader.ready())
    		return false;
        str = reader.readLine();
    	if(str.equals("BibliographyType,ISBN,Identifier,Author,Title,Journal,Volume,Number,Month,Pages,Year,Address,Note,URL,Booktitle,Chapter,Edition,Series,Editor,Publisher,ReportType,Howpublished,Institution,Organizations,School,Annote,Custom1,Custom2,Custom3,Custom4,Custom5"))
    		return true; // this is discouraged except for demonstration purposes
    	else
    		return false;
    }

    @Override
    public ParserResult importDatabase(BufferedReader input) throws IOException {
        List<BibEntry> bibitems = new ArrayList<>();
        
        String primeiraLinha = input.readLine(); //descarta a primeira linha
        primeiraLinha = primeiraLinha.replace("Identifier", "bibtexkey"); //mudando Identifier (nome que o JabRef salva a key) para bibtexkey
        String [] campos = primeiraLinha.split(",");
        String line = input.readLine();
        while (line != null) {
        	if (!line.trim().isEmpty()) {
                String[] fields = line.split(",");
                BibEntry entry = null;
                //verificando o tipo da entrada com base no BibliographyType
                switch(fields[0]) {
                	case "1": entry = new BibEntry("book"); break;
                	case "2": entry = new BibEntry("booklet"); break;
                	case "3": entry = new BibEntry("proceedings"); break;
                	case "5": entry = new BibEntry("inbook"); break;
                	case "6": entry = new BibEntry("inproceedings"); break;
                	case "7": entry = new BibEntry("article"); break;
                	case "8": entry = new BibEntry("manual"); break;
                	case "9": entry = new BibEntry("mastersthesis"); break;
                	case "10": entry = new BibEntry("conference"); break;
                	case "13": entry = new BibEntry("techreport"); break;
                	case "14": entry = new BibEntry("unpublished"); break;
                }
                for(int i = 1; i < fields.length; i++) {                		
            		if(!fields[i].contains("\"\"")) { //verificando se nao é vazio
            			if(fields[i].contains("\"")) //verificando se nao é interio
            				entry.setField(campos[i],fields[i].substring(1,fields[i].length()-1));
            			else
            				entry.setField(campos[i],fields[i]); 
            		}
                }
                bibitems.add(entry);
                line = input.readLine();
            }
        }
        return new ParserResult(bibitems);
    }

}
