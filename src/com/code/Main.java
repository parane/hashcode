package com.code;

import java.io.*;
import java.util.*;

public class Main {


    static Integer noLib=0;
    static Integer noBook=0;
    static Integer noDays=0;
    static List<Integer> scores= new ArrayList<>();
    static List<Library> libraries= new ArrayList<>();
    static List<Library> selectedLib=new ArrayList<>();

    public static long OPTIMISE_PARAM=10000000L;
            ;
    static List<Library> finalSelectedLib=new ArrayList<>();
    static int maxScore=0;
    public static String fileName= "c_incunabula"; //"a_example"; "b_read_on"; "c_incunabula" "e_so_many_books"; "d_tough_choices" ,,,; "f_libraries_of_the_world"
    public static void main(String[] args) throws IOException, CloneNotSupportedException {

        if(args.length>0&& args[0]!=null){
            fileName=args[0];
        }
        if(args.length>1&&args[1]!=null){
            OPTIMISE_PARAM=Long.parseLong(args[1]);
        }
        input();
        calculate();
        output();
    }
    public static void output() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName+"_"+maxScore+".txt"));
        out.println(finalSelectedLib.size());
        Set<Integer> sets=new HashSet<>();
        for(Library lib:finalSelectedLib){
            out.println(lib.id+" "+lib.scannedBooks.size());
            for(Book bk:lib.scannedBooks){
                sets.add(bk.id);
               out.print(bk.id+" ");
            }

            out.println();
        }
        System.out.println(maxScore);
        out.close();
    }

    public static void calculate() throws CloneNotSupportedException {

        int globalLibScore=0;
        int remainLibDays=noDays;

        //Todo : need to optimise library order
        for(long m=0;m< OPTIMISE_PARAM;m++) {
            selectedLib=new ArrayList<>();
            globalLibScore=0;
            remainLibDays=noDays;


            if(m==0){
                Collections.sort(libraries);
            }else{
                Collections.shuffle(libraries);
            }

            Set<Integer> scannedReady=new HashSet<>();

            for (int z=0;z<libraries.size();z++) {
                Library lib= libraries.get(z);


                lib.scannedBooks=new ArrayList<>();
                lib.score=0;

                if (remainLibDays > lib.signDay) {

                    remainLibDays = remainLibDays - lib.signDay;
                    int totalbookAcc = remainLibDays * lib.scanDay;
                    for (int i = 0, k = 0; i < totalbookAcc && k < lib.noOfBook; k++) {
                        Book book = lib.books.get(k);
                        if (scannedReady.contains(book.id)) {
                            continue;
                        }else{
                            lib.scannedBooks.add(book);
                            lib.score += book.score;
                            scannedReady.add(book.id);
                        }

                        i++;
                    }
                    if(lib.scannedBooks.size()>0){
                        selectedLib.add(lib);
                    }else {
                        remainLibDays=remainLibDays+lib.signDay;
                    }

                } else {
                    break;
                }

                globalLibScore+=lib.score;


            }



            if(maxScore<globalLibScore){
                maxScore=globalLibScore;
                finalSelectedLib=deepCopy(selectedLib);


            }

        }


    }

    public static List<Library> deepCopy(List<Library> list) throws CloneNotSupportedException {
        List<Library> clone = new ArrayList<Library>(list.size());
        for (Library item : list) clone.add((Library) item.clone());
        return clone;
    }



    public static List<List<Library>> getAllPermuationLib(List<Library> original ){
        if (original.isEmpty()) {
            List<List<Library>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        Library firstElement = original.remove(0);
        List<List<Library>> returnValue = new ArrayList<>();
        List<List<Library>> permutations = getAllPermuationLib(original);
        for (List<Library> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Library> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
    public static void input() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(fileName+".txt"));
        int i = 0;
        while(scanner.hasNextInt())
        {
            if(i==0){
                noBook=scanner.nextInt();
            }else if(i==1){
                noLib=scanner.nextInt();
            }else if(i==2){
                noDays=scanner.nextInt();
            }else{
                for(int j=0;j<noBook;j++){

                    scores.add(scanner.nextInt());
                }

                for(int j=0;j<noLib;j++){

                    Library library=new Library();
                        library.id=j;
                        library.noOfBook=scanner.nextInt();
                        library.signDay =scanner.nextInt();
                        library.scanDay =scanner.nextInt();
                        for(int k=0; k<library.noOfBook;k++){
                            Book book=new Book();
                            book.id=scanner.nextInt();
                            book.score=scores.get(book.id);
                            library.bookTotalscore+=book.score;
                            library.books.add(book);
                        }
                    Collections.sort(library.books);
                    libraries.add(library);
                    }


                }
             i++;
            }




        }





}
