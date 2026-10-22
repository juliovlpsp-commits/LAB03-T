package filmnow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testes da classe filmnow
 */
public class FilmNowTest {

    private String executaComando(FilmNow fn, String nomeMetodo, String entrada) throws Exception {
        Method metodo = MainFilmNow.class.getDeclaredMethod(nomeMetodo, FilmNow.class, Scanner.class);
        metodo.setAccessible(true);
        InputStream entradaOriginal = System.in;
        PrintStream saidaOriginal = System.out;
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8.name()));
            metodo.invoke(null, fn, new Scanner(System.in, StandardCharsets.UTF_8.name()));
        } finally {
            System.setIn(entradaOriginal);
            System.setOut(saidaOriginal);
        }
        return saida.toString(StandardCharsets.UTF_8.name()).replace("\r\n", "\n");
    }

    private String executaAdicao(FilmNow fn, String entrada) throws Exception {
        return executaComando(fn, "adicionaFilme", entrada);
    }

    private String executaDetalhamento(FilmNow fn, String entrada) throws Exception {
        return executaComando(fn, "detalharFilme", entrada);
    }

    @Test
    public void testAdicionaFilmeEmPosicaoVazia() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "1\nAvatar\n2009\nDisney+\n").contains("FILME ADICIONADO"));
        assertEquals("Avatar", fn.getFilme(1).getNome());
    }

    @Test
    public void testAdicionaFilmeEmPosicaoExistente() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n2023\nCinema\n").contains("FILME ADICIONADO"));
        assertEquals("20 dias em Mariupol", fn.getFilme(1).getNome());
    }

    @Test
    public void testAdicionaFilmeDuplicadoEmOutraPosicao() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "3\nAvatar\n2009\nDisney+\n").contains("FILME JÁ ADICIONADO"));
        assertNull(fn.getFilme(3));
    }

    @Test
    public void testAdicionaMesmoNomeEAnoComLocalDiferente() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "3\nAvatar\n2009\nPopcornflix\n").contains("FILME JÁ ADICIONADO"));
        assertNull(fn.getFilme(3));
    }

    @Test
    public void testAdicionaFilmeNaPosicaoLimite() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "100\nAvatar\n2009\nDisney+\n").contains("FILME ADICIONADO"));
        assertEquals("Avatar", fn.getFilme(100).getNome());
    }

    @Test
    public void testAdicionaFilmeAcimaDoLimite() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "101\n").contains("POSIÇÃO INVÁLIDA"));
        assertNull(fn.getFilme(100));
    }

    @Test
    public void testAdicionaFilmeAbaixoDoLimite() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "0\n").contains("POSIÇÃO INVÁLIDA"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testAdicionaFilmeComLocalVazio() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n2023\n\n").contains("FILME INVALIDO"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testAdicionaFilmeComAnoVazio() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n\nCinema\n").contains("FILME ADICIONADO"));
        assertEquals("", fn.getFilme(1).getAno());
    }

    @Test
    public void testAdicionaFilmeComNomeVazio() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaAdicao(fn, "1\n\n2023\nCinema\n").contains("FILME INVALIDO"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testDetalhaFilmeComTodosOsDados() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertEquals("\nFilme> Avatar, 2009\nDisney+\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testDetalhaFilmeSemAno() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "20 dias em Mariupol", "", "Cinema");

        assertEquals("\nFilme> 20 dias em Mariupol\nCinema\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testDetalhaPosicaoSemFilme() throws Exception {
        FilmNow fn = new FilmNow();

        assertEquals("\nFilme> ", executaDetalhamento(fn, "100\n"));
    }

    @Test
    public void testDetalhaPosicaoAbaixoDoLimite() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaDetalhamento(fn, "0\n").contains("POSIÇÃO INVÁLIDA!"));
    }

    @Test
    public void testDetalhaPosicaoAcimaDoLimite() throws Exception {
        FilmNow fn = new FilmNow();

        assertTrue(executaDetalhamento(fn, "101\n").contains("POSIÇÃO INVÁLIDA!"));
    }

    @Test
    public void testDetalhaFilmeDaHotList() throws Exception {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");
        fn.adicionaHot(fn.getFilme(1), 1);

        assertEquals("\nFilme> 🔥 Avatar, 2009\nDisney+\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testGetFilmesVazio() {
        FilmNow fn = new FilmNow();
        Filme[] filmes = fn.getFilmes();
        assertEquals(100, filmes.length);
        for (Filme filme : filmes) {
            assertNull(filme);
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
        assertNull(fn.getFilme(5));
    }

    @Test
    public void testCadastraFilmeSobrescrevePosicao() {
        FilmNow fn = new FilmNow();
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");

        assertTrue(fn.cadastraFilme(1, "Shrek 2", "2004", "Netflix"));
        assertEquals("Shrek 2", fn.getFilme(1).getNome());
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
        assertEquals("1 - Anatomia de uma Queda, 2023", fn.listaHots());
    }

    @Test
    public void testListaHotsVazia() {
        FilmNow fn = new FilmNow();
        assertEquals("", fn.listaHots());
    }
}
