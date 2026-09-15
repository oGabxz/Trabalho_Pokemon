package monstraria.testes;

import monstraria.MonstroAgua;
import monstraria.Monstro;
import monstraria.Treinador;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class CapturaTest {

    @Test
    void chanceAumentaComVidaBaixa() {
        Treinador treinador = new Treinador("Teste");
        Monstro selvagem = new MonstroAgua();
        double chanceComVidaCheia = treinador.calcularChanceCaptura(selvagem);
        selvagem.receberDano(selvagem.getVidaMaxima() - 1);
        double chanceComVidaBaixa = treinador.calcularChanceCaptura(selvagem);
        assertTrue(chanceComVidaBaixa > chanceComVidaCheia);
    }

    @Test
    void capturaComSorteioFavoravelAdicionaAEquipe() {
        Treinador treinador = new Treinador("Teste");
        Monstro selvagem = new MonstroAgua();
        Random sorteioSempreZero = new Random() {
            @Override
            public double nextDouble() {
                return 0.0;
            }
        };
        boolean capturado = treinador.tentarCapturar(selvagem, sorteioSempreZero);
        assertTrue(capturado);
        assertEquals(1, treinador.getEquipe().size());
    }
}
