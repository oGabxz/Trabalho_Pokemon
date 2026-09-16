package monstraria.testes;

import monstraria.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonstroTest {

    @Test
    void fogoVenceContraPlanta() {
        Monstro fogo = new MonstroFogo();
        Monstro planta = new MonstroPlanta();
        assertEquals(2.0, fogo.calcularEfetividadeContra(planta));
    }

    @Test
    void fogoPerdeContraAgua() {
        Monstro fogo = new MonstroFogo();
        Monstro agua = new MonstroAgua();
        assertEquals(0.5, fogo.calcularEfetividadeContra(agua));
    }

    @Test
    void receberDanoReduzVida() {
        Monstro agua = new MonstroAgua();
        int vidaAntes = agua.getVidaAtual();
        agua.receberDano(10);
        assertEquals(vidaAntes - 10, agua.getVidaAtual());
    }

    @Test
    void ganharExperienciaSuficienteEvolui() {
        Monstro planta = new MonstroPlanta();
        assertEquals(1, planta.getEstagio());
        assertEquals("Sproutle", planta.getNome());
        // XP suficiente para alcançar nível 5 (limiar da 1ª evolução)
        planta.ganharExperiencia(300);
        assertTrue(planta.getEstagio() >= 2);
        assertEquals("Mossclaw", planta.getNome());
    }

    @Test
    void naoUltrapassaQuatroGolpes() {
        Monstro fogo = new MonstroFogo();
        fogo.ganharExperiencia(1000); // evolui até o estágio final
        assertTrue(fogo.getGolpes().size() <= 4);
    }
}
