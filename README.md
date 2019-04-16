# Ohjelmistotekniikka - harjoitustyö

## Tetris-klooni

## Dokumentaatio

[Käyttöohje](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


## Release

[Viikko 5](https://github.com/Malpel/ot-harjoitustyo/releases/tag/viikko5)


## Komentorivi

### Testaus

Testien suorittaminen

`mvn test`

Testikattavuusraportin luominen

`mvn jacoco:report`

Raporttia voi tarkastella avaamalla tiedosto *target/site/jacoco/index.html* selaimessa 

## Checkstyle

Tiedoston [checkstyle.xml](https://github.com/Malpel/ot-harjoitustyo/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Virheilmoituksia voi tarkastella  avaamalla *target/site/checkstyle.html* selaimessa
