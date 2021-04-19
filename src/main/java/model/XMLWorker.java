package model;

import org.apache.commons.io.IOUtils;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLWorker {
    public static int TYPE_XSD=1,TYPE_DTD=0;
    public ArrayList<HashMap<Integer, String>> resolvedErrors=new ArrayList<>();
    public String name,content;
    InputStream inputs;
    public XMLWorker(String name) throws IOException {
        this.name=name;
        inputs=new FileInputStream(name);
        content = IOUtils.toString(inputs, StandardCharsets.UTF_8);
    }
    public boolean validate(int Type) throws IOException {
        //inputs=new FileInputStream(name);
        File tempXmlFile = File.createTempFile("xmlEditor-", ".xml");
        tempXmlFile.deleteOnExit();
        FileWriter myWriter = new FileWriter(tempXmlFile);
        myWriter.write(content);
        myWriter.close();
        SAXBuilder builder;
        if(Type==TYPE_DTD)
         builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        else
            if(Type==TYPE_XSD)
            builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        else
            builder = new SAXBuilder();
        try {
            Document validDocument = builder.build(tempXmlFile);

            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            //output.output(validDocument, System.out);
        } catch (JDOMException | IOException e) {

            System.out.println(e.getLocalizedMessage());
            HashMap<Integer, String> res = errorResolver(e.getLocalizedMessage());
            System.out.println(res.entrySet().iterator().next().getKey());
            return false;
        }


        return true;
    }

    public static StyleSpans<Collection<String>> computeHighlighting2(String text){
        Pattern ATTRIBUTES  = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");
        Pattern xmlPattern = Pattern.compile("(?<ERROR><note>)|(?<STAG>(?<=<)\\w*)|(?<ETAG>(?<=</)\\w*)|(?<DATA>(?<=>).+(?=<))|(?<ATTR>\\w*\\s*=\\s*\"\\w*\")|(?<BRACKET>[<,>])");
        int GROUP_ATTRIBUTE_NAME = 1;
        int GROUP_EQUAL_SYMBOL = 2;
        int GROUP_ATTRIBUTE_VALUE = 3;
        int lastKwEnd = 0;

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        Matcher matcher = xmlPattern.matcher(text);

        while(matcher.find()) {
            String styleClass =
                    matcher.group("ERROR") != null ? "error" :
                            matcher.group("BRACKET") != null ? "bracket" :
                                    matcher.group("ATTR") != null ? "ATTR" :
                                            matcher.group("STAG") != null ? "start-tag" :
                                                    matcher.group("ETAG") != null ? "end-tag" :
                                                            matcher.group("DATA") != null ? "data" :
                                                                    matcher.group("BRACKET") != null ? "bracket" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);

            if(styleClass.equals("ATTR")) {
                String attributesText = matcher.group("ATTR");
                lastKwEnd = 0;
                Matcher aMatcher = ATTRIBUTES.matcher(attributesText);
                while(aMatcher.find()) {
                    spansBuilder.add(Collections.emptyList(), aMatcher.start() - lastKwEnd);
                    spansBuilder.add(Collections.singleton("attribute"), aMatcher.end(GROUP_ATTRIBUTE_NAME) - aMatcher.start(GROUP_ATTRIBUTE_NAME));
                    spansBuilder.add(Collections.singleton("equal"), aMatcher.end(GROUP_EQUAL_SYMBOL) - aMatcher.end(GROUP_ATTRIBUTE_NAME));
                    spansBuilder.add(Collections.singleton("att_value"), aMatcher.end(GROUP_ATTRIBUTE_VALUE) - aMatcher.end(GROUP_EQUAL_SYMBOL));
                    lastKwEnd = aMatcher.end();
                }
                if(attributesText.length() > lastKwEnd)
                    spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
                lastKwEnd = matcher.end("ATTR");
            }

            else {
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
        }

        return spansBuilder.create();

    }

    public static HashMap<Integer, String> errorResolver(String err){
        Pattern element_reg = Pattern.compile("(?<=(type \")).(?=\")", Pattern.CASE_INSENSITIVE);
        Pattern line_reg = Pattern.compile("(?<=Error on line )[0-9]+(?=:)");
        Pattern attr_Err = Pattern.compile("(?<=Error on line )[0-9]+(?=:)");
        int line=-1;
        String element="null";
        Matcher matcher = element_reg.matcher(err);
        if(matcher.find())
            element=matcher.group(0);

        matcher = line_reg.matcher(err);
        if(matcher.find())
            line= Integer.parseInt(matcher.group(0));
        HashMap<Integer, String> result = new HashMap<>();
        result.put(line,element);
        return result;
    }

}
