# Cigarette Smokers

## Problema

No problema existem 3 fumantes que precisam de 3 recursos para montar e fumar
seus cigarros. Os recursos são: Papel, tabaco e fósforos.
Cada fumante possui um estoque infinito de um dos 3 recursos. Um fumante com papel,
outro com tabaco e o último com fósforos.

Além dos fumantes existe um agente com estoque infinito dos 3 recursos que
disponibiliza por vez 2 destes recursos para os fumantes de forma aleatória.

O problema consiste em apenas acordar as threads representando os fumantes que
precisam exatamente dos 2 recursos disponibilizados pelo agente.
Ex: O agente disponibiliza papel e fósforos, então apenas o fumante com estoque de
tabaco acorda e consome os recurso

## Restrições

As restrições são:
* O código do agente deve apenas indicar a disponibilidade de recursos;
* O código do agente não pode ser alterado nem ter conhecimento das outras threads executando;

## Solução

Threads auxiliares chamadas Pushers foram criadas para ser um intermédio entre
o agente e os fumantes. Cada Pusher tem a sí atribuído um recurso e o mesmo é 
acordado quando o recurso se torna disponível. Ao acordar o Pusher verifica se
outro Pusher já indicou a presença de um recurso. Se sim, ele acorda o fumante
com o recurso complementar ao recurso indicado como diponivel e o recurso atribuído
ao pusher (Ex: Pusher de papel e tabaco indicado como disponível -> fumante com 
fósforos é acordado). Se nenhum recurso foi marcado como disponível o Pusher 
indica que seu recurso está disponível.
O Fumante ao acordar consome os recursos e indica ao Agente que ele pode disponibilizar
mais uma dupla de recursos.