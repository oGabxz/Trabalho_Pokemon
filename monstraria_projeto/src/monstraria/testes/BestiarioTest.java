package monstraria.testes;

import monstraria.Bestiario;
import monstraria.Monstro;
import monstraria.MonstroFogo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BestiarioTest {

    @Test
    void naoRegistraDuplicado() {
        Bestiario bestiario = new Bestiario();
        Monstro a = new MonstroFogo();
        Monstro b = new MonstroFogo();
        bestiario.registrar(a);
        bestiario.registrar(b);
        assertEquals(1, bestiario.getQuantidadeRegistrada());
    }

    @Test
    void marcaComoDescoberto() {
        Bestiario bestiario = new Bestiario();
        Monstro a = new MonstroFogo();
        bestiario.registrar(a);
        assertTrue(bestiario.foiDescoberto("Emberit"));
        assertFalse(bestiario.foiDescoberto("Pyronix"));
    }
}
