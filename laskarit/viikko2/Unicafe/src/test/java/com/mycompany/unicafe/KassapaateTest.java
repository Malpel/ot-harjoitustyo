package com.mycompany.unicafe;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti eiRiitaKortti;

    public KassapaateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
        eiRiitaKortti = new Maksukortti(200);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void uudenKassanSaldoOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void uusiKassaEiOleMyynytMaukkaita() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void uusiKassaEiOleMyynytEdullisia() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void riittavaMaksuKasvattaaSaldoaEdullinen() {
        kassa.syoEdullisesti(500);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void riittavaMaksuKasvattaaSaldoaMaukas() {
        kassa.syoMaukkaasti(1000);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void vaihtorahaOikeinEdullinen() {
        assertEquals(160, kassa.syoEdullisesti(400));
    }

    @Test
    public void vaihtorahaOikeinMaukas() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }

    @Test
    public void riittavaMaksuKasvattaaMyytyjenEdullistenMaaraa() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void riittavaMaksuKasvattaaMyytyjenMaukkaidenMaaraa() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaMaksuEiKasvataMyytyjenEdullistenMaaraa() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaMaksuEiKasvataMyytyjenMaukkaidenMaaraa() {
        kassa.syoMaukkaasti(200);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaMaksuEiKasvataSaldoaEdullinen() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void eiRiittavaMaksuEiKasvataSaldoaMaukas() {
        kassa.syoMaukkaasti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void eiRiittavaMaksuVaihtorahaOikeinEdullinen() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }

    @Test
    public void eiRiittavaMaksuVaihtorahaOikeinMaukas() {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }

    @Test
    public void riittavaSaldoKortillaEdullinen() {
        assertThat(kassa.syoEdullisesti(kortti), is(true));
    }

    @Test
    public void riittavaSaldoKortillaMaukas() {
        assertThat(kassa.syoMaukkaasti(kortti), is(true));
    }

    @Test
    public void riittavaSaldoKortillaKasvattaaMyytyjenMaaraaEdullinen() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void riittavaSaldoKortillaKasvattaaMyytyjenMaaraaMaukas() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
        @Test
    public void eiRiittavaSaldoKortillaEdullinen() {
        assertThat(kassa.syoEdullisesti(eiRiitaKortti), is(false));
    }

    @Test
    public void eiRiittavaSaldoKortillaMaukas() {
        assertThat(kassa.syoMaukkaasti(eiRiitaKortti), is(false));
    }

    @Test
    public void eiRiittavaSaldoKortillaEiKasvataMyytyjenMaaraaEdullinen() {
        kassa.syoEdullisesti(eiRiitaKortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaSaldoKortillaEiKasvataMyytyjenMaaraaMaukas() {
        kassa.syoMaukkaasti(eiRiitaKortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maksuEiRiittavallaSaldollaEiMuutaKortinRahamaaraaEdullinen() {
        kassa.syoEdullisesti(eiRiitaKortti);
        assertEquals(200, eiRiitaKortti.saldo());
    }
    
    @Test
    public void maksuEiRiittavallaSaldollaEiMuutaKortinRahamaaraaMaukas() {
        kassa.syoMaukkaasti(eiRiitaKortti);
        assertEquals(200, eiRiitaKortti.saldo());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstaessaEdullinen() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstaessaMaukas() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    
    @Test
    public void kortinlatausKasvattaaKassanRahamaaraaOikein() {
        kassa.lataaRahaaKortille(kortti, 5678);
        assertEquals(105678, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaSummaa() {
        kassa.lataaRahaaKortille(kortti, -5678);
        assertEquals(100000, kassa.kassassaRahaa());
    }

}
