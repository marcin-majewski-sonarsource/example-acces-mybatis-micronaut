/*
 * Copyright 2017 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

import jakarta.inject.Inject;
import java.util.List;
import javax.validation.constraints.NotBlank;
import software.amazon.cloudwatchlogs.emf.config.Configuration;
import software.amazon.cloudwatchlogs.emf.environment.DefaultEnvironment;
import software.amazon.cloudwatchlogs.emf.environment.Environment;
import software.amazon.cloudwatchlogs.emf.exception.DimensionSetExceededException;
import software.amazon.cloudwatchlogs.emf.exception.InvalidDimensionException;
import software.amazon.cloudwatchlogs.emf.exception.InvalidMetricException;
import software.amazon.cloudwatchlogs.emf.logger.MetricsLogger;
import software.amazon.cloudwatchlogs.emf.model.DimensionSet;
import software.amazon.cloudwatchlogs.emf.model.Unit;

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@Controller("/")
@Validated
public class HelloController {

  @Inject
  TestMapper projectMapper;

  @Get(uri = "/hello/{name}", produces = MediaType.APPLICATION_JSON)
  public List<TestDto> hello(@NotBlank String name) {


    Configuration config = new Configuration();
    config.setServiceName("TestServiceName");
    config.setServiceType("Dev");
    Environment environemtn = new DefaultEnvironment(config);
    MetricsLogger metrics = new MetricsLogger(environemtn);

    try {
      metrics.putDimensions(DimensionSet.of( "Service", "Aggregator"));
      metrics.putMetric("ProcessingLatency", 100, Unit.MILLISECONDS);
      metrics.putMetric("ProcessingDuration", 200, Unit.COUNT);
    } catch (Exception e) {
      System.out.println(e);
    }

    metrics.putProperty("RequestId", "422b1569-16f6-4a03-b8f0-fe3fd9b100f8");
    metrics.flush();

    return projectMapper.selectAll();
  }
}
