package org.motion.motion_api.application.helpers;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class ListaObj<T> {
    @Getter
    private T[] vetor;
    private int nroElem;

    public ListaObj(int tamanho) {
        this.vetor = (T[]) new Object[tamanho];
        this.nroElem = 0;
    }

    public ListaObj(List<T> list) {
        this.vetor = (T[]) new Object[list.size()];
        this.nroElem = 0;
        for (int i = 0; i < list.size(); i++) {
            this.vetor[i] = list.get(i);
        }
    }

    public void adiciona(T element) {
        if (nroElem == vetor.length) {
            throw new IllegalStateException();
        }
        vetor[nroElem++] = element;
    }

    public void exibe() {
        for (int i = 0; i < nroElem; i++) {
            if (i != nroElem - 1) System.out.print(vetor[i] + ", ");
            else System.out.println(vetor[i]);
        }
    }

    public int getTamanho() {
        return nroElem;
    }

    public T getElemento(int i) {
        if (i >= nroElem || i < 0) return null;
        return vetor[i];
    }

    public int busca(T target) {
        for (int i = 0; i < nroElem; i++) {
            if (target.equals(vetor[i])) {
                return i;
            }
        }
        return -1;
    }

    public boolean removePeloIndice(int ind) {
        if (ind >= 0 && ind < nroElem) {
            for (int i = ind; i < nroElem - 1; i++) {
                vetor[i] = vetor[i + 1];
            }
            vetor[nroElem - 1] = null;
            nroElem--;
            return true;
        }
        return false;
    }

    public boolean removeElemento(T element) {
        int indElement = busca(element);
        if (indElement == -1) return false;
        return removePeloIndice(indElement);
    }

    public void substitui(T elementOld, T elementNew) {
        int i = busca(elementOld);
        if (i != -1) {
            vetor[i] = elementNew;
        }
    }

    public int contaOcorrencias(T element) {
        int qtdOcorrencias = 0;
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i] == element) qtdOcorrencias++;
        }
        return qtdOcorrencias;
    }

    public void adicionaInicio(T element) {
        if (nroElem == vetor.length) return;
        libera0();
        vetor[0] = element;
    }

    private void libera0() {
        for (int i = nroElem - 1; i >= 0; i--) {
            vetor[i + 1] = vetor[i];
        }
        vetor[0] = null;
        nroElem++;
    }

    public T[] toArray() {
        return Arrays.copyOf(vetor, nroElem);
    }

}