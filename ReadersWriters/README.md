# Readers-Writers

## Problema

Este problema consiste em gerenciar threads fazendo leitura e escrita em um mesmo banco de dados.

## Restrições

As restrições são:
* Qualquer número de leitores pode estar na seção crítica simultaneamente;
* Os escritores devem ter acesso exclusivo à seção crítica;
* Quando um escritor chega, os leitores existentes podem terminar, mas nenhum leitor adicional pode entrar;

## Solução

Modelamos a solução com uma classe Reader e outra Writer, ambas possuindo um Database. O Writer escreve inteiros
aleatórios no Database; já o Reader lê do Database.

