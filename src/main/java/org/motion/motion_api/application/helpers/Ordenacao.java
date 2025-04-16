package org.motion.motion_api.application.helpers;

import org.motion.motion_api.domain.entities.pitstop.Tarefa;

import java.time.LocalDate;

public class Ordenacao {

    public static void quickSort(ListaObj<Tarefa> lista) {
        quickSort(lista, 0, lista.getTamanho() - 1);
    }

    private static void quickSort(ListaObj<Tarefa> lista, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivot = particionar(lista, inicio, fim);
            quickSort(lista, inicio, indicePivot - 1);
            quickSort(lista, indicePivot + 1, fim);
        }
    }

    private static int particionar(ListaObj<Tarefa> lista, int inicio, int fim) {
        LocalDate pivot = lista.getElemento(fim).getDtDeadline();
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (lista.getElemento(j).getDtDeadline().isBefore(pivot)) {
                i++;
                trocar(lista, i, j);
            }
        }

        trocar(lista, i + 1, fim);
        return i + 1;
    }

    private static void trocar(ListaObj<Tarefa> lista, int i, int j) {
        Tarefa temp = lista.getElemento(i);
        lista.getVetor()[i] = lista.getElemento(j);
        lista.getVetor()[j] = temp;
    }
}
