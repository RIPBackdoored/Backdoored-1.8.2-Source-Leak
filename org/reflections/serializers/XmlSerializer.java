package org.reflections.serializers;

import java.lang.reflect.*;
import java.util.*;
import org.reflections.util.*;
import org.dom4j.io.*;
import java.io.*;
import org.dom4j.*;
import org.reflections.*;

public class XmlSerializer implements Serializer
{
    public XmlSerializer() {
        super();
    }
    
    @Override
    public Reflections read(final InputStream v0) {
        Reflections v;
        try {
            final Constructor<Reflections> a1 = Reflections.class.getDeclaredConstructor((Class<?>[])new Class[0]);
            a1.setAccessible(true);
            v = a1.newInstance(new Object[0]);
        }
        catch (Exception v13) {
            v = new Reflections(new ConfigurationBuilder());
        }
        try {
            final Document v2 = new SAXReader().read(v0);
            for (final Object v3 : v2.getRootElement().elements()) {
                final Element v4 = (Element)v3;
                for (final Object v5 : v4.elements()) {
                    final Element v6 = (Element)v5;
                    final Element v7 = v6.element("key");
                    final Element v8 = v6.element("values");
                    for (final Object v9 : v8.elements()) {
                        final Element v10 = (Element)v9;
                        v.getStore().getOrCreate(v4.getName()).put((Object)v7.getText(), (Object)v10.getText());
                    }
                }
            }
        }
        catch (DocumentException v11) {
            throw new ReflectionsException("could not read.", (Throwable)v11);
        }
        catch (Throwable v12) {
            throw new RuntimeException("Could not read. Make sure relevant dependencies exist on classpath.", v12);
        }
        return v;
    }
    
    @Override
    public File save(final Reflections v-2, final String v-1) {
        final File v0 = Utils.prepareFile(v-1);
        try {
            final Document a1 = this.createDocument(v-2);
            final XMLWriter a2 = new XMLWriter((OutputStream)new FileOutputStream(v0), OutputFormat.createPrettyPrint());
            a2.write(a1);
            a2.close();
        }
        catch (IOException v2) {
            throw new ReflectionsException("could not save to file " + v-1, v2);
        }
        catch (Throwable v3) {
            throw new RuntimeException("Could not save to file " + v-1 + ". Make sure relevant dependencies exist on classpath.", v3);
        }
        return v0;
    }
    
    @Override
    public String toString(final Reflections v-2) {
        final Document document = this.createDocument(v-2);
        try {
            final StringWriter a1 = new StringWriter();
            final XMLWriter v1 = new XMLWriter((Writer)a1, OutputFormat.createPrettyPrint());
            v1.write(document);
            v1.close();
            return a1.toString();
        }
        catch (IOException v2) {
            throw new RuntimeException();
        }
    }
    
    private Document createDocument(final Reflections v-8) {
        final Store store = v-8.getStore();
        final Document document = DocumentFactory.getInstance().createDocument();
        final Element addElement = document.addElement("Reflections");
        for (final String s : store.keySet()) {
            final Element addElement2 = addElement.addElement(s);
            for (final String v0 : store.get(s).keySet()) {
                final Element v2 = addElement2.addElement("entry");
                v2.addElement("key").setText(v0);
                final Element v3 = v2.addElement("values");
                for (final String a1 : store.get(s).get(v0)) {
                    v3.addElement("value").setText(a1);
                }
            }
        }
        return document;
    }
}
