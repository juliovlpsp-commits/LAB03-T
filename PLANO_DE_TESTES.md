# Plano de Testes - FilmNow

Plano de testes de unidade da Etapa 3 (verificação e validação) do Laboratório 3.
Framework: **JUnit 5**. Comando: `.\runtests.bat` (ou `./runtests.ps1`).

## Escopo

Testar o comportamento das classes `Filme` e `FilmNow`, sem depender da interface
de texto (`MainFilmNow`) nem do carregamento de csv (`LeitorFilmNow`).

## Casos de teste - FilmeTest

| id  | cenário | resultado esperado |
| --- | --- | --- |
| FT1 | dois filmes com mesmo nome e ano (locais diferentes) | são iguais |
| FT2 | dois filmes com nome diferente | são diferentes |
| FT3 | dois filmes com mesmo nome e ano diferente | são diferentes |
| FT4 | filme comparado com uma String | são diferentes |
| FT5 | filme comparado com null | são diferentes |
| FT6 | filme comparado consigo mesmo | são iguais |
| FT7 | toString de um filme | `nome, ano` |
| FT8 | getters de um filme | devolvem nome, ano e local |

## Casos de teste - FilmNowTest

| id  | cenário | resultado esperado |
| --- | --- | --- |
| FN1 | getFilmes de um sistema novo | array de 100 posições vazio |
| FN2 | getFilme em posição vazia | null |
| FN3 | cadastraFilme válido | retorna true e o filme fica na posição |
| FN4 | cadastraFilme repetido (mesma posição) | retorna false |
| FN5 | cadastraFilme repetido (outra posição) | retorna false e a posição continua vazia |
| FN6 | cadastraFilme em posição ocupada | sobrescreve o filme anterior |
| FN7 | adicionaHot em posição livre | filme fica na hotlist |
| FN8 | contemHot para filme fora da hotlist | false |
| FN9 | removeHot | filme deixa de estar na hotlist |
| FN10 | adicionaHot do mesmo filme em outra posição | aparece uma única vez |
| FN11 | adicionaHot em posição ocupada | o antigo deixa de ser hot |
| FN12 | listaHots com um filme | `1 - nome, ano` |
| FN13 | listaHots vazia | string vazia |

## Comandos

Usa o `junit-platform-console-standalone` (baixado como `junit.jar`):

```
# Windows
.\runtests.bat

# Linux/Mac
./runtests.ps1
```

O `junit.jar` é baixado automaticamente na primeira execução e fica fora do git
(`.gitignore`). Os testes ficam em `test/filmnow/`.
