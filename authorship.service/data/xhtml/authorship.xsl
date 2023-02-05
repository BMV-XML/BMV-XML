<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/authorship"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:str="http://exslt.org/strings"
                xmlns:base="http://www.ftn.uns.ac.rs/base-schame" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Autorsko pravo</title>
                <style type="text/css">
                    .container {
                        padding: 50px;
                    }
                    .field {
                        border: 1px solid black;
                        width: 100%;
                        padding: 10px;
                    }
                    .receiver {
                        margin-left: 0;
                    }
                    #title {
                        display: flex;
                        justify-content: center;
                        margin-bottom: 20px;
                    }
                    table{
                        border: 1px solid black;
                        width: 100%;
                    }
                    td {
                        border-bottom: 1px solid black;
                        border-right: 1px solid black;
                        padding: 5px;
                        width: 50%;
                    }
                    .wrapper {
                        position: absolute;
                        bottom: 0;
                        right: 0;
                        height: 230px;
                        width: 300px;
                        border: 1px solid black;
                    }
                    .footer {
                        position: relative;
                        height: 250px;
                    }
                    .upper {
                        border-bottom: 1px solid black;
                    }
                    .common {
                        padding-left: 10px;
                        height: 50%;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="field">
                        <div class="receiver">
                            <p><strong><xsl:value-of select="aut:Zahtjev/base:Primalac/base:Naziv"/></strong></p>
                            <p>
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Grad"/>,&#160;
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Ulica"/>&#160;
                                <xsl:value-of select="aut:Zahtjev/base:Primalac/base:Adresa/base:Broj"/>
                            </p>
                        </div>
                        <div id="title">
                            <h2>ZAHTEV ZA UNOŠENJE U EVIDNCIJU I DEPONOVANJE AUTORSKIH PRAVA</h2>
                        </div>
                    </div>

                    <!-- PODNOSILAC -->
                    <div class="field">
                        <h2>PODNOSILAC</h2>
                        <table>
                            <tr>
                                <xsl:choose>
                                    <xsl:when test="//aut:Podnosilac/aut:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                        <td>IME I PREZIME</td>
                                        <td>
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Ime"/>
                                            &#160;
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Prezime"/>
                                        </td>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <td>POSLOVNO IME</td>
                                        <td>
                                            <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Poslovno_ime"/>
                                        </td>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </tr>
                            <tr>
                                <td>ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA</td>
                                <td>
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Ulica"/>
                                    &#160;
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Broj"/>,
                                    &#160;
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                    &#160;
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Grad"/>,
                                    &#160;
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Adresa/base:Drzava"/>
                                </td>
                            </tr>
                            <tr>
                                <td>TELEFON</td>
                                <td>
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:Broj_telefona"/>
                                </td>
                            </tr>
                            <tr>
                                <td>FAKS</td>
                                <td>
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:Broj_faksa"/>
                                </td>
                            </tr>
                            <tr>
                                <td>E-MAIL</td>
                                <td>
                                    <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Kontakt/base:E_posta"/>
                                </td>
                            </tr>
                            <xsl:if test="//aut:Podnosilac/aut:Osoba[@xsi:type = 'base:TFizicko_lice']">
                                <tr>
                                    <td>DRAŽAVLJANSTVO</td>
                                    <td>
                                        <xsl:value-of select="//aut:Podnosilac/aut:Osoba/base:Drzavljanstvo"/>
                                    </td>
                                </tr>
                            </xsl:if>
                        </table>
                    </div>

                    <!-- PUNOMOCNIK -->
                    <div class="field">
                        <h2>PUNOMOĆNIK</h2>
                        <xsl:choose>
                            <xsl:when test="not(//aut:Punomocnik)">
                                <h3>PRIJAVA SE PODNOSI BEZ PUNOMOĆNIKA</h3>
                            </xsl:when>
                            <xsl:otherwise>
                                <table>
                                    <tr>
                                        <td>IME I PREZIME</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Ime"/>
                                            &#160;
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Prezime"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>DRAŽAVLJANSTVO</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Drzavljanstvo"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Ulica"/>
                                            &#160;
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Broj"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Grad"/>,
                                            &#160;
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Adresa/base:Drzava"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>TELEFON</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:Broj_telefona"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>FAKS</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:Broj_faksa"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>E-MAIL</td>
                                        <td>
                                            <xsl:value-of select="//aut:Punomocnik/aut:Osoba/base:Kontakt/base:E_posta"/>
                                        </td>
                                    </tr>
                                </table>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>

                    <!-- AUTORSKO DJELO -->
                    <div class="field">
                        <h2>AUTORSKO DELO</h2>
                        <table>
                            <tr>
                                <td>NASLOV</td>
                                <td>
                                    <xsl:value-of select="//aut:Autorsko_djelo/aut:Naslov"/>
                                </td>
                            </tr>
                            <xsl:if test="//aut:Autorsko_djelo/aut:Alternativni_naslov">
                                <tr>
                                    <td>ALTERNATIVNI NASLOV</td>
                                    <td>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Alternativni_naslov"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <tr>
                                <td>VRSTA DELA</td>
                                <td>
                                    <xsl:value-of select="//aut:Autorsko_djelo/aut:Vrsta"/>
                                </td>
                            </tr>
                            <tr>
                                <td>FORMA ZAPISA</td>
                                <td>
                                    <xsl:value-of select="//aut:Autorsko_djelo/aut:Forma_zapisa"/>
                                </td>
                            </tr>
                            <tr>
                                <td>DA LI JE DELO STVORENO U RADNOM ODNOSU</td>
                                <td>
                                    <xsl:value-of select="//aut:Autorsko_djelo/aut:Stvoreno_u_radnom_odnosu"/>
                                </td>
                            </tr>
                            <xsl:if test="//aut:Autorsko_djelo/aut:Preradjeno_djelo">
                                <tr>
                                    <td colspan="2" style="background-color: #ccc;">&#160;</td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="font-weight: 700;">PODACI O DELU NA KOME SE ZASNIVA DELO PRERADE</td>
                                </tr>
                                <tr>
                                    <td>NASLOV DELA</td>
                                    <td>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Naslov"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>IME AUTORA</td>
                                    <td>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Ime_autora"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>PREZIME AUTORA</td>
                                    <td>
                                        <xsl:value-of select="//aut:Autorsko_djelo/aut:Preradjeno_djelo/aut:Prezime_autora"/>
                                    </td>
                                </tr>
                            </xsl:if>
                        </table>
                    </div>

                    <!-- AUTOR -->
                    <div class="field">
                        <h2>AUTOR</h2>
                        <xsl:choose>
                            <xsl:when test="//aut:Podnosilac/aut:Podnosilac_je_autor[text() = 'DA']">
                                <h3>PODNOSILAC &#160; JE &#160; I &#160; AUTOR</h3>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:if test="//aut:Autor/aut:Anonimni_autor[text() = 'DA']">
                                    <h3>AUTOR &#160; JE &#160; ANONIMAN</h3>
                                </xsl:if>
                                <xsl:if test="//aut:Autor/aut:Anonimni_autor[text() = 'NE']">
                                    <xsl:for-each select="//aut:Autor">
                                        <table>
                                            <tr>
                                                <td>IME I PREZIME</td>
                                                <td>
                                                    <xsl:value-of select="aut:Osoba/base:Ime"/>
                                                    &#160;
                                                    <xsl:value-of select="aut:Osoba/base:Prezime"/>
                                                </td>
                                            </tr>
                                            <xsl:if test="aut:Pseudonim">
                                                <tr>
                                                    <td>PSEUDONIM</td>
                                                    <td>
                                                        <xsl:value-of select="aut:Pseudonim"/>
                                                    </td>
                                                </tr>
                                            </xsl:if>
                                            <xsl:choose>
                                                <xsl:when test="aut:Godina_smrti[text() = 0]">
                                                    <tr>
                                                        <td>DRŽAVLJANSTVO</td>
                                                        <td>
                                                            <xsl:value-of select="aut:Osoba/base:Drzavljanstvo"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>ULICA I BROJ, POŠTANSKI BROJ, MESTO I DRŽAVA</td>
                                                        <td>
                                                            <xsl:value-of select="aut:Osoba/base:Adresa/base:Ulica"/>
                                                            &#160;
                                                            <xsl:value-of select="aut:Osoba/base:Adresa/base:Broj"/>,
                                                            &#160;
                                                            <xsl:value-of select="aut:Osoba/base:Adresa/base:Postanski_broj"/>,
                                                            &#160;
                                                            <xsl:value-of select="aut:Osoba/base:Adresa/base:Grad"/>,
                                                            &#160;
                                                            <xsl:value-of select="aut:Osoba/base:Adresa/base:Drzava"/>
                                                        </td>
                                                    </tr>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <tr>
                                                        <td>GODINA SMRTI</td>
                                                        <td>
                                                            <xsl:value-of select="aut:Godina_smrti"/>
                                                        </td>
                                                    </tr>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </table> <br />
                                    </xsl:for-each>
                                </xsl:if>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>

                    <!-- PRILOZI -->
                    <div class="field">
                        <h2>PRILOZI</h2>
                        <table>
                            <tr>
                                <td>OPIS AUTORSKOG DJELA</td>
                                <xsl:choose>
                                    <xsl:when test="//aut:Prilozi/aut:Opis_djela">
                                        <td>DA</td>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <td>NE</td>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </tr>
                            <tr>
                                <td>PRIMJER AUTORSKOG DJELA</td>
                                <xsl:choose>
                                    <xsl:when test="//aut:Prilozi/aut:Primjer_djela">
                                        <td>DA</td>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <td>NE</td>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </tr>
                        </table>
                    </div>

                    <!-- ID -->
                    <div class="field footer">
                        <div class="wrapper">
                            <div class="common upper">
                                <p style="font-size: large;">Broj prijave:</p>
                                <h2 style="margin-bottom: 5px;">
                                    <xsl:value-of select="//aut:Podaci_o_zahtjevu/aut:Broj_prijave"/>
                                </h2>
                            </div>
                            <div class="common lower">
                                <p style="font-size: large;">Datum podnošenja:</p>
                                <h2 style="margin-bottom: 5px;">
                                    <xsl:variable name="datum_prijave" select="str:tokenize(//aut:Podaci_o_zahtjevu/aut:Datum_prijave, '-')"/>
                                    <xsl:value-of select="$datum_prijave[3]"/>.
                                    <xsl:value-of select="$datum_prijave[2]"/>.
                                    <xsl:value-of select="$datum_prijave[1]"/>.
                                </h2>
                            </div>
                        </div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
