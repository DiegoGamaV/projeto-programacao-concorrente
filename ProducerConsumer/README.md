# Producer-Consumer

## Problema

Este problema consiste em sincronizar produtores e consumidores que escrevem e leem respectivamente de uma mesma
estrutura de dados (ou buffer). A ideia é implementar exclusão mútua na estrutura de dados, para que não ocorra 
inconsistência no buffer. 

## Restrições

As restrições são:
* Deve haver exclusão mútua no buffer;
* O produtor pode continuar produzindo enquanto há espaço no buffer, 
e se encontrar o buffer cheio, deve esperar até que haja espaço novamente.
* O consumidor pode continuar consumindo enquanto há elementos no buffer,
e se encontrar o buffer vazio, deve esperar até que haja elementos novamente.

## Solução

Modelamos a solução com uma classe Producer e outra Consumer, ambas possuindo um Buffer. O Producer gera inteiros não
negativos aleatórios de 0 a 9 e põe no Buffer; já o Consumer consome do Buffer quaisquer mensagens que encontrar. Estes
eventos são impressos na saída padrão, bem como o estado do Buffer. O Buffer foi pensado como uma lista de tamanho fixo, 
definido na construção.

Outro ponto adicional nesta solução, é que há a opção de criar Producers e Consumers que executam indefinidamente, e 
também a opção de cria-los com execução limitada a um determinado número de iterações, o que facilita para testes de 
comportamento.

Há também uma classe Exemples que contém 3 casos de teste, chamados em sequência, no método principal. Eles são:
1. Um Consumer e um Producer, cada um com 10 ciclos;
2. Um Consumer com 10 ciclos e dois Producers com 5 ciclos cada;
3. Dois Consumers com 5 ciclos cada e um Producer com 10 ciclos.