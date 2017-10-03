package com.pf.app;

import org.springframework.stereotype.Component;

import java.util.List;

// this class can be used to do all the write operations related to the file/s and console
@Component
public class WriteOutput {

    public void printOnConsole(int emptyLinesBefore, String text, int emptyLinesAfter){
        addNewLines(emptyLinesBefore);
        System.out.print(text);
        addNewLines(emptyLinesAfter);
    }

    public void printOnConsole(String text){
        System.out.print(text);
    }

    public void addNewLines(int n){
        for(int i =0; i<n; i++){
            System.out.println();
        }
    }

    public  void printTheList(List<Integer> pfList, int num){
        for(int i = 0; i<pfList.size(); i++){
            printOnConsole(pfList.get(i).toString());
            if (i< (pfList.size() -1)) {
                printOnConsole(" , ");
            }
        }
        addNewLines(2);
    }

}
