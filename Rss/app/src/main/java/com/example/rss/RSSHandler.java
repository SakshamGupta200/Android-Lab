package com.example.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

public class RSSHandler extends DefaultHandler {

    private ArrayList<String> items;
    private StringBuilder currentValue = new StringBuilder();
    private boolean isItem = false;

    public RSSHandler(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            isItem = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isItem) {
            if (qName.equalsIgnoreCase("title")) {
                items.add(currentValue.toString());
            }
            currentValue.setLength(0);
        }
        if (qName.equalsIgnoreCase("item")) {
            isItem = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isItem) {
            currentValue.append(ch, start, length);
        }
    }
}
