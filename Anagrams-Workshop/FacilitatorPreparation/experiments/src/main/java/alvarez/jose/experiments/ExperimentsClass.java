package alvarez.jose.experiments;

import java.util.HashSet;

public class ExperimentsClass {

    public static void main(String[] args) {

        HashSet<String> aHashSet = new HashSet<>();

        String hello_world = "Hello World!";
        aHashSet.add(hello_world);
        String spots = "spots";
        aHashSet.add(spots);

        System.out.println(aHashSet.contains(hello_world));
        System.out.println(aHashSet.contains("spots"));




    }

}
