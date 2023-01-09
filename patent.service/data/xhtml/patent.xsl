<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:pat="http://www.ftn.uns.ac.rs/patent"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:str="http://exslt.org/strings"
                xmlns:base="http://www.ftn.uns.ac.rs/base-schame" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Patent</title>
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
                    h3{ text-align: center; }
                    .main.table.first{
                    border-collapse: collapse;
                    text-indent: 10px;
                    border: 3px solid black;
                    margin-left: 5%;
                    width: 90%;
                    text-align: left; }
                    .main.table.first {
                    }
                    table.main.table td {
                    text-align: left;
                    padding: 10px 0px 5px 5px;
                    border: 1px solid black;
                    vertical-align:top;
                    }

                    .main.table.second{
                    border-collapse: collapse;
                    text-indent: 10px;
                    border: 3px solid black;
                    margin: 100px 0 0 5%;
                    width: 90%;
                    text-align: left; }

                    .title.section {
                    padding: 20px 0px 3px; 10px;
                    border-top: 3px solid black;
                    height: 50px;
                    }
                </style>
            </head>
            <body>
                <div>
                    <table class="podaci" align="left">
                        <tr>
                            <td colspan="2">
                                <p class="smaller">Broj prijave</p>
                                <p>
                                    <xsl:value-of
                                            select="pat:Zahtev_za_priznavanje_patenta/pat:Podaci_o_zahtevu/pat:Broj_prijave"/>
                                </p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="smaller">Datum prijema</p>
                                <p>
                                    <xsl:variable name="datum_prijema" select="str:tokenize(//pat:Podaci_o_zahtevu/pat:Datum_prijave, '-')"/>
                                    <xsl:value-of select="$datum_prijema[3]"/>.
                                    <xsl:value-of select="$datum_prijema[2]"/>.
                                    <xsl:value-of select="$datum_prijema[1]"/>.
                                </p>
                            </td>
                            <td>
                                <p class="smaller">Priznati datum podnosenja</p>
                                <p>
                                    <xsl:variable name="datum_podnosenja" select="str:tokenize(//pat:Podaci_o_zahtevu/pat:Priznati_datum_podnosenja, '-')"/>
                                    <xsl:value-of select="$datum_podnosenja[3]"/>.
                                    <xsl:value-of select="$datum_podnosenja[2]"/>.
                                    <xsl:value-of select="$datum_podnosenja[1]"/>.
                                </p>
                            </td>
                        </tr>
                    </table>
                    <div class="primalac">
                        <div>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Drzava"/>
                        </div>
                        <div>
                            <xsl:value-of select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Naziv"/>
                        </div>
                        <div>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Ulica"/>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Broj"/>
                        </div>
                        <div>
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Postanski_broj"/>
                            &#160;
                            <xsl:value-of
                                    select="pat:Zahtev_za_priznavanje_patenta/base:Primalac/base:Adresa/base:Grad"/>
                        </div>
                    </div>

                    <h3>
                        Zahtev za priznanje patenta
                    </h3>

                    <div class="main table">
                        <table class="main table first" style="vertical-align: top;">
                            <tr>
                                <th class="title section" colspan="3">Naziv Pronalaska</th>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <xsl:for-each select="pat:Zahtev_za_priznavanje_patenta/pat:Naslov">
                                        <p>
                                            <xsl:value-of select="@jezik"/>:
                                            <xsl:value-of select="text()"/>
                                        </p>
                                    </xsl:for-each>
                                </td>
                            </tr>

                            <tr class="title section">
                                <th colspan="2">Podnosilac prijave</th>
                                <xsl:if test="//pat:Podnosilac/pat:Podnosilac_je_i_pronalazac[text() = 'DA']">
                                    <th>Podnosilac je i pronalazac</th>
                                </xsl:if>
                            </tr>
                            <tr>
                                <td rowspan="3" style="vertical-align: top;">
                                    <xsl:choose>
                                        <xsl:when test="//pat:Podnosilac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                            <p>
                                                <strong>Ime i prezime</strong>
                                            </p>
                                            <p>
                                                <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Ime"/>
                                                &#160;
                                                <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Prezime"/>
                                            </p>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <p>
                                                <strong>Poslovno ime</strong>
                                            </p>
                                            <p>
                                                <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Poslovno_ime"/>
                                            </p>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td rowspan="3">
                                    <p style="text-align: left;">
                                        <strong>Ulica i broj, poštanski broj, mesto i država</strong>
                                    </p>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Ulica"/>
                                        &#160;
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Broj"/>
                                    </label>
                                    <br/>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of
                                                select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Postanski_broj"/>
                                        &#160;
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Grad"/>
                                    </label>
                                    <br/>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Adresa/base:Drzava"/>
                                    </label>
                                </td>
                                <td>
                                    <p>
                                        <strong>Broj telefona</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of
                                                select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <strong>Broj faksa</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                    </p>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <strong>E-pošta</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Kontakt/base:E_posta"/>
                                    </p>
                                </td>

                            </tr>
                            <xsl:if test="//pat:Podnosilac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                <tr>
                                    <td colspan="4">
                                        <p>
                                            <strong>Državljanstvo</strong>
                                        </p>
                                        <p>
                                            <xsl:value-of select="//pat:Podnosilac/pat:Osoba/base:Drzavljanstvo"/>
                                        </p>
                                    </td>

                                </tr>
                            </xsl:if>

                            <xsl:if test="//pat:Podnosilac/pat:Podnosilac_je_i_pronalazac[text() = 'NE']">
                                <tr class="title section">
                                    <th colspan="2">Pronalazač</th>
                                    <xsl:if test="//pat:Pronalazac/pat:Hoce_da_bude_naveden[text() = 'DA']">
                                        <th>Pronalazač hoće da bude naveden</th>
                                    </xsl:if>
                                    <xsl:if test="//pat:Pronalazac/pat:Hoce_da_bude_naveden[text() = 'NE']">
                                        <th>Pronalazač ne hoće da bude naveden</th>
                                    </xsl:if>
                                </tr>
                                <tr>
                                    <td rowspan="3" style="vertical-align: top;">
                                        <xsl:choose>
                                            <xsl:when
                                                    test="//pat:Pronalazac/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                                <p>
                                                    <strong>Ime i prezime</strong>
                                                </p>
                                                <p>
                                                    <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Prezime"/>
                                                </p>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <p>
                                                    <strong>Poslovno ime</strong>
                                                </p>
                                                <p>
                                                    <xsl:value-of
                                                            select="//pat:Pronalazac/pat:Osoba/base:Poslovno_ime"/>
                                                </p>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                    <td rowspan="3">
                                        <p style="text-align: left;">
                                            <strong>Ulica i broj, poštanski broj, mesto i država</strong>
                                        </p>
                                        <label style="padding-left: 15px;">
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Ulica"/>&#160;
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Broj"/>
                                        </label>
                                        <br/>
                                        <label style="padding-left: 15px;">
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Postanski_broj"/>&#160;
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Grad"/>
                                        </label>
                                        <br/>
                                        <label style="padding-left: 15px;">
                                            <xsl:value-of select="//pat:Pronalazac/pat:Osoba/base:Adresa/base:Drzava"/>
                                        </label>
                                    </td>
                                    <td>
                                        <p>
                                            <strong>Broj telefona</strong>
                                        </p>
                                        <p>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p>
                                            <strong>Broj faksa</strong>
                                        </p>
                                        <p>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </p>
                                    </td>

                                </tr>
                                <tr>
                                    <td>
                                        <p>
                                            <strong>E-pošta</strong>
                                        </p>
                                        <p>
                                            <xsl:value-of
                                                    select="//pat:Pronalazac/pat:Osoba/base:Kontakt/base:E_posta"/>
                                        </p>
                                    </td>

                                </tr>
                            </xsl:if>

                        </table>

                        <table class="main table second" style="vertical-align: top;">

                            <!-- Punomocnik -->
                            <tr>
                                <th class="title section" colspan="3">Punomoćnik</th>
                            </tr>
                            <tr>
                                <th colspan="1"></th>
                                <xsl:if test="//pat:Punomocnik/pat:Punomocnik_za_zastupanje[text() = 'DA']">
                                    <th>Za zastupanje</th>
                                </xsl:if>
                                <xsl:if test="//pat:Punomocnik/pat:Punomocnik_za_prijem_pismena[text() = 'DA']">
                                    <th>Za prijem pisama</th>
                                </xsl:if>
                            </tr>
                            <tr>
                                <xsl:if test="//pat:Punomocnik/pat:Zajednicki_predstavnik[text() = 'DA']">
                                    <th>Zajednički predstavnik</th>
                                </xsl:if>
                            </tr>
                            <tr>
                                <td rowspan="3" style="vertical-align: top;">
                                    <xsl:choose>
                                        <xsl:when test="//pat:Punomocnik/pat:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                            <p>
                                                <strong>Ime i prezime</strong>
                                            </p>
                                            <p>
                                                <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Ime"/>
                                                &#160;
                                                <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Prezime"/>
                                            </p>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <p>
                                                <strong>Poslovno ime</strong>
                                            </p>
                                            <p>
                                                <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Poslovno_ime"/>
                                            </p>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td rowspan="3">
                                    <p style="text-align: left;">
                                        <strong>Ulica i broj, poštanski broj, mesto i država</strong>
                                    </p>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Ulica"/>&#160;
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Broj"/>
                                    </label>
                                    <br/>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of
                                                select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Postanski_broj"/>&#160;
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Grad"/>
                                    </label>
                                    <br/>
                                    <label style="padding-left: 15px;">
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Adresa/base:Drzava"/>
                                    </label>
                                </td>
                                <td>
                                    <p>
                                        <strong>Broj telefona</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of
                                                select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:Broj_telefona"/>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <strong>Broj faksa</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:Broj_faksa"/>
                                    </p>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <strong>E-pošta</strong>
                                    </p>
                                    <p>
                                        <xsl:value-of select="//pat:Punomocnik/pat:Osoba/base:Kontakt/base:E_posta"/>
                                    </p>
                                </td>

                            </tr>


                            <!-- Adresa za dostavljanje -->
                            <tr>
                                <th class="title section" colspan="3">Adresa za dostavljanje</th>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <p style="text-align: left;">
                                        <strong>Ulica i broj, poštanski broj, mesto i država</strong>
                                    </p>
                                    <p style="padding-left: 15px;">
                                        <xsl:value-of select="//pat:Dostavljanje/base:Adresa/base:Ulica"/>
                                        <xsl:value-of select="'&#160;'"/> <!-- SPACE -->
                                        <xsl:value-of select="//pat:Dostavljanje/base:Adresa/base:Broj"/>
                                        <xsl:value-of select="',&#160;'"/> <!-- SPACE -->
                                        <xsl:value-of
                                                select="//pat:Dostavljanje/base:Adresa/base:Postanski_broj"/>
                                        <xsl:value-of select="',&#160;'"/> <!-- SPACE -->
                                        <xsl:value-of select="//pat:Dostavljanje/base:Adresa/base:Grad"/>
                                        <xsl:value-of select="',&#160;'"/> <!-- SPACE -->
                                        <xsl:value-of select="//pat:Dostavljanje/base:Adresa/base:Drzava"/>
                                    </p>
                                </td>
                            </tr>


                            <!-- Nacin dostavljanje -->
                            <tr class="title section">
                                <th colspan="3">Nacin dostavljanja</th>
                            </tr>
                            <tr>
                                <xsl:if test="//pat:Dostavljanje/pat:Obavesti_me_elektronskim_putem[text() = 'DA']">
                                    <td colspan="3">Obavesti me elektorskim putem</td>
                                </xsl:if>
                            </tr>
                            <tr>
                                <xsl:if test="//pat:Punomocnik/pat:Obavesti_me_preko_poste[text() = 'DA']">
                                    <td colspan="3">Obavesti me preko poste</td>
                                </xsl:if>
                            </tr>


                            <!-- Tip prijave -->
                            <xsl:if test="//pat:Dodatna_prijava/pat:Dopunska_prijava[text() = 'DA'] | //pat:Dodatna_prijava/pat:Izdvojena_prijava[text() = 'DA']">
                                <tr class="title section">
                                    <th colspan="3">Tip prijave</th>
                                </tr>

                                <tr>
                                    <th colspan="1"></th>
                                    <xsl:if test="//pat:Dodatna_prijava/pat:Dopunska_prijava[text() = 'DA']">
                                        <th>Dopunska prijava</th>
                                    </xsl:if>
                                    <xsl:if test="//pat:Dodatna_prijava/pat:Izdvojena_prijava[text() = 'DA']">
                                        <th>Izdvojena prijava</th>
                                    </xsl:if>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <p>
                                            Broj osnovne prijave:
                                            <xsl:value-of select="//pat:Dodatna_prijava/pat:Broj_prijave"/>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <p>
                                            Datum podnošenja osnovne prijave:
                                            <xsl:variable name="datum_osnovne" select="str:tokenize(//pat:Dodatna_prijava/pat:Dodatna_prijava, '-')"/>
                                            <xsl:value-of select="$datum_osnovne[3]"/>.
                                            <xsl:value-of select="$datum_osnovne[2]"/>.
                                            <xsl:value-of select="$datum_osnovne[1]"/>.
                                        </p>
                                    </td>
                                </tr>
                            </xsl:if>

                            <!-- Prava prvenstva -->

                            <xsl:if test="count(//pat:Prava_prvenstva) > 0">
                                <tr class="title section">
                                    <th colspan="3">Zahtev za prava prvenstva iz ranijih prijava</th>
                                </tr>

                                <tr>
                                    <td>Datum podnošenja ranije prijave</td>
                                    <td>Broj ranije prijave</td>
                                    <td>Dvoslovna oznaka države</td>
                                </tr>
                                <xsl:for-each select="//pat:Prava_prvenstva">
                                    <tr>
                                        <td>
                                            <label><xsl:value-of select="position()"/>.
                                            </label>
                                            <label>
                                               <!-- <xsl:variable name="date" select=""/>-->
                                               <!-- <xsl:value-of select="format-date(pat:Datum_podnosenja, 'dd. MM. yyyy.')"/>-->
                                               <!-- <xsl:value-of select="pat:Datum_podnosenja"/>-->


                                                <xsl:variable name="tokens" select="str:tokenize(pat:Datum_podnosenja, '-')"/>
                                                <xsl:variable name="day" select="$tokens[3]"/>
                                                <xsl:variable name="month" select="$tokens[2]"/>
                                                <xsl:variable name="year" select="$tokens[1]"/>
                                                <xsl:value-of select="$day"></xsl:value-of>.
                                                <xsl:value-of select="$month"></xsl:value-of>.
                                                <xsl:value-of select="$year"></xsl:value-of>.
                                            </label>
                                        </td>
                                        <td>
                                            <xsl:value-of select="pat:Broj_prjave"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="pat:Drzava"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:if>
                        </table>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
