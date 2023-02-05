<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/authorship"
                xmlns:base="http://www.ftn.uns.ac.rs/base-schame"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
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
                <fo:flow flow-name="xsl-region-body">

                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block margin-left="0">
                            <fo:block font-size="10"><xsl:value-of select="aut:Zahtjev/base:Primalac/base:Naziv"/></fo:block>
                            <fo:block font-size="10">
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Grad"/>,&#160;
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Ulica"/>&#160;
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Broj"/>
                            </fo:block>
                        </fo:block>
                        <fo:block text-align="center" margin-bottom="20px">
                            <fo:block font-size="20">ZAHTEV ZA UNOŠENJE U EVIDNCIJU I DEPONOVANJE AUTORSKIH PRAVA</fo:block>
                        </fo:block>
                    </fo:block>

                    <!-- PODNOSILAC -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block font-size="20">PODNOSILAC</fo:block>
                        <fo:table table-layout="fixed" border="1px" width="100%">
                            <fo:table-column column-width="proportional-column-width(1)" number-columns-repeated="2" />
                            <fo:table-body>
                                <fo:table-row>
                                    <xsl:choose>
                                        <xsl:when test="//aut:Podnosilac/aut:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>IME I PREZIME</fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Prezime"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>POSLOVNO IME</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Poslovno_ime"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>
                                        ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA
                                    </fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Ulica"/>
                                            &#160;
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Broj"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Grad"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>TELEFON</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>FAKS</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>E-MAIL</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:if test="//aut:Podnosilac/aut:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DRAŽAVLJANSTVO</fo:block></fo:table-cell>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                            <fo:block>
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Drzavljanstvo"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- PUNOMOCNIK -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block font-size="20">PUNOMOCNIK</fo:block>
                        <xsl:choose>
                            <xsl:when test="not(//aut:Punomocnik)">
                                <fo:block font-size="15">PRIJAVA SE PODNOSI BEZ PUNOMOCNIKA</fo:block>
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:table table-layout="fixed" border="1px" width="100%">
                                    <fo:table-column column-width="proportional-column-width(1)" number-columns-repeated="2" />
                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>IME I PREZIME</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Ime"/>
                                                &#160;
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Prezime"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DRAŽAVLJANSTVO</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Drzavljanstvo"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>
                                                ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA
                                            </fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Ulica"/>
                                                &#160;
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Broj"/>,
                                                &#160;
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                                &#160;
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Grad"/>,
                                                &#160;
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Drzava"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>TELEFON</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:Broj_telefona"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>FAKS</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:Broj_faksa"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>E-MAIL</fo:block></fo:table-cell>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                <fo:block>
                                                <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:E_posta"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-body>
                                </fo:table>
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>

                    <!-- AUTORSKO DJELO -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block font-size="20">AUTORSKO DELO</fo:block>
                        <fo:table table-layout="fixed" border="1px" width="100%">
                            <fo:table-column column-width="proportional-column-width(1)" number-columns-repeated="2" />
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>NASLOV</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Naslov"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:if test="//aut:Autorsko_djelo/aut:Alternativni_naslov">
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>ALTERNATIVNI NASLOV</fo:block></fo:table-cell>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                            <fo:block>
                                            <xsl:value-of select="//aut:Autorsko_djelo/aut:Alternativni_naslov"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>VRSTA DELA</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Vrsta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>FORMA ZAPISA</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Forma_zapisa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DA LI JE DELO STVORENO U RADNOM ODNOSU</fo:block></fo:table-cell>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                        <fo:block>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Stvoreno_u_radnom_odnosu"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:if test="//aut:Autorsko_djelo/aut:Preradjeno_djelo">
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px" number-columns-spanned="2" background-color="#ccc">
                                            <fo:block>&#160;</fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px" number-columns-spanned="2" font-weight="700">
                                            <fo:block>PODACI O DELU NA KOME SE ZASNIVA DELO PRERADE</fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>NASLOV DELA</fo:block></fo:table-cell>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                            <fo:block>
                                            <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Naslov"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>IME AUTORA</fo:block></fo:table-cell>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                            <fo:block>
                                            <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Ime_autora"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>PREZIME AUTORA</fo:block></fo:table-cell>
                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                            <fo:block>
                                            <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Prezime_autora"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- AUTOR -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block font-size="20">AUTOR</fo:block>
                        <xsl:choose>
                            <xsl:when test="//aut:Podnosilac/aut:Podnosilac_je_autor[text() = 'DA']">
                                <fo:block font-size="15">PODNOSILAC &#160; JE &#160; I &#160; AUTOR</fo:block>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:if test="//aut:Autor/aut:Anonimni_autor[text() = 'DA']">
                                    <fo:block font-size="15">AUTOR &#160; JE &#160; ANONIMAN</fo:block>
                                </xsl:if>
                                <xsl:if test="//aut:Autor/aut:Anonimni_autor[text() = 'NE']">
                                    <xsl:for-each select="//aut:Autor">
                                        <fo:table table-layout="fixed" border="1px" width="100%">
                                            <fo:table-column column-width="proportional-column-width(1)" number-columns-repeated="2" />
                                            <fo:table-body>
                                                <fo:table-row>
                                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>IME I PREZIME</fo:block></fo:table-cell>
                                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                        <fo:block>
                                                            <xsl:value-of select="aut:Osoba/base:Ime"/>
                                                            &#160;
                                                            <xsl:value-of select="aut:Osoba/base:Prezime"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                                <xsl:if test="aut:Pseudonim">
                                                    <fo:table-row>
                                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>PSEUDONIM</fo:block></fo:table-cell>
                                                        <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                            <fo:block>
                                                            <xsl:value-of select="aut:Pseudonim"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>
                                                <xsl:choose>
                                                    <xsl:when test="aut:Godina_smrti[text() = 0]">
                                                        <fo:table-row>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DRŽAVLJANSTVO</fo:block></fo:table-cell>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                                <fo:block>
                                                                <xsl:value-of select="aut:Osoba/base:Drzavljanstvo"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                        <fo:table-row>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                                <fo:block>
                                                                ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA
                                                                </fo:block>
                                                            </fo:table-cell>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                                <fo:block>
                                                                    <xsl:value-of select="aut:Osoba/base:Adresa/base:Ulica"/>
                                                                    &#160;
                                                                    <xsl:value-of select="aut:Osoba/base:Adresa/base:Broj"/>,
                                                                    &#160;
                                                                    <xsl:value-of select="aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                                                    &#160;
                                                                    <xsl:value-of select="aut:Osoba/base:Adresa/base:Grad"/>,
                                                                    &#160;
                                                                    <xsl:value-of select="aut:Osoba/base:Adresa/base:Drzava"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <fo:table-row>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>GODINA SMRTI</fo:block></fo:table-cell>
                                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px">
                                                                <fo:block>
                                                                <xsl:value-of select="aut:Godina_smrti"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </fo:table-body>
                                        </fo:table>
                                        <fo:block linefeed-treatment="preserve">&#xA;</fo:block>
                                    </xsl:for-each>
                                </xsl:if>
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>

                    <!-- PRILOZI -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block font-size="20">PRILOZI</fo:block>
                        <fo:table table-layout="fixed" border="1px" width="100%">
                            <fo:table-column column-width="proportional-column-width(1)" number-columns-repeated="2" />
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>OPIS AUTORSKOG DJELA</fo:block></fo:table-cell>
                                    <xsl:choose>
                                        <xsl:when test="//aut:Prilozi/aut:Opis_djela">
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DA</fo:block></fo:table-cell>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>NE</fo:block></fo:table-cell>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>PRIMJER AUTORSKOG DJELA</fo:block></fo:table-cell>
                                    <xsl:choose>
                                        <xsl:when test="//aut:Prilozi/aut:Primjer_djela">
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>DA</fo:block></fo:table-cell>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:table-cell border-bottom="1px" border-right="1px" padding="5px"><fo:block>NE</fo:block></fo:table-cell>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- ID -->
                    <fo:block padding-left="10px" border="1px solid black" width="100%">
                        <fo:block padding-top="2mm" margin-right="5mm" height="180px" width="300px" text-align="right">
                            <fo:block padding-left="10px" height="50%">
                                <fo:block font-size="large">Broj prijave:</fo:block>
                                <fo:block margin-bottom="5px" font-size="20">
                                    <xsl:value-of select="//aut:Podaci_o_zahtjevu/aut:Broj_prijave"/>
                                </fo:block>
                            </fo:block>
                            <fo:block padding-left="10px" height="50%">
                                <fo:block font-size="large">Datum podnošenja:</fo:block>
                                <fo:block margin-bottom="5px" font-size="20">
                                    <xsl:variable name="datum_prijave" select="str:tokenize(//aut:Podaci_o_zahtjevu/aut:Datum_prijave, '-')"/>
                                    <xsl:value-of select="$datum_prijave[3]"/>.
                                    <xsl:value-of select="$datum_prijave[2]"/>.
                                    <xsl:value-of select="$datum_prijave[1]"/>.
                                </fo:block>
                            </fo:block>
                        </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
