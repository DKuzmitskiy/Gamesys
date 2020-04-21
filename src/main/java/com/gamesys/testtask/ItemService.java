package com.gamesys.testtask;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.stream.XMLInputFactory.newInstance;

@Service
@Slf4j
public class ItemService {

    @Value("${url}")
    @Getter
    private String urlString;

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        URL url = getUrl();
        if (url == null) return items;

        try(InputStream inputStream = url.openStream()) {
            XMLEventReader reader = newInstance().createXMLEventReader(inputStream);
            Item item = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    if ("item".equals(element.getName().getLocalPart())) {
                        item = new Item();
                    }
                    if (item != null) {
                        if ("guid".equals(element.getName().getLocalPart())) {
                            item.setGuid(reader.nextEvent().asCharacters().getData());
                        }
                        if ("pubDate".equals(element.getName().getLocalPart())) {
                            item.setPubDate(reader.nextEvent().asCharacters().getData());
                        }
                        if ("title".equals(element.getName().getLocalPart())) {
                            item.setTitle(reader.nextEvent().asCharacters().getData());
                        }
                        if ("link".equals(element.getName().getLocalPart())) {
                            item.setLink(reader.nextEvent().asCharacters().getData());
                        }
                        if ("description".equals(element.getName().getLocalPart())) {
                            item.setDescription(reader.nextEvent().asCharacters().getData());
                        }
                    }
                }
                if (event.isEndElement()) {
                    EndElement element = event.asEndElement();
                    if ("item".equals(element.getName().getLocalPart()) && item != null) {
                        item.setCategory("science");
                        items.add(item);
                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            log.error("Cannot parse items from url=" + url.toString() + " with cause: " + e.getMessage());
        }
        return items;
    }

    private URL getUrl() {
        try {
            return new URL(getUrlString());
        } catch (MalformedURLException e) {
            log.error("Cannot find url. " + e.getMessage());
        }
        return null;
    }

}
