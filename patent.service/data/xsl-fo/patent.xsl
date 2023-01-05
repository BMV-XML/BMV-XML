<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:pat="http://www.ftn.uns.ac.rs/patent"
                xmlns:base="http://www.ftn.uns.ac.rs/base-schame"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:str="http://exslt.org/strings"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="patent-page">
                    <fo:region-body margin="0.25in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="patent-page">
                <fo:flow flow-name="xsl-region-body">

                    <!-- Prva tabela -->
                    <fo:block>
                        <fo:table border="1px">
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="40%"/>
                            <fo:table-body>
                                <fo:table-row border="2px solid black">
                                    <fo:table-cell border="1px solid black" number-columns-spanned="2"
                                                   font-family="sans-serif" padding-left="10px" padding-bottom="10px"
                                                   padding-top="3">
                                        <fo:block>
                                            <fo:block font-size="10">
                                                Broj prijave
                                            </fo:block>
                                            <xsl:value-of
                                                    select="pat:Zahtev_za_priznavanje_patenta/pat:Podaci_o_zahtevu/pat:Broj_prijave"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="2px solid darkgrey">
                                    <fo:table-cell border="2px solid black" font-family="sans-serif" padding-left="10px"
                                                   padding-bottom="10px" padding-top="3">
                                        <fo:block>
                                            <fo:block font-size="10">
                                                Datum prijema
                                            </fo:block>
                                            <xsl:value-of
                                                    select="pat:Zahtev_za_priznavanje_patenta/pat:Podaci_o_zahtevu/pat:Datum_prijave"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="2px solid black" font-family="sans-serif" padding-left="10px"
                                                   padding-bottom="10px" padding-top="3">
                                        <fo:block>
                                            <fo:block font-size="10">
                                                Priznati datum podnosenja
                                            </fo:block>
                                            <xsl:value-of
                                                    select="pat:Zahtev_za_priznavanje_patenta/pat:Podaci_o_zahtevu/pat:Priznati_datum_podnosenja"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- Primalac -->
                    <fo:block font-family="sans-serif" font-size="10" margin="3px 0px 30px 20px" padding="2px">
                        <fo:block>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Drzava"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Naziv"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Ulica"/>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Broj"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Postanski_broj"/>
                            &#160;
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Grad"/>
                        </fo:block>
                    </fo:block>

                    <!-- Naslov -->
                    <fo:block font-weight="bold" text-align="center" font-size="14px">
                        Zahtev za priznanje patenta
                    </fo:block>

                    <!-- Podnosilac -->
                    <fo:block>
                        <fo:table font-family="Arial" border="2px solid black" border-collapse="collapse">
                            <fo:table-column column-width="33%"/>
                            <fo:table-column column-width="33%"/>
                            <fo:table-column column-width="33%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell number-columns-spanned="3" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Naziv Pronalaska
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <!-- Naslov -->
                                <xsl:for-each select="pat:Zahtev_za_priznavanje_patenta/pat:Naslov">
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" padding="2px 3px 0px 20px">
                                            <fo:block font-size="10">
                                                Na
                                                <xsl:value-of select="@jezik"/> jeziku:
                                                <xsl:value-of select="text()"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>

                                <!-- Podnosilac -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell number-columns-spanned="1" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Podnosilac prijave
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" padding="15px 0px 10px 50px">
                                        <xsl:if test="//pat:Podnosilac/pat:Podnosilac_je_i_pronalazac[text() = 'DA']">
                                            <fo:block color="blue">Podnosilac je i pronalazac</fo:block>
                                        </xsl:if>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell border="1px solid black" number-rows-spanned="3"
                                                   padding="2px 0px 0px 20px">
                                        <xsl:choose>
                                            <xsl:when
                                                    test="//pat:Podnosilac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                                <fo:block padding="5">Ime i prezime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Prezime"/>
                                                </fo:block>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block padding="5">Poslovno ime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of
                                                            select="//pat:Podnosilac/pat:Osoba/base:Poslovno_ime"/>
                                                </fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>
                                    <fo:table-cell number-rows-spanned="3" border="1px solid black" padding="5">
                                        <fo:block padding="5">Ulica i broj, poštanski broj, mesto i država</fo:block>
                                        <fo:block padding="5">
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Ulica"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Broj"/>

                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Postanski_broj"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Grad"/>
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid black" padding="5">
                                        <fo:block>
                                            Broj telefona:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            Broj faksa:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            E-pošta:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:if test="//pat:Podnosilac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" padding="10px">
                                            <fo:block>
                                                Državljanstvo:
                                                <xsl:value-of select="'&#x2028;'"/> <!-- ENTER -->
                                                <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Drzavljanstvo"/>
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>
                                </xsl:if>


                                <!-- Pronalazac -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell number-columns-spanned="1" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Pronalazač
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" padding="15px 0px 10px 50px">
                                        <fo:block color="blue">
                                            <xsl:if test="//pat:Pronalazac/pat:Hoce_da_bude_naveden[text() = 'DA']">
                                                Pronalazač hoće da bude naveden
                                            </xsl:if>
                                            <xsl:if test="//pat:Pronalazac/pat:Hoce_da_bude_naveden[text() = 'NE']">
                                                Pronalazač ne hoće da bude naveden
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid black" number-rows-spanned="3"
                                                   padding="2px 0px 0px 20px">
                                        <xsl:choose>
                                            <xsl:when
                                                    test="//pat:Pronalazac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                                <fo:block padding="5">Ime i prezime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Prezime"/>
                                                </fo:block>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block padding="5">Poslovno ime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of
                                                            select="//pat:Pronalazac/pat:Osoba/base:Poslovno_ime"/>
                                                </fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>
                                    <fo:table-cell number-rows-spanned="3" border="1px solid black" padding="5">
                                        <fo:block padding="5">Ulica i broj, poštanski broj, mesto i država</fo:block>
                                        <fo:block padding="5">
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Ulica"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Broj"/>

                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Postanski_broj"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Grad"/>
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid black" padding="5">
                                        <fo:block>
                                            Broj telefona:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            Broj faksa:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            E-pošta:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:if test="//pat:Pronalazac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" padding="10px">
                                            <fo:block>
                                                Državljanstvo:
                                                <xsl:value-of select="'&#x2028;'"/> <!-- ENTER -->
                                                <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Drzavljanstvo"/>
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>
                                </xsl:if>

                            </fo:table-body>
                        </fo:table>

                        <fo:table page-break-before="always" font-family="Arial" border="2px solid black"
                                  border-collapse="collapse" margin="50px 0px 50px 0px">
                            <fo:table-body>
                                <!-- Punomocnik -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell
                                            number-columns-spanned="1" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Punomoćnik
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" padding="15px 0px 10px 50px">
                                        <fo:block color="blue">
                                            <xsl:if test="//pat:Punomocnik/pat:Punomocnik_za_prijem_pismena[text() = 'DA']">
                                                Punomoćnik za prijem pisama
                                            </xsl:if>
                                            <xsl:if test="//pat:Punomocnik/pat:Punomocnik_za_zastupanje[text() = 'DA']">
                                                Punomoćnik za zastupanje
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block color="blue">
                                            <xsl:if test="//pat:Punomocnik/pat:Zajednicki_predstavnik[text() = 'DA']">
                                                Zajednički predstavnik
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid black" number-rows-spanned="3"
                                                   padding="2px 0px 0px 20px">
                                        <xsl:choose>
                                            <xsl:when
                                                    test="//pat:Punomocnik/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                                <fo:block padding="5">Ime i prezime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Prezime"/>
                                                </fo:block>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block padding="5">Poslovno ime</fo:block>
                                                <fo:block padding="5">
                                                    <xsl:value-of
                                                            select="//pat:Punomocnik/pat:Osoba/base:Poslovno_ime"/>
                                                </fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>
                                    <fo:table-cell number-rows-spanned="3" border="1px solid black" padding="5">
                                        <fo:block padding="5">Ulica i broj, poštanski broj, mesto i država</fo:block>
                                        <fo:block padding="5">
                                            <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Ulica"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Broj"/>

                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Postanski_broj"/>
                                            &#160;
                                            <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Grad"/>
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid black" padding="5">
                                        <fo:block>
                                            Broj telefona:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            Broj faksa:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid black">
                                    <fo:table-cell padding="5">
                                        <fo:block>
                                            E-pošta:
                                            <xsl:value-of select="'&#x2028;'"/>
                                            <xsl:value-of
                                                    select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:if test="//pat:Punomocnik/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" padding="10px">
                                            <fo:block>
                                                Državljanstvo:
                                                <xsl:value-of select="'&#x2028;'"/> <!-- ENTER -->
                                                <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Drzavljanstvo"/>
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>
                                </xsl:if>


                                <!-- Adresa za dostavljanje -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell number-columns-spanned="3" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Adresa za dostavljanje
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="3">
                                        <fo:block padding="5">Ulica i broj, poštanski broj, mesto i država</fo:block>
                                        <fo:block padding="5">
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Ulica"/>
                                            <xsl:value-of select="'&#160;'"/> <!-- SPACE -->
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Broj"/>,
                                            <xsl:value-of select="'&#160;'"/>  <!-- SPACE -->
                                            <xsl:value-of
                                                    select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Postanski_broj"/>,
                                            <xsl:value-of select="'&#160;'"/> <!-- SPACE -->
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Grad"/>,
                                            <xsl:value-of select="'&#160;'"/> <!-- SPACE -->
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>


                                <!-- Način dostavljanja -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell number-columns-spanned="3" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Način dostavljanja
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="3" padding="5px">
                                        <fo:block>
                                            <xsl:if test="//pat:Dostavljanje/pat:Obavesti_me_elektronskim_putem[text() = 'DA']">
                                                Obavesti me elektorskim putem
                                            </xsl:if>
                                            <xsl:if test="//pat:Dostavljanje/pat:Obavesti_me_preko_poste[text() = 'DA']">
                                                Obavesti me preko poste
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>


                                <!-- Tip prijave -->
                                <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                    <fo:table-cell number-columns-spanned="1" padding="10px 0px 10px 15px">
                                        <fo:block font-size="15">
                                            Tip prijave
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" padding="15px 0px 10px 50px">
                                        <fo:block>
                                            <xsl:choose>
                                                <xsl:when
                                                        test="//pat:Dodatna_prijava/pat:Dopunska_prijava[text() = 'DA']">
                                                    Dopunska prijava
                                                </xsl:when>
                                                <xsl:when
                                                        test="//pat:Dodatna_prijava/pat:Izdvojena_prijava[text() = 'DA']">
                                                    Izdvojena prijava
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    Jednostavna prijava
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:if test="//pat:Dodatna_prijava/pat:Dopunska_prijava[text() = 'DA'] | //pat:Dodatna_prijava/pat:Izdvojena_prijava[text() = 'DA']">
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block>
                                                Proj osnovne prijave:
                                                <xsl:value-of
                                                        select="//pat:Dodatna_prijava/pat:Broj_prijave"></xsl:value-of>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block>
                                                Datum podnošenja osnovne prijave:
                                                <xsl:value-of
                                                        select="//pat:Dodatna_prijava/pat:Datum_podnosenja"></xsl:value-of>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>


                                <!-- Prava prvenstva  -->

                                <xsl:if test="count(//pat:Prava_prvenstva) > 0">
                                    <fo:table-row border-top="2px solid black" border-bottom="1px solid black">
                                        <fo:table-cell number-columns-spanned="3" padding="10px 0px 10px 15px">
                                            <fo:block font-size="15">
                                                Prava prvenstva
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row border="1px solid black">
                                        <fo:table-cell padding="10px 0px 10px 0px" border="1px solid black">
                                            <fo:block>
                                                Datum podnošenja ranije prijave
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px 0px 10px 0px" border="1px solid black">
                                            <fo:block>
                                                Broj ranije prijave
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px 0px 10px 0px" border="1px solid black">
                                            <fo:block>
                                                Dvoslovna oznaka države
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:for-each select="//pat:Prava_prvenstva">
                                        <fo:table-row border="1px solid black">
                                            <fo:table-cell padding="10px 0px 10px 15px" border="1px solid black">
                                                <fo:block>
                                                    <xsl:value-of select="position()"/>.
                                                    <!--<xsl:value-of select="format-date(pat:Datum_podnosenja, 'dd. MM. yyyy.')"/>-->

                                                    <xsl:variable name="tokens" select="str:tokenize(pat:Datum_podnosenja, '-')"/>
                                                    <xsl:variable name="day" select="$tokens[3]"/>
                                                    <xsl:variable name="month" select="$tokens[2]"/>
                                                    <xsl:variable name="year" select="$tokens[1]"/>
                                                    <xsl:value-of select="$day"></xsl:value-of>.
                                                    <xsl:value-of select="$month"></xsl:value-of>.
                                                    <xsl:value-of select="$year"></xsl:value-of>.
<!--                                                    <xsl:value-of select="pat:Datum_podnosenja"/>-->
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="10px 0px 10px 15px" border="1px solid black">
                                                <fo:block>
                                                    <xsl:value-of select="pat:Broj_prjave"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="10px 0px 10px 15px" border="1px solid black">
                                                <fo:block>
                                                    <xsl:value-of select="pat:Drzava"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </xsl:if>

                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>