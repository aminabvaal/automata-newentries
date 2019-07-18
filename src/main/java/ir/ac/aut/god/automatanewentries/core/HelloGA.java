package ir.ac.aut.god.automatanewentries.core;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.function.Consumer;

public class HelloGA {
    // 2.) Definition of the fitness function.
    private static double eval(Genotype<DoubleGene> gt) {
        Chromosome<DoubleGene> chromosome = gt.getChromosome();
        double s = 0;
        for (DoubleGene doubleGene : chromosome) {
            Double allele = doubleGene.getAllele();
            s = s + Math.pow((allele - 2.5) ,2);
        }
        System.out.println(s);
        return -s;
    }

    public static void main(String[] args) {
        // 1.) Define the genotype (factory) suitable
        //     for the problem.
//        Factory<Genotype<BitGene>> gtf =
//                Genotype.of(BitChromosome.of(5, 0.5));


        Genotype<DoubleGene> of = Genotype.of(DoubleChromosome.of(DoubleGene.of(0, 5), DoubleGene.of(0, 5)));


//
//        // 3.) Create the execution environment.
//        Engine<BitGene, Double> engine = Engine
//            .builder(HelloGA::eval, gtf)
//            .build();

        // 3.) Create the execution environment.
        Engine<DoubleGene, Double> engine = Engine
                .builder(HelloGA::eval, of)
                .build();

        // 4.) Start the execution (evolution) and
        //     collect the result.
        Genotype<DoubleGene> result = engine.stream()
                .limit(100)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println("Hello World:\n" + result);
    }
}