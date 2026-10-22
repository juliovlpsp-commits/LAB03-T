package filmnow;

/**
 * sistema que mantem os seus filmes prediletos
 * podem existir 100 filmes
 * cada filme e representado por um objeto da classe filme
 */
public class FilmNow {

    private static final int QUANTIDADE_FILMES = 100;
    private static final int TAMANHO_HOTLIST = 10;

    private Filme[] filmes;
    private Filme[] hotList;

    /**
     * cria o filmnow
     */
    public FilmNow() {
        this.filmes = new Filme[QUANTIDADE_FILMES];
        this.hotList = new Filme[TAMANHO_HOTLIST];
    }

    /**
     * acessa a lista de filmes mantida
     *
     * @return o array de filmes
     */
    public Filme[] getFilmes() {
        return this.filmes.clone();
    }

    /**
     * acessa os dados de um filme especifico
     *
     * @param posicao posicao do filme no sistema entre 1 e 100
     * @return o filme na posicao null se nao ha filme na posicao
     */
    public Filme getFilme(int posicao) {
        return this.filmes[posicao - 1];
    }

    /**
     * adiciona um filme em uma posicao se ja existir filme na posicao sobrescreve o anterior
     * nao permite adicionar um filme com nome e ano ja existentes no sistema
     *
     * @param posicao posicao do filme entre 1 e 100
     * @param nome    nome do filme
     * @param ano     ano de lancamento do filme
     * @param local   local onde o filme pode ser assistido
     * @return true se o filme foi adicionado false se a posicao e invalida ou ja existe um filme igual em outra posicao
     */
    public boolean cadastraFilme(int posicao, String nome, String ano, String local) {
        if (posicao < 1 || posicao > this.filmes.length) {
            return false;
        }

        Filme novoFilme = new Filme(nome, ano, local);
        int indiceDestino = posicao - 1;
        for (int i = 0; i < this.filmes.length; i++) {
            Filme filme = this.filmes[i];
            if (i != indiceDestino && filme != null && filme.equals(novoFilme)) {
                return false;
            }
        }
        this.filmes[indiceDestino] = novoFilme;
        return true;
    }

    /**
     * adiciona um filme a hotlist se a posicao ja tiver um filme o antigo deixa de ser hot
     * um filme nao aparece mais de uma vez na hotlist
     *
     * @param filme   filme a ser adicionado
     * @param posicao posicao na hotlist entre 1 e 10
     */
    public void adicionaHot(Filme filme, int posicao) {
        removeHot(filme);
        this.hotList[posicao - 1] = filme;
    }

    /**
     * remove o filme de uma posicao da hotlist
     *
     * @param posicao posicao na hotlist entre 1 e 10
     */
    public void removeHot(int posicao) {
        this.hotList[posicao - 1] = null;
    }

    /**
     * remove todas as ocorrencias de um filme da hotlist
     *
     * @param filme filme a ser removido
     */
    public void removeHot(Filme filme) {
        for (int i = 0; i < this.hotList.length; i++) {
            if (this.hotList[i] != null && this.hotList[i].equals(filme)) {
                this.hotList[i] = null;
            }
        }
    }

    /**
     * verifica se um filme esta na hotlist
     *
     * @param filme filme a ser verificado
     * @return true se o filme esta na hotlist false caso contrario
     */
    public boolean contemHot(Filme filme) {
        for (Filme f : this.hotList) {
            if (f != null && f.equals(filme)) {
                return true;
            }
        }
        return false;
    }

    /**
     * lista os filmes da hotlist no formato posicao nome ano
     *
     * @return a representacao da hotlist
     */
    public String listaHots() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < this.hotList.length; i++) {
            if (this.hotList[i] != null) {
                if (resultado.length() > 0) {
                    resultado.append("\n");
                }
                resultado.append((i + 1)).append(" - ").append(this.hotList[i].toString());
            }
        }
        return resultado.toString();
    }
}
