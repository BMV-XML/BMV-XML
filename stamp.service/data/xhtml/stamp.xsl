<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:stamp="http://www.ftn.uns.ac.rs/stamp"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:str="http://exslt.org/strings"
                xmlns:base="http://www.ftn.uns.ac.rs/base-schame" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Stamp</title>
                <style type="text/css">
                    table.podaci {
                    border-collapse: collapse;
                    margin-top: 30px;
                    margin-bottom: 8px;
                    margin-left: 10px;
                    margin-right: 50%;
                    width: 50%;
                    }
                    p.smaller {
                    font-size: 14px;
                    margin: 3px auto auto auto;
                    }
                    table.podaci td {
                    text-align: left;
                    padding: 3px 0px 5px 5px;
                    border: 3px solid black;
                    }
                    table.podaci > tr { border: 3px solid black; }
                    body { font-family: sans-serif;
                    display: block; }
                    p { padding-left: 15px; }
                    .primalac {
                    text-indent: 45px;
                    }
                    .main.table.first{
                    border-collapse: collapse;
                    text-indent: 10px;
                    border: 3px solid black;
                    margin-left: 5%;
                    margin-top: 2%;
                    width: 70%;
                    text-align: left; }
                    .main.table.first {
                    }
                    table.main.table td {
                    text-align: left;
                    padding: 10px 0px 7px 7px;
                    border: 1px solid black;
                    vertical-align:top;
                    }

                    .image{
                    height: 250px;
                    width: 300px;
                    }
                </style>
            </head>
            <body>
                <div>
                    <h1 align="center">Zahtev za priznavanje žiga</h1>

                    <div align="center">
                        <h2><xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Naziv"/>,
                            <xsl:value-of select="'&#160;'"/>   <!-- SPACE -->
                            <xsl:value-of select="stamp:Zahtev_za_zig/base:Primalac/base:Adresa/base:Ulica"/>
                            <xsl:value-of select="'&#160;'"/>
                            <xsl:value-of select="//base:Primalac/base:Adresa/base:Broj"/>,
                            <xsl:value-of select="'&#160;'"/>
                            <xsl:value-of select="//base:Primalac/base:Adresa/base:Postanski_broj"/>,
                            <xsl:value-of select="'&#160;'"/>
                            <xsl:value-of select="//base:Primalac/base:Adresa/base:Grad"/>
                        </h2>
                    </div>

                    <!-- tabele za podnosioce zahteva -->
                    <div class="main table" align="center">
                        <xsl:for-each select="//stamp:Podnosioci_prijave">
                            <xsl:variable name="temp" select="."/>
                            <table class="main table first" style="vertical-align: top;" keep-together="always">
                                <tr>
                                    <th colspan="6">
                                        <b>1. Podnosilac prijave -
                                            <xsl:value-of select="position()"/>
                                        </b>
                                    </th>
                                </tr>
                                <tr>
                                    <xsl:choose>
                                        <xsl:when test="$temp[@xsi:type = 'base:TFizicko_lice']">
                                            <td colspan="3">Ime i prezime</td>
                                            <td colspan="3">
                                                <xsl:value-of select="$temp/base:Ime"/>
                                                <xsl:value-of select="'&#160;'"/>
                                                <xsl:value-of select="$temp/base:Prezime"/>
                                            </td>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <td colspan="3">Poslovno ime</td>
                                            <td colspan="3">
                                                <xsl:value-of select="$temp/base:Poslovno_ime"/>
                                            </td>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </tr>
                                <tr>
                                    <td colspan="3">Ulica i broj</td>
                                    <td colspan="3">
                                        <xsl:value-of select="$temp/base:Adresa/base:Ulica"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$temp/base:Adresa/base:Broj"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3">Poštanski broj, mesto, država</td>
                                    <td colspan="3"><xsl:value-of select="$temp/base:Adresa/base:Postanski_broj"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$temp/base:Adresa/base:Grad"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$temp/base:Adresa/base:Drzava"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">telefon :
                                        <xsl:value-of select="$temp/base:Kontakt/base:Broj_telefona"/>
                                    </td>
                                    <td colspan="2">e-mail :
                                        <xsl:value-of select="$temp/base:Kontakt/base:E_posta"/>
                                    </td>
                                    <td colspan="2">faks:
                                        <xsl:value-of select="$temp/base:Kontakt/base:Broj_faksa"/>
                                    </td>
                                </tr>

                            </table>
                        </xsl:for-each>
                    </div>

                    <!--  tabela za punomocnika -->
                    <div class="main table" align="center">
                        <table class="main table first" style="vertical-align: top;" keep-together="always">
                            <tr>
                                <xsl:variable name="punomocnik" select="//stamp:Punomocnik"/>
                                <th colspan="6">
                                    <b>2. Punomoćnik</b>
                                </th>

                                <tr>
                                    <xsl:choose>
                                        <xsl:when test="$punomocnik[@xsi:type = 'base:TFizicko_lice']">
                                            <td colspan="3">Ime i prezime</td>
                                            <td colspan="3">
                                                <xsl:value-of select="$punomocnik/base:Ime"/>
                                                <xsl:value-of select="'&#160;'"/>
                                                <xsl:value-of select="$punomocnik/base:Prezime"/>
                                            </td>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <td colspan="3">Poslovno ime</td>
                                            <td colspan="3">
                                                <xsl:value-of select="$punomocnik/base:Poslovno_ime"/>
                                            </td>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </tr>
                                <tr>
                                    <td colspan="3">Ulica i broj</td>
                                    <td colspan="3">
                                        <xsl:value-of select="$punomocnik/base:Adresa/base:Ulica"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$punomocnik/base:Adresa/base:Broj"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3">Poštanski broj, mesto, država</td>
                                    <td colspan="3"><xsl:value-of select="$punomocnik/base:Adresa/base:Postanski_broj"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$punomocnik/base:Adresa/base:Grad"/>,
                                        <xsl:value-of select="'&#160;'"/>
                                        <xsl:value-of select="$punomocnik/base:Adresa/base:Drzava"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">telefon :
                                        <xsl:value-of select="$punomocnik/base:Kontakt/base:Broj_telefona"/>
                                    </td>
                                    <td colspan="2">e-mail :
                                        <xsl:value-of select="$punomocnik/base:Kontakt/base:E_posta"/>
                                    </td>
                                    <td colspan="2">faks:
                                        <xsl:value-of select="$punomocnik/base:Kontakt/base:Broj_faksa"/>
                                    </td>
                                </tr>


                            </tr>
                        </table>
                    </div>

                    <!-- tabela za Zajednickog ako postoji -->

                    <div>
                        <xsl:if test="count(//stamp:Zajednicki_predstavnik) = 1">
                            <div class="main table" align="center">
                                <table class="main table first" style="vertical-align: top;" keep-together="always">
                                    <tr>
                                        <xsl:variable name="zajednicki" select="//stamp:Zajednicki_predstavnik"/>
                                        <th colspan="6">
                                            <b>3. Zajednički predstavnik</b>
                                        </th>

                                        <tr>
                                            <xsl:choose>
                                                <xsl:when test="$zajednicki[@xsi:type = 'base:TFizicko_lice']">
                                                    <td colspan="3">Ime i prezime</td>
                                                    <td colspan="3">
                                                        <xsl:value-of select="$zajednicki/base:Ime"/>
                                                        <xsl:value-of select="'&#160;'"/>
                                                        <xsl:value-of select="$zajednicki/base:Prezime"/>
                                                    </td>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <td colspan="3">Poslovno ime</td>
                                                    <td colspan="3">
                                                        <xsl:value-of select="$zajednicki/base:Poslovno_ime"/>
                                                    </td>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </tr>
                                        <tr>
                                            <td colspan="3">Ulica i broj</td>
                                            <td colspan="3">
                                                <xsl:value-of select="$zajednicki/base:Adresa/base:Ulica"/>
                                                <xsl:value-of select="'&#160;'"/>
                                                <xsl:value-of select="$zajednicki/base:Adresa/base:Broj"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">Poštanski broj, mesto, država</td>
                                            <td colspan="3"><xsl:value-of
                                                    select="$zajednicki/base:Adresa/base:Postanski_broj"/>,
                                                <xsl:value-of select="'&#160;'"/>
                                                <xsl:value-of select="$zajednicki/base:Adresa/base:Grad"/>,
                                                <xsl:value-of select="'&#160;'"/>
                                                <xsl:value-of select="$zajednicki/base:Adresa/base:Drzava"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">telefon :
                                                <xsl:value-of select="$zajednicki/base:Kontakt/base:Broj_telefona"/>
                                            </td>
                                            <td colspan="2">e-mail :
                                                <xsl:value-of select="$zajednicki/base:Kontakt/base:E_posta"/>
                                            </td>
                                            <td colspan="2">faks:
                                                <xsl:value-of select="$zajednicki/base:Kontakt/base:Broj_faksa"/>
                                            </td>
                                        </tr>
                                    </tr>
                                </table>
                            </div>
                        </xsl:if>
                    </div>


                    <!-- Tabela za podatke o zigu -->
                    <div class="main table" align="center">
                        <table class="main table first" style="vertical-align: top;" keep-together.within-page="always">
                            <tr>
                                <td colspan="2">
                                    <b>4. Prijava se podnosi za žig:</b>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>a) vrsta znaka:</b>
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Vrsta"/>
                                </td>
                                <td colspan="1" rowspan="6" style="width:50%">
                                    <b>c) izgled znaka:</b>
                                    <div>
                                        <img class="image">
                                            <xsl:attribute name="src">
                                                <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Izgled_znaka"/>
                                            </xsl:attribute>
                                        </img>
                                    </div>

                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>b) tip znaka:</b>
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Tip"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>5. Boje od kojih se znak sastoji:</b>
                                    <xsl:for-each select="//stamp:Podaci_o_zigu/stamp:Boja_znaka">
                                        <label>
                                            <xsl:value-of select="."/>
                                            <xsl:value-of select="'&#160;'"/>
                                        </label>
                                    </xsl:for-each>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>6. Transliteracija znaka:</b>
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Transliteracija_znaka"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>7. Prevod znaka:</b>
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Prevod_znaka"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">
                                    <b>8. Opis znaka:</b>
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Opis_znaka"/>
                                </td>

                            </tr>

                            <tr>
                                <td colspan="2">
                                    <b>9. Brojevi klasa roba i usluga prema Ninčanskoj klasifikaciji:</b>
                                    <xsl:for-each select="//stamp:Podaci_o_zigu/stamp:Klase_robe_i_usluga">
                                        <label>
                                            <xsl:value-of select="."/>,
                                            <xsl:value-of select="'&#160;'"/>
                                        </label>
                                    </xsl:for-each>
                                </td>

                            </tr>

                            <tr>
                                <td colspan="2">
                                    <b>10. Zatrazeno pravo prvenstva:</b> <!-- OSNOV ??? -->
                                    <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Prvenstvo"/>
                                </td>
                            </tr>

                        </table>
                    </div>


                    <!-- tabela za takse -->
                    <div class="main table" align="center">
                        <table class="main table first" style="vertical-align: top;" keep-together="always">
                            <tr>
                                <td colspan="2">
                                    <b>11. Placene takse</b>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1" style="width:50%">a) osnovna taksa</td>
                                <td colspan="1" style="width:50%">
                                    <xsl:value-of select="//stamp:Podaci_o_taksama/stamp:Osnovna_taksa"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">b) za
                                    <xsl:value-of select="count(//stamp:Podaci_o_zigu/stamp:Klase_robe_i_usluga)"/>
                                    klasa
                                </td>
                                <td colspan="1" style="width:50%">
                                    <xsl:variable name="tax" select="count(//stamp:Podaci_o_taksama/stamp:Taksa_za_klasu)"/>
                                    <xsl:value-of
                                            select="count(//stamp:Podaci_o_zigu/stamp:Klase_robe_i_usluga) * $tax"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1" style="width:50%">c) za grafičko rešenje</td>
                                <td colspan="1" style="width:50%">
                                    <xsl:value-of
                                            select="count(//stamp:Podaci_o_taksama/stamp:Taksa_za_graficko_resenje)"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2" align="right">Ukupno:
                                    <xsl:value-of select="//stamp:Podaci_o_taksama/stamp:Ukupno"/>
                                </td>
                            </tr>

                        </table>
                    </div>

                    <!-- tabela za priloge -->
                    <div class="main table" align="center">
                        <table class="main table first" style="vertical-align: top;" keep-together="always">
                            <tr>
                                <td colspan="1">
                                    <b>Prilozi uz zahtev:</b>
                                </td>
                                <td colspan="1">priloženo</td>
                                <td colspan="1" rowspan="9">
                                    <div align="center" style="padding-top:50px; padding-bottom:20px; padding-left:10px; padding-right:50px;">
                                        <p>Broj prijave žiga:</p>
                                        <p>
                                            <xsl:value-of select="//stamp:Podaci_o_zigu/stamp:Broj_prijave_ziga"/>
                                        </p>
                                        <p>Datum podnošenja:</p>
                                        <p>
                                            <xsl:variable name="tokens"
                                                          select="str:tokenize(//stamp:Datum_prijave, '-')"/>
                                            <xsl:variable name="day" select="$tokens[3]"/>
                                            <xsl:variable name="month" select="$tokens[2]"/>
                                            <xsl:variable name="year" select="$tokens[1]"/>
                                            <xsl:value-of select="$day"></xsl:value-of>.
                                            <xsl:value-of select="$month"></xsl:value-of>.
                                            <xsl:value-of select="$year"></xsl:value-of>.
                                        </p>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="1">Primerak znaka</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Primer_znaka/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Spisak robe i usluga</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Spisak_robe_i_usluga/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Punomoćje</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Punomocje/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Generalno punomoćje ranije priloženo</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Generalno_punomocje_ranije_prilozeno/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Punomoćje će biti naknadno dostavljeno</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Punomocje_ce_biti_naknadno_dostavljeno/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Opšti akt o kolektivnom žigu/žigu garancije</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Opsti_akt_o_kolektivnom_zigu_ili_zigu_garancije/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Dokaz o pravu prvenstva</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Dokaz_o_pravu_prvenstva/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1">Dokaz o uplati takse</td>
                                <td colspan="1">
                                    <xsl:value-of
                                            select="//stamp:Podaci_o_prilozima/stamp:Dokaz_o_uplati_takse/stamp:Predat_prilog"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
