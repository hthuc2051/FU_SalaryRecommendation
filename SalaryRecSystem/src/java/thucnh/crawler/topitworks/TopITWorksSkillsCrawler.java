/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.topitworks;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import thucnh.crawler.BaseCrawler;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class TopITWorksSkillsCrawler extends BaseCrawler {

    private String url;

    public TopITWorksSkillsCrawler(ServletContext context, String url) {
        super(context);
        this.url = url;
    }

    public TopITWorksSkillsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getSkills() {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            if (reader != null) {
                String line = "";
                String document = "<document>";
                boolean isStart = false;
                boolean isFound = false;
                while ((line = reader.readLine()) != null) {
                    if (isStart && line.contains("<h3>Content Management</h3>")) {
                        break;
                    }
                    if (isStart) {
                        document += line.trim() + "\n";
                    }
                    if (isFound && line.contains("<div class=\"row\">")) {
                        isStart = true;
                    }
                    if (line.contains("<div class=\"col-sm-12 jobbyskill\">")) {
                        isFound = true;
                    }
                }
                document += "</div></document>";
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);

                return stAXParserForSkills(document);
            }
        } catch (Exception e) {
            Logger.getLogger(TopITWorksSkillsCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Logger.getLogger(TopITWorksSkillsCrawler.class.getName() + " - Finally_Reader").log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        return null;
    }

    public Map<String, String> stAXParserForSkills(String document) throws UnsupportedEncodingException, XMLStreamException {

        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> result = new TreeMap<>();
        String skillType = "";
        String link = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                String skillName = "";

                if (tagName.equals("h")) {
                    event = (XMLEvent) eventReader.next();
                    Characters content = event.asCharacters();
                    if (content != null) {
                        skillType = content.getData();
                    }
                }
                if (tagName.equals("a")) {
                    Attribute href = startElement.getAttributeByName(new QName("href"));
                    link = href.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters content = event.asCharacters();
                    if (content != null) {
                        skillName = content.getData();
                    }
                }
                if (!skillName.equalsIgnoreCase("") && !skillType.equalsIgnoreCase("")) {
                    if (skillType.contains("Front End")) {
                        skillType = "FrontEnd";
                    } else if (skillType.contains("Back End")) {
                        skillType = "BackEnd";
                    } else if (skillType.contains("Software") && !skillType.contains("Testing")) {
                        skillType = "Software";
                    } else if (skillType.contains("Mobile")) {
                        skillType = "Mobile";
                    } else if (skillType.contains("Window")) {
                        skillType = "Window";
                    } else if (skillType.contains("Testing")) {
                        skillType = "Testing";
                    }
                    String temp = skillName.toLowerCase();
                    if (temp.contains(".net")) {
                        skillName = ".NET";
                    } else if (temp.contains("test")) {
                        skillName = "Test";
                    } else if (temp.contains("windows")) {
                        skillName = "Windows";
                    } else if (temp.contains("node")) {
                        skillName = "NodeJS";
                    }else if(temp.contains("react")){
                        skillName = "ReactJS";
                    }
                    String key = skillName + "=" + skillType;
                    if (!result.containsKey(key)) {
                        result.put(key, link);
                    }
                }

            }
        }
        return result;
    }
}
