package com.tpapad.jpandas.service;

import com.tpapad.jpandas.service.interop.PandasMean;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.python.embedding.utils.GraalPyResources;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@Slf4j
public class PythonContextService {
    private static final Random R = new Random();
    private Context context;
    private PandasMean pandasMean;

    @PostConstruct
    void loadPythonContext() {
        log.info("Loading Python context");
        context = GraalPyResources.createContext();
        log.info("Importing Python modules");
        context.eval("python", "import pandas as pd");
        log.info("Loading function");
        pandasMean = context.eval("python",
                // language=Python
                """
                        def pandasMean(p):
                            df = pd.DataFrame({
                            "MetricValues": p,
                            }
                            )
                            return df['MetricValues'].mean().item()
                        pandasMean
                        """).as(PandasMean.class);
        log.info("Python context loaded");
    }

    public Double calculateMean(float[] values) {
        return pandasMean.mean(values);
    }

    public Double calculateMean(int sampleSize) {
        log.info("Elements: {}", sampleSize);
        final float[] data = new float[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            data[i] = R.nextFloat();
        }
        final long startTime = System.nanoTime();
        final Double retVal = calculateMean(data);
        final long endTime = System.nanoTime();
        final Duration duration = Duration.ofNanos(endTime - startTime);
        log.info("Calculated mean of {} values to be {} in {}", sampleSize, retVal, duration);

        return retVal;
    }

}
