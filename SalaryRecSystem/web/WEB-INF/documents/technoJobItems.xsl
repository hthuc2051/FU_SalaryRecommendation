<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : technoJobItems.xsl
    Created on : November 28, 2019, 12:51 PM
    Author     : HP
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:element name="listLink">
            <xsl:for-each select="//div[contains(normalize-space(@class),'job-ti')]/h/a">
                <xsl:element name="link">
                    <xsl:value-of select="@href"/>
                </xsl:element>
                <xsl:element name="jobName">
                    <xsl:value-of select="text()"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
