package filmnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testes da classe filme
 */
public class FilmeTest {

    private Filme filmeBase;

    @BeforeEach
    public void preparaFilme() {
        this.filmeBase = new Filme("Avatar", "2009", "Disney+");
    }

    @Test
    public void testNomeAnoSemAno() {
        Filme filme = new Filme("20 dias em Mariupol", "", "Cinema");

        assertEquals("20 dias em Mariupol", filme.toString());
    }

    @Test
    public void testEqualsMesmoFilme() {
        Filme filme2 = new Filme("Avatar", "2009", "Popcornflix");
        assertEquals(this.filmeBase, filme2);
    }

    @Test
    public void testEqualsFilmesDiferentes() {
        Filme outroFilme = new Filme("Shrek 2", "2004", "Netflix");
        assertNotEquals(this.filmeBase, outroFilme);
    }

    @Test
    public void testEqualsMesmoNomeAnoDiferente() {
        Filme filme = new Filme("Avatar", "2010", "Disney+");
        assertNotEquals(this.filmeBase, filme);
    }

    @Test
    public void testEqualsComString() {
        assertNotEquals(this.filmeBase, "Avatar");
    }

    @Test
    public void testEqualsComNull() {
        assertNotEquals(this.filmeBase, null);
    }

    @Test
    public void testEqualsComSiMesmo() {
        assertEquals(this.filmeBase, this.filmeBase);
    }

    @Test
    public void testToString() {
        assertEquals("Avatar, 2009", this.filmeBase.toString());
    }

    @Test
    public void testGetters() {
        assertEquals("Avatar", this.filmeBase.getNome());
        assertEquals("2009", this.filmeBase.getAno());
        assertEquals("Disney+", this.filmeBase.getLocal());
    }
}
