package filmnow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testes da classe filmnow
 */
public class FilmNowTest {

    private FilmNow filmNow;

    @BeforeEach
    public void preparaFilmNow() {
        this.filmNow = new FilmNow();
    }

    private String executaComando(String opcao, FilmNow fn, String entrada) throws Exception {
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8.name());
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream saidaOriginal = System.out;
        try {
            System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8));
            MainFilmNow.comando(opcao, fn, scanner);
        } finally {
            System.setOut(saidaOriginal);
        }
        return saida.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    private String executaAdicao(FilmNow fn, String entrada) throws Exception {
        return executaComando("A", fn, entrada);
    }

    private String executaDetalhamento(FilmNow fn, String entrada) throws Exception {
        return executaComando("D", fn, entrada);
    }

    @Test
    public void testAdicionaFilmeNaPosicaoInvalida() {
        FilmNow fn = this.filmNow;

        assertFalse(fn.cadastraFilme(0, "Avatar", "2009", "Disney+"));
        assertFalse(fn.cadastraFilme(101, "Avatar", "2009", "Disney+"));
        assertNull(fn.getFilme(1));
        assertNull(fn.getFilme(100));
    }

    @Test
    public void testAdicionaFilmeEmPosicaoVazia() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "1\nAvatar\n2009\nDisney+\n").contains("FILME ADICIONADO"));
        assertEquals("Avatar", fn.getFilme(1).getNome());
    }

    @Test
    public void testAdicionaFilmeEmPosicaoExistente() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n2023\nCinema\n").contains("FILME ADICIONADO"));
        assertEquals("20 dias em Mariupol", fn.getFilme(1).getNome());
    }

    @Test
    public void testAdicionaFilmeDuplicadoEmOutraPosicao() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "3\nAvatar\n2009\nDisney+\n").contains("FILME JÁ ADICIONADO"));
        assertNull(fn.getFilme(3));
    }

    @Test
    public void testAdicionaMesmoNomeEAnoComLocalDiferente() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(executaAdicao(fn, "3\nAvatar\n2009\nPopcornflix\n").contains("FILME JÁ ADICIONADO"));
        assertNull(fn.getFilme(3));
    }

    @Test
    public void testAdicionaFilmeNaPosicaoLimite() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "100\nAvatar\n2009\nDisney+\n").contains("FILME ADICIONADO"));
        assertEquals("Avatar", fn.getFilme(100).getNome());
    }

    @Test
    public void testAdicionaFilmeAcimaDoLimite() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "101\n").contains("POSIÇÃO INVÁLIDA"));
        assertNull(fn.getFilme(100));
    }

    @Test
    public void testAdicionaFilmeAbaixoDoLimite() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "0\n").contains("POSIÇÃO INVÁLIDA"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testAdicionaFilmeComLocalVazio() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n2023\n\n").contains("FILME INVALIDO"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testAdicionaFilmeComAnoVazio() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "1\n20 dias em Mariupol\n\nCinema\n").contains("FILME ADICIONADO"));
        assertEquals("", fn.getFilme(1).getAno());
    }

    @Test
    public void testAdicionaFilmeComNomeVazio() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaAdicao(fn, "1\n\n2023\nCinema\n").contains("FILME INVALIDO"));
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testDetalhaFilmeComTodosOsDados() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertEquals("\nFilme> Avatar, 2009\nDisney+\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testDetalhaFilmeSemAno() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "20 dias em Mariupol", "", "Cinema");

        assertEquals("\nFilme> 20 dias em Mariupol\nCinema\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testDetalhaPosicaoSemFilme() throws Exception {
        FilmNow fn = this.filmNow;

        assertEquals("\nFilme> ", executaDetalhamento(fn, "100\n"));
    }

    @Test
    public void testDetalhaPosicaoAbaixoDoLimite() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaDetalhamento(fn, "0\n").contains("POSIÇÃO INVÁLIDA!"));
    }

    @Test
    public void testDetalhaPosicaoAcimaDoLimite() throws Exception {
        FilmNow fn = this.filmNow;

        assertTrue(executaDetalhamento(fn, "101\n").contains("POSIÇÃO INVÁLIDA!"));
    }

    @Test
    public void testDetalhaFilmeDaHotList() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");
        fn.adicionaHot(fn.getFilme(1), 1);

        assertEquals("\nFilme> 🔥 Avatar, 2009\nDisney+\n", executaDetalhamento(fn, "1\n"));
    }

    @Test
    public void testMostrarFilmes() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");
        fn.cadastraFilme(4, "Shrek 2", "2004", "Netflix");

        String saida = executaComando("M", fn, "");

        assertTrue(saida.contains("1 - Avatar"));
        assertTrue(saida.contains("4 - Shrek 2"));
        assertFalse(saida.contains("2 - "));
    }

    @Test
    public void testExibirHotList() throws Exception {
        FilmNow fn = this.filmNow;
        fn.adicionaHot(new Filme("Avatar", "2009", "Disney+"), 2);

        String saida = executaComando("E", fn, "");

        assertTrue(saida.contains("2 - Avatar, 2009"));
    }

    @Test
    public void testAtribuirHotList() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        executaComando("H", fn, "1\n3\n");

        assertTrue(fn.contemHot(fn.getFilme(1)));
        assertTrue(fn.listaHots().contains("3 - Avatar, 2009"));
    }

    @Test
    public void testRemoverHotList() throws Exception {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");
        fn.adicionaHot(fn.getFilme(1), 3);

        executaComando("R", fn, "3\n");

        assertFalse(fn.contemHot(fn.getFilme(1)));
    }

    @Test
    public void testGetFilmesVazio() {
        FilmNow fn = this.filmNow;
        Filme[] filmes = fn.getFilmes();
        assertEquals(100, filmes.length);
        for (Filme filme : filmes) {
            assertNull(filme);
        }
    }

    @Test
    public void testGetFilmePosicaoSemFilme() {
        FilmNow fn = this.filmNow;
        assertNull(fn.getFilme(1));
    }

    @Test
    public void testCadastraFilme() {
        FilmNow fn = this.filmNow;
        assertTrue(fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema"));
        Filme filme = fn.getFilme(1);
        assertNotNull(filme);
        assertEquals("Anatomia de uma Queda", filme.getNome());
        assertEquals("2023", filme.getAno());
        assertEquals("Cinema", filme.getLocal());
    }

    @Test
    public void testCadastraFilmeSubstituiFilmeAntigoEPermiteNovoEmOutraPosicao() {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");
        fn.cadastraFilme(1, "Shrek 2", "2004", "Netflix");

        assertTrue(fn.cadastraFilme(2, "Avatar", "2009", "Popcornflix"));
        assertEquals("Avatar", fn.getFilme(2).getNome());
    }

    @Test
    public void testCadastraFilmeSubstituiMesmoFilmeNaMesmaPosicao() {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Avatar", "2009", "Disney+");

        assertTrue(fn.cadastraFilme(1, "Avatar", "2009", "Popcornflix"));
        assertEquals("Popcornflix", fn.getFilme(1).getLocal());
    }

    @Test
    public void testCadastraFilmeRepetido() {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");
        assertFalse(fn.cadastraFilme(5, "Anatomia de uma Queda", "2023", "Popcornflix"));
        assertNull(fn.getFilme(5));
    }

    @Test
    public void testCadastraFilmeSobrescrevePosicao() {
        FilmNow fn = this.filmNow;
        fn.cadastraFilme(1, "Anatomia de uma Queda", "2023", "Cinema");

        assertTrue(fn.cadastraFilme(1, "Shrek 2", "2004", "Netflix"));
        assertEquals("Shrek 2", fn.getFilme(1).getNome());
    }

    @Test
    public void testAdicionaHotEContem() {
        FilmNow fn = this.filmNow;
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        assertTrue(fn.contemHot(filme));
    }

    @Test
    public void testContemHotFilmeForaDaHotList() {
        FilmNow fn = this.filmNow;
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        assertFalse(fn.contemHot(filme));
    }

    @Test
    public void testRemoveHot() {
        FilmNow fn = this.filmNow;
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        fn.removeHot(1);
        assertFalse(fn.contemHot(filme));
    }

    @Test
    public void testHotFilmeSoUmaVez() {
        FilmNow fn = this.filmNow;
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        fn.adicionaHot(filme, 3);
        String resultado = fn.listaHots();
        assertEquals(1, resultado.split("Anatomia").length - 1);
        assertTrue(resultado.contains("3 - Anatomia de uma Queda, 2023"));
    }

    @Test
    public void testAdicionaHotSubstituiPosicao() {
        FilmNow fn = this.filmNow;
        Filme filme1 = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        Filme filme2 = new Filme("Shrek 2", "2004", "Netflix");
        fn.adicionaHot(filme1, 1);
        fn.adicionaHot(filme2, 1);
        assertFalse(fn.contemHot(filme1));
        assertTrue(fn.contemHot(filme2));
    }

    @Test
    public void testListaHots() {
        FilmNow fn = this.filmNow;
        Filme filme = new Filme("Anatomia de uma Queda", "2023", "Cinema");
        fn.adicionaHot(filme, 1);
        assertEquals("1 - Anatomia de uma Queda, 2023", fn.listaHots());
    }

    @Test
    public void testListaHotsVazia() {
        FilmNow fn = this.filmNow;
        assertEquals("", fn.listaHots());
    }
}
