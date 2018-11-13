import java.util.ArrayList;
import java.util.HashSet;

public class demo {
    public static void main(String args[]){


        new PasringTable();
        //System.out.println(CFG.firstSet);



        //System.out.println(new DFA().getDFA().size());
//        Person a = new Person("02",20);
//        Person b = new Person("02",20);
//
//        System.out.println(a==b);
//        System.out.println(a.toString());
//        System.out.println(a.toString().equals(b.toString()));
//        System.out.println(a.toString().hashCode()==b.toString().hashCode());

    }

    static class Person{
        String name;
        int age;
        public  Person(String name,int age){
            this.age=age;
            this.name=name;
        }

        public String toString(){
            return name+age;
        }



    }


}
