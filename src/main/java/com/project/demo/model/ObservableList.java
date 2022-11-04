package com.project.demo.model;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ObservableList<Utente> implements javafx.collections.ObservableList {
    @Override
    public void addListener(ListChangeListener listChangeListener) {

    }

    @Override
    public void removeListener(ListChangeListener listChangeListener) {

    }

    @Override
    public boolean addAll(Object[] objects) {
        return false;
    }

    @Override
    public boolean setAll(Object[] objects) {
        return false;
    }

    @Override
    public boolean setAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Object[] objects) {
        return false;
    }

    @Override
    public boolean retainAll(Object[] objects) {
        return false;
    }

    @Override
    public void remove(int i, int i1) {

    }

    @Override
    public FilteredList filtered(Predicate predicate) {
        return javafx.collections.ObservableList.super.filtered(predicate);
    }

    @Override
    public SortedList sorted(Comparator comparator) {
        return javafx.collections.ObservableList.super.sorted(comparator);
    }

    @Override
    public SortedList sorted() {
        return javafx.collections.ObservableList.super.sorted();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    /*
    @Override
    public void forEach(Consumer action) {
        ObservableList.super.forEach(action);
    }*/

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /*
    @Override
    public Object[] toArray(IntFunction generator) {
        return ObservableList.super.toArray(generator);
    }*/

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    /*
    @Override
    public boolean removeIf(Predicate filter) {
        return ObservableList.super.removeIf(filter);
    }*/

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    /*
    @Override
    public void replaceAll(UnaryOperator operator) {
        ObservableList.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator c) {
        ObservableList.super.sort(c);
    }*/

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    /*
    @Override
    public Spliterator spliterator() {
        return ObservableList.super.spliterator();
    }

    @Override
    public Stream stream() {
        return ObservableList.super.stream();
    }

    @Override
    public Stream parallelStream() {
        return ObservableList.super.parallelStream();
    }*/

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
