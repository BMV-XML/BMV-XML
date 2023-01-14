<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:stamp="http://www.ftn.uns.ac.rs/stamp"
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

                    <!-- Naslov -->
                    <fo:block font-family="Arial" font-weight="bold" text-align="center" font-size="16px" padding="5px 0px 5px 0px">
                        Zahtev za priznanje žiga
                    </fo:block>

                    <fo:block font-weight="bold" font-family="Arial" text-align="center" font-size="12px">
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Naziv"/>,
                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Ulica"/>
                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Broj"/>,
                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Postanski_broj"/>,
                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Grad"/>,
                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                        <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Drzava"/>
                    </fo:block>

                    <!-- Tabela za Podnosioce prijave -->
                    <fo:block>
                        <xsl:for-each select="//stamp:Podnosioci_prijave">
                            <xsl:variable name="temp" select="."/>

                            <fo:table border="1px solid black" margin="10px" font-family="Arial">

                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="10%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="10%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="6" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block font-weight="bold">
                                                1. Podnosilac prijave -
                                                <xsl:value-of select="position()"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <xsl:choose>
                                            <xsl:when test="$temp[@xsi:type='base:TFizicko_lice']">
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>Ime i prezime</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>
                                                        <xsl:value-of select="$temp/base:Ime"/>
                                                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                        <xsl:value-of select="./base:Prezime"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>Poslovno ime
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>
                                                        <xsl:value-of select="./base:Poslovno_ime"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                Ulica i broj
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                <xsl:value-of select="./base:Adresa/base:Ulica"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of select="./base:Adresa/base:Broj"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                Poštanski broj, mesto, država
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                <xsl:value-of select="./base:Adresa/base:Postanski_broj"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of select="./base:Adresa/base:Grad"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of select="./base:Adresa/base:Drzava"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                telefon:
                                            </fo:block>
                                            <fo:block>
                                                <xsl:value-of select="./base:Kontakt/base:Broj_telefona"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                e-mail:
                                                <xsl:value-of select="./base:Kontakt/base:E_posta"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                faks:
                                                <xsl:value-of select="./base:Kontakt/base:Broj_faksa"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                </fo:table-body>
                            </fo:table>
                        </xsl:for-each>
                    </fo:block>


                    <!-- Tabela za Punomocnike prijave -->
                    <fo:block>
                        <fo:table border="1px solid black" margin="10px" font-family="Arial">
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="10%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="10%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="20%"/>

                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="6" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block font-weight="bold">
                                            2. Punomoćnik
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <xsl:choose>
                                        <xsl:when test="//stamp:Punomocnik[@xsi:type='base:TFizicko_lice']">
                                            <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                           padding="3px 5px 5px 3px">
                                                <fo:block>Ime i prezime</fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                           padding="3px 5px 5px 3px">
                                                <fo:block>
                                                    <xsl:value-of select="//stamp:Punomocnik/base:Ime"/>
                                                    <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                    <xsl:value-of select="//stamp:Punomocnik/base:Prezime"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                           padding="3px 5px 5px 3px">
                                                <fo:block>Poslovno ime
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                           padding="3px 5px 5px 3px">
                                                <fo:block>
                                                    <xsl:value-of select="//stamp:Punomocnik/base:Poslovno_ime"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            Ulica i broj
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Ulica"/>,
                                            <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                            <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Broj"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            Poštanski broj, mesto, država
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Postanski_broj"/>,
                                            <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                            <xsl:value-of select="./base:Adresa/base:Grad"/>,
                                            <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                            <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Drzava"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            telefon:
                                            <xsl:value-of select="//stamp:Punomocnik/base:Kontakt/base:Broj_telefona"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            e-mail:
                                            <xsl:value-of select="//stamp:Punomocnik/base:Kontakt/base:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                   padding="3px 5px 5px 3px">
                                        <fo:block>
                                            faks:
                                            <xsl:value-of select="//stamp:Punomocnik/base:Kontakt/base:Broj_faksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- Tabela za Zajdnicke predstavnike ako postoje -->
                    <xsl:if test="count(//stamp:Zajednicki_predstavnik) = 1">
                        <fo:block>
                            <fo:table border="1px solid black" margin="10px" font-family="Arial">
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="10%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="10%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-body>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="6" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block font-weight="bold">
                                                3. Zajednički predstavnik
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="//stamp:Zajednicki_predstavnik[@xsi:type='base:TFizicko_lice']">
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>Ime i prezime</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block padding="5">
                                                        <xsl:value-of select="//stamp:Zajednicki_predstavnik/base:Ime"/>
                                                        <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                        <xsl:value-of
                                                                select="//stamp:Zajednicki_predstavnik/base:Prezime"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>Poslovno ime
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                               padding="3px 5px 5px 3px">
                                                    <fo:block>
                                                        <xsl:value-of
                                                                select="//stamp:Zajednicki_predstavnik/base:Poslovno_ime"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                           <fo:block>
                                               Ulica i broj
                                           </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Ulica"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of select="//stamp:Punomocnik/base:Adresa/base:Broj"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                Poštanski broj, mesto, država
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="3" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                <xsl:value-of
                                                        select="//stamp:Zajednicki_predstavnik/base:Adresa/base:Postanski_broj"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of
                                                        select="//stamp:Zajednicki_predstavnik/base:Adresa/base:Grad"/>,
                                                <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                                                <xsl:value-of
                                                        select="//stamp:Zajednicki_predstavnik/base:Adresa/base:Drzava"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>

                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                telefon:
                                                <xsl:value-of
                                                        select="//stamp:Punomocnik/base:Kontakt/base:Broj_telefona"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                e-mail:
                                                <xsl:value-of select="//stamp:Punomocnik/base:Kontakt/base:E_posta"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                       padding="3px 5px 5px 3px">
                                            <fo:block>
                                                faks:
                                                <xsl:value-of select="//stamp:Punomocnik/base:Kontakt/base:Broj_faksa"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </xsl:if>

                    <!-- Podaci o zigu -->
                    <fo:block>
                        <fo:table border="1px solid black" margin="10px" font-family="Arial" page-break-before="always">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="2" padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            4. Prijava se podnosi za žig:
                                        </fo:block>
                                    </fo:table-cell>

                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            a) vrsta žiga:
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Vrsta"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <xsl:variable name="image" select="//stamp:Podaci_o_zigu/stamp:Izgled_znaka"/>
                                    <fo:table-cell number-columns-spanned="1" number-rows-spanned="6"
                                                   padding="3px 3px 3px 3px"
                                                   border="1px solid black">
                                        <fo:block>
                                            c) izgled znaka:
                                        </fo:block>
                                        <fo:block>
                                            <fo:inline>
                                                <fo:external-graphic src="url({$image})" content-height="250px"
                                                                     content-width="250"/>
                                            </fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            b) tip žiga:
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Tip"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            5. Boje:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:for-each select="//stamp:Podaci_o_zigu/stamp:Boja_znaka">
                                                <xsl:value-of select="."/>
                                                <xsl:value-of select="'&#160;'"/>
                                            </xsl:for-each>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            6. Transliteracija znaka:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Transliteracija_znaka"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            7. Prevod znaka:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Prevod_znaka"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            8. Opis znaka:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Opis_znaka"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <!-- Tabela o taksama -->

                    <fo:block>
                        <fo:table border="1px solid black" margin="10px" font-family="Arial">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            11. Plaćene takse
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            a) osnovna
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of select="//stamp:Podaci_o_taksama/stamp:Osnovna_taksa"/> dinara
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            b) za
                                            <xsl:value-of
                                                    select="count(//stamp:Podaci_o_zigu/stamp:Klase_robe_i_usluga)"/>
                                            klasa
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:variable name="tax"
                                                          select="count(//stamp:Podaci_o_taksama/stamp:Taksa_za_klasu)"/>
                                            <xsl:value-of
                                                    select="count(//stamp:Podaci_o_zigu/stamp:Klase_robe_i_usluga) * $tax"/>
                                            dinara

                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            c) za grafičko rešenje
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_taksama/stamp:Taksa_za_graficko_resenje"/>
                                            dinara
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="2" border="1px solid black"
                                                   padding="3px 3px 3px 3px" text-align="right">
                                        <fo:block>
                                            Ukupno:
                                            <xsl:value-of select="//stamp:Podaci_o_taksama/stamp:Ukupno"/> dinara
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block>
                        <fo:table border="1px solid black" margin="10px" font-family="Arial">
                            <fo:table-column column-width="54%"/>
                            <fo:table-column column-width="13%"/>
                            <fo:table-column column-width="33%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block font-weight="bold">
                                            Prilozi uz zahtev
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            priloženo
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell number-columns-spanned="1" number-rows-spanned="9"
                                                   padding="60px 10px 20px 10px" text-align="center"
                                                   border="1px solid black">
                                        <fo:block padding="3px 3px 0px 3px">
                                            Broj prijave žiga:
                                        </fo:block>
                                        <fo:block  padding="1px 3px 0px 3px">
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Broj_prijave_ziga"/>
                                        </fo:block>
                                        <fo:block font-weight="bold"  padding="3px 3px 0px 3px">
                                            Datum podnošenja:
                                        </fo:block>
                                        <fo:block font-weight="bold"  padding="1px 3px 0px 3px">
                                            <xsl:variable name="tokens"
                                                          select="str:tokenize(//stamp:Datum_prijave, '-')"/>
                                            <xsl:variable name="day" select="$tokens[3]"/>
                                            <xsl:variable name="month" select="$tokens[2]"/>
                                            <xsl:variable name="year" select="$tokens[1]"/>
                                            <xsl:value-of select="$day"></xsl:value-of>.
                                            <xsl:value-of select="$month"></xsl:value-of>.
                                            <xsl:value-of select="$year"></xsl:value-of>.
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Primerak znaka</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Primer_znaka/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Spisak robe i usluga</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Spisak_robe_i_usluga/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Ponomoćje</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Punomocje/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Generalno punomoćje ranije priloženo</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Generalno_punomocje_ranije_prilozeno/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Punomoćje će biti naknadno dostavljeno</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Punomocje_ce_biti_naknadno_dostavljeno/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Opsti akt o kolektivnom žigu/žigu garancije</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Opsti_akt_o_kolektivnom_zigu_ili_zigu_garancije/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Dokaz o pravu prvenstva</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Dokaz_o_pravu_prvenstva/stamp:Predat_prilog"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>Dokaz o uplati takse</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-columns-spanned="1" border="1px solid black"
                                                   padding="3px 3px 3px 3px">
                                        <fo:block>
                                            <xsl:value-of
                                                    select="//stamp:Podaci_o_prilozima/stamp:Dokaz_o_uplati_takse/stamp:Predat_prilog"/>
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