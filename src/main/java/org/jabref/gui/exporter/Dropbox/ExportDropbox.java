package org.jabref.gui.exporter.Dropbox;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.jabref.Globals;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.actions.MnemonicAwareAction;
import org.jabref.gui.worker.AbstractWorker;
import org.jabref.logic.exporter.ExportFormat;
import org.jabref.logic.exporter.ExportFormats;
import org.jabref.logic.exporter.SavePreferences;
import org.jabref.logic.l10n.Localization;
import org.jabref.logic.layout.LayoutFormatterPreferences;
import org.jabref.model.entry.BibEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dropbox.core.*;

import java.util.Locale;

public class ExportDropbox {

    private static final Log LOGGER = LogFactory.getLog(ExportDropbox.class);

    public ExportDropbox() {
    }
    
    /**
     * Create an AbstractAction for performing an export operation.
     *
     * @param frame
     *            The JabRefFrame of this JabRef instance.
     * @param selectedOnly
     *            true indicates that only selected entries should be exported,
     *            false indicates that all entries should be exported.
     * @return The action.
     */
    public static AbstractAction getExportAction(JabRefFrame frame, boolean selectedOnly) {

        class InternalExportAction extends MnemonicAwareAction {

            
			private static final long serialVersionUID = 1L;

			private final JabRefFrame frame;

            private final boolean selectedOnly;

            public InternalExportAction(JabRefFrame frame, boolean selectedOnly) {
                this.frame = frame;
                this.selectedOnly = selectedOnly;
                putValue(Action.NAME, selectedOnly ? Localization.menuTitle("Export selected entries to Dropbox") : Localization
                        .menuTitle("Export to Dropbox"));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, ExportFormat> customFormats = Globals.prefs.customExports.getCustomExportFormats(Globals.prefs,
                        Globals.journalAbbreviationLoader);
                LayoutFormatterPreferences layoutPreferences = Globals.prefs
                        .getLayoutFormatterPreferences(Globals.journalAbbreviationLoader);
                SavePreferences savePreferences = SavePreferences.loadForExportFromPreferences(Globals.prefs);
                ExportFormats.initAllExports(customFormats, layoutPreferences, savePreferences);
                 
                List<BibEntry> entries;
                if (selectedOnly) {
                    // Selected entries
                    entries = frame.getCurrentBasePanel().getSelectedEntries();
                } else {
                    // All entries
                    entries = frame.getCurrentBasePanel().getDatabase().getEntries();
                }

                final List<BibEntry> finEntries = entries;
                
                AbstractWorker exportWorker = new AbstractWorker() {

                    String errorMessage;

                    @Override
                    public void run() {
                        try {
                            frame.output(Localization.lang("Exporting to Dropbox..."));
                        	String bibtex = "\r\n";
                        	for(int i = 0; i < finEntries.size(); i++)
                        		bibtex = bibtex + finEntries.get(i).toString() + "\r\n\r\n";
                        	
        	                String accessToken = "2DcE7b1wPzAAAAAAAAAAI7ELkyHpL-se0p4iMLWpaB0kcLGqa1CeZlQi23Vru5_o";
        	                InputStream inputStream = new ByteArrayInputStream(bibtex.getBytes());
        	                   	                
        	                funcDropbox(accessToken,inputStream);
        	                
                        } catch (Exception ex) {
                            LOGGER.warn("Problem exporting", ex);
                            if (ex.getMessage() == null) {
                                errorMessage = ex.toString();
                            } else {
                                errorMessage = ex.getMessage();
                            }
                        }
                    }

                    @Override
                    public void update() {
                        // No error message. Report success:
                        if (errorMessage == null) {
                            frame.output(Localization.lang("Successfully exported to the Dropbox."));
                        }
                        // ... or show an error dialog:
                        else {
                            frame.output(Localization.lang("Could not save file.") + " - " + errorMessage);
                            // Need to warn the user that saving failed!
                            JOptionPane.showMessageDialog(frame,
                                    Localization.lang("Could not save file.") + "\n" + errorMessage,
                                    Localization.lang("Save library"), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };

                // Run the export action in a background thread:
                exportWorker.getWorker().run();
                // Run the update method:
                exportWorker.update();
            }
        }

        return new InternalExportAction(frame, selectedOnly);
    }
    
    public static void funcDropbox(String accessToken, InputStream inputStream) throws DbxException, IOException {
    	DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
                        
        DbxClient client = new DbxClient(config, accessToken);

        try {
            client.uploadFile("/bibtex_v" + LocalDateTime.now().toString().replace( "T", " " ) + ".bib",DbxWriteMode.add(), inputStream.available(), inputStream);
        } finally {
            inputStream.close();
        }
    }
}