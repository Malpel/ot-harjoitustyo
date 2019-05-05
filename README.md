# Ohjelmistotekniikka - harjoitustyö

## Tetris-klooni

Sovellus on toteutus klassikkopeli Tetriksestä.

## Dokumentaatio

[Käyttöohje](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumetti](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


## Release

[Viikko 5](https://github.com/Malpel/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/Malpel/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/Malpel/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivi

### Testaus

Testien suorittaminen

`mvn test`

Testikattavuusraportin luominen

`mvn jacoco:report`

Raporttia voi tarkastella avaamalla tiedosto *target/site/jacoco/index.html* selaimessa 

## Jarin generointi

Komento

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston Tetris-1.0-SNAPSHOT.jar

## JavaDoc

JavaDoc generoidaan komennolla

`mvn javadoc:javadoc`

JavaDocia voi tarkastella avaamalla tiedosto _target/site/apidocs/index.html_ selaimessa

## Checkstyle

Tiedoston [checkstyle.xml](https://github.com/Malpel/ot-harjoitustyo/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Virheilmoituksia voi tarkastella  avaamalla *target/site/checkstyle.html* selaimessa
