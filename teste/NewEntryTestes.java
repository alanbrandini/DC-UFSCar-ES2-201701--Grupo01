package TestesES2;

import java.io.IOException;
import java.io.StringWriter;

import org.jabref.logic.bibtex.BibEntryWriter;
import org.jabref.logic.bibtex.LatexFieldFormatter;
import org.jabref.logic.bibtex.LatexFieldFormatterPreferences;
import org.jabref.logic.util.OS;
import org.jabref.model.database.BibDatabaseMode;
import org.jabref.model.entry.BibEntry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NewEntryTestes {

    private BibEntryWriter writer;

    @Before
    public void setUpWriter() {
    	LatexFieldFormatterPreferences latexFieldFormatterPreferences = mock(LatexFieldFormatterPreferences.class, Answers.RETURNS_DEEP_STUBS);
        writer = new BibEntryWriter(new LatexFieldFormatter(latexFieldFormatterPreferences), true);
    }
	
    @Test
	public void testCamposVaziosArtigo() throws IOException {
    	StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("article");
        
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = OS.NEWLINE + "@Article{," + OS.NEWLINE +
                "}" + OS.NEWLINE;

        assertEquals(expected, actual);
    }
    
	@Test
	public void testCamposObrigatoriosArtigo() throws IOException {
		StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("article");
        entry.setCiteKey("einstein1916");
        entry.setField("author", "Einstein, Albert");
        entry.setField("title", "Die grundlage der allgemeinen relativitatstheorie");
        entry.setField("journal", "Annalen der Physik");
        entry.setField("year", "1916");    
        
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = OS.NEWLINE + "@Article{einstein1916," + OS.NEWLINE +
                "  author  = {Einstein, Albert}," + OS.NEWLINE +
                "  title   = {Die grundlage der allgemeinen relativitatstheorie}," + OS.NEWLINE +
                "  journal = {Annalen der Physik}," + OS.NEWLINE +
                "  year    = {1916}," + OS.NEWLINE +
                "}" + OS.NEWLINE;

        assertEquals(expected, actual);
	}
	
	@Test
	public void testTodosCamposArtigo() throws IOException {
		StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("article");
        entry.setCiteKey("einstein1905");
        entry.setField("author", "Einstein, Albert");
        entry.setField("title", "Ist die Tragheit eines Korpers von seinem Energieinhalt abhangig?");
        entry.setField("journal", "Annalen der Physik");
        entry.setField("year", "1905");
        entry.setField("volume", "323");
        entry.setField("number", "13");
        entry.setField("pages", "639-641");
        entry.setField("month", "7");
        entry.setField("note", "Drei Monate nach seiner grundlegenden Arbeit zur Speziellen Relativitatstheorie");
        entry.setField("crossref", "Zur Elektrodynamik bewegter Korper");
        entry.setField("keywords", "Energieinhalt, Korpes");
        entry.setField("doi", "19053231314");
        entry.setField("url", "http://onlinelibrary.wiley.com/doi/10.1002/andp.19053231314/abstract");
        entry.setField("comment", "Comment");
        entry.setField("owner", "Albert");
        entry.setField("timestamp", "2006");
        entry.setField("abstract", "No abstract");
        entry.setField("review", "Sehr gut");
                
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = OS.NEWLINE + "@Article{einstein1905," + OS.NEWLINE +
        	"  author    = {Einstein, Albert}," + OS.NEWLINE +
        	"  title     = {Ist die Tragheit eines Korpers von seinem Energieinhalt abhangig?}," + OS.NEWLINE +
        	"  journal   = {Annalen der Physik}," + OS.NEWLINE +
        	"  year      = {1905}," + OS.NEWLINE +
        	"  volume    = {323}," + OS.NEWLINE +
        	"  number    = {13}," + OS.NEWLINE +
        	"  pages     = {639-641}," + OS.NEWLINE +
        	"  month     = {7}," + OS.NEWLINE +
        	"  note      = {Drei Monate nach seiner grundlegenden Arbeit zur Speziellen Relativitatstheorie}," + OS.NEWLINE +
        	"  abstract  = {No abstract}," + OS.NEWLINE +
        	"  comment   = {Comment}," + OS.NEWLINE +
        	"  crossref  = {Zur Elektrodynamik bewegter Korper}," + OS.NEWLINE +
        	"  doi       = {19053231314}," + OS.NEWLINE +
        	"  keywords  = {Energieinhalt, Korpes}," + OS.NEWLINE +
        	"  owner     = {Albert}," + OS.NEWLINE +
        	"  review    = {Sehr gut}," + OS.NEWLINE +
        	"  timestamp = {2006}," + OS.NEWLINE +
        	"  url       = {http://onlinelibrary.wiley.com/doi/10.1002/andp.19053231314/abstract}," + OS.NEWLINE +
        	"}" + OS.NEWLINE;

        assertEquals(expected, actual);
	}

    @Test
	public void testCamposVaziosLivro() throws IOException {
    	StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("book");
        
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = OS.NEWLINE + "@Book{," + OS.NEWLINE +
                "}" + OS.NEWLINE;

        assertEquals(expected, actual);
    }
    
    
    @Test
	public void testCamposObrigatoriosLivro() throws IOException {
		StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("book");
        entry.setCiteKey("newton1999");
        entry.setField("author", "Newton, Isaac");
        entry.setField("title", "The Principia: mathematical principles of natural philosophy");
        entry.setField("publiser", "Univ of California Press");
        entry.setField("year", "1999");  
        entry.setField("editor", "University of California");  
        
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = OS.NEWLINE + "@Book{newton1999," + OS.NEWLINE +
						"  title    = {The Principia: mathematical principles of natural philosophy}," + OS.NEWLINE +
						"  year     = {1999}," + OS.NEWLINE +
						"  author   = {Newton, Isaac}," + OS.NEWLINE +
						"  editor   = {University of California}," + OS.NEWLINE +
						"  publiser = {Univ of California Press}," + OS.NEWLINE +
						"}" + OS.NEWLINE;

        assertEquals(expected, actual);
	}
    
	@Test
	public void testTodosCamposLivro() throws IOException {
		StringWriter stringWriter = new StringWriter();

		BibEntry entry = new BibEntry("book");
        entry.setCiteKey("darwin2003");
        entry.setField("author", "Darwin, Charles");
        entry.setField("title", "The Origin of Species: 150th Anniversary Edition");
        entry.setField("publiser", "Singnet Book");
        entry.setField("year", "2003");   
        entry.setField("volume", "1");
        entry.setField("number", "1");
        entry.setField("series", "1");
        entry.setField("address", "London");
        entry.setField("edition", "150th Anniversary ed.");
        entry.setField("month", "7");
        entry.setField("isbn", "0451529065");
        entry.setField("note", "Anniversary Edition");
        entry.setField("crossref", "Crossref");
        entry.setField("keywords", "Origin, Speceis");
        entry.setField("file", "home/Documents/darwin.pdf");
        entry.setField("doi", "12345678901");
        entry.setField("url", "https://www.amazon.com.br/Origin-Species-150th-Anniversary/dp/0451529065");
        entry.setField("comment", "Comment");
        entry.setField("owner", "Charles");
        entry.setField("timestamp", "2003");
        entry.setField("abstract", "No abstract");
        entry.setField("review", "Very good");
        
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected =  OS.NEWLINE + "@Book{darwin2003," + OS.NEWLINE +
						"  title     = {The Origin of Species: 150th Anniversary Edition}," + OS.NEWLINE +
						"  year      = {2003}," + OS.NEWLINE +
						"  author    = {Darwin, Charles}," + OS.NEWLINE +
						"  volume    = {1}," + OS.NEWLINE +
						"  number    = {1}," + OS.NEWLINE +
						"  series    = {1}," + OS.NEWLINE +
						"  address   = {London}," + OS.NEWLINE +
						"  edition   = {150th Anniversary ed.}," + OS.NEWLINE +
						"  month     = {7}," + OS.NEWLINE +
						"  isbn      = {0451529065}," + OS.NEWLINE +
						"  note      = {Anniversary Edition}," + OS.NEWLINE +
						"  abstract  = {No abstract}," + OS.NEWLINE +
						"  comment   = {Comment}," + OS.NEWLINE +
						"  crossref  = {Crossref}," + OS.NEWLINE +
						"  doi       = {12345678901}," + OS.NEWLINE +
						"  file      = {home/Documents/darwin.pdf}," + OS.NEWLINE +
						"  keywords  = {Origin, Speceis}," + OS.NEWLINE +
						"  owner     = {Charles}," + OS.NEWLINE +
						"  publiser  = {Singnet Book}," + OS.NEWLINE +
						"  review    = {Very good}," + OS.NEWLINE +
						"  timestamp = {2003}," + OS.NEWLINE +
						"  url       = {https://www.amazon.com.br/Origin-Species-150th-Anniversary/dp/0451529065}," + OS.NEWLINE +
						"}" + OS.NEWLINE;

        assertEquals(expected, actual);
	}
}
