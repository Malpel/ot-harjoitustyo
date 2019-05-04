# Arkkitehtuurikuvaus

## Rakenne
Sovelluksen pakkausrakenne on seuraavanlainen:

![Rakenne](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkausrakenne.png)

Pakkaus _ui_ sisältää JavaFX-käyttöliittymän, _domain_ sovelluslogiikan. Tietokantaan tallentamisesta huolehtii pakkaus _dao_.

## Käyttöliittymä

Sovelluksen käyttöliittymässä on neljä erillistä näkymää

- aloitus
- high scores
- pelinäkymä
- pisteiden talletus

Jokainen näkymä on oma Scene-olionsa ja ne ovat yksi kerrallaan sijoitettuna stageen.

Sovelluslogiikka ja käyttöliittymä on pyritty eristämään toisistaan. Käyttöliittymä ainoastaan kutsuu sovellusloogikan toteuttavan luokan _TetrisServicen_ metodeja sopivilla parametreilla.

Pelinäkymän osia, varsinaista "pelilautaa" ja putoavaa tetrominoa piirretään jatkuvasti uusiksi kutsumalla metodeja `drawMatrix()` ja `drawFaller()`.

## Sovelluslogiikka

Sovelluksen pelilogiikan toteuttavat luokat _TetrisService_, _Tetris_ ja _Tetromino_.

![Luokkakaavio](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.png)

_Tetris_ vastaa "pelilaudasta". Se huolehtii palojen kiinnittämisestä matriisiin, täysistä riveistä ja niiden poistosta, sekä täytetystä matriisista. Sen kautta siis tarkistetaan, voiko putoava tetromino jatkaa matkaa alaspäin.

_Tetromino_ taas keskittyy puotavan palan tietoihin, tärkeimpänä sen koordinaatit matriisissa/pelilaudalla.

Koko sovelluksen ydin on _TetrisService_. Se vastaa pelitilan päivittämisestä. _Tetris_- ja _Tetromino_-luokkien toiminta tapahtuu _TetrisServicen_ kutsumana. Lisäksi pisteiden tallentaminen tapahtuu _TetrisServicen_ kautta.

## Tietokantaan tallentaminen

Pisteiden pysyväistallennus tapahtuu kutsumalla _ScoreDao_-luokkaa. Pysyväistallennus toteutetaan Data Access Object-mallin mukaisen luokan _ScoreDao_ avulla. Sovellus käyttää tietokantana perinteistä relaatiotietokantaa sekä [SQLite](https://www.sqlite.org/index.html)-tietokannanhallintajärjestelmää.

## Päätoiminnallisuudet

Seuraavaksi tarkastelussa on joitakin sovelluksen päätoimintoja kuvattuna sekvenssikaavioina.

### Pelilaudan päivitys

Kun pelilautaa tulee päivittää, joko silloin kun tetromino putoaa automaattisesti tai sitä pudotetaan käyttäjän toimesta, tapahtuu seuraavaa:  

![Sekvenssikaavio1](https://github.com/Malpel/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi1.png)

