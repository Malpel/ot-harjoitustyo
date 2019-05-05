# Arkkitehtuurikuvaus

## Rakenne
Sovelluksen pakkausrakenne on seuraavanlainen:

![rakenne](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkausrakenne.png)

Pakkaus _ui_ sisältää JavaFX-käyttöliittymän, _domain_ sovelluslogiikan. Tietokantaan tallentamisesta huolehtii pakkaus _dao_.

## Käyttöliittymä

Sovelluksen käyttöliittymässä on neljä erillistä näkymää

- aloitus
- high scores
- pelinäkymä
- pisteiden talletus

Käyttöliittymä on ohjelmallisesti toteutettu [JavaFX:llä]() luokassa TetrisUi. Sovelluslogiikka ja käyttöliittymä on pyritty eristämään toisistaan; käyttöliittymä ainoastaan kutsuu sovellusloogikan toteuttavan luokan _TetrisServicen_ metodeja sopivilla parametreilla.

Pelinäkymän osia, varsinaista "pelilautaa" ja putoavaa tetrominoa piirretään jatkuvasti uusiksi kutsumalla metodeja `drawMatrix()` ja `drawFaller()`.

## Sovelluslogiikka

Sovelluksen pelilogiikan toteuttavat luokat _TetrisService_, _Tetris_ ja _Tetromino_.

![luokkakaavio](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.png)

_Tetris_ vastaa "pelilaudasta". Se huolehtii palojen kiinnittämisestä matriisiin, täysistä riveistä ja niiden poistosta, sekä täytetystä matriisista. Sen kautta siis tarkistetaan, voiko putoava tetromino jatkaa matkaa alaspäin.

_Tetromino_ taas keskittyy puotavan palan tietoihin, tärkeimpänä sen koordinaatit matriisissa/pelilaudalla.

Koko sovelluksen ydin on _TetrisService_. Se vastaa pelitilan päivittämisestä ja kaikki pelilaudan toiminta tapahtuu sen kautta; _Tetris_- ja _Tetromino_-luokat toimivat _TetrisServicen_ kutsumana. Kaikki käyttöliittymän toiminnot, jotka liittyvät pelaamiseen, toteutetaan tämän luokan metodeina. Lisäksi pisteiden tallentaminen tapahtuu myös _TetrisServicen_ kautta.

## Tietokantaan tallentaminen

Pisteiden pysyväistallennus tapahtuu kutsumalla _ScoreDao_-luokkaa. Pysyväistallennus toteutetaan [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object)-mallin mukaisen luokan _ScoreDao_ avulla. Sovellus käyttää tietokantana perinteistä relaatiotietokantaa sekä [SQLite](https://www.sqlite.org/index.html)-tietokannanhallintajärjestelmää.

## Päätoiminnallisuudet

Tarkastellaan seuraavaksi kahta pelin päätoiminnallisuutta sekvenssikaavioiden avulla.

### Pelilaudan päivitys

Kun pelilautaa tulee päivittää, joko silloin kun tetromino putoaa automaattisesti tai sitä pudotetaan käyttäjän toimesta, tapahtuu seuraavaa:  

![sekvenssikaavio1](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi1.png)

Käyttöliittymästä kutsutaan metodia `updateTetris()`, joka pyytää putoavan tetrominon koordinaatit. Sen jälkeen kutsutaan metodia `blocked(tetromino, y, x)`, joka tarkistaa, että tetrominon uudet koordinaatit eivät törmää pelilaudan laitoihin tai pudonnettein palojen matriisiin. 

Jos tuloksena on `false`, kutsutaan _Tetrominon_ metodia `dropDown()`, joka kasvattaa y-koordinaattia yhdellä. Käyttöliittymä piirtää pelilaudan ja tetrominon uusilla koordinaateilla.

Jos tuloksena on `true`, päivitetään pelilautaa metodilla `updateMatrix(tetromino)`. Tetrominon osat kirjoitetaan matriisiin, jonka jälkeen tarkistetaan syntyneet täydet rivit metodilla `checkFullRows()`. Täysien rivien määrä palautetaan _TetrisServicelle_, joka päivittää pistemäärän ja vaikeustason, ja luo uuden tetrominon. Tässä vaiheessa tehdään samanlainen tarkistus kuin aikaisemmin; jos tetromino osuu matriisiin, peli päättyy.


### Tetrominon pyörittäminen

Tetrominon pyörittäminen noudattaa osittain samankaltaista tapahtumasarjaa. 

![sekvenssikaavio2](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi2.png)


Kun käyttäjä painaa välilyöntiä, tapahtumakäsittelijä kutsuu metodia `rotate()`. Sen sisällä haetaan putoavalta tetrominolta tarvittavat tiedot, jonka jälkeen kutsutaan metodia `blocked(tetromino, y, x)`. Näin varmistetaan, että tetromino ei pyörähtäessään törmää pelilaudan laitoihin tai pudonneisiin paloihin. Jos törmäys tapahtuisi, ei tehdä mitään, muutoin tetromino pyöräytetään.

