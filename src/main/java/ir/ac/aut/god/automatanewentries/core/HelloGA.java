package ir.ac.aut.god.automatanewentries.core;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class HelloGA {
    // 2.) Definition of the fitness function.
    private static int eval(Genotype<BitGene> gt) {
        int i = -gt.getChromosome()
                .as(BitChromosome.class)
                .bitCount();
        System.out.println(i);

        BitGene gene = gt.getChromosome().getGene();

        return (int) Math.sin(((double)i));
    }
 
    public static void main(String[] args) {
        // 1.) Define the genotype (factory) suitable
        //     for the problem.
        Factory<Genotype<BitGene>> gtf =
            Genotype.of(BitChromosome.of(5, 0.5));
 
        // 3.) Create the execution environment.
        Engine<BitGene, Integer> engine = Engine
            .builder(HelloGA::eval, gtf)
            .build();
 
        // 4.) Start the execution (evolution) and
        //     collect the result.
        Genotype<BitGene> result = engine.stream()
            .limit(1000)
            .collect(EvolutionResult.toBestGenotype());
 
        System.out.println("Hello World:\n" + result);
    }
}