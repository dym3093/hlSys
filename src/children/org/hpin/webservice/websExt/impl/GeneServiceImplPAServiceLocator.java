/**
 * GeneServiceImplPAServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.hpin.webservice.websExt.impl;

public class GeneServiceImplPAServiceLocator extends org.apache.axis.client.Service implements org.hpin.webservice.websExt.impl.GeneServiceImplPAService {

    public GeneServiceImplPAServiceLocator() {
    }


    public GeneServiceImplPAServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GeneServiceImplPAServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GeneServiceImplPAPort
    private java.lang.String GeneServiceImplPAPort_address = "http://192.168.1.56:8088/websGene/servicePA";

    public java.lang.String getGeneServiceImplPAPortAddress() {
        return GeneServiceImplPAPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GeneServiceImplPAPortWSDDServiceName = "GeneServiceImplPAPort";

    public java.lang.String getGeneServiceImplPAPortWSDDServiceName() {
        return GeneServiceImplPAPortWSDDServiceName;
    }

    public void setGeneServiceImplPAPortWSDDServiceName(java.lang.String name) {
        GeneServiceImplPAPortWSDDServiceName = name;
    }

    public org.hpin.webservice.websExt.impl.GeneServiceImplPA getGeneServiceImplPAPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GeneServiceImplPAPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGeneServiceImplPAPort(endpoint);
    }

    public org.hpin.webservice.websExt.impl.GeneServiceImplPA getGeneServiceImplPAPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceSoapBindingStub _stub = new org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGeneServiceImplPAPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGeneServiceImplPAPortEndpointAddress(java.lang.String address) {
        GeneServiceImplPAPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.hpin.webservice.websExt.impl.GeneServiceImplPA.class.isAssignableFrom(serviceEndpointInterface)) {
                org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceSoapBindingStub _stub = new org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceSoapBindingStub(new java.net.URL(GeneServiceImplPAPort_address), this);
                _stub.setPortName(getGeneServiceImplPAPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GeneServiceImplPAPort".equals(inputPortName)) {
            return getGeneServiceImplPAPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.websExt.webservice.hpin.org/", "GeneServiceImplPAService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.websExt.webservice.hpin.org/", "GeneServiceImplPAPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GeneServiceImplPAPort".equals(portName)) {
            setGeneServiceImplPAPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
