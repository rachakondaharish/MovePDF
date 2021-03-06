package com.ej.entities;
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.03 at 05:50:07 PM IST 
//




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VNDRACCNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CNTRYCD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STMTYR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STMTMO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EFFDA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ACCTID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STMTDOCTRACKINGID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FILENAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FILEPATH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vndraccno",
    "cntrycd",
    "stmtyr",
    "stmtmo",
    "effda",
    "acctid",
    "stmtdoctrackingid",
    "filename",
    "filepath"
})
@XmlRootElement(name = "AccountInfo")
public class AccountInfo {

    @XmlElement(name = "VNDRACCNO", required = true)
    protected String vndraccno;
    @XmlElement(name = "CNTRYCD", required = true)
    protected String cntrycd;
    @XmlElement(name = "STMTYR", required = true)
    protected String stmtyr;
    @XmlElement(name = "STMTMO", required = true)
    protected String stmtmo;
    @XmlElement(name = "EFFDA", required = true)
    protected String effda;
    @XmlElement(name = "ACCTID", required = true)
    protected String acctid;
    @XmlElement(name = "STMTDOCTRACKINGID", required = true)
    protected String stmtdoctrackingid;
    @XmlElement(name = "FILENAME", required = true)
    protected String filename;
    @XmlElement(name = "FILEPATH", required = true)
    protected String filepath;

    /**
     * Gets the value of the vndraccno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVNDRACCNO() {
        return vndraccno;
    }

    /**
     * Sets the value of the vndraccno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVNDRACCNO(String value) {
        this.vndraccno = value;
    }

    /**
     * Gets the value of the cntrycd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTRYCD() {
        return cntrycd;
    }

    /**
     * Sets the value of the cntrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTRYCD(String value) {
        this.cntrycd = value;
    }

    /**
     * Gets the value of the stmtyr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTMTYR() {
        return stmtyr;
    }

    /**
     * Sets the value of the stmtyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTMTYR(String value) {
        this.stmtyr = value;
    }

    /**
     * Gets the value of the stmtmo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTMTMO() {
        return stmtmo;
    }

    /**
     * Sets the value of the stmtmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTMTMO(String value) {
        this.stmtmo = value;
    }

    /**
     * Gets the value of the effda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEFFDA() {
        return effda;
    }

    /**
     * Sets the value of the effda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEFFDA(String value) {
        this.effda = value;
    }

    /**
     * Gets the value of the acctid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCTID() {
        return acctid;
    }

    /**
     * Sets the value of the acctid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCTID(String value) {
        this.acctid = value;
    }

    /**
     * Gets the value of the stmtdoctrackingid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTMTDOCTRACKINGID() {
        return stmtdoctrackingid;
    }

    /**
     * Sets the value of the stmtdoctrackingid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTMTDOCTRACKINGID(String value) {
        this.stmtdoctrackingid = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFILENAME() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFILENAME(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the filepath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFILEPATH() {
        return filepath;
    }

    /**
     * Sets the value of the filepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFILEPATH(String value) {
        this.filepath = value;
    }

}
