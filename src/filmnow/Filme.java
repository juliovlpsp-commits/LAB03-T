package filmnow;

/**
 * representa um filme pelo seu nome ano de lancamento e local onde pode ser assistido
 */
public class Filme {

    private String nome;
    private String ano;
    private String local;

    /**
     * cria um filme
     *
     * @param nome  nome do filme
     * @param ano   ano de lancamento do filme
     * @param local local onde o filme pode ser assistido
     */
    public Filme(String nome, String ano, String local) {
        this.nome = nome;
        this.ano = ano;
        this.local = local;
    }

    /**
     * acessa o nome do filme
     *
     * @return o nome do filme
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * acessa o ano de lancamento do filme
     *
     * @return o ano de lancamento do filme
     */
    public String getAno() {
        return this.ano;
    }

    /**
     * acessa o local onde o filme pode ser assistido
     *
     * @return o local onde o filme pode ser assistido
     */
    public String getLocal() {
        return this.local;
    }

    /**
     * dois filmes sao iguais se possuem o mesmo nome e o mesmo ano de lancamento
     *
     * @param o objeto a ser comparado
     * @return true se os filmes sao iguais false caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Filme filme = (Filme) o;
        return this.nome.equals(filme.nome) && this.ano.equals(filme.ano);
    }

    /**
     * representacao textual do filme com nome e ano de lancamento
     *
     * @return o nome e o ano de lancamento do filme
     */
    @Override
    public String toString() {
        if (this.ano == null || this.ano.isEmpty()) {
            return this.nome;
        }
        return this.nome + ", " + this.ano;
    }
}
