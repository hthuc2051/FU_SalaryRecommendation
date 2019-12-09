<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : jobDetails.xsl
    Created on : November 28, 2019, 12:59 PM
    Author     : HP
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8" />
    
    <xsl:template match="/">
        <xsl:element name="jobs">
            <xsl:variable name="nameData" select="//div[contains(normalize-space(@class), 'job-listing-title')]"/>   
            <xsl:element name="jobName">
                <xsl:value-of select="$nameData"/>
            </xsl:element>
            <xsl:element name="jobSalary">
                <xsl:value-of select="//table/tr[./th[contains(text(),'Sala')]]/td"/>
            </xsl:element>
            <xsl:element name="jobDetails">
                <xsl:value-of select="//div[contains(@class,'job-listing-body')]"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>

