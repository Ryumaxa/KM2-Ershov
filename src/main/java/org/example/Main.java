package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        TripletDeque<Object> deq = new TripletDeque<>();
        //ТОЧТОНАДО


        //ВЫВОД ВСЕЙ ОЧЕРЕДИ
        int print_num = 1;
        while (deq.first != null) {
            System.out.println(print_num + ") " + deq.first);
            deq.first = deq.first.next;
            print_num++;
        }

    }
}