<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:str="http://exslt.org/strings"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="authorship-page">
                    <fo:region-body margin="0.25in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="authorship-page">
                <fo:flow flow-name="xsl-region-body" font-family="Arial">

                    <fo:block font-weight="bold" text-align="center" font-size="14px"
                              padding="10px">
                        Izjveštaj za zahtjeve za autorska prava
                    </fo:block>

                    <fo:block margin-left="100px" margin-bottom="30px">
                        U periodu:
                        <fo:block font-size="16px">
                            <xsl:variable name="datum_prijema" select="str:tokenize(//startDate, '-')"/>
                            <xsl:value-of select="$datum_prijema[3]"/>.
                            <xsl:value-of select="$datum_prijema[2]"/>.
                            <xsl:value-of select="$datum_prijema[1]"/>.
                            -
                            <xsl:variable name="datum_prijema" select="str:tokenize(//endDate, '-')"/>
                            <xsl:value-of select="$datum_prijema[3]"/>.
                            <xsl:value-of select="$datum_prijema[2]"/>.
                            <xsl:value-of select="$datum_prijema[1]"/>.
                        </fo:block>
                    </fo:block>

                    <fo:block>
                        <fo:table>
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="2px solid black" padding="5px">
                                        <fo:block>
                                            Broj zahteva:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" padding="5px">
                                        <fo:block>
                                            <xsl:value-of select="//number"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row color="green">
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" padding="5px">
                                        <fo:block>
                                            Broj prihvaćenih zahteva:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" padding="5px">
                                        <fo:block>
                                            <xsl:value-of select="//approved"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row color="red">
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" padding="5px">
                                        <fo:block>
                                            Broj odbijenih zahteva:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" padding="5px">
                                        <fo:block>
                                            <xsl:value-of select="//declined"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>