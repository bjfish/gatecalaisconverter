package org.gateconverter;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Document;

class Converter {
	static Document gateDocumentToCalaisRDF(gate.Document gateDocument){
		def Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		org.w3c.dom.Document dom = db.newDocument();
		
		Element rootElement = dom.createElement("RDF");
		dom.appendChild(rootElement);
		
		AnnotationSet annotationSet = doc.getAnnotations().get("Lookup");
		
		Iterator<Annotation> iter = annotationSet.iterator();
		while (iter.hasNext()) {
			Annotation ann = (Annotation)iter.next();
			Element descriptionElement = createDescriptionElementFromAnnotation(ann, dom, doc);
			rootElement.appendChild(descriptionElement);
		}
		return doc;
	}
	
	static Element createDescriptionElementFromAnnotation(Annotation ann, Document dom, gate.Document doc){
		FeatureMap features = ann.getFeatures();
		
		Element description = dom.createElement("Description");
		
		Element type = dom.createElement("Type");
		type.setAttribute("resource", (String) features.get("classURI"));
		description.appendChild(type);
		
		Element subject= dom.createElement("Subject");
		subject.setAttribute("resource", (String) features.get("URI"));
		description.appendChild(subject);
		
		Element exact = dom.createElement("exact");
		String strExact = null;
		try {
			strExact = doc.getContent().getContent(ann.getStartNode().getOffset().longValue(), ann.getEndNode().getOffset().longValue()).toString();
		} catch (InvalidOffsetException e) {
			e.printStackTrace();
		}
		Text txtExact = dom.createTextNode(strExact);
		exact.appendChild(txtExact);
		description.appendChild(exact);
		
		Element offset = dom.createElement("offset");
		Integer intOffset = ann.getStartNode().getOffset().intValue();
		Text txtOffset = dom.createTextNode(intOffset.toString());
		offset.appendChild(txtOffset);
		description.appendChild(offset);
		
		Element length = dom.createElement("length");
		Integer intLength = ann.getEndNode().getOffset().intValue() - intOffset;
		Text txtLength = dom.createTextNode(intLength.toString());
		length.appendChild(txtLength);
		description.appendChild(length);
		
		return description;
	}
}
