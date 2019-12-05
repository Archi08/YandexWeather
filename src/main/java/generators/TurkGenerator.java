package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import service.Coordinates;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TurkGenerator extends Generator<HashMap> {
    public Coordinates coordinates;

    public TurkGenerator() {
        super(HashMap.class);
        this.coordinates = new Coordinates();
    }

    @Override
    public HashMap generate(SourceOfRandomness random, GenerationStatus status) {
        HashMap<String, Double> bound = new HashMap<>();
        try {
            coordinates.getCorner("Turkey");
        } catch (IOException e) {
            e.printStackTrace();
        }
        double lonMin = coordinates.innerlon.get("lower");
        double lonMax = coordinates.innerlon.get("upper");
        double latMin = coordinates.innerlat.get("lower");
        double latMax = coordinates.innerlat.get("upper");

        double lon = new BigDecimal(ThreadLocalRandom.current().nextDouble(lonMin, lonMax + 1))
                .setScale(6, RoundingMode.HALF_UP).doubleValue();
        double lat = new BigDecimal(ThreadLocalRandom.current().nextDouble(latMin, latMax + 1))
                .setScale(6, RoundingMode.HALF_UP).doubleValue();

        bound.put("lon", lon);
        bound.put("lat", lat);
        return bound;
    }
}
