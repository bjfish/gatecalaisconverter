//
// Generated from archetype; please customize.
//

package org.gateconverter

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;

import groovy.util.GroovyTestCase

/**
 * Tests for the {@link Example} class.
 */
class ConverterTest
    extends GroovyTestCase
{
    void testConvert() {
		FileInputStream input = new FileInputStream("src/test/resources/gate-doc-serialized.xml");
		XStream xstream = new XStream();
		gate.Document gateDocument = xstream.fromXML(input);
		Document doc = Converter.gateDocumentToCalaisRDF(gateDocument);
    }
}
