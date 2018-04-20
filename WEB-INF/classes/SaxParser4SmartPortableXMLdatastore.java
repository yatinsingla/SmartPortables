import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser4SmartPortableXMLdatastore extends DefaultHandler {
    String consoleXmlFileName;
    String elementValueRead;

    Smartwatch smartwatch;
    List<Smartwatch> smartwatches;

    Headphone headphone;
    List<Headphone> headphones;

    Phone phone;
    List<Phone> phones;

    Laptop laptop;
    List<Laptop> laptops;

    ExternalStorage external;
    List<ExternalStorage> externals;

    Speaker speaker;
    List<Speaker> speakers;

    Accessory accessory;
    List<Accessory> accessories;

    public SaxParser4SmartPortableXMLdatastore(String consoleXmlFileName) {
        this.consoleXmlFileName = consoleXmlFileName;
        smartwatches = new ArrayList<Smartwatch>();
        speakers = new ArrayList<Speaker>();
        headphones = new ArrayList<Headphone>();
        phones = new ArrayList<Phone>();
        laptops = new ArrayList<Laptop>();
        externals = new ArrayList<ExternalStorage>();
        accessories = new ArrayList<Accessory>();
        parseDocument();
        // prettyPrint();
    }

    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    /* private void prettyPrint() {
	
        for (Console console: consoles) {
            System.out.println("console #"+ console.id +":");
            System.out.println("\t\t retailer: " + console.retailer);
            System.out.println("\t\t name: " + console.name);
            for (String accessory: console.accessories) {
                System.out.println("\t\t accessory: " + accessory);
            }
        }
    }*/

    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("smartwatch")) {
            smartwatch = new Smartwatch();
            smartwatch.setId(attributes.getValue("id"));
            smartwatch.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("headphone")) {
            headphone = new Headphone();
            headphone.setId(attributes.getValue("id"));
            headphone.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("speaker")) {
            speaker = new Speaker();
            speaker.setId(attributes.getValue("id"));
            speaker.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("phone")) {
            phone = new Phone();
            phone.setId(attributes.getValue("id"));
            phone.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("laptop")) {
            laptop = new Laptop();
            laptop.setId(attributes.getValue("id"));
            laptop.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("externalstorage")) {
            external = new ExternalStorage();
            external.setId(attributes.getValue("id"));
            external.setRetailer(attributes.getValue("retailer"));
        }

        if (elementName.equalsIgnoreCase("accessorycat")) {
            accessory = new Accessory();
            accessory.setId(attributes.getValue("id"));
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        // for smartwatches
        if (element.equals("smartwatch")) {
            smartwatches.add(smartwatch);
	    return;
        }
        if (element.equalsIgnoreCase("image")) {
            smartwatch.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("name")) {
            smartwatch.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("accessory")){
           smartwatch.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("price")){
            smartwatch.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("condition")){
            smartwatch.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("discount")){
            smartwatch.setDiscount(elementValueRead);
	    return;
        }
        
        // for headphones 
        if (element.equals("headphone")) {
            headphones.add(headphone);
	    return;
        }
        if (element.equalsIgnoreCase("himage")) {
            headphone.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("hname")) {
            headphone.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("haccessory")){
           headphone.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("hprice")){
            headphone.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("hcondition")){
            headphone.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("hdiscount")){
            smartwatch.setDiscount(elementValueRead);
	    return;
        }

        // for speakers 
        if (element.equals("speaker")) {
            speakers.add(speaker);
	    return;
        }
        if (element.equalsIgnoreCase("simage")) {
            speaker.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("sname")) {
            speaker.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("saccessory")){
           speaker.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("sprice")){
            speaker.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("scondition")){
            speaker.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("sdiscount")){
            speaker.setDiscount(elementValueRead);
	    return;
        }

        // for phones 
        if (element.equals("phone")) {
            phones.add(phone);
	    return;
        }
        if (element.equalsIgnoreCase("pimage")) {
            phone.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("pname")) {
            phone.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("paccessory")){
           phone.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("pprice")){
            phone.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("pcondition")){
            phone.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("pdiscount")){
            phone.setDiscount(elementValueRead);
	    return;
        }

        // for laptops 
        if (element.equals("laptop")) {
            laptops.add(laptop);
	    return;
        }
        if (element.equalsIgnoreCase("limage")) {
            laptop.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("lname")) {
            laptop.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("laccessory")){
           laptop.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("lprice")){
            laptop.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("lcondition")){
            laptop.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("ldiscount")){
            laptop.setDiscount(elementValueRead);
	    return;
        }

        // for external storage 
        if (element.equals("externalstorage")) {
            externals.add(external);
	    return;
        }
        if (element.equalsIgnoreCase("eimage")) {
            external.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("ename")) {
            external.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("eaccessory")){
           external.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("eprice")){
            external.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("econdition")){
            external.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("ediscount")){
            external.setDiscount(elementValueRead);
	    return;
        }

        // for accessories
        if(element.equals("accessorycat")) {
            accessories.add(accessory);
        return;
        }

        if (element.equalsIgnoreCase("aimage")) {
            accessory.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("aname")) {
            accessory.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("aprice")){
            accessory.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("acondition")){
            accessory.setCondition(elementValueRead);
	    return;
        }

    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


}