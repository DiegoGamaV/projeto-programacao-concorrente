# No Starve Mutex

## Problema

Este problema consiste em permitir que todas as threads sejam executadas resolvendo o problema de starvation do mutex.

## Restrições

As restrições são:
* Se houver apenas uma thread pronta para ser executada, o controlador deve deixá-la ser executada.
* Se uma thread está pronta para ser executada, o tempo que ela espera até que seja executada é limitado.
* se houver threads esperando por um semáforo quando a thread executa o sinal, então uma das threads em espera deve ser ativada.

## Solução

Modelamos a solução com uma classe ThreadExample, que possui um MutexRooms. O ThreadExample solicita o uso 
da região critica; já o MutexRooms vai controlar o uso entre as threads.

