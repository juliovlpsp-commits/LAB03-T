# Plano de testes - FilmNow

Plano de testes de unidade da Etapa 3 de verificacao e validacao, executados com JUnit 5 por `runtests.bat`.

## FilmeTest

| caso | entrada ou situacao | resultado esperado |
| --- | --- | --- |
| igualdade | mesmo nome e ano com locais diferentes | filmes iguais |
| desigualdade | nome ou ano diferente | filmes diferentes |
| comparacoes | String, null e o proprio objeto | resultado de igualdade correto |
| representacao com ano | nome e ano preenchidos | `nome, ano` |
| representacao sem ano | ano vazio | somente o nome |
| getters | filme criado com os tres dados | valores correspondentes |

## FilmNowTest - adicionar filme

| caso | entrada ou situacao | resultado esperado |
| --- | --- | --- |
| posicao vazia | Avatar, 2009, Disney+ na posicao 1 | filme cadastrado e mensagem `FILME ADICIONADO` |
| posicao ocupada | novo filme na posicao 1 | substitui o anterior e informa sucesso |
| duplicado em outra posicao | Avatar repetido na posicao 3 | nao cadastra e informa `FILME JA ADICIONADO` |
| mesmo nome e ano, local diferente | Avatar/2009/Popcornflix na posicao 3 | nao cadastra |
| limite superior | novo filme na posicao 100 | cadastra com sucesso |
| acima do limite | posicao 101 | `POSICAO INVALIDA` |
| abaixo do limite | posicao 0 | `POSICAO INVALIDA` |
| local vazio | local em branco | `FILME INVALIDO` e nao cadastra |
| ano vazio | ano em branco | cadastra com sucesso |
| nome vazio | nome em branco | `FILME INVALIDO` e nao cadastra |

## FilmNowTest - detalhar filme

| caso | entrada ou situacao | resultado esperado |
| --- | --- | --- |
| todos os dados | Avatar/2009/Disney+ na posicao 1 | nome, ano e local |
| sem ano | 20 dias em Mariupol, ano vazio, Cinema | nome e local, sem virgula sobrando |
| posicao vazia | posicao 100 sem filme | nenhuma mensagem adicional |
| abaixo do limite | posicao 0 | `POSICAO INVALIDA` |
| acima do limite | posicao 101 | `POSICAO INVALIDA` |
| filme na HotList | Avatar marcado como hot | `🔥 Avatar, 2009` e local |

## Outras funcionalidades de FilmNow

Os testes adicionais cobrem mostrar filmes cadastrados, atribuir/remover/exibir itens da HotList, unicidade de filme na HotList e consulta de filmes presentes ou ausentes.

## Execucao

Execute `runtests.bat` no Windows. O script compila o codigo de producao e os testes e executa a suite JUnit 5 usando `junit.jar`, baixado automaticamente se necessario. Os testes ficam em `test/filmnow/`.
