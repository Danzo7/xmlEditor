package model;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.InputStream;
public class xmlValidator {

   public static void dtdValidation(InputStream data){

        SAXBuilder builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        Document validDocument = null;
        try {
            validDocument = builder.build(data);

            XMLOutputter outputter = new XMLOutputter(
                    Format.getPrettyFormat());
            outputter.output(validDocument, System.out);


            DocType docType = validDocument.getDocType();
            System.out.println("Public ID: " + docType.getPublicID());
            System.out.println("System ID: " + docType.getSystemID());
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

    }






}
