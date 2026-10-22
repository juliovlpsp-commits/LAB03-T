# Plano de testes - FilmNow

Plano de testes de unidade da Etapa 3 de verificacao e validacao.
Framework: **JUnit 5**. Comando: `runtests.bat` (Windows) ou `./runtests.ps1`.

## Escopo

Os testes cobrem o comportamento de `Filme`, `FilmNow` e as mensagens de entrada e saida das funcionalidades especificadas em `MainFilmNow`.

## Casos de teste - FilmeTest

| id | cenario | resultado esperado |
| --- | --- | --- |
| FT1 | filmes com mesmo nome e ano e locais diferentes | sao iguais |
| FT2 | filmes com nomes diferentes | sao diferentes |
| FT3 | mesmo nome e anos diferentes | sao diferentes |
| FT4 | filme comparado com String | sao diferentes |
| FT5 | filme comparado com null | sao diferentes |
| FT6 | filme comparado consigo mesmo | sao iguais |
| FT7 | representacao de filme com ano | nome e ano separados por virgula |
| FT8 | representacao de filme sem ano | apenas o nome |
| FT9 | getters | devolvem nome, ano e local |

## Casos de teste - FilmNowTest

| id | cenario | resultado esperado |
| --- | --- | --- |
| FN1 | sistema novo | array de 100 posicoes vazio |
| FN2 | leitura de posicao vazia | null |
| FN3 | cadastro valido | sucesso e filme armazenado |
| FN4 | cadastro duplicado | falha |
| FN5 | cadastro duplicado em outra posicao | falha e posicao permanece vazia |
| FN6 | cadastro em posicao ocupada com filme diferente | substitui o filme anterior |
| FN7 | cadastro na posicao limite 100 | sucesso |
| FN8 | cadastro nas posicoes 0 e 101 pelo sistema | mensagem de posicao invalida |
| FN9 | cadastro com nome ou local vazio pelo sistema | mensagem de filme invalido |
| FN10 | cadastro repetido pelo sistema | mensagem de filme ja adicionado |
| FN11 | cadastro com ano vazio | filme aceito |
| FN12 | detalhamento com todos os dados | nome, ano e local |
| FN13 | detalhamento sem ano | apenas nome e local |
| FN14 | detalhamento de posicao vazia | nenhuma mensagem adicional |
| FN15 | detalhamento nas posicoes 0 e 101 | mensagem de posicao invalida |
| FN16 | detalhamento de filme na HotList | representacao com 🔥, nome, ano e local |
| FN17 | adicionaHot em posicao livre | filme fica na HotList |
| FN18 | contemHot para filme ausente | false |
| FN19 | removeHot | filme deixa de estar na HotList |
| FN20 | mesmo filme adicionado em outra posicao HotList | aparece apenas uma vez |
| FN21 | nova atribuicao a posicao HotList ocupada | filme anterior deixa de ser hot |
| FN22 | listaHots com filme | posicao e representacao do filme |
| FN23 | listaHots vazia | string vazia |

## Execucao

O projeto usa o `junit-platform-console-standalone` disponibilizado como `junit.jar`.
O arquivo e baixado automaticamente na primeira execucao e nao e versionado.
Os testes estao em `test/filmnow/`.
