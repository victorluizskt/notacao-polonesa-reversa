package model;

import model.exception.StackException;

public class PilhaDinamica<T> {

    private Celula topo;
    private long size;

    private class Celula {

        T item;
        Celula prox;

        public Celula(T item) {
            this.item = item;
        }
    }

    public void push(T item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException("Item nulo. Não é possível inserir.");
        } else {
            Celula novo = new Celula(item);
            novo.prox = topo;
            topo = novo;
            size++;
        }
    }

    public T pop() throws StackException {
        if (isEmpty()) {
            throw new StackException();
        } else {
            T item = topo.item;
            topo = topo.prox;
            size--;
            return item;
        }
    }

    public boolean isEmpty() {
        return (topo == null);
    }

    public long getNumElementos() {
        return size;
    }

    public T getTopo() {
        if (!isEmpty()) {
            return topo.item;
        }
        return null;
    }

    public void refresh() {
        topo = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Celula end = topo;
        while (end != null) {
            sb.append(end.item);
            end = end.prox;
        }
        return sb.toString();
    }

}