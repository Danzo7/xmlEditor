package model;

import org.apache.commons.io.IOUtils;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
public class XMLWorker {
    public static int TYPE_XSD=1,TYPE_DTD=0;
    public String name,content;
    InputStream inputs;
    public XMLWorker(String name) throws IOException {
        this.name=name;
        inputs=new FileInputStream(name);
        content = IOUtils.toString(inputs, StandardCharsets.UTF_8);
    }
    public boolean validate(int Type){
        SAXBuilder builder;
        if(Type==TYPE_DTD)
         builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        else
            builder = new SAXBuilder(XMLReaders.XSDVALIDATING);

        try {
            Document validDocument = builder.build(inputs);

            XMLOutputter output = new XMLOutputter(
                    Format.getPrettyFormat());
            output.output(validDocument, System.out);


            DocType docType = validDocument.getDocType();
            System.out.println("Public ID: " + docType.getPublicID());
            System.out.println("System ID: " + docType.getSystemID());
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }


        return true;
    }
}
