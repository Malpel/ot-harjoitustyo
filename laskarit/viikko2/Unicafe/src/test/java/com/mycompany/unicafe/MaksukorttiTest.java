package com.mycompany.unicafe;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriLuoSaldonOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(2000);
        assertEquals(3000, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeOikeinKunRahaaTarpeeksi() {
        kortti.otaRahaa(1000);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(2500);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahatRiittavatTrue() {
        assertThat(kortti.otaRahaa(500), is(true));
    }
    
    @Test
    public void rahatRiittavatFalse() {
        assertThat(kortti.otaRahaa(1200), is(false));
    }
    
    @Test
    public void alkuSaldoTulostuuOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test public void saldoTulostuuOikeinMaksunJalkeen() {
        kortti.otaRahaa(325);
        assertEquals("saldo: 6.75", kortti.toString());
    }
}
