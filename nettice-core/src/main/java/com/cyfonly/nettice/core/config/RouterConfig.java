package com.cyfonly.nettice.core.config;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.ConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.xml.sax.InputSource;

public class RouterConfig {

	private List<String> actionPackages;
	
	private List<Namespace> namespaces;

	private static final String NAME = "name";
	
	private static final String PACKAGES = "packages";

	public static RouterConfig parse(String filePath) throws Exception {
		System.out.println(filePath);
		File file = Files.findFile(filePath);
		if (Lang.isEmpty(file)) {
			throw new IllegalArgumentException("config file [" + filePath + "] not exists");
		}
		return parse(Streams.fileIn(file));
	}

	private static RouterConfig parse(InputStream ins) throws Exception {
		RouterConfig config = new RouterConfig();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new InputSource(ins));
		} catch (DocumentException e) {
			throw new ConfigurationException("config file parse error");
		}
		if (document != null) {
			Element rootElement = document.getRootElement();
			Iterator<?> ie = rootElement.elementIterator();
			while (ie.hasNext()) {
				Element element = (Element) ie.next();
				if ("action-package".equals(element.getName())) {
					config.actionPackages = parseActionPackages(element);
				} else if ("namespaces".equals(element.getName())) {
					config.namespaces = parseNamespaces(element);
				}
			}
		}
		return config;
	}

	private static List<String> parseActionPackages(Element actionPackagesElem) {
		Iterator<?> packageInterator = actionPackagesElem.elementIterator();
		List<String> packages = new ArrayList<String>();
		while (packageInterator.hasNext()) {
			Element element = (Element) packageInterator.next();
			packages.add(element.getTextTrim());
		}
		return packages;
	}

	private static List<Namespace> parseNamespaces(Element namespaceElement) {
		Iterator<?> elementIterator = namespaceElement.elementIterator();
		List<Namespace> namespaces = new ArrayList<Namespace>();
		while (elementIterator.hasNext()) {
			Element element = (Element) elementIterator.next();
			String name = element.attributeValue(NAME);
			String packages = element.attributeValue(PACKAGES);
			Namespace ns = new Namespace(name, packages);
			namespaces.add(ns);
		}
		return namespaces;
	}

	public List<String> getActionPacages() {
		return actionPackages;
	}

	public List<Namespace> getNamespaces() {
		return namespaces;
	}

}
