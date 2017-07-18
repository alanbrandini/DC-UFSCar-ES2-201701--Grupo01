package TestesES2;

import org.jabref.gui.exporter.Dropbox.ExportDropbox;
import org.junit.Test;

import com.dropbox.core.DbxException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DropboxTestes {

    @Test
    public void ExportDropboxTeste() {
        try {
			ExportDropbox.funcDropbox("2DcE7b1wPzAAAAAAAAAAI7ELkyHpL-se0p4iMLWpaB0kcLGqa1CeZlQi23Vru5_o",
					new ByteArrayInputStream((new String("teste")).getBytes()));
		} catch (DbxException | IOException e) {
			e.printStackTrace();
		}
    }   
}

