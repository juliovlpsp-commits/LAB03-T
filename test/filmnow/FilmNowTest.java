package filmnow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testes da classe filmnow
 */
public class FilmNowTest {

    @Test
    public void testGetFilmesVazio() {
        FilmNow fn = new FilmNow();
        Filme[] filmes = fn.getFilmes();
        assertEquals(100, filmes.length);
        for (Filme f : filmes) {
            assertNull(f);
        }
    }

    @Test
    public void testGetFilmePosicaoSemFilme() {
        FilmNow fn = new FilmNow();
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testCadastraFilme() {
        FilmNow fn = new FilmNow();
        assertTrue(fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema"));
        Filme filme = fn.getFilme(1);
        assertNotNull(filme);
        assertEquals("Anatomia de uma Queda", filme.getNome());
        assertEquals("2023", filme.getAno());
        assertEquals("Cinema", filme.getLocal());
    }

    @Test
    public void testCadastraFilmeRepetido() {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");
        assertFalse(fn.cadastraFilme(5, "Anatomia de uma Queda", "2023", "Popcornflix"));
    }

    @Test
    public void testCadastraFilmeRepetidoOutraPosicao() {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");
        assertFalse(fn.cadastraFilme(2, "Anatomia de uma Queda", "2023", "Popcornflix"));
        assertNull(fn.getFilme(2));
    }

    @Test
    public void testCadastraFilmeSobrescreveRemoveAntigo() {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");
        fn.cadastraFilme(1, "Shrek 2", "2004", "Netflix");
        assertTrue(fn.cadastraFilme(2, "Anatomia de uma Queda", "2023", "Popcornflix"));
        assertEquals("Anatomia de uma Queda", fn.getFilme(2).getNome());
    }

    @Test
    public void testCadastraFilmeSobrescrevePosicao() {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");
        fn.cadastraFilme(1, "Shrek 2", "2004", "Netflix");
        Filme filme = fn.getFilme(1);
        assertEquals("Shrek 2", filme.getNome());
        assertEquals("2004", filme.getAno());
    }

    @Test
    public void testAdicionaHotEContem() {
        FilmNow fn = new FilmNow();
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        assertTrue(fn.contemHot(filme));
    }

    @Test
    public void testContemHotFilmeForaDaHotList() {
        FilmNow fn = new FilmNow();
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        assertFalse(fn.contemHot(filme));
    }

    @Test
    public void testRemoveHot() {
        FilmNow fn = new FilmNow();
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        fn.removeHot(1);
        assertFalse(fn.contemHot(filme));
    }

    @Test
    public void testHotFilmeSoUmaVez() {
        FilmNow fn = new FilmNow();
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        fn.adicionaHot(filme, 3);
        String resultado = fn.listaHots();
        assertEquals(1, resultado.split("Anatomia").length - 1);
        assertTrue(resultado.contains("3 - Anatomia de uma Queda, 2023"));
    }

    @Test
    public void testAdicionaHotSubstituiPosicao() {
        FilmNow fn = new FilmNow();
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        Filme filme2 = new Filme("Shrek 2", "2004", "Netflix");
        fn.adicionaHot(filme1, 1);
        fn.adicionaHot(filme2, 1);
        assertFalse(fn.contemHot(filme1));
        assertTrue(fn.contemHot(filme2));
    }

    @Test
    public void testListaHots() {
        FilmNow fn = new FilmNow();
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        String resultado = fn.listaHots();
        assertTrue(resultado.contains("1 - Anatomia de uma Queda, 2023"));
    }

    @Test
    public void testListaHotsVazia() {
        FilmNow fn = new FilmNow();
        assertEquals("", fn.listaHots());
    }
}
