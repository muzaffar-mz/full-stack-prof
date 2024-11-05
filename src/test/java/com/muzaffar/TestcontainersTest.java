package com.muzaffar;


import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 05/11/24.
 */

@Testcontainers
public class TestcontainersTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresDB() {

        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();

    }

}
