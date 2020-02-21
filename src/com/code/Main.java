package com.code;

import java.io.*;
import java.util.*;

public class Main {


    static Integer noLib=0;
    static Integer noBook=0;
    static Integer noDays=0;
    static List<Integer> score= new ArrayList<>();
    static List<Library> libraries= new ArrayList<>();
    static List<Library> selectedLib=new ArrayList<>();

    static List<Library> finalSelectedLib=new ArrayList<>();
    static int maxScore=0;
    public static String fileName= "read_on"; //"example"; "read_on"; "c_incunabula" "e_so_many_books"; "d_tough_choices"; "f_libraries_of_the_world"
    public static void main(String[] args) throws IOException {
        input();
        calculate();
        output();
    }
    public static void output() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName+"_"+maxScore+".txt"));
        out.println(finalSelectedLib.size());
        for(Library lib:finalSelectedLib){
           /* if(lib.scannedBooks.size()==0){
                continue;
            }*/
            out.println(lib.id+" "+lib.scannedBooks.size());
            for(Book bk:lib.scannedBooks){
               out.print(bk.id+" ");
            }
            out.println();
        }

        out.close();
    }

    public static void calculate(){
        //lib order 1
        Set<Book> globalScan= new HashSet<>();
        int globalScore=0;

        int remainLibDays=noDays;

        //System.out.println("****************"+libraries.size());

       // List<List<Library>> perm=  getAllPermuationLib(libraries ); e-g: 100*99*
        for(int m=0;m<2000;m++) {
            globalScan= new HashSet<>();
            selectedLib=new ArrayList<>();
            globalScore=0;
            remainLibDays=noDays;

            List<Library> shuffleLibs= new ArrayList<>(libraries);
            Collections.shuffle(shuffleLibs);
            for (Library lib : shuffleLibs) {

                lib.scannedBooks=new ArrayList<>();
                lib.score=0;

                if (remainLibDays > lib.signDay) {

                    remainLibDays = remainLibDays - lib.signDay;
                    int remainbookDays = remainLibDays;
                    int totalbookAcc = remainbookDays * lib.scanDay;
                    for (int i = 0, k = 0; i < totalbookAcc && k < lib.noOfBook; k++) {
                        Book book = lib.books.get(k);
                        if (globalScan.contains(book)) {
                            continue;
                        }
                        lib.scannedBooks.add(book);
                        lib.score += book.score;
                        globalScore += book.score;
                        globalScan.add(book);
                        i++;
                    }
                    if(lib.scannedBooks.size()!=0){
                        selectedLib.add(lib);
                    }

                } else {
                    break;
                }

                if(maxScore<globalScore){
                    maxScore=globalScore;
                    finalSelectedLib=selectedLib;

                }

            }

            //System.out.println(m+" Global score"+globalScore);
        }
        //System.out.println(globalScore);


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

                    score.add(scanner.nextInt());
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
                            book.score=score.get(book.id);
                            library.books.add(book);
                        }
                    Collections.sort(library.books);
                    libraries.add(library);
                    }


                }
             i++;
            }




        }

       //System.out.println();




}
