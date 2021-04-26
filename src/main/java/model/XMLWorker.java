package model;

import org.apache.commons.io.IOUtils;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLWorker {
    public boolean isSaved=true;
    public static int TYPE_DTD=0;
    public static int TYPE_AUTO=3;
    private final String fileType;
    static String skips="([\\s\\n\\r])*";
    static public String NullPlaceHolder="NULL/NULL/NULL";
    public String unformattedTag =NullPlaceHolder, unformattedAttribute =NullPlaceHolder, invalidAttribute =NullPlaceHolder, invalidTag =NullPlaceHolder;
    public int[] errorLines =new int[2];
    boolean wellFormed=true,valid=true;
    public boolean noXmlValidator=true;
    public String name,errorString;
 String mainElement="";
    private String content;
    public String[] errorMessages =new String[2];
    public String infoString="";
    InputStream inputs;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        isSaved=false;
        this.content = content;
    }
    public void saving(){
        isSaved=true;
    }

    public XMLWorker(String name) throws IOException {
        this.name=name;
        inputs=new FileInputStream(name);
        content = IOUtils.toString(inputs, StandardCharsets.UTF_8);
        content = Normalizer.normalize(content, Normalizer.Form.NFKD);
        content = content.replaceAll("[^\\p{ASCII}]", "");

        fileType = name.substring(name.lastIndexOf('.') + 1);

    }
    void addSyntaxError(String Error){
        wellFormed=false;
        errorMessages[0]=Error;
    }
    void addValidationError(String Error){
        valid=false;
        errorMessages[1]=Error;
    }
   void  resetErrorValue(){
       wellFormed=true;
       valid=true;
       unformattedTag =invalidAttribute= unformattedAttribute = invalidTag =NullPlaceHolder;
       errorLines[0]=-1;errorLines[1]=-1;
       errorString="Good";
       errorMessages[0]="Everything is good.";errorMessages[1]=NullPlaceHolder;
    mainElement=NullPlaceHolder;
    }
    public void validate(int Type) throws IOException {
        File tempXmlFile = File.createTempFile("xmlEditor-", "."+fileType);
        tempXmlFile.deleteOnExit();
        FileWriter myWriter = new FileWriter(tempXmlFile);
        myWriter.write(content);
        myWriter.close();
        SAXBuilder builder;
        resetErrorValue();
      if(Type!=TYPE_AUTO) {
          try {
              if (Type == TYPE_DTD)
                  builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
              else
              builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
              Document validDocument = builder.build(tempXmlFile);
              //XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
          } catch (JDOMException | IOException e) {
              addValidationError(e.getMessage());
          }
      }
        else{
            builder = new SAXBuilder();
            try {
                Document validDocument = builder.build(tempXmlFile);
                //XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            } catch (JDOMException e) {
                addSyntaxError(e.getMessage());
            }
            if(content.contains("<!DOCTYPE")||content.contains("xsi:schemaLocation")){
                noXmlValidator=false;
                try {
                if (content.contains("<!DOCTYPE")){
                    builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
                    Pattern pattern = Pattern.compile("(?<ERRORFORM>(?<=<!DOCTYPE) (?<MAIN>\\w*)(.*?)(?=>))");
                    Matcher matcher = pattern.matcher(content);

                    mainElement=matcher.find()?matcher.group("MAIN"):NullPlaceHolder;
                }
                else if (content.contains("xsi:schemaLocation"))
                    builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
                Document validDocument = builder.build(tempXmlFile);
                //XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
                }
            catch (JDOMException e){
                addValidationError(e.getMessage());
            }}
            else{
                noXmlValidator=true;
            }

        }
        tempXmlFile.delete();
        errorResolver();
    }

    public  StyleSpans<Collection<String>> computeHighlighting2(String text) throws IOException {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        if(fileType.equals("xml")||fileType.equals("xsd")) {
      validate(TYPE_AUTO);
      Pattern ATTRIBUTES = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");
      // Pattern xmlPattern = Pattern.compile("(?<ERROR>(?<=<)("+ unformattedTag +"|"+invalidTag+"))|(?<ERRORATTR>"+ unformattedAttribute +"|"+ invalidAttribute +".*?(?=>))|(?<DECLARATION><\\?.*\\?>)|(?<ETAG>(?<=<)/\\w*)|(?<STAG>(?<=<)"+skips+"\\w*)|(?<DATA>(?<=>).+(?=<))|(?<ATTR>\\w*"+skips+"="+skips+"\""+skips+"\\w*"+skips+"\")|(?<BRACKET>[<,>])");
      Pattern xmlPattern = Pattern.compile("(?<ERRVALID>(?<=<)("+invalidTag+skips+invalidAttribute+")|"+invalidTag+"|"+(!valid?mainElement:NullPlaceHolder)+".*?(?=>))|(?<ERRORFORM>(?<=<)("+unformattedTag+skips+unformattedAttribute+")|"+unformattedTag+".*?(?=>))|(?<MAINELEMENT>(?<=</?)"+mainElement+".*?(?=>))|(?<DECLARATION><\\?.*\\?>)|(?<STAG>(?<=</?)" + skips + "\\w*)|(?<DATA>(?<=>).+(?=<))|(?<ATTR>\\w*" + skips + "=" + skips + "\"" + skips + "\\w*" + skips + "\")|(?<BRACKET></?|>)");
            System.out.println("(?<ERRVALID>(?<=<)("+invalidTag+skips+invalidAttribute+")|"+invalidTag+".*?(?=>))|(?<ERRORFORM>(?<=<)("+unformattedTag+skips+unformattedAttribute+")|"+unformattedTag+".*?(?=>))|(?<MAINELEMENT>(?<=</)"+mainElement+".*?(?=>))|(?<DECLARATION><\\?.*\\?>)|(?<ETAG>(?<=<)/\\w*)|(?<STAG>(?<=<)" + skips + "\\w*)|(?<DATA>(?<=>).+(?=<))|(?<ATTR>\\w*" + skips + "=" + skips + "\"" + skips + "\\w*" + skips + "\")|(?<BRACKET>[<,>])");
      int GROUP_ATTRIBUTE_NAME = 1;
      int GROUP_EQUAL_SYMBOL = 2;
      int GROUP_ATTRIBUTE_VALUE = 3;
      int lastKwEnd = 0;
      Matcher matcher = xmlPattern.matcher(text);
      while (matcher.find()) {
          String styleClass =
                  matcher.group("ERRORFORM") != null ? "error-form" :
                          matcher.group("ERRVALID") != null ? "error-valid" :
                                  matcher.group("DECLARATION") != null ? "declaration" :
                                          matcher.group("MAINELEMENT") != null ? "greenEl" :
                                                  matcher.group("BRACKET") != null ? "bracket" :
                                                  matcher.group("ATTR") != null ? "ATTR" :
                                                          matcher.group("STAG") != null ? "start-tag" :
                                                                          matcher.group("DATA") != null ? "data" :
                                                                                  matcher.group("BRACKET") != null ? "bracket" :
                                                                                          null; /* never happens */
          assert styleClass != null;
          spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);

          if (styleClass.equals("ATTR")) {
              String attributesText = matcher.group("ATTR");
              lastKwEnd = 0;
              Matcher aMatcher = ATTRIBUTES.matcher(attributesText);
              while (aMatcher.find()) {
                  spansBuilder.add(Collections.emptyList(), aMatcher.start() - lastKwEnd);
                  spansBuilder.add(Collections.singleton("attribute"), aMatcher.end(GROUP_ATTRIBUTE_NAME) - aMatcher.start(GROUP_ATTRIBUTE_NAME));
                  spansBuilder.add(Collections.singleton("equal"), aMatcher.end(GROUP_EQUAL_SYMBOL) - aMatcher.end(GROUP_ATTRIBUTE_NAME));
                  spansBuilder.add(Collections.singleton("att_value"), aMatcher.end(GROUP_ATTRIBUTE_VALUE) - aMatcher.end(GROUP_EQUAL_SYMBOL));
                  lastKwEnd = aMatcher.end();
              }
              if (attributesText.length() > lastKwEnd)
                  spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
              lastKwEnd = matcher.end("ATTR");
          } else {
              spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
              lastKwEnd = matcher.end();
          }
      }

  }


        //dtdHL
        if(fileType.equals("dtd")){
            int lastKwEnd = 0;
            Pattern dtdPattern = Pattern.compile("(?<ELEMENT>(?<=<)!\\w+)|(?<BRACKET><|>)");
            Matcher matcher = dtdPattern.matcher(text);
            while (matcher.find()) {
                String styleClass =
                        matcher.group("ELEMENT") != null ? "bracket" :
                                matcher.group("BRACKET") != null ? "bracket" :null;
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }

        }
        spansBuilder.add(Collections.emptyList(),0);
        return spansBuilder.create();

    }
    String errorFormat(String Err){

        System.out.println(Err);
        return Err.replaceAll("of document file.*\\.xml","");
    }
    public void errorResolver(){

        Pattern element_reg = Pattern.compile("(?<=(type \"))\\w*(?=\")", Pattern.CASE_INSENSITIVE);
        Pattern line_reg = Pattern.compile("(?<=Error on line )[0-9]+(?=:)");
        Pattern tagValid = Pattern.compile("((?<=(type \"))\\w*(?=\" is incomplete))|((?<=(type \\\"))\\w*(?=\\\"))");
        Pattern validAttribute = Pattern.compile("(?<=((Attribute|Attribute name) \"))\\w*(?=\")");
        Pattern attr_Err = Pattern.compile("(?<=((Attribute name) \"))\\w*(?=\")");
        Matcher matcher;
        errorString=(errorMessages[1].equals(NullPlaceHolder) ?errorFormat(errorMessages[0]):errorFormat(errorMessages[1]));
        infoString="F: "+(wellFormed?"OK":"NO")+" V: "+(noXmlValidator?"unset":valid?"OK":"NO");
        if(!wellFormed){
             matcher = element_reg.matcher(errorMessages[0]);
            unformattedTag =matcher.find()?matcher.group(0): unformattedTag;
            matcher = attr_Err.matcher(errorMessages[0]);
            unformattedAttribute =matcher.find()?matcher.group(0):unformattedAttribute;
            matcher = line_reg.matcher(errorMessages[1]);
            errorLines[0]= matcher.find()?Integer.parseInt(matcher.group(0)):-1;
        }
        if(!valid){
            matcher = tagValid.matcher(errorMessages[1]);
            invalidTag =matcher.find()?matcher.group(0): invalidTag;
            matcher = line_reg.matcher(errorMessages[1]);
            errorLines[1]= matcher.find()?Integer.parseInt(matcher.group(0)):-1;
            matcher = validAttribute.matcher(errorMessages[1]);
            invalidAttribute = matcher.find() ? matcher.group(0) : invalidAttribute;
        }
    }

}
