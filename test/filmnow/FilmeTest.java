package filmnow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testes da classe filme
 */
public class FilmeTest {

    @Test
    public void testNomeAnoSemAno() {
        Filme filme = new Filme("20 dias em Mariupol", "", "Cinema");

        assertEquals("20 dias em Mariupol", filme.toString());
    }

    @Test
    public void testEqualsMesmoFilme() {
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        Filme filme2 = new Filme("Anatomia de uma Queda", "2023", "Popcornflix");
        assertEquals(filme1, filme2);
    }

    @Test
    public void testEqualsFilmesDiferentes() {
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        Filme outroFilme = new Filme("Shrek 2", "2004", "Netflix");
        assertNotEquals(filme1, outroFilme);
    }

    @Test
    public void testEqualsMesmoNomeAnoDiferente() {
        Filme filme1 = new Filme("Shrek 2", "2004", "Netflix");
        Filme filme2 = new Filme("Shrek 2", "2005", "Netflix");
        assertNotEquals(filme1, filme2);
    }

    @Test
    public void testEqualsComString() {
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        assertNotEquals(filme1, "Donzela");
    }

    @Test
    public void testEqualsComNull() {
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        assertNotEquals(filme1, null);
    }

    @Test
    public void testEqualsComSiMesmo() {
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        assertEquals(filme1, filme1);
    }

    @Test
    public void testToString() {
        Filme filme = new Filme("A Sociedade da Neve", "2023", "Netflix");
        assertEquals("A Sociedade da Neve, 2023", filme.toString());
    }

    @Test
    public void testGetters() {
        Filme filme = new Filme("Duna", "2021", "HBO Max");
        assertEquals("Duna", filme.getNome());
        assertEquals("2021", filme.getAno());
        assertEquals("HBO Max", filme.getLocal());
    }
}
